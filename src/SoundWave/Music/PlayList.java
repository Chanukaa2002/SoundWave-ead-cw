package SoundWave.Music;
import SoundWave.App.UserUI.FilePath;
import SoundWave.DBConnection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayList {
    private String playlistId,name,image,listenerId;
    private volatile boolean shouldStop = false;
    Connection conn;

    public PlayList(){
        try{
            conn= DBConnection.getConnection();
        }
        catch(SQLException e){
            System.out.println(e);
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
    public boolean addSong(String songId,String playListId){
        boolean status = false;
        try{
            String sql = "Insert into playlist_song (PlayListId,SongId) values (?,?)";
            PreparedStatement insertStatement = conn.prepareStatement(sql);
            insertStatement.setString(1,playListId);
            insertStatement.setString(2,songId);

            int rowsAffected = insertStatement.executeUpdate();
            if(rowsAffected>0){
                status = true;
            }
        }catch(Exception e){
            System.out.println("PlayList AddSong method Error: "+e);
        }
        return status;
    }
    public boolean removeSong(String songId,String playlistId){
        boolean status = false;
        try{
            String sql = "delete from playlist_song where SongId=? and PlayListId=?";
            PreparedStatement insertStatement = conn.prepareStatement(sql);
            insertStatement.setString(1,songId);
            insertStatement.setString(2,playlistId);

            int rowsAffected = insertStatement.executeUpdate();
            if(rowsAffected>0){
                status = true;
            }
        }catch(Exception e){
            System.out.println("PlayList removeSOng method Error: "+e);
        }
        return status;
    }

    public void stopAll() {
        shouldStop = true; // Set the flag to true when stopping the playlist
    }

    public void playAll(String playlistId) {
        shouldStop = false; // Reset the flag before starting playback

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
                    break; // Exit the loop if stop is requested
                }

                String songId = songDetails[0];
                String songTitle = songDetails[1];
                String songName = songDetails[3];
                float duration = Float.parseFloat(songDetails[2]);

                // Get song details using songId

                String songPath = FilePath.getSongPath() + songName; // Assuming the path to the song file is in the 3rd position

                // Start playing the song
                System.out.println("Now playing: " + songTitle);
                System.out.println(duration);
                song.start(songPath);

                // Sleep for the duration of the song
                // The duration is in seconds, so multiply by 1000 to convert to milliseconds
                Thread.sleep((long) ( duration * 1000));

                // Stop the current song before playing the next one
                song.stop();

            }

            System.out.println("All songs in the playlist have been played.");

        } catch (Exception e) {
            System.out.println("Error in PlayList playAll method: " + e);
        }
    }


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
        finally{
            conn.close();
        }
        return songs;
    }//checked

}
