package SoundWave.User;

import SoundWave.Authentication.Authentication;
import SoundWave.DBConnection.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    protected void viewProfile(String userName){
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
        }
    }
    protected void editProfile(String userName,String password, String name, String email, String contactNo, String dp){
        try{
            //userName cannot be changed!
            Statement statement = conn.createStatement();
            int rowsAffected = statement.executeUpdate("Update user set UserName='"+userName+"',Password='"+password+"',Name='"+name+"',Email='"+email+"',ContactNo='"+contactNo+"',DP='"+dp+"' where UserName='"+userName+"'");
            if(rowsAffected>0){
                System.out.println("Profile Update Success!");
            }
            else{
                System.out.println("Profile Update UnSuccess");
            }
        }catch (Exception e){
            System.out.println(e);
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
    public abstract boolean register(String userName,String password, String name, String email, String contactNo, String dp);
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



}
