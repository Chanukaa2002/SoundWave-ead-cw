package SoundWave.User;

import SoundWave.Music.PlayList;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class Listener extends User{

    List<PlayList> playlist;

    //methods
    public void createPlayList(){}
    public void addSongToPlayList(){}
    public void removeSongFromPlayList(){}
    public void controlSong(){}
    public boolean likeSong(){return true;}
    public double rateSong(){return 0.0;}
    public void exploreSong(){}
    public  boolean register(String userName,String password, String name, String email, String contactNo, String dp){
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
                    int rowsAffected1 =statement.executeUpdate("Insert into user (UserName,Password,Name,Email,ContactNo) values('"+userName+"','"+password+"','"+name+"','"+email+"','"+contactNo+"')");
                    int rowsAffected2 = statement.executeUpdate("Insert into listener(ListenerId,UserName) values('"+ListenerId+"','"+userName+"')");
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
