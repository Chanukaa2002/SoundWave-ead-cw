package SoundWave.Authentication;

import java.io.InputStream;
import java.sql.SQLException;

public interface Authentication {
    public boolean login(String userName,String password) throws SQLException;
    public boolean register(String userName, String password, String name, String email, String contactNo, InputStream dpInputSteam,String fileExtension) throws SQLException;
    public boolean logOut();
    public boolean forgetPassword(String userName,String password) throws SQLException;
}
