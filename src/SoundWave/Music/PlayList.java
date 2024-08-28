package SoundWave.Music;
import SoundWave.App.UserUI.FilePath;
import SoundWave.DBConnection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlayList implements Runnable {
    private String playlistId,name,image,listenerId;
    private volatile boolean shouldStop = false;
    Connection conn;

    private Thread runningThread;

    public PlayList(){
        try{
            conn= DBConnection.getConnection();
        }
        catch(SQLException e){
            System.out.println("Playlist class constructor Error"+e);
        }
    }

    //getter setter
    public String getPlaylistId() {
        return playlistId;
    }
    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    //methods
    public void stopAll() {
        System.out.println("COme to stop all method");
        shouldStop = true;
        if(runningThread != null){
            System.out.println("come to if condition!");
            runningThread.interrupt();
            runningThread = null;
        }
        else{
            System.out.println("runningThread is null!");
        }
    }//not working
    public void playAll(String playlistId) {
        shouldStop = false;
        this.runningThread = Thread.currentThread();

        try {
            // Retrieve the list of songs in the playlist
            ArrayList<String[]> songList = getSongList(playlistId);

            if (songList.isEmpty()) {
                System.out.println("The playlist is empty.");
                return;
            }

            // Create a Song object to play the songs
            Song song = new Song();

            for (String[] songDetails : songList) {
                if (shouldStop) {
                    System.out.println("Playback stopped.");
                }

                String songId = songDetails[0];
                String songTitle = songDetails[1];
                String songName = songDetails[3];
                float duration = Float.parseFloat(songDetails[2]);
                String songPath = FilePath.getSongPath() + songName;

                System.out.println("Now playing: " + songTitle); // add UI
                song.start(songPath);

                try {
                    Thread.sleep((long) (duration * 1000));
                } catch (InterruptedException e) {
                    System.out.println("Playback interrupted.");
                    song.stop();
                    break;
                }
                song.stop();

            }

            System.out.println("All songs in the playlist have been played.");

        } catch (Exception e) {
            System.out.println("Error in PlayList playAll method: " + e);
        }
    }//checked
    public ArrayList<String[]> getSongList(String playlistId) throws SQLException {

        ArrayList<String[]> songs = new ArrayList<>();
        try
        {
            String sql = "SELECT s.SongId, s.Title, s.Duration, s.Song FROM song s JOIN playlist_song ps ON s.SongId = ps.SongId WHERE ps.PlayListId = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, playlistId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String[] songList = new String[4];
                songList[0] = resultSet.getString("SongId");
                songList[1] = resultSet.getString("Title");
                songList[2] = resultSet.getString("Duration");
                songList[3] = resultSet.getString("Song");
                System.out.println(songList[0]);
                System.out.println(songList[1]);
                songs.add(songList);
            }

        } catch(Exception e)
        {
            System.out.println("Error:" + e);
        }
        return songs;
    }//checked

    @Override
    public void run() {
        playAll(playlistId);
    }
}
