package SoundWave.User;
import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.InputStream;

public class Artist extends User{

    //methods
    public void uploadSong(String title,float duration,String coverImg,InputStream coverImgStream,String artistId){
        try{
            String songId;
            String sql1 = "Select Max(ListenerId) from song";
            String sql2 = "Insert into song (SongId,Title,Duration,CoverImg,ArtistId) values(?,?,?,?,?)";

            //auto increment id
            PreparedStatement selectStatement = conn.prepareStatement(sql1);
            ResultSet result = selectStatement.executeQuery();

            if (result.next()) {
                String maxId = result.getString(1);
                if (maxId != null) {
                    int numaricPart = Integer.parseInt(maxId.substring(1));
                    numaricPart++;
                    songId = String.format("S%03d", numaricPart);
                } else {
                    songId = "S001";
                }

                PreparedStatement insertStatement = conn.prepareStatement(sql2);
                insertStatement.setString(1,songId);
                insertStatement.setString(2,title);
                insertStatement.setFloat(3,duration);
                insertStatement.setString(4,coverImg);
                insertStatement.setString(5,artistId);

                int rowsAffected= insertStatement.executeUpdate();
                if(rowsAffected>0){
                    String coverImgPath = "C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/Images/SongCoverImage/" + coverImg;
                    boolean isDpSaved = saveFile(coverImgStream,coverImgPath);
                    if(isDpSaved){
                        isAuthenticated=true;
                    }else {
                        System.out.println("Failed to save cover Image.");
                    }
                }
            }

        }catch(Exception e){
            System.out.println(e);
        }
    }
    public void updateSong(String songId,String title,float duration,String coverImg,InputStream coverImgStream,String artistId){
        try {
            String sql1 = "SELECT CoverImg FROM song WHERE SongId=?";
            String sql2 = "UPDATE song SET Title=?, Duration=?, CoverImg=? WHERE SongId=? and ArtistId=?";
            //delete old coverImg

            PreparedStatement selectStatement = conn.prepareStatement(sql1);
            selectStatement.setString(1,songId);
            ResultSet result = selectStatement.executeQuery();

            if (result.next()) {
                String oldCoverImg = result.getString("CoverImg");
                String oldDpFilePath = "C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/Images/SongCoverImage/" + oldCoverImg;
                File oldFile = new File(oldDpFilePath);
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }
            //Update data
            PreparedStatement updateStatement = conn.prepareStatement(sql2);
            updateStatement.setString(1,title);
            updateStatement.setFloat(2,duration);
            updateStatement.setString(3,coverImg);
            updateStatement.setString(4,songId);
            updateStatement.setString(5,artistId);

            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected > 0) {
                //update DP
                String dpFilePath = "C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/Images/Dp/" + coverImg;
                boolean isDpSaved = saveFile(coverImgStream, dpFilePath);
                isAuthenticated = true;
            } else {
                System.out.println("Song update unsuccessful.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void removeSong(String songId){
        try {
            String sql1 = "SELECT CoverImg FROM song WHERE SongId=?";
            String sql2 = "Delete from song where SongId=?";

            PreparedStatement selectStatement = conn.prepareStatement(sql1);
            selectStatement.setString(1,songId);

            ResultSet result = selectStatement.executeQuery();

            if (result.next()) {
                //removing image from local storage
                String oldCoverImg = result.getString("CoverImg");
                String oldDpFilePath = "C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/Images/SongCoverImage/" + oldCoverImg;

                File oldFile = new File(oldDpFilePath);
                if (oldFile.exists()) {
                    boolean delete = oldFile.delete();
                }
            }
            PreparedStatement removeStatement = conn.prepareStatement(sql2);
            removeStatement.setString(1,songId);

            int rowsAffected = removeStatement.executeUpdate();
            if(rowsAffected>0){
                System.out.println("Song removed successfully!");
            }
        }
        catch(Exception e){
            System.out.println("Error"+e);
        }
    }
    public void viewSongFeedback(){}
    public  boolean register(String userName, String password, String name, String email, String contactNo, String dp, InputStream dpInputStream){
        try {
            String sql1 = "Select * from user where UserName=?";
            String sql2 = "Select Max(ArtistId) from listener";
            String sql3 = "Insert into user (UserName,Password,Name,Email,ContactNo,Dp) values(?,?,?,?,?,?)";
            String sql4 = "Insert into artist(ArtistId,UserName) values(?,?)";

            //initialize Listener Id for inset sql
            String ArtistId;

            //sql command-1
            PreparedStatement selectStatement = conn.prepareStatement(sql1);
            selectStatement.setString(1,userName);
            ResultSet result1 = selectStatement.executeQuery();

            if(!(result1.next())){

                //auto increment id
                //sql command-2
                PreparedStatement selectMacIDStatement = conn.prepareStatement(sql2);
                ResultSet result2 = selectMacIDStatement.executeQuery();

                if(result2.next()){
                    String maxId = result2.getString(1);
                    if(maxId!=null){
                        int numaricPart = Integer.parseInt(maxId.substring(1));
                        numaricPart++;
                        ArtistId = String.format("A%03d",numaricPart);
                    }
                    else{
                        ArtistId = "A001";
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
                    insertStatementTwo.setString(1,ArtistId);
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
            }

        }
        catch(Exception e){
            System.out.println(e);
        }
        finally{
            //conn.close
        }
        return isAuthenticated;
    }
}
