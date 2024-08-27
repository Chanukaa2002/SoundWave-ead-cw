package SoundWave.App.ListenerUI;

import SoundWave.App.ListenerUI.Actions.LCreatePlayListBtnActions;
import SoundWave.App.ListenerUI.Actions.LSideBarBtnActions;
import SoundWave.App.ListenerUI.Actions.ListenSongBtnsActions;
import SoundWave.User.Listener;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class LSideBar extends JPanel {
    GridBagConstraints gbc;
    LMainContent mc;
    JLabel listenerLabel, playListLabel;
    JButton homeBtn, playListBtn,createPlaylistBtn,logOutBtn;
    private String userName,listenerId;
    private Listener listener;

    public LSideBar(LMainContent mc,String userName) throws SQLException {
        this.mc = mc;
        this.userName = userName;

        listener = new Listener();
        listenerId = listener.getId(userName);
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


            gbc.gridy = 0;
            gbc.gridx = 0;
            this.listenerLabel = new JLabel("Welcome "+userName, SwingConstants.CENTER);
            listenerLabel.setForeground(Color.WHITE);
            listenerLabel.addMouseListener(new LSideBarBtnActions(userName,mc));
            add(listenerLabel, gbc);

            gbc.gridy++;
            add(Box.createVerticalStrut(10), gbc);

            homeBtn();
            gbc.gridy++;

            playListLabel();
            gbc.gridy++;

            playlists();

            createPlayListBtn();

            gbc.weighty = 1.0;
            add(Box.createVerticalGlue(), gbc);
            logOutBtn();

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
            homeBtn.addActionListener(new LSideBarBtnActions(mc,null,listenerId));

            gbc.gridy++;
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

            gbc.gridy++;
            add(playListLabel, gbc);
        } catch (Exception e) {
            System.out.println("Side Bar playList Label method Error: " + e);
        }
    }
    private void playlists() {
        try {
            Listener listener = new Listener();
            ArrayList<String[]> playlists = listener.viewAllPlayList(listenerId);
            for (String[] i : playlists) {
                this.playListBtn = new JButton(i[1]);
                playListBtn.setBackground(new Color(224, 143, 255));
                playListBtn.setFocusPainted(false);
                playListBtn.setBorderPainted(false);
                playListBtn.setActionCommand("PlayList");
                 playListBtn.addActionListener(new LSideBarBtnActions(mc,i[0],listenerId));

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
            createPlaylistBtn.addActionListener(new LCreatePlayListBtnActions( listenerId, this));
            createPlaylistBtn.addActionListener(new LSideBarBtnActions(mc,null,listenerId));

            add(createPlaylistBtn,gbc);
        }catch (Exception e){
            System.out.println("Side Bar create playlist method Error: "+e);
        }
    }
    public void refreshPlaylists() {
        removeAll();
        UI();
    }
    private void logOutBtn(){
        try {
            gbc.weighty = 0;
            gbc.gridy++;
            this.logOutBtn = new JButton("LogOut");
            logOutBtn.setMargin(new Insets(7, 10, 7, 10));
            logOutBtn.setBackground(new Color(224, 143, 255));
            logOutBtn.setForeground(Color.BLACK);
            logOutBtn.setBorderPainted(false);
            logOutBtn.setFocusPainted(false);
            logOutBtn.setActionCommand("LogOut");
            logOutBtn.addActionListener(new LSideBarBtnActions(mc,null,listenerId));
            add(logOutBtn, gbc);
        }catch (Exception e){
            System.out.println("Side Bar Panel logoutBtn method Error: "+e);
        }
    }
}
