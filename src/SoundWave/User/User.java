package SoundWave.User;

import SoundWave.Authentication.Authentication;
import SoundWave.DBConnection.DBConnection;

import java.sql.*;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class User implements Authentication {

    //data members
    protected String userName;
    protected String name;
    protected String email;
    protected String password;
    protected String DP;
    protected String contactNo;
    Connection conn;
    boolean isAuthenticated = false;//change datatype
    public String getContactNo() {
        return contactNo;
    }
    //constractor
    public User(){
        try{
            conn=DBConnection.getConnection();
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
    //getters and setters
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getDP() {
        return DP;
    }
    public void setDP(String DP) {
        this.DP = DP;
    }
    //methods
    public void viewProfile(String userName){
        try{
            String sql = "Select * from user where UserName=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,userName);
            ResultSet result =statement.executeQuery();
            while(result.next()){
                this.userName = result.getString("UserName");
                this.password = result.getString("Password");
                this.name = result.getString("Name");
                this.email = result.getString("Email");
                this.contactNo = result.getString("ContactNo");
                this.DP = result.getString("DP");
            }
        }catch (Exception e){
            System.out.println(e);
        }finally{
        }
    }//Checked
    public boolean editProfile(String userName,String password, String name, String email, String contactNo, String dp, InputStream dpInputStream){
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
        } catch (Exception e) {
            System.out.println("Error: "+e);
        }
        return isAuthenticated;
    }//Checked
    //interface methods
    public boolean login(String userName, String password){
        try{
            String sql = "Select * from user where UserName=? and Password=?";
            PreparedStatement selectStatement = conn.prepareStatement(sql);
            selectStatement.setString(1,userName);
            selectStatement.setString(2,password);

            ResultSet result = selectStatement.executeQuery();
            if(result.next()){
                this.isAuthenticated=true;
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally {
            //close connection
        }
        return isAuthenticated;
    }//Checked
    public abstract boolean register(String userName,String password, String name, String email, String contactNo, String dp, InputStream dpInputStream);//Checked
    public boolean logOut(){

        return true;
    }//-------------------Not checked------------
    public boolean forgetPassword(String userName,String password){
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
        }
        catch (Exception e){
            System.out.println(e);
        }
        return isAuthenticated;
    }//Checked
    public int viewLikeCount(String songId){
        int count = 0;
        try{
            String sql = "SELECT COUNT(Likes) FROM feedback WHERE SongId=?;";

            PreparedStatement selectStatement = conn.prepareStatement(sql);
            selectStatement.setString(1,songId);
            count = selectStatement.executeUpdate();
        }
        catch(Exception e){
            System.out.println("Error: " + e);
        }
        return count;
    } //-------------------Not checked------------
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
