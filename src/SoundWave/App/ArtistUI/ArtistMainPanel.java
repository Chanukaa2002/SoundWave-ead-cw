package SoundWave.App.ArtistUI;

import javax.swing.*;
import java.awt.*;

public class ArtistMainPanel extends JFrame {

    public ArtistMainPanel() {
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
            AMainContentPanel mainContentPanel = new AMainContentPanel();
            add(mainContentPanel, BorderLayout.CENTER);

            // Add sidebar panel
            ASidebarPanel sidebarPanel = new ASidebarPanel(mainContentPanel);
            add(sidebarPanel, BorderLayout.WEST);

            setVisible(true);
        }
        catch(Exception e){
            System.out.println("Artist Main Panel UI method Error: "+e);
            e.getStackTrace()[0].getLineNumber();
        }
    }
    public static void main(String[] args) {
        new ArtistMainPanel();
    }
}
