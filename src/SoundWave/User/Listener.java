package SoundWave.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.io.InputStream;
public class Listener extends User{

    //methods
    public void createPlayList(String name,String coverImg,InputStream inputCoverImg,String listenerId) throws SQLException {
        try{
            conn.setAutoCommit(false);
            String playlistId;
            String sql1 ="Select Max(ListenerId) from playlist";
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

                }
                else
                {
                    playlistId = "P001";
                }
                PreparedStatement inputStatement = conn.prepareStatement(sql2);
                inputStatement.setString(1,playlistId);
                inputStatement.setString(2,name);
                inputStatement.setString(3,coverImg);
                inputStatement.setString(4,listenerId);
                int rowsAffected= inputStatement.executeUpdate();

                if(rowsAffected>0){
                    //upload image into local storage
                    String coverImgPath = "C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/Images/PlayListCoverImage/" + coverImg;
                    boolean isDpSaved = saveFile(inputCoverImg,coverImgPath);

                    if(isDpSaved){
                        conn.commit();
                        isAuthenticated=true;
                    }else {
                        conn.rollback();
                        System.out.println("Failed to save cover Image.");
                    }
                }
            }
            result.close();
        }catch(Exception e){
            conn.rollback();
            System.out.println("Error "+e);
        }finally {
            try {
                conn.setAutoCommit(true);  // Restore auto-commit mode
            } catch (SQLException ex) {
                System.out.println("Failed to restore auto-commit: " + ex.getMessage());
            }
        }
    }//checked
    public boolean addSongToPlayList(String playlistId, String songId) throws SQLException {
        try{
            String sql = "insert into playlist_song (PlayListId,SongId) values(?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,playlistId);
            statement.setString(2,songId);

            int rowAffected = statement.executeUpdate();
            if(rowAffected>0){
                isAuthenticated=true;
            }
        }catch(Exception e){
            System.out.println("Error: "+e);
        }
        finally{
            conn.close();
        }
        return isAuthenticated;
    }//checked
    public boolean removeSongFromPlayList(String playlistId, String songId) throws SQLException {
        try{
            String sql = "delete from playlist_song where PlayListId=? and SongId=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,playlistId);
            statement.setString(2,songId);

            int rowAffected = statement.executeUpdate();
            if(rowAffected>0){
                isAuthenticated=true;
            }
        }catch(Exception e){
            System.out.println("Error: "+e);
        }
        finally{
            conn.close();
        }
        return isAuthenticated;
    }//checked
    public void controlSong(){}//-------------------work on this------------
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

                    int rowsAffected = insertStatement.executeUpdate();
                    if(rowsAffected>0){
                        isAuthenticated = true;
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
    }//checked
    public void unlikeSong(String songId, String listenerId) throws SQLException {
        try{
            String sql = "Delete from feedback where ListenerId=? and SongId = ?";
            PreparedStatement deleteStatement = conn.prepareStatement(sql);
            deleteStatement.setString(1,listenerId);
            deleteStatement.setString(2,songId);

            int rowsAffected = deleteStatement.executeUpdate();
            if(rowsAffected>0){
                isAuthenticated=true;
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }
        finally{
            conn.close();
        }
    }//checked
    public void exploreSong(){
        try{
            String sql = "Select * from song";
            PreparedStatement selectStatement = conn.prepareStatement(sql);
            ResultSet result = selectStatement.executeQuery();
            ArrayList<String[]> songList = new ArrayList<>();
            while(result.next()){

                //:(
            }
        }
        catch (Exception e){
            System.out.println("Error:"+e);
        }
    }//-------------------working on this------------
    public  boolean register(String userName, String password, String name, String email, String contactNo, String dp, InputStream dpInputStream) throws SQLException {
        try {
            String sql1 = "Select * from user where UserName=?";
            String sql2 = "Select Max(ListenerId) from listener";
            String sql3 = "Insert into user (UserName,Password,Name,Email,ContactNo,Dp) values(?,?,?,?,?,?)";
            String sql4 = "Insert into listener(ListenerId,UserName) values(?,?)";
            //initialize Listener Id for inset sql
            String ListenerId;
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
                    }
                    else{
                        ListenerId = "L001";
                    }
                    //insert sql command
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

                    //check command was true
                    if(rowsAffected1>0 && rowsAffected2>0){
                        //uploading image into local file
                        String dpFilePath = "C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/Images/Dp/" + dp;
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
