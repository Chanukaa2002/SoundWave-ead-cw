package SoundWave.User;
import SoundWave.App.UserUI.FilePath;
import SoundWave.DBConnection.DBConnection;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.InputStream;
import java.util.ArrayList;

public class Artist extends User{
    private Connection conn;

    public Artist(){
        try{
            conn= DBConnection.getConnection();
        }
        catch(SQLException e){
            System.out.println("Artist class constructor Error: "+e);
        }
    }
    //methods
    public boolean isUser(String userName) throws SQLException {
    boolean status=false;
    try{
        String sql = "Select * from artist where UserName=?";
        PreparedStatement selectStatement = conn.prepareStatement(sql);
        selectStatement.setString(1,userName);

        ResultSet result = selectStatement.executeQuery();
        if(result.next()){
            status=true;
        }
        result.close();
    }
    catch(Exception e){
        System.out.println("Artist Class isUser method Error: "+e);
    }
    finally {
        conn.close();
    }
    return  status;
}
    public String getId(String userName)throws  SQLException{
        String artistId="";
        try{
            String sql = "Select ArtistId from artist where UserName=?";
            PreparedStatement selectStatement = conn.prepareStatement(sql);
            selectStatement.setString(1,userName);

            ResultSet result = selectStatement.executeQuery();
            if(result.next()){
                artistId= result.getString(1);
            }
            result.close();
        }
        catch(Exception e){
            System.out.println("Artist class getId method Error: "+e);
        }
        finally {
            conn.close();
        }
        return artistId;
    }
    public boolean uploadSong(String title,float duration,InputStream coverImgStream,String artistId,InputStream songStream,String song,String imgFileExtension) throws SQLException {
        boolean status=false;
        try{
            conn.setAutoCommit(false);

            String songId;
            String sql1 = "Select Max(SongId) from song";
            String sql2 = "Insert into song (SongId,Title,Song,Duration,CoverImg,ArtistId) values(?,?,?,?,?,?)";

            String coverImg;
            String songName;

            PreparedStatement selectStatement = conn.prepareStatement(sql1);
            ResultSet result = selectStatement.executeQuery();

            if (result.next()) {
                String maxId = result.getString(1);
                if (maxId != null) {
                    int numericPart = Integer.parseInt(maxId.substring(1));
                    numericPart++;
                    songId = String.format("S%03d", numericPart);
                    coverImg = "Song-CoverImg-"+songId+"."+imgFileExtension;
                    songName = songId+"-"+song;
                } else {
                    songId = "S001";
                    coverImg = "Song-CoverImg-S001"+"."+imgFileExtension;
                    songName = "S001-"+song;
                }

                PreparedStatement insertStatement = conn.prepareStatement(sql2);
                insertStatement.setString(1,songId);
                insertStatement.setString(2,title);
                insertStatement.setString(3,songName);
                insertStatement.setFloat(4,duration);
                insertStatement.setString(5,coverImg);
                insertStatement.setString(6,artistId);

                int rowsAffected= insertStatement.executeUpdate();
                if(rowsAffected>0){
                    String coverImgPath = FilePath.getSongCoverImgPath() + coverImg;
                    boolean isDpSaved = saveFile(coverImgStream,coverImgPath);

                    String songFilePath = FilePath.getSongPath() +songName;
                    boolean isSongSaved = saveSong(songStream,songFilePath);
                    if(isDpSaved && isSongSaved){
                        conn.commit();
                        status=true;
                    }else {
                        conn.rollback();
                    }
                }
            }

        }catch(Exception e){
            conn.rollback();
            System.out.println("Artist class upload song method Error: " +e);
        }finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                System.out.println("Failed to restore auto commit in upload Song: " + ex);
            }
        }
        return status;
    }//checked
    public boolean removeSong(String songId) throws SQLException {
        boolean status=false;
        try {
            String sqlSelect = "SELECT CoverImg, Song FROM song WHERE SongId=?";
            String sqlDeleteFeedback = "DELETE FROM feedback WHERE SongId=?";
            String sqlDeletePlaListSong = "DELETE FROM playlist_song WHERE SongId=? and PlayListId=?";
            String sqlDeleteSong = "DELETE FROM song WHERE SongId=?";
            conn.setAutoCommit(false);

            PreparedStatement selectStatement = conn.prepareStatement(sqlSelect);
            selectStatement.setString(1, songId);
            ResultSet result = selectStatement.executeQuery();

            if (result.next()) {
                String oldCoverImg = result.getString("CoverImg");
                String songName = result.getString("Song");

//                String imgFileExtension="";
//                int dotIndex = oldCoverImg.lastIndexOf('.');
//                if (dotIndex > 0 && dotIndex < oldCoverImg.length() - 1) {
//                    imgFileExtension = oldCoverImg.substring(dotIndex + 1).toLowerCase();
//                }
                String oldDpFilePath = FilePath.getSongCoverImgPath() + oldCoverImg;
                String oldSongFilePath = FilePath.getSongPath() + songName;

                File image = new File(oldDpFilePath);
                File song = new File(oldSongFilePath);

                if (image.exists() && song.exists()) {

                    PreparedStatement removeFeedbackStatement = conn.prepareStatement(sqlDeleteFeedback);
                    PreparedStatement removeFeedbackSongStatement = conn.prepareStatement(sqlDeletePlaListSong);
                    PreparedStatement removeSongStatement = conn.prepareStatement(sqlDeleteSong);

                    removeFeedbackStatement.setString(1, songId);
                    removeFeedbackSongStatement.setString(1, songId);
                    removeSongStatement.setString(1, songId);

                    removeFeedbackStatement.executeUpdate();
                    removeFeedbackSongStatement.executeUpdate();
                    int rowsAffected = removeSongStatement.executeUpdate();

                    if (rowsAffected > 0 ) {
                        image.delete();
                        song.delete();
                        conn.commit();
                        status=true;
                    } else {
                        conn.rollback();
                    }

                } else {
                    conn.rollback();
                }
            }

        } catch (Exception e) {
            conn.rollback();
            System.out.println("Artist class remove song method Error: " + e);
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }

        return status;
    }//checked
    public  boolean register(String userName, String password, String name, String email, String contactNo, InputStream dpInputStream,String fileExtension) throws SQLException {
        try {
            conn.setAutoCommit(false);
            String sql1 = "Select * from user where UserName=?";
            String sql2 = "Select Max(ArtistId) from artist";
            String sql3 = "Insert into user (UserName,Password,Name,Email,ContactNo,Dp) values(?,?,?,?,?,?)";
            String sql4 = "Insert into artist(ArtistId,UserName) values(?,?)";

            String ArtistId;
            String dp;
            PreparedStatement selectStatement = conn.prepareStatement(sql1);
            selectStatement.setString(1,userName);
            ResultSet result1 = selectStatement.executeQuery();

            if(!(result1.next())){
                PreparedStatement selectMacIDStatement = conn.prepareStatement(sql2);
                ResultSet result2 = selectMacIDStatement.executeQuery();

                if(result2.next()){
                    String maxId = result2.getString(1);
                    if(maxId!=null){
                        int numericPart = Integer.parseInt(maxId.substring(1));
                        numericPart++;
                        ArtistId = String.format("A%03d",numericPart);
                        dp = "DP-"+ArtistId;
                    }
                    else{
                        ArtistId = "A001";
                        dp = "DP-A001";
                    }
                    PreparedStatement insertStatementOne = conn.prepareStatement(sql3);
                    insertStatementOne.setString(1,userName);
                    insertStatementOne.setString(2,password);
                    insertStatementOne.setString(3,name);
                    insertStatementOne.setString(4,email);
                    insertStatementOne.setString(5,contactNo);
                    insertStatementOne.setString(6,dp);

                    int rowsAffected1 =insertStatementOne.executeUpdate();

                    PreparedStatement insertStatementTwo = conn.prepareStatement(sql4);
                    insertStatementTwo.setString(1,ArtistId);
                    insertStatementTwo.setString(2,userName);

                    int rowsAffected2 = insertStatementTwo.executeUpdate();
                    if(rowsAffected1>0 && rowsAffected2>0){

                        String dpFilePath = FilePath.getDpImgPath() + dp+"."+fileExtension;
                        boolean isDpSaved = saveFile(dpInputStream,dpFilePath);
                        if(isDpSaved){
                            conn.commit();
                            isAuthenticated=true;
                        }else {
                            conn.rollback();
                        }
                    }
                }
            }
            else{
                conn.rollback();
            }
        }
        catch(Exception e){
            conn.rollback();
            System.out.println("Artist class register method Error: " +e);
        }finally {
            conn.setAutoCommit(true);
            conn.close();
        }
        return isAuthenticated;
    }//Checked
    private boolean saveSong(InputStream inputStream, String filePath) {
        try {
            Files.copy(inputStream, Paths.get(filePath));
            return true;
        } catch (Exception e) {
            System.out.println("artist class saveSong method Error: " + e);
            return false;
        }
    }//checked
    public ArrayList<String[]> viewMyAllSong(String artistId){
        ArrayList<String[]> songList = new ArrayList<>();
        try{
            String sql = "select * from song where ArtistId=?";
            PreparedStatement selectStatement = conn.prepareStatement(sql);
            selectStatement.setString(1,artistId);
            ResultSet result = selectStatement.executeQuery();

            while(result.next()){
                String[] songDetails = new String[6];
                songDetails[0] = result.getString("SongId");
                songDetails[1] = result.getString("Title");
                songDetails[2] = result.getString("Song");
                songDetails[3] = result.getString("Duration");
                songDetails[4] = result.getString("CoverImg");

                songList.add(songDetails);
            }
        }catch (Exception e){
            System.out.println("Artist view All Playlist Error: "+e);
        }
        return songList;
    }
}
