package SoundWave.User;

import SoundWave.DBConnection.DBConnection;
import SoundWave.Music.Song;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.io.InputStream;
import java.util.Scanner;

public class Listener extends User{

    private String listenerId;
    private Connection conn;
    public Listener(){
        try{
            conn= DBConnection.getConnection();
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }

    public String getListenerId() {
        return listenerId;
    }
    public void setListenerId(String listenerId) {
        this.listenerId = listenerId;
    }

    @Override
    public void viewProfile(String userName) throws SQLException {
        try{
            String sql = "Select u.UserName,u.Password,u.Name,u.Email,u.ContactNo,u.Dp,l.ListenerId From user u Inner Join artist a On u.UserId = a.UserId Where u.UserName = ?;";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,userName);
            ResultSet result =statement.executeQuery();
            if (result.next()) {
                setListenerId(result.getString("ListenerId"));
                setUserName(result.getString("UserName"));
                setName(result.getString("Name"));
                setPassword(result.getString("Password"));
                setDP(result.getString("Dp"));
                setContactNo(result.getString("ContactNo"));
                setEmail(result.getString("Email"));
            }

            result.close();
        }catch (Exception e){
            System.out.println("Error: "+e);
        }finally{
            conn.close();
        }
    }

    //methods
    public boolean isUser(String userName) throws SQLException {
        boolean status=false;
        try{
            String sql = "Select * from listener where UserName=?";
            PreparedStatement selectStatement = conn.prepareStatement(sql);
            selectStatement.setString(1,userName);

            ResultSet result = selectStatement.executeQuery();
            if(result.next()){
                status=true;
            }
            result.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally {
            conn.close();
        }
        return  status;
    }
    public String getId(String userName)throws  SQLException{
        String artistId="";
        try{
            String sql = "Select ListenerId from listener where UserName=?";
            PreparedStatement selectStatement = conn.prepareStatement(sql);
            selectStatement.setString(1,userName);

            ResultSet result = selectStatement.executeQuery();
            if(result.next()){
                artistId= result.getString(1);
            }
            result.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally {
            conn.close();
        }
        return artistId;
    }
    public boolean createPlayList(String name,InputStream inputCoverImg,String listenerId,String imgExtension) throws SQLException {
        boolean status = false;
        try{
            conn.setAutoCommit(false);
            String playlistId;
            String coverImg;

            String sql1 ="Select Max(PlayListId) from playlist";
            String sql2 = "Insert into playlist (PlayListId,Name,CoverImg,ListenerId) values(?,?,?,?)";
                //auto increment id
            PreparedStatement selectStatement = conn.prepareStatement(sql1);
            ResultSet result = selectStatement.executeQuery();

            if (result.next()) {
                String maxId = result.getString(1);

                if (maxId != null) {
                    int numaricPart = Integer.parseInt(maxId.substring(1));
                    numaricPart++;
                    playlistId = String.format("P%03d", numaricPart);
                    coverImg = "PlayList-CoverImg-"+playlistId;

                }
                else
                {
                    playlistId = "P001";
                    coverImg = "PlayList-CoverImg-P001";
                }
                PreparedStatement inputStatement = conn.prepareStatement(sql2);
                inputStatement.setString(1,playlistId);
                inputStatement.setString(2,name);
                inputStatement.setString(3,coverImg);
                inputStatement.setString(4,listenerId);
                int rowsAffected= inputStatement.executeUpdate();

                if(rowsAffected>0){
                    //upload image into local storage
                    String coverImgPath = "C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/Images/PlayListCoverImage/" + coverImg+"."+imgExtension;
                    boolean isDpSaved = saveFile(inputCoverImg,coverImgPath);
                    if(isDpSaved){
                        conn.commit();
                        status=true;
                    }else {
                        conn.rollback();
                        System.out.println("Failed to save cover Image.");
                    }
                }
            }
            result.close();
        }catch(Exception e){
            conn.rollback();
            System.out.println("Error in Listener Create Playlist: "+e);
        }finally {
            try {
                conn.setAutoCommit(true);  // Restore auto-commit mode
            } catch (SQLException ex) {
                System.out.println("Failed to restore auto-commit: " + ex.getMessage());
            }
        }
        return status;
    }//checked
    public boolean deletePlayList(String playListId) throws SQLException {
        boolean status = false;
        try {
            String sqlSelect = "SELECT CoverImg FROM playlist WHERE PlayListId=?";
            String sqlDeletePlayList = "DELETE FROM playList WHERE PlayListId=?;";
            String sqlDeletePlayListSong = "DELETE FROM playlist_song WHERE PlayListId=?; ";

            conn.setAutoCommit(false);
            PreparedStatement selectStatement = conn.prepareStatement(sqlSelect);
            selectStatement.setString(1, playListId);
            ResultSet result = selectStatement.executeQuery();

            if (result.next()) {
                String oldCoverImg = result.getString("CoverImg");
                String oldDpFilePath = "C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/Images/PlayListCoverImage/" + oldCoverImg;
                File image = new File(oldDpFilePath);
                if (image.exists()) {
                    PreparedStatement removePlaListSongStatement = conn.prepareStatement(sqlDeletePlayListSong);
                    PreparedStatement removePlaListStatement = conn.prepareStatement(sqlDeletePlayList);
                    removePlaListSongStatement.setString(1, playListId);
                    removePlaListStatement.setString(1, playListId);
                    int rowsAffected1 = removePlaListSongStatement.executeUpdate();
                    int rowsAffected2 = removePlaListStatement.executeUpdate();

                    if (rowsAffected1 > 0 && rowsAffected2>0) {
                        image.delete();
                        conn.commit();
                        System.out.println("PlayList removed successfully!");
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
            System.out.println("Error: " + e);
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }

        return status;
    }
    public boolean addSongToPlayList(String playlistId, String songId) throws SQLException {
        boolean status = false;
        try{
            String sql = "insert into playlist_song (PlayListId,SongId) values(?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,playlistId);
            statement.setString(2,songId);

            int rowAffected = statement.executeUpdate();
            if(rowAffected>0){
                status=true;
            }
        }catch(Exception e){
            System.out.println("Error: "+e);
        }
        finally{
            conn.close();
        }
        return status;
    }//checked
    public boolean removeSongFromPlayList(String playlistId, String songId) throws SQLException {
        boolean status=false;
        try{
            String sql = "delete from playlist_song where PlayListId=? and SongId=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,playlistId);
            statement.setString(2,songId);

            int rowAffected = statement.executeUpdate();
            if(rowAffected>0){
                status=true;
            }
        }catch(Exception e){
            System.out.println("Error: "+e);
        }
        finally{
            conn.close();
        }
        return status;
    }//checked
    public void controlSong(String songPath) {
        Song s = new Song();
        Scanner scn = new Scanner(System.in);
        String command;

        do {
            command = scn.nextLine();
            switch (command) {
                case "start":
                    s.start(songPath); // Play the song from the beginning
                    break;
                case "p":
                    s.pause();
                    break;
                case "resume":
                    s.resume();
                    break;
                case "stop":
                    s.stop();
                    break;
                default:
                    System.out.println("Unknown command. Please use 'start', 'p', 'resume', or 'stop'.");
            }
        } while (!command.equals("stop"));

        scn.close();
    }//-------------------work on this------------
    public boolean likeSong(String songId, String listenerId) throws SQLException {
        boolean status = false;
        try{
            String sql1 = "Select Max(FeedbackId) from feedback";
            String sql2 = "Insert into feedback(FeedbackId,Likes,SongId,ListenerId) values(?,?,?,?)";
            String sql3 = "Select FeedbackId from feedback where SongId=? and ListenerId=?";
            String feedBackId;
            PreparedStatement checkStatement = conn.prepareStatement(sql3);
            checkStatement.setString(1,songId);
            checkStatement.setString(2,listenerId);
            ResultSet res = checkStatement.executeQuery();
            if(res.next()){
                unlikeSong(songId,listenerId);
            }
            else{
                PreparedStatement selectMaxIDStatement = conn.prepareStatement(sql1);
                ResultSet result = selectMaxIDStatement.executeQuery();
                if(result.next()){
                    String maxId = result.getString(1);
                    if(maxId!=null){
                        int numaricPart = Integer.parseInt(maxId.substring(1));
                        numaricPart++;
                        feedBackId = String.format("F%03d",numaricPart);
                    }
                    else{
                        feedBackId = "F001";
                    }
                    PreparedStatement insertStatement = conn.prepareStatement(sql2);
                    insertStatement.setString(1,feedBackId);
                    insertStatement.setInt(2,1);
                    insertStatement.setString(3,songId);
                    insertStatement.setString(4,listenerId);

                    int rowsAffected = insertStatement.executeUpdate();
                    if(rowsAffected>0){
                        status = true;
                    }
                }
                result.close();
            }

        }
        catch(Exception e){

            System.out.println("Error: "+e);
        }
        finally{
            conn.close();
        }
        return status;
    }//checked
    public void unlikeSong(String songId, String listenerId) throws SQLException {
        try{
            String sql = "Delete from feedback where ListenerId=? and SongId = ?";
            PreparedStatement deleteStatement = conn.prepareStatement(sql);
            deleteStatement.setString(1,listenerId);
            deleteStatement.setString(2,songId);
            deleteStatement.executeUpdate();
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }
        finally{
            conn.close();
        }
    }//checked
    public ArrayList<String[]> viewAllPlayList(String listenerId){
        ArrayList<String[]> playLists = new ArrayList<>();
        try{
            String sql = "select * from playlist where ListenerId=?";
            PreparedStatement selectStatement = conn.prepareStatement(sql);
            selectStatement.setString(1,listenerId);
            ResultSet result = selectStatement.executeQuery();

            while(result.next()){
                String[] songDetails = new String[6];
                songDetails[0] = result.getString("PlayListId");
                songDetails[1] = result.getString("Name");
                songDetails[2] = result.getString("CoverImg");
                songDetails[3] = result.getString("ListenerId");

                playLists.add(songDetails);
            }
        }catch (Exception e){
            System.out.println("Listener view All Playlist Error: "+e);
        }
        return playLists;
    }
    public ArrayList<String[]> exploreSong(){
        ArrayList<String[]> songList = new ArrayList<>();
        try{
            String sql = "SELECT s.SongId,s.Title, s.Song, s.Duration, s.CoverImg,s.ArtistId,u.Name " +
                    "FROM song s " +
                    "INNER JOIN artist a ON s.ArtistId = a.ArtistId " +
                    "INNER JOIN user u ON a.UserName = u.UserName";
            PreparedStatement selectStatement = conn.prepareStatement(sql);
            ResultSet result = selectStatement.executeQuery();

            while(result.next()){
                String[] songDetails = new String[6];
                songDetails[0] = result.getString("SongId");
                songDetails[1] = result.getString("Title");
                songDetails[2] = result.getString("Song");
                songDetails[3] = result.getString("Duration");
                songDetails[4] = result.getString("CoverImg");
                songDetails[5] = result.getString("Name");

                songList.add(songDetails);
            }
        }
        catch (Exception e){
            System.out.println("Error:"+e);
        }
        return songList;
    }//checked-
    public ArrayList<String[]> searchSong(String title){
        ArrayList<String[]> searchSong = new ArrayList<>();
        try{
            String sql = "Select SongId,Title from song where Like ?";
            PreparedStatement selectStatement = conn.prepareStatement(sql);
            ResultSet result = selectStatement.executeQuery();

            while(result.next()){
                String[] songDetails = new String[2];
                songDetails[0] = result.getString("SongId");
                songDetails[1] = result.getString("Title");

                searchSong.add(songDetails);
            }
        }
        catch (Exception e){
            System.out.println("Error:"+e);
        }
        return searchSong;

    }//--------------------Not checked------------------------
    public  boolean register(String userName, String password, String name, String email, String contactNo, InputStream dpInputStream,String fileExtension) throws SQLException {
        try {
            String sql1 = "Select * from user where UserName=?";
            String sql2 = "Select Max(ListenerId) from listener";
            String sql3 = "Insert into user (UserName,Password,Name,Email,ContactNo,Dp) values(?,?,?,?,?,?)";
            String sql4 = "Insert into listener(ListenerId,UserName) values(?,?)";


            //initialize Listener Id for inset sql
            String ListenerId;
            String dp;


            //sql command-1
            PreparedStatement selectStatement = conn.prepareStatement(sql1);
            selectStatement.setString(1,userName);
            ResultSet result1 = selectStatement.executeQuery();

            if(!(result1.next())){

                    //auto increment id
                //sql command-2
                PreparedStatement selectMaxIDStatement = conn.prepareStatement(sql2);
                ResultSet result2 = selectMaxIDStatement.executeQuery();

                if(result2.next()){
                    String maxId = result2.getString(1);

                    if(maxId!=null){
                        int numaricPart = Integer.parseInt(maxId.substring(1));
                        numaricPart++;
                        ListenerId = String.format("L%03d",numaricPart);
                        dp = "DP-"+ListenerId;
                    }
                    else{
                        ListenerId = "L001";
                        dp = "DP-L001";
                    }
                    //insert sql command
                    PreparedStatement insertStatementOne = conn.prepareStatement(sql3);
                    insertStatementOne.setString(1,userName);
                    insertStatementOne.setString(2,password);
                    insertStatementOne.setString(3,name);
                    insertStatementOne.setString(4,email);
                    insertStatementOne.setString(5,contactNo);
                    System.out.println(dp);
                    insertStatementOne.setString(6,dp);

                    int rowsAffected1 =insertStatementOne.executeUpdate();

                    PreparedStatement insertStatementTwo = conn.prepareStatement(sql4);
                    insertStatementTwo.setString(1,ListenerId);
                    insertStatementTwo.setString(2,userName);

                    int rowsAffected2 = insertStatementTwo.executeUpdate();

                    //check command was true
                    if(rowsAffected1>0 && rowsAffected2>0){
                        //uploading image into local file
                        String dpFilePath = "C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/Images/Dp/" + dp+"."+fileExtension;
                        boolean isDpSaved = saveFile(dpInputStream,dpFilePath);
                        if(isDpSaved){
                            //if file upload success, return true !
                            isAuthenticated=true;
                        }else {
                            System.out.println("Failed to save display picture.");
                        }
                    }
                }
            }
            else{
                System.out.println("User Name has been used!");
                isAuthenticated=false;
            }
            result1.close();
        }
        catch(Exception e){
            isAuthenticated=false;
            System.out.println("Error "+e);
        }
        finally{
            conn.close();
        }
        return isAuthenticated;
    }//Checked
}
