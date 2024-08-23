package SoundWave.Music;

import SoundWave.DBConnection.DBConnection;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
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
    Clip clip;
    private AudioInputStream audioInput;

    //methods
    public Song(){
        try{
            conn= DBConnection.getConnection();
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }

//stay for java swing coding after gui was code, implement that correctly
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
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the clip continuously
        } catch (Exception e) {
            System.out.println("Error: "+e);
        }
    }//-------------------------work on this-------------------------
    public void pause() {
        if (clip != null && clip.isRunning()) {
            clip.stop(); // Stop the clip (simulate pause)
        }
    }//-------------------------work on this-------------------------
    public void resume() {
        if (clip != null && !clip.isRunning()) {
            clip.start(); // Start the clip (resume from pause)
        }
    }//-------------------------work on this-------------------------
    public void stop() {
        if (clip != null) {
            clip.stop(); // Stop the clip
            clip.close(); // Close the clip and release resources
        }
    }//-------------------------work on this-------------------------
    public void next(){}
    public void back(){}
    public void volume(){}
    public String[] getDetails(String songId) {
        String[] details = new String[7];
        try {
            // Corrected SQL query: Added missing commas and fixed JOIN clause syntax
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
                System.out.println(details[4]);
                details[5] = result.getString("Name");
                details[6] = result.getString("ArtistId");
            } else {
                details = null;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return details;
    }
//-----------------Not checked--------------

}
