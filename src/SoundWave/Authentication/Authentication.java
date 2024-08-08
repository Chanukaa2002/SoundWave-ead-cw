package SoundWave.Authentication;

import java.io.InputStream;
import java.sql.SQLException;

public interface Authentication {
    public boolean login(String userName,String password) throws SQLException;
    public boolean register(String userName, String password, String name, String email, String contactNo, String dp, InputStream dpInputSteam) throws SQLException;
    public boolean logOut();
    public boolean forgetPassword(String userName,String password) throws SQLException;
}
