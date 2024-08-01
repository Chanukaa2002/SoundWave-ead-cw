package SoundWave.Authentication;

public interface Authentication {
    public boolean login(String userName,String password);
    public boolean register(String userName,String password, String name, String email, String contactNo, String dp);
    public boolean logOut();
    public boolean forgetPassword(String userName,String password);
}
