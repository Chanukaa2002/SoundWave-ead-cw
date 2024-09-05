package SoundWave.App.ListenerUI;

import SoundWave.App.ListenerUI.Actions.LViewPlaylistBtnActions;
import SoundWave.App.UserUI.FilePath;
import SoundWave.Music.PlayList;
import SoundWave.User.Listener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LViewPlayListPanel extends JPanel {

    private GridBagConstraints gridBag;
    private JLabel coverImg,titleLbl,playlistSongLbl,exploreSongLbl;
    private JButton playBtn,stopBtn;
    private JScrollPane playlistSongScroll,exploreSongScroll;
    private JPanel topPanel,leftPanel,rightPanel;
    private PlayList playlist;
    private Listener listener;
    private String playlistId;
    private ArrayList<String[]> playlistSongs;
    private String[] list;
    private List<String[]> songList;

    public JButton getPlayBtn() {
        return playBtn;
    }
    public JButton getStopBtn() {
        return stopBtn;
    }


    private void customizeButton(JButton button) {
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
    }
    public void refreshPanel(){
        removeAll();
        UI();
        revalidate();
        repaint();
    }
    public  LViewPlayListPanel(String playlistId) {
        try{
            this.playlistId = playlistId;
            this.playlist = new PlayList();
            this.listener = new Listener();
            list = listener.viewPlayList(playlistId);
            UI();
        }
        catch (Exception e){
            System.out.println("LViewPlayListPanel constructor error: "+e);
        }
    }
    private void UI(){
        try {
            setLayout(new GridBagLayout());
            setBackground(new Color(58, 65, 74));
            this.gridBag = new GridBagConstraints();
            gridBag.insets = new Insets(10, 10, 10, 10);
            gridBag.fill = GridBagConstraints.HORIZONTAL;

            topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            topPanel.setBackground(new Color(58, 65, 74));

            leftPanel = new JPanel(new BorderLayout());
            leftPanel.setBackground(new Color(224, 143, 255));

            rightPanel = new JPanel(new BorderLayout());
            rightPanel.setBackground(new Color(224, 143, 255));

            gridBag.gridy=0;
            gridBag.gridx=0;
            gridBag.gridwidth=3;
            gridBag.anchor = GridBagConstraints.NORTH;
            add(topPanel, gridBag);

            gridBag.gridy=1;
            gridBag.gridx=0;
            gridBag.gridwidth=1;
            gridBag.anchor = GridBagConstraints.WEST;
            add(leftPanel, gridBag);

            gridBag.gridy=1;
            gridBag.gridx=2;
            gridBag.anchor = GridBagConstraints.EAST;
            add(rightPanel, gridBag);

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
            String playlistImg = list[2];
            ImageIcon originalIcon = new ImageIcon(FilePath.getPlayListCoverImgPath() +playlistImg);
            Image scaledImg = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImg);

            coverImg = new JLabel(scaledIcon);
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
            titleLbl = new JLabel(list[1]);
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
            ImageIcon playIcon = new ImageIcon(FilePath.playBtn());
            ImageIcon stopIcon = new ImageIcon(FilePath.stopBtn());

            playBtn = new JButton(playIcon);
            stopBtn = new JButton(stopIcon);

            customizeButton(playBtn);
            customizeButton(stopBtn);

            stopBtn.setVisible(false);

            topPanel.add(playBtn);
            topPanel.add(stopBtn);

            playBtn.setActionCommand("Play");
            stopBtn.setActionCommand("Stop");

            playBtn.setActionCommand("Play");
            stopBtn.setActionCommand("Stop");

            playBtn.addActionListener(new LViewPlaylistBtnActions(null,playlistId,this));
            stopBtn.addActionListener(new LViewPlaylistBtnActions(null,playlistId,this));
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

            this.playlistSongs =  playlist.getSongList(playlistId);
            for (String[] i : playlistSongs) {
                JPanel songPanel = new JPanel(new BorderLayout());
                songPanel.setBackground(new Color(232, 213, 255));
                JLabel songTitle = new JLabel( i[1]);

                String songId = i[0];
                songTitle.setPreferredSize(new Dimension(50, 30));
                songPanel.add(songTitle, BorderLayout.CENTER);

                JButton deleteBtn = new JButton("x");
                deleteBtn.setPreferredSize(new Dimension(50, 30));
                deleteBtn.setBackground(Color.RED);
                deleteBtn.setForeground(Color.WHITE);
                deleteBtn.setActionCommand("Delete");
                deleteBtn.addActionListener(new LViewPlaylistBtnActions(songId,playlistId,this));
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

            this.songList =  listener.exploreSongPlaylist(playlistId);


            for (String[] i : songList) {
                JPanel songPanel = new JPanel(new BorderLayout());
                songPanel.setBackground(new Color(232, 213, 255));

                JLabel songTitle = new JLabel(i[1]);
                songTitle.setPreferredSize(new Dimension(75, 30));
                songPanel.add(songTitle, BorderLayout.CENTER);

                JButton addBtn = new JButton("Add");
                addBtn.setPreferredSize(new Dimension(75, 30));
                addBtn.setBackground(Color.GREEN);
                addBtn.setForeground(Color.WHITE);
                addBtn.setActionCommand("Add");

                String songId = i[0];

                addBtn.addActionListener( new LViewPlaylistBtnActions(songId,playlistId,this));
                songPanel.add(addBtn, BorderLayout.EAST);


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
