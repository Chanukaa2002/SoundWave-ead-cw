package SoundWave.App.ArtistUI;

import SoundWave.User.Artist;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ArtistMainPanel extends JFrame {
    private String userName,artistId;
    private Artist artist;
    public ArtistMainPanel(String userName) throws SQLException {
        this.userName = userName;
        this.artist = new Artist();
        artistId = artist.getId(userName);
        UI();
    }
    private void UI(){
        try {
            // Frame settings
            setTitle("My Songs");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1000, 600);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            // Add main content panel
            AMainContentPanel mainContentPanel = new AMainContentPanel(artistId);
            add(mainContentPanel, BorderLayout.CENTER);

            // Add sidebar panel
            ASidebarPanel sidebarPanel = new ASidebarPanel(mainContentPanel,userName,artistId);
            add(sidebarPanel, BorderLayout.WEST);

            setVisible(true);
        }
        catch(Exception e){
            System.out.println("Artist Main Panel UI method Error: "+e);
            e.getStackTrace()[0].getLineNumber();
        }
    }
//    public static void main(String[] args) {
//        new ArtistMainPanel();
//    }
}
