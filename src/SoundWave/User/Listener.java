package SoundWave.User;

import SoundWave.App.UserUI.FilePath;
import SoundWave.DBConnection.DBConnection;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.io.InputStream;

public class Listener extends User{

    private String listenerId;
    private Connection conn;
    public Listener(){
        try{
            conn= DBConnection.getConnection();
        }
        catch(SQLException e){
            System.out.println("Listener Constructor Error : "+e);
        }
    }

    public String getListenerId() {
        return listenerId;
    }
    public void setListenerId(String listenerId) {
        this.listenerId = listenerId;
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
            System.out.println("Listener isUser method Error: "+e);
        }
        finally {
            conn.close();
        }
        return  status;
    }
    public boolean editProfile(String userName,String password, String name, String email,String dp,InputStream dpInputStream,String fileExtension) throws SQLException {
        try {
            String sql1 = "SELECT DP FROM user WHERE UserName=?";
            String sql2 = "UPDATE user SET Password=?, Name=?, Email=?, DP=? WHERE UserName=?";

            PreparedStatement selectStatement = conn.prepareStatement(sql1);
            selectStatement.setString(1,userName);
            ResultSet result = selectStatement.executeQuery();

            if (result.next()) {
                String oldDp = result.getString("DP");
                String oldDpFilePath = FilePath.getDpImgPath() + oldDp;
                File oldFile = new File(oldDpFilePath);
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }
            PreparedStatement updateStatement  = conn.prepareStatement(sql2);
            updateStatement.setString(1,password);
            updateStatement.setString(2,name);
            updateStatement.setString(3,email);
            updateStatement.setString(4,dp);
            updateStatement.setString(5,userName);
            int rowsAffected = updateStatement.executeUpdate();
            if (rowsAffected > 0) {
                String dpFilePath = FilePath.getDpImgPath() + dp + "." + fileExtension;
                isAuthenticated = saveFile(dpInputStream, dpFilePath);
            }
            result.close();
        } catch (Exception e) {
            System.out.println("Listener class edit profiles method Error: "+e);
        }
        finally{
            conn.close();
        }
        return isAuthenticated;
    }//Checked
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
            PreparedStatement selectStatement = conn.prepareStatement(sql1);
            ResultSet result = selectStatement.executeQuery();

            if (result.next()) {
                String maxId = result.getString(1);

                if (maxId != null) {
                    int numaricPart = Integer.parseInt(maxId.substring(1));
                    numaricPart++;
                    playlistId = String.format("P%03d", numaricPart);
                    coverImg = "PlayList-CoverImg-"+playlistId+"."+imgExtension;

                }
                else
                {
                    playlistId = "P001";
                    coverImg = "PlayList-CoverImg-P001."+imgExtension;
                }
                PreparedStatement inputStatement = conn.prepareStatement(sql2);
                inputStatement.setString(1,playlistId);
                inputStatement.setString(2,name);
                inputStatement.setString(3,coverImg);
                inputStatement.setString(4,listenerId);
                int rowsAffected= inputStatement.executeUpdate();

                if(rowsAffected>0){
                    String coverImgPath = FilePath.getPlayListCoverImgPath() + coverImg;
                    boolean isDpSaved = saveFile(inputCoverImg,coverImgPath);
                    if(isDpSaved){
                        conn.commit();
                        status=true;
                    }else {
                        conn.rollback();
                    }
                }
            }
            result.close();
        }catch(Exception e){
            conn.rollback();
            System.out.println("Error in Listener Create Playlist: "+e);
        }finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                System.out.println("Failed to restore auto commit in Listener createPlaylist method: " + ex);
            }
        }
        return status;
    }//checked
    public boolean addSongToPlayList(String playlistId, String songId){
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
            System.out.println("Listener AddSong method Error: "+e);
        }
        return status;
    }//checked
    public boolean removeSongFromPlayList(String playlistId, String songId){
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
            System.out.println("PlayList removeSong method Error: "+e);
        }
        return status;
    }//checked
    public String[] viewPlayList(String playlistId){
        String[] playlistDetail = new String[4];
        try{
            String sql = "Select * from playlist where PlayListId=?";
            PreparedStatement selectStatement = conn.prepareStatement(sql);
            selectStatement.setString(1,playlistId);
            ResultSet result = selectStatement.executeQuery();
            if(result.next()){
                playlistDetail[0] = result.getString("PlayListId");
                playlistDetail[1] = result.getString("Name");
                playlistDetail[2] = result.getString("CoverImg");
                playlistDetail[3] = result.getString("ListenerId");
            }
            else{
                playlistDetail = null;
            }
        }catch (Exception e){
            System.out.println("Listener View Playlist Error: "+e);
        }
        return playlistDetail;
    }//checked
    public void likeSong(String songId, String listenerId) throws SQLException {
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
                    insertStatement.executeUpdate();
                }
                result.close();
            }

        }
        catch(Exception e){

            System.out.println("Listener Class likeSong method Error: "+e);
        }
        finally{
            conn.close();
        }
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
            System.out.println("Listener class unlike song method Error: " + e);
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
    }//checked
    public ArrayList<String[]> exploreSongPlaylist(String playlistId){
        ArrayList<String[]> songList = new ArrayList<>();
        try{
            String sql = "SELECT s.SongId, s.Title, s.Song, s.Duration, s.CoverImg, s.ArtistId " +
                    "FROM song s " +
                    "WHERE s.SongId NOT IN (SELECT ps.SongId FROM playlist_song ps WHERE ps.PlaylistId = ?)";
            PreparedStatement selectStatement = conn.prepareStatement(sql);
            selectStatement.setString(1,playlistId);
            ResultSet result = selectStatement.executeQuery();

            while(result.next()){
                String[] songDetails = new String[6];
                songDetails[0] = result.getString("SongId");
                songDetails[1] = result.getString("Title");
                songDetails[2] = result.getString("Song");
                songDetails[3] = result.getString("Duration");
                songDetails[4] = result.getString("CoverImg");
                songDetails[5] = result.getString("ArtistId");

                songList.add(songDetails);
            }
        }
        catch (Exception e){
            System.out.println("Listener class explore song playlist method error:"+e);
        }
        return songList;
    }//checked
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
            System.out.println("Listener class explore song Error: "+e);
        }
        return songList;
    }//checked
    public  boolean register(String userName, String password, String name, String email, String contactNo, InputStream dpInputStream,String fileExtension) throws SQLException {
        try {
            String sql1 = "Select * from user where UserName=?";
            String sql2 = "Select Max(ListenerId) from listener";
            String sql3 = "Insert into user (UserName,Password,Name,Email,ContactNo,Dp) values(?,?,?,?,?,?)";
            String sql4 = "Insert into listener(ListenerId,UserName) values(?,?)";

            String ListenerId;
            String dp;

            PreparedStatement selectStatement = conn.prepareStatement(sql1);
            selectStatement.setString(1,userName);
            ResultSet result1 = selectStatement.executeQuery();

            if(!(result1.next())){
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
                    PreparedStatement insertStatementOne = conn.prepareStatement(sql3);
                    insertStatementOne.setString(1,userName);
                    insertStatementOne.setString(2,password);
                    insertStatementOne.setString(3,name);
                    insertStatementOne.setString(4,email);
                    insertStatementOne.setString(5,contactNo);
                    insertStatementOne.setString(6,dp);

                    int rowsAffected1 =insertStatementOne.executeUpdate();

                    PreparedStatement insertStatementTwo = conn.prepareStatement(sql4);
                    insertStatementTwo.setString(1,ListenerId);
                    insertStatementTwo.setString(2,userName);

                    int rowsAffected2 = insertStatementTwo.executeUpdate();

                    if(rowsAffected1>0 && rowsAffected2>0){
                        String dpFilePath = FilePath.getDpImgPath() + dp+"."+fileExtension;
                        boolean isDpSaved = saveFile(dpInputStream,dpFilePath);
                        if(isDpSaved){
                            isAuthenticated=true;
                        }
                    }
                }
            }
            else{
                isAuthenticated=false;
            }
            result1.close();
        }
        catch(Exception e){
            isAuthenticated=false;
            System.out.println("Listener class register method Error: "+e);
        }
        finally{
            conn.close();
        }
        return isAuthenticated;
    }//Checked
}
