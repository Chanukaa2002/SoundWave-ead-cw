package SoundWave.Authentication;

import java.io.InputStream;
import java.sql.SQLException;

public interface Authentication {

    boolean login(String userName,String password) throws SQLException;
    boolean register(String userName, String password, String name, String email, String contactNo, InputStream dpInputSteam,String fileExtension) throws SQLException;

}
