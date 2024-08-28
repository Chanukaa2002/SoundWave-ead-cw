package SoundWave.App.ListenerUI;

import SoundWave.App.ListenerUI.Actions.ListenSongBtnsActions;
import SoundWave.App.UserUI.FilePath;
import SoundWave.Music.Feedback;
import SoundWave.Music.Song;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LListenSongPanel extends JPanel {

    private GridBagConstraints gridBag;
    private JPanel controlPanel,likePanel;
    private JButton coverImage,playBtn,pauseBtn,nextBtn, previousBtn,likedBtn,disLikedBtn;
    private JLabel likeCountLbl,titleLbl;
    private JProgressBar songProgressBar;
    private JSlider volumeSlider;
    private LMainContent mc;
    private String songId;
    private String likeCount;
    private String songDetails[];
    private String listenerId;
    private Feedback feedback;

    public JSlider getVolumeSlider() {
        return volumeSlider;
    }
    public JButton getPlayBtn() {
        return playBtn;
    }
    public JButton getPauseBtn() {
        return pauseBtn;
    }
    public JButton getLikedBtn() {
        return likedBtn;
    }
    public JButton getDisLikedBtn() {
        return disLikedBtn;
    }
    public JProgressBar getSongProgressBar() {
        return songProgressBar;
    }
    public String[] getSongDetails() {
        return songDetails;
    }

    public LListenSongPanel(LMainContent mc,String songId,String listenerId) throws SQLException {
        this.mc = mc;
        this.songId = songId;
        this.listenerId = listenerId;
        Song song = new Song();
        songDetails =  song.getDetails(songId);
        feedback = new Feedback();
        likeCount= feedback.getFeedbackDetails(songId);
        UI();
    }
    private void UI(){
        try{
            setLayout(new GridBagLayout());
            setBackground(new Color(58,65,74));
            this.gridBag = new GridBagConstraints();
            gridBag.insets = new Insets(10,10,10,10);
            gridBag.fill = GridBagConstraints.HORIZONTAL;

            coverImage();
            likeCount();
            progressBar();
            volume();
            controlSong();

        }catch(Exception e){
            System.out.println("Listen Song UI method Error: "+e);
        }
    }
    private void customizeButton(JButton button) {
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
    }
    private void coverImage(){
        try{
            String songImg = songDetails[4];
            ImageIcon originalIcon = new ImageIcon(FilePath.getSongCoverImgPath() +songImg);
            Image scaledImg = originalIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImg);

            gridBag.gridy=0;
            gridBag.gridx=0;
            coverImage = new JButton(scaledIcon);
            coverImage.setBackground(new Color(216,191,216));
            coverImage.setPreferredSize(new Dimension(200,200));
            coverImage.setFocusPainted(false);
            coverImage.setBorderPainted(false);
            add(coverImage, gridBag);


            String title = songDetails[1];
            gridBag.gridy=1;
            gridBag.gridx=0;
            titleLbl = new JLabel(title);
            titleLbl.setForeground(Color.WHITE);
            titleLbl.setFont(new Font("Font.SERIF",Font.ITALIC,16));
            add(titleLbl, gridBag);
        }catch(Exception e){
            System.out.println("Listen Song Cover Image method Error: "+e);
        }
    }
    private void progressBar(){
        try{
            gridBag.gridy=3;
            gridBag.gridx=0;
            gridBag.gridwidth = 2;
            songProgressBar = new JProgressBar(0, 100);
            songProgressBar.setForeground(new Color(224, 143, 255));
            songProgressBar.setStringPainted(false);
            songProgressBar.setPreferredSize(new Dimension(300, 10));
            add(songProgressBar, gridBag);
        }catch(Exception e){
            System.out.println("Listen Song Progressbar method Error: "+e);
        }
    }
    private void volume(){
        try{
            gridBag.gridy = 1;
            gridBag.gridx = 2;
            gridBag.gridheight = 2;
            volumeSlider = new JSlider(JSlider.VERTICAL, 0, 2000, 1000);
            volumeSlider.setPaintTicks(false);
            volumeSlider.setPaintLabels(false);
            volumeSlider.setPreferredSize(new Dimension(20, 125));
            volumeSlider.setBackground(new Color(58, 65, 74));
            volumeSlider.setForeground(new Color(58, 65, 74));
            volumeSlider.addChangeListener(new ListenSongBtnsActions(this,listenerId));

            add(volumeSlider, gridBag);
        }catch(Exception e){
            System.out.println("Listen Song Volume method Error: "+e);
        }
    }
    private void likeCount(){
        try{
            feedback = new Feedback();
            boolean isLiked = feedback.isLiked(songId,listenerId);
            ImageIcon likedIcon = new ImageIcon(FilePath.like());
            ImageIcon unLikedIcon = new ImageIcon(FilePath.dislike());

            likedBtn = new JButton(likedIcon);
            disLikedBtn = new JButton(unLikedIcon);

            customizeButton(likedBtn);
            customizeButton(disLikedBtn);

            likePanel = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));
            likePanel.setBackground(new Color(58,65,74));
            if(isLiked){
                disLikedBtn.setVisible(false);
            }else{
                likedBtn.setVisible(false);
            }
            likePanel.add(likedBtn);
            likePanel.add(disLikedBtn);

            gridBag.gridx=0;
            gridBag.gridy=2;
            gridBag.anchor=GridBagConstraints.WEST;


            likedBtn.setActionCommand("Like");
            likedBtn.addActionListener(new ListenSongBtnsActions(this,listenerId));

            disLikedBtn.setActionCommand("DisLike");
            disLikedBtn.addActionListener(new ListenSongBtnsActions(this,listenerId));

            add(likePanel, gridBag);


            gridBag.gridx = 1;
            gridBag.gridy = 2;

            likeCountLbl = new JLabel(likeCount);
            likeCountLbl.setForeground(Color.WHITE);
            likeCountLbl.setFont(new Font("Font.SERIF", Font.ITALIC, 16));
            add(likeCountLbl, gridBag);

        }catch(Exception e){
            System.out.println("Listen Song Like Count method Error: "+e);
        }
    }
    private void controlSong(){
        try{
            ImageIcon playIcon = new ImageIcon(FilePath.playBtn());
            ImageIcon pauseIcon = new ImageIcon(FilePath.stopBtn());
            ImageIcon nextIcon = new ImageIcon(FilePath.next());
            ImageIcon previousIcon = new ImageIcon(FilePath.back());


            playBtn = new JButton(playIcon);
            pauseBtn = new JButton(pauseIcon);
            nextBtn = new JButton(nextIcon);
            previousBtn = new JButton(previousIcon);

            customizeButton(playBtn);
            customizeButton(pauseBtn);
            customizeButton(nextBtn);
            customizeButton(previousBtn);

            controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,0));
            controlPanel.setBackground(new Color(58,65,74));

            pauseBtn.setVisible(false);

            controlPanel.add(previousBtn);
            controlPanel.add(playBtn);
            controlPanel.add(pauseBtn);
            controlPanel.add(nextBtn);

            gridBag.gridx=0;
            gridBag.gridy=4;
            gridBag.gridwidth=2;
            gridBag.anchor=GridBagConstraints.CENTER;

            playBtn.setActionCommand("Play");
            playBtn.addActionListener(new ListenSongBtnsActions(this,listenerId));

            pauseBtn.setActionCommand("Stop");
            pauseBtn.addActionListener(new ListenSongBtnsActions(this,listenerId));

            nextBtn.setActionCommand("Next");
            nextBtn.addActionListener(new ListenSongBtnsActions(this,listenerId));

            previousBtn.setActionCommand("Back");
            previousBtn.addActionListener(new ListenSongBtnsActions(this,listenerId));

            add(controlPanel, gridBag);
        }catch(Exception e){
            System.out.println("Listen Song control Song method Error: "+e);
        }
    }

}
