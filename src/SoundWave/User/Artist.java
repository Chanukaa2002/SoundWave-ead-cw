package SoundWave.User;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.InputStream;

public class Artist extends User{
//-----------------------------------------------------------------------|DONE|------------------------------------------------
    //methods
    public boolean uploadSong(String title,String duration,String coverImg,InputStream coverImgStream,String artistId,InputStream songStream,String songPath) throws SQLException {
        try{
            conn.setAutoCommit(false);

            String songId;
            String sql1 = "Select Max(SongId) from song";
            String sql2 = "Insert into song (SongId,Title,Song,Duration,CoverImg,ArtistId) values(?,?,?,?,?,?)";

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
                insertStatement.setString(3,songPath);
                insertStatement.setString(4,duration);
                insertStatement.setString(5,coverImg);
                insertStatement.setString(6,artistId);

                int rowsAffected= insertStatement.executeUpdate();
                if(rowsAffected>0){
                    String coverImgPath = "C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/Images/SongCoverImage/" + coverImg;
                    boolean isDpSaved = saveFile(coverImgStream,coverImgPath);
                    String songFilePath = "C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/Images/Songs/" +songPath;
                    boolean isSongSaved = saveSong(songStream,songFilePath);
                    if(isDpSaved && isSongSaved){
                        conn.commit();
                        isAuthenticated=true;
                    }else {
                        conn.rollback();
                        System.out.println("Failed to save cover Image or song.");
                    }
                }
            }

        }catch(Exception e){
            conn.rollback();
            System.out.println("Error" +e);
        }finally {
            try {
                conn.setAutoCommit(true);  // Restore auto-commit mode
            } catch (SQLException ex) {
                System.out.println("Failed to restore auto-commit: " + ex.getMessage());
            }
        }
        return isAuthenticated;
    }//checked
    public void updateSong(String songId, String title, String duration, String coverImg, InputStream coverImgStream) throws SQLException {
        try {
            conn.setAutoCommit(false);
            String sql1 = "SELECT CoverImg,Song FROM song WHERE SongId=?";
            String sql2 = "UPDATE song SET Title=?, Duration=?, CoverImg=? WHERE SongId=?";
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
            updateStatement.setString(2,duration);
            updateStatement.setString(3,coverImg);
            updateStatement.setString(4,songId);

            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected > 0) {
                //update DP
                String dpFilePath = "C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/Images/Dp/" + coverImg;
                boolean isDpSaved = saveFile(coverImgStream, dpFilePath);
                conn.commit();
                System.out.println("Song update successful.");
            } else {
                conn.rollback();
                System.out.println("Song update unsuccessful.");
            }
        } catch (Exception e) {
            conn.rollback();
            System.out.println("Error" +e);
        }
        finally {
            try {
                conn.setAutoCommit(true);  // Restore auto-commit mode
            } catch (SQLException ex) {
                System.out.println("Failed to restore auto-commit: " + ex.getMessage());
            }
        }
    }//checked
    public boolean removeSong(String songId){
        try {
            conn.setAutoCommit(false);
            String sql1 = "SELECT CoverImg,Song FROM song WHERE SongId=?";
            String sql2 = "Delete from song where SongId=?";

            PreparedStatement selectStatement = conn.prepareStatement(sql1);
            selectStatement.setString(1,songId);

            ResultSet result = selectStatement.executeQuery();

            if (result.next()) {
                //removing image from local storage
                String oldCoverImg = result.getString("CoverImg");
                String songName =result.getString("Song");
                String oldDpFilePath = "C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/Images/SongCoverImage/" + oldCoverImg;
                String oldSongFilePath = "C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/Images/SongCoverImage/" + songName;

                File image = new File(oldDpFilePath);
                File song = new File(oldDpFilePath);

                if (image.exists() &&song.exists()) {
                    image.delete();
                    song.delete();
                    PreparedStatement removeStatement = conn.prepareStatement(sql2);
                    removeStatement.setString(1,songId);
                    int rowsAffected = removeStatement.executeUpdate();
                    conn.commit();
                    if(rowsAffected>0){
                        System.out.println("Song removed successfully!");
                        isAuthenticated = true;
                    }
                }
                else{
                    conn.rollback();
                }
            }

        }
        catch(Exception e){
            System.out.println("Error"+e);
        }
        return isAuthenticated;
    }//checked
    public  boolean register(String userName, String password, String name, String email, String contactNo, String dp, InputStream dpInputStream) throws SQLException {
        try {
            conn.setAutoCommit(false);
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
                            conn.commit();
                            //if file upload success, return true !
                            isAuthenticated=true;
                        }else {
                            conn.rollback();
                            System.out.println("Failed to save display picture.");
                        }
                    }
                }
            }
            else{
                conn.rollback();
                System.out.println("User Name has been used!");
            }

        }
        catch(Exception e){
            conn.rollback();
            System.out.println("Error" +e);
        }finally {
            try {
                conn.setAutoCommit(true);  // Restore auto-commit mode
            } catch (SQLException ex) {
                System.out.println("Failed to restore auto-commit: " + ex.getMessage());
            }
        }
        return isAuthenticated;
    }//Checked
    private boolean saveSong(InputStream inputStream, String filePath) {
        try {
            Files.copy(inputStream, Paths.get(filePath));
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        }
    }//work
}
