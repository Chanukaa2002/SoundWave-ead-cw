package SoundWave.Music;

import SoundWave.DBConnection.DBConnection;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import javax.sound.sampled.*;

public class Song {
    //data members
    public String songId,title,artistName,image;
    public double duration;
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
    public void previes(){}
    public void volume(){}
    public void getDetails(String songId){
        try{
            String sql = "SELECT s.Title, s.Song, s.Duration, s.CoverImg,u.Name  FROM song s INNER JOIN artist a ON s.ArtistId = a.ArtistId" +
                    "INNER JOIN user u ON a.UserName = u.UserName" +
                    "WHERE s.SongId = ?;";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                this.songId = songId;
                this.title = result.getString("Title");
                this.artistName = result.getString("Name");
                this.image = result.getString("CoverImg");
                this.duration = result.getFloat("Duration");
                //song
            }
        }
        catch(Exception e){
            System.out.println("Error:"+e);
        }
    }

}
