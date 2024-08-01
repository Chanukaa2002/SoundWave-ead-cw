package SoundWave.Music;
import java.util.List;

public class PlayList {
    private String playlistId,name,image;
    List<Song> song;
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
    public void getDetails(){}



}
