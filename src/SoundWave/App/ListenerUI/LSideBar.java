package SoundWave.App.ListenerUI;

import SoundWave.App.ListenerUI.Actions.LCreatePlayListBtnActions;
import SoundWave.App.ListenerUI.Actions.LSideBarBtnActions;
import SoundWave.User.Listener;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class LSideBar extends JPanel {
    private GridBagConstraints gridBag;
    private LMainContent mc;
    private JLabel listenerLabel, playListLabel;
    private JButton homeBtn, playListBtn,createPlaylistBtn,logOutBtn;
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

            this.gridBag = new GridBagConstraints();
            gridBag.insets = new Insets(10, 10, 10, 10);
            gridBag.fill = GridBagConstraints.HORIZONTAL;


            gridBag.gridy = 0;
            gridBag.gridx = 0;
            this.listenerLabel = new JLabel("Welcome "+userName, SwingConstants.CENTER);
            listenerLabel.setForeground(Color.WHITE);
            listenerLabel.addMouseListener(new LSideBarBtnActions(userName,mc));
            add(listenerLabel, gridBag);

            gridBag.gridy++;
            add(Box.createVerticalStrut(10), gridBag);

            homeBtn();
            gridBag.gridy++;

            playListLabel();
            gridBag.gridy++;

            playlists();
            createPlayListBtn();

            gridBag.weighty = 1.0;
            add(Box.createVerticalGlue(), gridBag);
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

            gridBag.gridy++;
            add(homeBtn, gridBag);
        } catch (Exception e) {
            System.out.println("Side Bar home Btn method Error: " + e);
        }
    }
    private void playListLabel() {
        try {
            this.playListLabel = new JLabel("PlayLists");
            playListLabel.setForeground(Color.WHITE);
            playListLabel.setFont(new Font("Font.SERIF", Font.BOLD, 17));

            gridBag.gridy++;
            add(playListLabel, gridBag);
        } catch (Exception e) {
            System.out.println("Side Bar playList Label method Error: " + e);
        }
    }
    private void playlists() {
        try {
            Listener listener = new Listener();
            ArrayList<String[]> playlists = listener.viewAllPlayList(listenerId);
            for (String[] i : playlists) {
                String playlistName = i[1];
                String playlistId = i[0];

                this.playListBtn = new JButton(playlistName);
                playListBtn.setBackground(new Color(224, 143, 255));
                playListBtn.setFocusPainted(false);
                playListBtn.setBorderPainted(false);
                playListBtn.setActionCommand("PlayList");
                 playListBtn.addActionListener(new LSideBarBtnActions(mc,playlistId,listenerId));

                gridBag.gridy++;
                add(playListBtn, gridBag);
            }
        } catch (Exception e) {
            System.out.println("Side Bar playlists method Error: " + e);
        }
    }
    private void createPlayListBtn(){
        try{
            gridBag.gridy++;
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

            add(createPlaylistBtn, gridBag);
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
            gridBag.weighty = 0;
            gridBag.gridy++;
            this.logOutBtn = new JButton("LogOut");
            logOutBtn.setMargin(new Insets(7, 10, 7, 10));
            logOutBtn.setBackground(new Color(224, 143, 255));
            logOutBtn.setForeground(Color.BLACK);
            logOutBtn.setBorderPainted(false);
            logOutBtn.setFocusPainted(false);
            logOutBtn.setActionCommand("LogOut");
            logOutBtn.addActionListener(new LSideBarBtnActions(mc,null,listenerId));
            add(logOutBtn, gridBag);
        }catch (Exception e){
            System.out.println("Side Bar Panel logoutBtn method Error: "+e);
        }
    }
}
