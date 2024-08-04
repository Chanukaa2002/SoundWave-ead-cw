package SoundWave.User;

import SoundWave.Music.PlayList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStream;
public class Listener extends User{

    //methods
    public void createPlayList(String name,String coverImg,InputStream inputCoverImg,String listenerId){
        try{
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
                PreparedStatement selectMacIDStatement = conn.prepareStatement(sql2);
                ResultSet result2 = selectMacIDStatement.executeQuery();

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
