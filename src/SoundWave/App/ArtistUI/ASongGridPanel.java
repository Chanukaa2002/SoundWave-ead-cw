package SoundWave.App.ArtistUI;

import SoundWave.App.ArtistUI.Actions.ASongGridBtnActions;
import SoundWave.App.UserUI.FilePath;
import SoundWave.User.Artist;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ASongGridPanel extends JPanel {
    AMainContentPanel mainContentPanel;
    JButton songButton;
    String artistId;
    JLabel songTitle;

    public ASongGridPanel(AMainContentPanel mainContentPanel,String artistId) {
        this.mainContentPanel= mainContentPanel;
        this.artistId = artistId;
        UI();
    }
    private void UI(){
        try{
            setLayout(new GridLayout(0, 4, 20, 20));
            setBackground(new Color(58, 65, 74));

            Artist artist = new Artist();
            ArrayList<String[]> songs = artist.viewMyAllSong(artistId);

            for (String[] i: songs) {

                String songId = i[0];
                String songName = i[1];
                String songImg = i[4];

                ImageIcon originalIcon = new ImageIcon(FilePath.getSongCoverImgPath() + songImg);
                Image scaledImg = originalIcon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImg);

                songButton = new JButton(scaledIcon);
                songButton.setPreferredSize(new Dimension(120, 120));
                songButton.setBackground(new Color(235, 215, 255));
                songButton.setForeground(Color.BLACK);
                songButton.setBorderPainted(false);
                songButton.setFocusPainted(false);
                songButton.setActionCommand("Song");
                songButton.addActionListener(new ASongGridBtnActions(mainContentPanel,songId,songName));
                add(songButton);

                songTitle = new JLabel(songName);
                songTitle.setForeground(Color.WHITE);
                add(songTitle);
            }
        }
        catch(Exception e){
            System.out.println("Song Grid Panel UI function  Error: "+e);
        }
    }
}
