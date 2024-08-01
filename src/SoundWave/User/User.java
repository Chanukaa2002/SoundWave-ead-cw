package SoundWave.User;

import SoundWave.Authentication.Authentication;
import SoundWave.DBConnection.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class User implements Authentication {

    //data members
    protected String userId;
    protected String name;
    protected String email;
    protected String password;
    protected String DP; //change datatype

    public User(){

    }

    //getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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



    //methods
    protected void viewProfile(){

    }
    protected void editProfile(){

    }
    //interface methods
    public boolean login(String userName, String password){
        try{

        }
        catch(Exception e){
            System.out.println(e.toString());
        }
        finally{

        }
        return true;
    }
    public boolean register(){

        return true;
    }
    public boolean logOut(){

        return true;
    }
    public boolean forgetPassword(){

        return true;
    }



}
