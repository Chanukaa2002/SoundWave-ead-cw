package SoundWave.App.ArtistUI;

import SoundWave.App.ArtistUI.Actions.ASideBarBtnActions;
import SoundWave.User.Artist;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ArtistMainPanel extends JFrame {
    private String userName,artistId;
    private Artist artist;

    public ArtistMainPanel(String userName) throws SQLException {
        this.userName = userName;
        this.artist = new Artist(); //------------------------------------>
        artistId = artist.getId(userName);
        UI();
    }
    private void UI(){
        try {
            setTitle("Artist");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1000, 600);
            setResizable(false);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());


            new ASideBarBtnActions(this);
            AMainContentPanel mainContentPanel = new AMainContentPanel(artistId);
            add(mainContentPanel, BorderLayout.CENTER);


            ASidebarPanel sidebarPanel = new ASidebarPanel(mainContentPanel,userName,artistId);
            add(sidebarPanel, BorderLayout.WEST);

            setVisible(true);
        }
        catch(Exception e){
            System.out.println("Artist Main Panel UI method Error: "+e);
        }
    }
}
