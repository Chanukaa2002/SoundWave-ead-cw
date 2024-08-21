package SoundWave.App.ArtistUI;

import SoundWave.App.ArtistUI.Actions.ASongGridBtnActions;

import javax.swing.*;
import java.awt.*;

public class ASongGridPanel extends JPanel {
    AMainContentPanel mainContentPanel;
    public ASongGridPanel(AMainContentPanel mainContentPanel) {
        this.mainContentPanel= mainContentPanel;
        UI();
    }
    private void UI(){
        try{
        setLayout(new GridLayout(0, 4, 20, 20)); // 4 columns, dynamic rows
        setBackground(new Color(58, 65, 74)); // Darker grey background


        for (int i = 0; i < 20; i++) {
            JButton songButton = new JButton("Song Title");
            songButton.setBackground(new Color(216, 191, 216)); // Light purple background
            songButton.setPreferredSize(new Dimension(150, 150)); // Fixed size for buttons
            songButton.setFocusPainted(false);
            songButton.setBorderPainted(false);
            songButton.addActionListener(new ASongGridBtnActions(mainContentPanel));
            add(songButton);
        }
        }
        catch(Exception e){
            System.out.println("Song Grid Panel UI function  Error: "+e);
            e.getStackTrace()[0].getLineNumber();
        }
    }
}
