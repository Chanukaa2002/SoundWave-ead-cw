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
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getArtistName() {
        return artistName;
    }
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public double getDuration() {
        return duration;
    }
    public void setDuration(double duration) {
        this.duration = duration;
    }

    //data members
    private String songId,title,artistName,image;
    private double duration;
    Connection conn;
    private static Clip  clip;
    private AudioInputStream audioInput;
    public float currentVol=0;
    public static FloatControl fc;

    //methods
    public Song(){
        try{
            conn= DBConnection.getConnection();
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }

    public void start(String path) {
        try {
            if (clip != null) {
                //pause();
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
        } catch (Exception e) {
            System.out.println("Error: "+e);
        }
    }//checked
    public void stop() {
        try {
            System.out.println("come to stop");
            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close();
            } else {
                System.out.println("Clip is either null or not running.");
            }
        } catch (Exception e) {
            System.out.println("Error stopping the clip: " + e);
        }
    }//checked
    //-------------------------work on this-------------------------
    public void next(){}
    public void back(){}
    public String[] getDetails(String songId) {
        String[] details = new String[7];
        try {

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
            System.out.println("Error: " + e);
        }
        return details;
    }//checked

}
