package SoundWave.User;
import SoundWave.Music.Song;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class Artist extends User{
    List<Song> song;

    //methods
    public void uploadSong(){}
    public void updateSong(){}
    public void removeSong(){}
    public void viewSongFeedback(){}

    public  boolean register(String userName,String password, String name, String email, String contactNo, String dp){
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
                    int rowsAffected1 =statement.executeUpdate("Insert into user (UserName,Password,Name,Email,ContactNo) values('"+userName+"','"+password+"','"+name+"','"+email+"','"+contactNo+"')");
                    int rowsAffected2 = statement.executeUpdate("Insert into artist(ArtistId,UserName) values('"+artistId+"','"+userName+"')");
                    if(rowsAffected1>0 && rowsAffected2>0){
                        isAuthenticated=true;
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
