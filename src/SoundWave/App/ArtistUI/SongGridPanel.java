package SoundWave.App.ArtistUI;

import SoundWave.App.ArtistUI.Actions.SongGridBtnActions;

import javax.swing.*;
import java.awt.*;

public class SongGridPanel extends JPanel {
    MainContentPanel mainContentPanel;
    public SongGridPanel(MainContentPanel mainContentPanel) {
        this.mainContentPanel= mainContentPanel;
       UI();
    }
    private void UI(){
        try{
        setLayout(new GridLayout(0, 4, 10, 10)); // 4 columns, dynamic rows
        setBackground(new Color(58, 65, 74)); // Darker grey background


        for (int i = 0; i < 20; i++) {
            JButton songButton = new JButton("Song Title");
            songButton.setBackground(new Color(216, 191, 216)); // Light purple background
            songButton.setPreferredSize(new Dimension(150, 150)); // Fixed size for buttons
            songButton.setFocusPainted(false);
            songButton.setBorderPainted(false);
            songButton.addActionListener(new SongGridBtnActions(mainContentPanel));
            add(songButton);
        }
        }
        catch(Exception e){
            System.out.println("Song Grid Panel UI function  Error: "+e);
            e.getStackTrace()[0].getLineNumber();
        }
    }
}
