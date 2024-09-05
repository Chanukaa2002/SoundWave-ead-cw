package SoundWave.Music;

import SoundWave.DBConnection.DBConnection;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sound.sampled.*;

public class Song {

    public String getSongId() {
        return songId;
    }
    public void setSongId(String songId) {
        this.songId = songId;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    //data members
    private String songId,image;
    private Connection conn;
    private static Clip  clip;
    private AudioInputStream audioInput;
    public static FloatControl fc;

    //methods
    public Song(){
    }
    public void start(String path) {
        try {
            if (clip != null) {
                clip.stop();
                clip.close();
            }
            File musicPath = new File(path);
            audioInput = AudioSystem.getAudioInputStream(musicPath);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            fc.setValue(6.0f);
        } catch (Exception e) {
            System.out.println("Song class start method Error: "+e);
        }
    }//checked
    public void stop() {
        try {
            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close();
            }
        } catch (Exception e) {
            System.out.println("Song class stop method Error: " + e);
        }
    }//checked
    public void next(){}
    public void back(){}
    public String[] getDetails(String songId) throws SQLException{
        String[] details = new String[7];
        try {
            conn= DBConnection.getConnection();

            String sql = "SELECT s.SongId, s.Title, s.Song, s.Duration, s.CoverImg, u.Name,s.ArtistId " +
                    "FROM song s " +
                    "INNER JOIN artist a ON s.ArtistId = a.ArtistId " +
                    "INNER JOIN user u ON a.UserName = u.UserName " +
                    "WHERE s.SongId = ?;";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, songId);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                details[0] = result.getString("SongId");
                details[1] = result.getString("Title");
                details[2] = result.getString("Song");
                details[3] = result.getString("Duration");
                details[4] = result.getString("CoverImg");
                details[5] = result.getString("Name");
                details[6] = result.getString("ArtistId");
            } else {
                details = null;
            }
        } catch (Exception e) {
            System.out.println("Song class getDetails method Error: " + e);
        }
        finally{
            conn.close();
        }
        return details;
    }//checked

}
