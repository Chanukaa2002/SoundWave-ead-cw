package SoundWave.User;

import SoundWave.Authentication.Authentication;
import SoundWave.DBConnection.DBConnection;
import java.sql.*;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public abstract class User implements Authentication {

    //data members
    protected String userName;
    protected String name;
    protected String email;
    protected String password;
    protected String DP;
    protected String contactNo;
    private Connection conn;
    boolean isAuthenticated = false;//change datatype
    public User(){
        try{
            conn=DBConnection.getConnection();
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    public String getContactNo() {
        return contactNo;
    }

    //getters and setters

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setDP(String DP) {
        this.DP = DP;
    }
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
    public String getUserName() {
        return userName;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getDP() {
        return DP;
    }

    //methods
    public abstract void viewProfile(String userName) throws SQLException;//Checked
    public boolean editProfile(String userName,String password, String name, String email, String contactNo, String dp, InputStream dpInputStream) throws SQLException {
        try {
            String sql1 = "SELECT DP FROM user WHERE UserName=?";
            String sql2 = "UPDATE user SET Password=?, Name=?, Email=?, ContactNo=?, DP=? WHERE UserName=?";

            //delete old DP
            PreparedStatement selectStatement = conn.prepareStatement(sql1);
            selectStatement.setString(1,userName);
            ResultSet result = selectStatement.executeQuery();

            if (result.next()) {
                String oldDp = result.getString("DP");
                String oldDpFilePath = "C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/Images/Dp/" + oldDp;
                File oldFile = new File(oldDpFilePath);
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }

            //Update data
            PreparedStatement updateStatement  = conn.prepareStatement(sql2);
            updateStatement.setString(1,password);
            updateStatement.setString(2,name);
            updateStatement.setString(3,email);
            updateStatement.setString(4,contactNo);
            updateStatement.setString(5,dp);
            updateStatement.setString(6,userName);
            int rowsAffected = updateStatement.executeUpdate();
            if (rowsAffected > 0) {
                //update DP
                String dpFilePath = "C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/Images/Dp/" + dp;
                boolean isDpSaved = saveFile(dpInputStream, dpFilePath);
                isAuthenticated = true;
            } else {
                System.out.println("Profile update unsuccessful.");
            }
            result.close();
        } catch (Exception e) {
            System.out.println("Error: "+e);
        }
        finally{
            conn.close();
        }
        return isAuthenticated;
    }//Checked
    //interface methods
    public boolean login(String userName, String password) throws SQLException {
        try{
            String sql = "Select * from user where UserName=? and Password=?";
            PreparedStatement selectStatement = conn.prepareStatement(sql);
            selectStatement.setString(1,userName);
            selectStatement.setString(2,password);

            ResultSet result = selectStatement.executeQuery();
            if(result.next()){
                this.isAuthenticated=true;
            }
            result.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally {
            conn.close();
        }
        return isAuthenticated;
    }//Checked
    public abstract boolean register(String userName,String password, String name, String email, String contactNo, InputStream dpInputStream,String fileExtension) throws SQLException;//Checked
    public boolean logOut(){

        return true;
    }//-------------------work on this------------
    public boolean forgetPassword(String userName,String password) throws SQLException {
        try{
            String sql1 = "Select * from user where Password=? and  UserName=?";
            String sql2 = "Update user set Password=? where  UserName=?";

            PreparedStatement selectStatement = conn.prepareStatement(sql1);
            selectStatement.setString(1,password);
            selectStatement.setString(2,userName);

            ResultSet result = selectStatement.executeQuery();
            if(!(result.next())){

                PreparedStatement updateStatement = conn.prepareStatement(sql2);
                updateStatement.setString(1,password);
                updateStatement.setString(2,userName);

                int rowAffected = updateStatement.executeUpdate();
                if(rowAffected>0){
                    isAuthenticated=true;
                }
            }
            else {
                System.out.println("This Password is previous one please enter another one!");
            }
            result.close();
        }
        catch (Exception e){
            System.out.println(e);
        }
        finally{
            conn.close();
        }
        return isAuthenticated;
    }//Checked
    public int viewLikeCount(String songId){
        int count = 0;
        try{
            String sql = "SELECT COUNT(Likes) FROM feedback WHERE SongId=?;";

            PreparedStatement selectStatement = conn.prepareStatement(sql);
            selectStatement.setString(1,songId);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }
        return count;
    } //checked
    public abstract boolean isUser(String userName) throws SQLException;
    protected boolean saveFile(InputStream inputStream, String filePath) {
        try {
            Files.copy(inputStream, Paths.get(filePath));
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        }
    }//Checked
}
