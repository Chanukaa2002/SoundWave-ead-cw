package SoundWave.Music;
import SoundWave.App.UserUI.FilePath;
import SoundWave.DBConnection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlayList implements Runnable {
    private String playlistId,image;
    private Connection conn;
    private Thread runningThread;

    public PlayList(){
    }

    //getter setter
    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    //methods
    public void playAll(String playlistId) throws SQLException{
        this.runningThread = Thread.currentThread();
        try {
            conn= DBConnection.getConnection();
            ArrayList<String[]> songList = getSongList(playlistId);

            if (songList.isEmpty()) {
                System.out.println("The playlist is empty.");
                return;
            }
            Song song = new Song();
            for (String[] songDetails : songList) {
                String songTitle = songDetails[1];
                float duration = Float.parseFloat(songDetails[2]);
                String songName = songDetails[3];
                String songPath = FilePath.getSongPath() + songName;

                System.out.println("Now playing: " + songTitle); // add into UI<<--------------------------------------------------------------
                song.start(songPath);

                try {
                    Thread.sleep((long) (duration * 1000));
                } catch (InterruptedException e) {
                    song.stop();
                    break;
                }
                song.stop();
            }
        } catch (Exception e) {
            System.out.println("Error in PlayList playAll method: " + e);
        }
        finally{
            conn.close();
        }
    }//checked
    public void stopAll() throws SQLException {
        try{
            conn= DBConnection.getConnection();
            if(runningThread != null){
                runningThread.interrupt();
                runningThread = null;
            }
        }catch (Exception e){
            System.out.println("Playlist stopAll method Error: "+e);
        }
        finally{
            conn.close();
        }
    }//working
    public ArrayList<String[]> getSongList(String playlistId) {
        ArrayList<String[]> songs = new ArrayList<>();
        try
        {
            conn= DBConnection.getConnection();
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
                songs.add(songList);
            }
        } catch(Exception e) {
            System.out.println("Playlist class getSongList method Error:" + e);
        }
        return songs;
    }//checked
    @Override
    public void run() {
        try {
            playAll(playlistId);
        } catch (SQLException e) {
            System.out.println("Playlist run method Error: "+e);
        }
    }
}
