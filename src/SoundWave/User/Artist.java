package SoundWave.User;
import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.InputStream;

public class Artist extends User{


    //methods

    public void uploadSong(String title,float duration,String coverImg,InputStream coverImgStream,String artistId){
        try{
            String songId;
            Statement statement = conn.createStatement();
            //auto increment id
            ResultSet result = statement.executeQuery("Select Max(ListenerId) from song");
            if (result.next()) {
                String maxId = result.getString(1);
                if (maxId != null) {
                    int numaricPart = Integer.parseInt(maxId.substring(1));
                    numaricPart++;
                    songId = String.format("S%03d", numaricPart);
                } else {
                    songId = "S001";
                }
                int rowsAffected= statement.executeUpdate("Insert into song (SongId,Title,Duration,CoverImg,ArtistId) values('"+songId+"','"+title+"','"+duration+"','"+coverImg+"','"+artistId+"')");
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
            Statement statement = conn.createStatement();
            //delete old coverImg
            ResultSet result = statement.executeQuery("SELECT CoverImg FROM song WHERE SongId='" + songId + "'");
            if (result.next()) {
                String oldCoverImg = result.getString("CoverImg");
                String oldDpFilePath = "C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/Images/SongCoverImage/" + oldCoverImg;
                File oldFile = new File(oldDpFilePath);
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }
            //Update data
            int rowsAffected = statement.executeUpdate("UPDATE song SET Title='" + title + "', Duration='" + duration + "', CoverImg='" + coverImg + "' WHERE SongId='" + songId + "' and ArtistId='"+artistId+"'");
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
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("SELECT CoverImg FROM song WHERE SongId='" + songId + "'");
            if (result.next()) {
                String oldCoverImg = result.getString("CoverImg");
                String oldDpFilePath = "C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/Images/SongCoverImage/" + oldCoverImg;
                File oldFile = new File(oldDpFilePath);
                if (oldFile.exists()) {
                    boolean delete = oldFile.delete();
                }
            }
            int rowsAffected = statement.executeUpdate("Delete from song where SongId='" + songId + "'");
            if(rowsAffected>0){
                System.out.println("Song removed successfully!");
            }
        }
        catch(Exception e){
            System.out.println("Error"+e);
        }
    }
    public void viewSongFeedback(){}
    public  boolean register(String userName,String password, String name, String email, String contactNo, String dp,InputStream dpInputStream){
        try {
            String artistId;
            Statement statement = conn.createStatement();
            ResultSet result1 = statement.executeQuery("Select * from user where UserName='"+userName+"'");
            if(!(result1.next())){
                result1.close();
                //auto increment id
                ResultSet result2 = statement.executeQuery("Select Max(ArtistId) from artist");
                if(result2.next()){
                    String maxId = result2.getString(1);
                    if(maxId!=null){
                        int numaricPart = Integer.parseInt(maxId.substring(1));
                        numaricPart++;
                        artistId = String.format("A%03d",numaricPart);
                    }
                    else{
                        artistId = "A001";
                    }
                    int rowsAffected1 =statement.executeUpdate("Insert into user (UserName,Password,Name,Email,ContactNo,Dp) values('"+userName+"','"+password+"','"+name+"','"+email+"','"+contactNo+"','"+dp+"')");
                    int rowsAffected2 = statement.executeUpdate("Insert into artist(ArtistId,UserName) values('"+artistId+"','"+userName+"')");
                    if(rowsAffected1>0 && rowsAffected2>0){
                        String dpFilePath = "C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/Images/Dp/" + dp;
                        boolean isDpSaved = saveFile(dpInputStream,dpFilePath);
                        if(isDpSaved){
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
            System.out.println("Error"+e);
        }
        finally{
            //conn.close
        }
        return isAuthenticated;
    }
}
