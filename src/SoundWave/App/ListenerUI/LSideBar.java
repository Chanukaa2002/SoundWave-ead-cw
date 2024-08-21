package SoundWave.App.ListenerUI;

import SoundWave.App.ListenerUI.Actions.LSideBarBtnActions;
import SoundWave.App.ListenerUI.Actions.ListenSongBtnsActions;

import javax.swing.*;
import java.awt.*;

public class LSideBar extends JPanel {
    GridBagConstraints gbc;
    LMainContent mc;
    JLabel listenerLabel, playListLabel;
    JButton homeBtn, playListBtn,createPlaylistBtn,logOutBtn;

    public LSideBar(LMainContent mc) {
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
            listenerLabel.addMouseListener(new LSideBarBtnActions(mc));
            add(listenerLabel, gbc);


            // space
            gbc.gridy++;
            add(Box.createVerticalStrut(10), gbc);

            // home btn
            homeBtn();
            gbc.gridy++;
            // Label
            playListLabel();
            gbc.gridy++;
            // playlists
            playlists();
            //create playlist btn
            createPlayListBtn();

            gbc.weighty = 1.0;
            add(Box.createVerticalGlue(), gbc);
            logOutBtn();
            // Force revalidate and repaint to ensure proper layout
            revalidate();
            repaint();
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
            homeBtn.addActionListener(new LSideBarBtnActions(mc));

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
            for (int i = 0; i < 8; i++) {
                this.playListBtn = new JButton("PlayList " + (i + 1));

                playListBtn.setBackground(new Color(224, 143, 255));
                playListBtn.setFocusPainted(false);
                playListBtn.setBorderPainted(false);
                playListBtn.setActionCommand("PlayList");
                 playListBtn.addActionListener(new LSideBarBtnActions(mc));

                gbc.gridy++;
                add(playListBtn, gbc);
            }
        } catch (Exception e) {
            System.out.println("Side Bar playlists method Error: " + e);
        }
    }
    private void createPlayListBtn(){
        try{
            gbc.gridy++;
            this.createPlaylistBtn = new JButton("Create PlayList");
            createPlaylistBtn.setMargin(new Insets(7,1,7,1));
            createPlaylistBtn.setSize(new Dimension(40,20));
            createPlaylistBtn.setBackground(new Color(224, 143, 255));
            createPlaylistBtn.setForeground(Color.BLACK);
            createPlaylistBtn.setFocusPainted(false);
            createPlaylistBtn.setBorderPainted(false);
            createPlaylistBtn.setActionCommand("CreatePlayList");
            //action->
            createPlaylistBtn.addActionListener(new LSideBarBtnActions(mc));
            add(createPlaylistBtn,gbc);
        }catch (Exception e){
            System.out.println("Side Bar create playlist method Error: "+e);
        }
    }
    private void logOutBtn(){
        try {
            gbc.weighty = 0;
            gbc.gridy++;
            this.logOutBtn = new JButton("LogOut");
            logOutBtn.setMargin(new Insets(7, 10, 7, 10));
            logOutBtn.setBackground(new Color(224, 143, 255)); // Light purple background
            logOutBtn.setForeground(Color.BLACK);
            logOutBtn.setBorderPainted(false);
            logOutBtn.setFocusPainted(false);
            logOutBtn.setActionCommand("LogOut");
            logOutBtn.addActionListener(new LSideBarBtnActions(mc));
            add(logOutBtn, gbc);
        }catch (Exception e){
            System.out.println("Side Bar Panel logoutBtn method Error: "+e);
        }
    }
}
