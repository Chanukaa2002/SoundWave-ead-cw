package SoundWave.User;

import SoundWave.Authentication.Authentication;
import SoundWave.DBConnection.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
    public User(){
        try{
            conn=DBConnection.getConnection();
        }
        catch(SQLException e){
            System.out.println(e);
        }
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
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("Select * from user where UserName='"+userName+"'");
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
    }
    public void editProfile(String userName,String password, String name, String email, String contactNo, String dp, InputStream dpInputStream){
        try {
            Statement statement = conn.createStatement();
            //delete old DP
            ResultSet result = statement.executeQuery("SELECT DP FROM user WHERE UserName='" + userName + "'");
            if (result.next()) {
                String oldDp = result.getString("DP");
                String oldDpFilePath = "C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/Images/Dp/" + oldDp;
                File oldFile = new File(oldDpFilePath);
                if (oldFile.exists()) {
                    oldFile.delete();
                }
            }
            //Update data
            int rowsAffected = statement.executeUpdate("UPDATE user SET Password='" + password + "', Name='" + name + "', Email='" + email + "', ContactNo='" + contactNo + "', DP='" + dp + "' WHERE UserName='" + userName + "'");
            if (rowsAffected > 0) {
                //update DP
                String dpFilePath = "C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/Images/Dp/" + dp;
                boolean isDpSaved = saveFile(dpInputStream, dpFilePath);
                isAuthenticated = true;
            } else {
                System.out.println("Profile update unsuccessful.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //interface methods
    public boolean login(String userName, String password){
        try{
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("Select * from user where UserName='"+userName+"' and Password='"+password+"'");
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
    }
    public abstract boolean register(String userName,String password, String name, String email, String contactNo, String dp, InputStream dpInputStream);
    public boolean logOut(){

        return true;
    }
    public boolean forgetPassword(String userName,String password){
        try{
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery("Select * from user where Password='"+password+"' and  UserName='"+userName+"'");
            if(!(result.next())){
                int rowAffected = statement.executeUpdate("Update user set Password='"+password+"' where  UserName='"+userName+"'");
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
    }
    protected boolean saveFile(InputStream inputStream, String filePath) {
        try {
            Files.copy(inputStream, Paths.get(filePath));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
