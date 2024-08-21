package SoundWave.App.ListenerUI;

import javax.swing.*;
import java.awt.*;

public class LViewPlayListPanel extends JPanel {

    GridBagConstraints gbc;
    JLabel coverImg,titleLbl,playlistSongLbl,exploreSongLbl;
    JButton playBtn,stopBtn;
    JScrollPane playlistSongScroll,exploreSongScroll;
    JPanel topPanel,leftPanel,rightPanel;
    LMainContent mc;

    private void customizeButton(JButton button) {
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
    }

    public  LViewPlayListPanel(LMainContent mc){
        this.mc = mc;
        UI();
    }
    private void UI(){
        try {
            setLayout(new GridBagLayout());
            setBackground(new Color(58, 65, 74));
            this.gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            topPanel.setBackground(new Color(58, 65, 74));

            leftPanel = new JPanel(new BorderLayout());
            leftPanel.setBackground(new Color(224, 143, 255));

            rightPanel = new JPanel(new BorderLayout());
            rightPanel.setBackground(new Color(224, 143, 255));

            gbc.gridy=0;
            gbc.gridx=0;
            gbc.gridwidth=3;
            gbc.anchor = GridBagConstraints.NORTH;
            add(topPanel,gbc);

            gbc.gridy=1;
            gbc.gridx=0;
            gbc.gridwidth=1;
            gbc.anchor = GridBagConstraints.WEST;
            add(leftPanel,gbc);

            gbc.gridy=1;
            gbc.gridx=2;
            gbc.anchor = GridBagConstraints.EAST;
            add(rightPanel,gbc);

            coverImg();
            title();
            controlBtn();
            playlistSongs();
            searchSong();


        }catch (Exception e){
            System.out.println("View PlayList UI method Error: "+e);
        }
    }
    private void coverImg(){
        try{
            ImageIcon icon = new ImageIcon();
            coverImg = new JLabel("hello world");
            coverImg.setPreferredSize(new Dimension(150,150));
            coverImg.setBackground(new Color(224, 143, 255));
            coverImg.setOpaque(true);
            coverImg.setForeground(Color.WHITE);
            topPanel.add(coverImg);
        }
        catch (Exception e){
            System.out.println("View Playlist CoverImage method Error: "+e);
        }
    }
    private void title(){
        try{
            titleLbl = new JLabel("Title");
            titleLbl.setForeground(Color.WHITE);
            titleLbl.setFont(new Font("Font.SERIF", Font.BOLD,18));

            topPanel.add(titleLbl);
        }
        catch (Exception e){
            System.out.println("View Playlist Title method Error: "+e);
        }
    }
    private void controlBtn(){
        try{
            ImageIcon playIcon = new ImageIcon("C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/SrcImg/play.png");
            ImageIcon stopIcon = new ImageIcon("C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/SrcImg/pause.png");

            playBtn = new JButton(playIcon);
            stopBtn = new JButton(stopIcon);

            customizeButton(playBtn);
            customizeButton(stopBtn);

            stopBtn.setVisible(false);

            topPanel.add(playBtn);
            topPanel.add(stopBtn);

            playBtn.setActionCommand("Play");
            stopBtn.setActionCommand("Stop");



        }
        catch (Exception e){
            System.out.println("View Playlist controlBtn method Error: "+e);
        }
    }
    private void playlistSongs() {
        try {
            playlistSongLbl = new JLabel("Playlist Songs");
            playlistSongLbl.setForeground(Color.BLACK);
            leftPanel.add(playlistSongLbl, BorderLayout.NORTH);

            JPanel playlistSongsPanel = new JPanel();
            playlistSongsPanel.setLayout(new BoxLayout(playlistSongsPanel, BoxLayout.Y_AXIS));
            playlistSongsPanel.setBackground(new Color(232, 213, 255));

            for (int i = 0; i < 400; i++) {
                JPanel songPanel = new JPanel(new BorderLayout());
                songPanel.setBackground(new Color(232, 213, 255));

                JLabel songTitle = new JLabel("Song Title: " + (i + 1));
                songTitle.setPreferredSize(new Dimension(50, 30));
                songPanel.add(songTitle, BorderLayout.CENTER);

                JButton deleteBtn = new JButton("x");
                deleteBtn.setPreferredSize(new Dimension(50, 30));
                deleteBtn.setBackground(Color.RED);
                deleteBtn.setForeground(Color.WHITE);
                songPanel.add(deleteBtn, BorderLayout.EAST);

                playlistSongsPanel.add(songPanel);
            }

            playlistSongScroll = new JScrollPane(playlistSongsPanel);
            playlistSongScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            playlistSongScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            playlistSongScroll.setPreferredSize(new Dimension(200, 300));

            leftPanel.add(playlistSongScroll, BorderLayout.CENTER);
        } catch (Exception e) {
            System.out.println("View Playlist PlaylistSongs method Error: " + e);
        }
    }
    private void searchSong(){
        try{
            exploreSongLbl = new JLabel("Explore Songs");
            exploreSongLbl.setForeground(Color.BLACK);
            rightPanel.add(exploreSongLbl, BorderLayout.NORTH);

            JPanel exploreSongPanel = new JPanel();
            exploreSongPanel.setLayout(new BoxLayout(exploreSongPanel, BoxLayout.Y_AXIS));
            exploreSongPanel.setBackground(new Color(232, 213, 255));

            for (int i = 0; i < 40; i++) {
                JPanel songPanel = new JPanel(new BorderLayout());
                songPanel.setBackground(new Color(232, 213, 255));

                JLabel songTitle = new JLabel("Song Title: " + (i + 1));
                songTitle.setPreferredSize(new Dimension(75, 30));
                songPanel.add(songTitle, BorderLayout.CENTER);

                JButton deleteBtn = new JButton("Add");
                deleteBtn.setPreferredSize(new Dimension(75, 30));
                deleteBtn.setBackground(Color.GREEN);
                deleteBtn.setForeground(Color.WHITE);
                songPanel.add(deleteBtn, BorderLayout.EAST);

                exploreSongPanel.add(songPanel);
            }

            exploreSongScroll = new JScrollPane(exploreSongPanel);
            exploreSongScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            exploreSongScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            exploreSongScroll.setPreferredSize(new Dimension(200, 300));

            rightPanel.add(exploreSongScroll, BorderLayout.CENTER);
        }
        catch (Exception e){
            System.out.println("View Playlist Search song method Error: "+e);
        }
    }


}
