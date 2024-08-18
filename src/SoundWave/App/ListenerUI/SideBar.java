package SoundWave.App.ListenerUI;

import javax.swing.*;
import java.awt.*;

public class SideBar extends JPanel {
    GridBagConstraints gbc;
    MainContent mc;
    JLabel listenerLabel, playListLabel;
    JButton homeBtn, playListBtn;

    public SideBar(MainContent mc) {
        this.mc = mc;
        UI();
    }

    private void UI() {
        try {
            setLayout(new GridBagLayout());
            setBackground(new Color(76, 83, 93));
            setPreferredSize(new Dimension(200, 600));

            this.gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Listener Label fixed at the top
            gbc.gridy = 0;
            gbc.gridx = 0;
            this.listenerLabel = new JLabel("Listener UserName", SwingConstants.CENTER);
            listenerLabel.setForeground(Color.WHITE);
            add(listenerLabel, gbc);


            // space
            gbc.gridy++;
            add(Box.createVerticalStrut(20), gbc);

            // home btn
            homeBtn();
            gbc.gridy++;
            // Label
            playListLabel();
            gbc.gridy++;
            // playlists
            playlists();
        } catch (Exception e) {
            System.out.println("Side Bar UI method Error: " + e);
        }
    }

    private void homeBtn() {
        try {
            this.homeBtn = new JButton("Home");

            homeBtn.setMargin(new Insets(0, 10, 7, 10));
            homeBtn.setBackground(new Color(224, 143, 255));
            homeBtn.setForeground(Color.BLACK);
            homeBtn.setFocusPainted(false);
            homeBtn.setBorderPainted(false);
            homeBtn.setActionCommand("Home");
            // homeBtn.addActionListener(new SideBarBtnActions(mc));

            gbc.gridy++; // Move to the next row
            add(homeBtn, gbc);
        } catch (Exception e) {
            System.out.println("Side Bar home Btn method Error: " + e);
        }
    }

    private void playListLabel() {
        try {
            this.playListLabel = new JLabel("PlayLists");
            playListLabel.setForeground(Color.WHITE);
            playListLabel.setFont(new Font("Font.SERIF", Font.BOLD, 17));

            gbc.gridy++; // Move to the next row
            add(playListLabel, gbc);
        } catch (Exception e) {
            System.out.println("Side Bar playList Label method Error: " + e);
        }
    }

    private void playlists() {
        try {
            for (int i = 0; i < 5; i++) {
                this.playListBtn = new JButton("PlayList " + (i + 1));

                playListBtn.setBackground(new Color(224, 143, 255));
                playListBtn.setFocusPainted(false);
                playListBtn.setBorderPainted(false);
                // playListBtn.addActionListener(new SongGridBtnActions(mainContentPanel));

                gbc.gridy++;
                add(playListBtn, gbc);
            }
        } catch (Exception e) {
            System.out.println("Side Bar playlists method Error: " + e);
        }
    }

    private void createPlayListBtn(){}

    private void logOutBtn(){}
}
