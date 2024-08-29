package SoundWave.User;

import SoundWave.Authentication.Authentication;
import SoundWave.DBConnection.DBConnection;
import java.sql.*;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class User implements Authentication {

    //data members
    protected String userName;
    protected String password;
    private Connection conn;
    protected boolean isAuthenticated = false;
    public User(){
        try{
            conn=DBConnection.getConnection();
        }
        catch(SQLException e){
            System.out.println("Error User constructor: "+e);
        }
    }

    //getters and setters
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }

    //methods
    public String[] viewProfile(String userName) throws SQLException{
        String[] userDetails = new String[6];
        try{
            String sql ="Select * from User where UserName=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,userName);
            ResultSet result =statement.executeQuery();
            if (result.next()) {
                userDetails[0] = result.getString("UserName");
                userDetails[1] = result.getString("Name");
                userDetails[2] = result.getString("Password");
                userDetails[3] = result.getString("Dp");
                userDetails[4] = result.getString("ContactNo");
                userDetails[5] = result.getString("Email");
            }
            result.close();
        }catch (Exception e){
            System.out.println("User class View Profile method Error: "+e);
        }finally{
            conn.close();
        }
        return userDetails;
    }//Checked
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
            System.out.println("User Class login method Error: "+e);
        }
        finally {
            conn.close();
        }
        return isAuthenticated;
    }//Checked
    public abstract boolean register(String userName,String password, String name, String email, String contactNo, InputStream dpInputStream,String fileExtension) throws SQLException;//Checked
    public abstract boolean isUser(String userName) throws SQLException;
    protected boolean saveFile(InputStream inputStream, String filePath) {
        try {
            Files.copy(inputStream, Paths.get(filePath));
            return true;
        } catch (Exception e) {
            System.out.println("User class saveFile method Error: " + e);
            return false;
        }
    }//Checked
}
