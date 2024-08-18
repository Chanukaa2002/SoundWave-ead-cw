package SoundWave.App.ListenerUI;

import SoundWave.App.ArtistUI.ArtistMainPanel;
import SoundWave.App.ArtistUI.MainContentPanel;
import SoundWave.App.ArtistUI.SidebarPanel;

import javax.swing.*;
import java.awt.*;

public class ListenerMainPanel extends JFrame{
    public ListenerMainPanel(){
        UI();
    }
    private void UI(){
        try {
            // Frame settings
            setTitle("Listener");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1000, 600);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());
            // Add main content panel
            MainContentPanel mainContentPanel = new MainContentPanel();
            add(mainContentPanel, BorderLayout.CENTER);

            // Add sidebar panel
            MainContent mc = new MainContent();
            SideBar sidebarPanel = new SideBar(mc);
            JScrollPane sideBarScroll = new JScrollPane(sidebarPanel);
            sideBarScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            sideBarScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            sideBarScroll.setBorder(BorderFactory.createEmptyBorder()); // Remove the border

            add(sideBarScroll, BorderLayout.WEST);

            setVisible(true);
        }
        catch(Exception e){
            System.out.println("Artist Main Panel UI method Error: "+e);
            e.getStackTrace()[0].getLineNumber();
        }
    }
    public static void main(String[] args) {
        new ListenerMainPanel();
    }
}
