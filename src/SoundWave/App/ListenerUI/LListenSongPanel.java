package SoundWave.App.ListenerUI;

import SoundWave.App.ListenerUI.Actions.ListenSongBtnsActions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LListenSongPanel extends JPanel {

    private GridBagConstraints gbc;
    private JPanel controlPanel,likePanel;
    private JButton coverImage,playBtn,pauseBtn,nextBtn, previousBtn,likedBtn,disLikedBtn;
    private JLabel likeCountLbl,titleLbl;
    private JProgressBar songProgressBar;
    private JSlider volumeSlider;
    private LMainContent mc;

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

    public LListenSongPanel(LMainContent mc){
        this.mc = mc;
        UI();
    }
    private void UI(){
        try{
            setLayout(new GridBagLayout());
            setBackground(new Color(58,65,74));
            this.gbc = new GridBagConstraints();
            gbc.insets = new Insets(10,10,10,10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            //coverImage
            coverImage();
            //like
            likeCount();
            //progress Bar
            progressBar();
            //volume control
            volume();
            //Song Control
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
            //img
            gbc.gridy=0;
            gbc.gridx=0;
            coverImage = new JButton();
            coverImage.setBackground(new Color(216,191,216));
            coverImage.setPreferredSize(new Dimension(200,200));
            coverImage.setFocusPainted(false);
            coverImage.setBorderPainted(false);
            add(coverImage,gbc);

            //title

            gbc.gridy=1;
            gbc.gridx=0;
            titleLbl = new JLabel("Title");
            titleLbl.setForeground(Color.WHITE);
            titleLbl.setFont(new Font("Font.SERIF",Font.ITALIC,16));
            add(titleLbl,gbc);
        }catch(Exception e){
            System.out.println("Listen Song Cover Image method Error: "+e);
        }
    }
    private void progressBar(){
        try{
            gbc.gridy=3;
            gbc.gridx=0;
            gbc.gridwidth = 2;
            songProgressBar = new JProgressBar(0, 100);
            songProgressBar.setForeground(new Color(224, 143, 255));
            songProgressBar.setValue(50);
            songProgressBar.setStringPainted(false);
            songProgressBar.setPreferredSize(new Dimension(300, 10));
            add(songProgressBar, gbc);
        }catch(Exception e){
            System.out.println("Listen Song UI method Error: "+e);
        }
    }
    private void volume(){
        try{
            // Volume slider
            gbc.gridy = 1;
            gbc.gridx = 2;
            gbc.gridheight = 2;
            volumeSlider = new JSlider(JSlider.VERTICAL, 0, 100, 50);
            volumeSlider.setPaintTicks(false);
            volumeSlider.setPaintLabels(false);
            volumeSlider.setPreferredSize(new Dimension(20, 125));
            volumeSlider.setBackground(new Color(58, 65, 74));
            volumeSlider.setForeground(new Color(58, 65, 74));

            // Add the slider to the panel
            add(volumeSlider, gbc);
        }catch(Exception e){
            System.out.println("Listen Song Volume method Error: "+e);
        }
    }
    private void likeCount(){
        try{
            //btn
            ImageIcon likedIcon = new ImageIcon("C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/SrcImg/liked.png");
            ImageIcon unLikedIcon = new ImageIcon("C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/SrcImg/notLiked.png");

            likedBtn = new JButton(likedIcon);
            disLikedBtn = new JButton(unLikedIcon);

            customizeButton(likedBtn);
            customizeButton(disLikedBtn);

            likePanel = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));
            likePanel.setBackground(new Color(58,65,74));

            likedBtn.setVisible(false);

            likePanel.add(likedBtn);
            likePanel.add(disLikedBtn);

            gbc.gridx=0;
            gbc.gridy=2;
            gbc.anchor=GridBagConstraints.WEST;

            //actions
            likedBtn.setActionCommand("Like");
            likedBtn.addActionListener(new ListenSongBtnsActions(this));

            disLikedBtn.setActionCommand("DisLike");
            disLikedBtn.addActionListener(new ListenSongBtnsActions(this));

            add(likePanel,gbc);

            //lbl
            gbc.gridx = 1;
            gbc.gridy = 2;

            likeCountLbl = new JLabel("0");
            likeCountLbl.setForeground(Color.WHITE);
            likeCountLbl.setFont(new Font("Font.SERIF", Font.ITALIC, 16));
            add(likeCountLbl, gbc);

        }catch(Exception e){
            System.out.println("Listen Song Like Count method Error: "+e);
        }
    }
    private void controlSong(){
        try{

            // Load icons
            ImageIcon playIcon = new ImageIcon("C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/SrcImg/play.png");
            ImageIcon pauseIcon = new ImageIcon("C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/SrcImg/pause.png");
            ImageIcon nextIcon = new ImageIcon("C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/SrcImg/next.png");
            ImageIcon previousIcon = new ImageIcon("C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/SrcImg/back.png");


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

            gbc.gridx=0;
            gbc.gridy=4;
            gbc.gridwidth=2;
            gbc.anchor=GridBagConstraints.CENTER;

            //actions
            playBtn.setActionCommand("Play");
            playBtn.addActionListener(new ListenSongBtnsActions(this));

            pauseBtn.setActionCommand("Stop");
            pauseBtn.addActionListener(new ListenSongBtnsActions(this));

            nextBtn.setActionCommand("Next");
            nextBtn.addActionListener(new ListenSongBtnsActions(this));

            previousBtn.setActionCommand("Back");
            previousBtn.addActionListener(new ListenSongBtnsActions(this));

            add(controlPanel,gbc);
        }catch(Exception e){
            System.out.println("Listen Song control Song method Error: "+e);
        }
    }

}
