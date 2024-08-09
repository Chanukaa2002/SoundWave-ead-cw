package SoundWave.Music;
import SoundWave.DBConnection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayList {
    private String playlistId,name,image,listenerId;
    Connection conn;
    List<String> songs;
    public PlayList(){
        try{
            conn= DBConnection.getConnection();
            songs = new ArrayList<>();
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
    public void addSong(){}
    public void removeSong(){}
    public void playAll(){}
    public List<String> getSongList(String playlistId) throws SQLException {
        try
        {
            String sql = "SELECT s.Title FROM song s JOIN playlist_song ps ON s.SongId = ps.SongId WHERE ps.PlayListId = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, playlistId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                songs.add(resultSet.getString("Title"));
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
