package SoundWave.Authentication;

public interface Authentication {
    public boolean login();
    public boolean register();
    public boolean logOut();
    public boolean forgetPassword();
}
