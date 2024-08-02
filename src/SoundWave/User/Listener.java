package SoundWave.User;

import SoundWave.Music.PlayList;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;
public class Listener extends User{


    //methods
    public void createPlayList(String name,String coverImg,InputStream inputCoverImg,String listenerId){
        try{
            String playlistId;
            Statement statement = conn.createStatement();
                //auto increment id
                ResultSet result = statement.executeQuery("Select Max(ListenerId) from playlist");
                if (result.next()) {
                    String maxId = result.getString(1);
                    if (maxId != null) {
                        int numaricPart = Integer.parseInt(maxId.substring(1));
                        numaricPart++;
                        playlistId = String.format("P%03d", numaricPart);
                    } else {
                        playlistId = "P001";
                    }
                    int rowsAffected= statement.executeUpdate("Insert into playlist (PlayListId,Name,CoverImg,ListenerId) values('"+playlistId+"','"+name+"','"+coverImg+"','"+listenerId+"')");
                    if(rowsAffected>0){
                        String coverImgPath = "C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/Images/PlayListCoverImage/" + coverImg;
                        boolean isDpSaved = saveFile(inputCoverImg,coverImgPath);
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
    public void addSongToPlayList(String playlistId, String songId){}
    public void removeSongFromPlayList(String playlistId, String songId){}
    public void controlSong(){}
    public boolean likeSong(String songId){return true;}
    public double rateSong(String feedbackId,String songId){return 0.0;}
    public void exploreSong(){}
    public  boolean register(String userName, String password, String name, String email, String contactNo, String dp, InputStream dpInputStream){
        try {
            String ListenerId;
            Statement statement = conn.createStatement();
            ResultSet result1 = statement.executeQuery("Select * from user where UserName='"+userName+"'");
            if(!(result1.next())){
                //auto increment id
                ResultSet result2 = statement.executeQuery("Select Max(ListenerId) from listener");
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
                    int rowsAffected1 =statement.executeUpdate("Insert into user (UserName,Password,Name,Email,ContactNo,Dp) values('"+userName+"','"+password+"','"+name+"','"+email+"','"+contactNo+"','"+dp+"')");
                    int rowsAffected2 = statement.executeUpdate("Insert into listener(ListenerId,UserName) values('"+ListenerId+"','"+userName+"')");
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
            System.out.println(e);
        }
        finally{
            //conn.close
        }
        return isAuthenticated;
    }


}
