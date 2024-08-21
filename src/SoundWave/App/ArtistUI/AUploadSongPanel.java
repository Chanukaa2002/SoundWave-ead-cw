package SoundWave.App.ArtistUI;

import SoundWave.App.ArtistUI.Actions.AUploadSongBtnActions;

import javax.swing.*;
import java.awt.*;

public class AUploadSongPanel extends JPanel {
    private JButton uploadBtn, coverImgBtn, releaseBtn, cancelBtn;
    private JTextField songNameTxt;
    private JLabel titleLbl;
    private JPanel buttonPanel;
    private GridBagConstraints gbc;

    public AUploadSongPanel() {
        UI();
    }
    private void UI() {
        try {
            setLayout(new GridBagLayout());
            setBackground(new Color(58, 65, 74));
            this.gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = 0;

            // Cover Image Button
            setUpCoverImgBtn();
            // Upload Your Song Button
            setUpUploadBtn();
            // Song
            setUpSongUploading();
            // Spacer
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add(Box.createVerticalStrut(20), gbc);

            // Create bottom btn panel
            setUpBottomBtnPanel();

        }
        catch(Exception e){
            System.out.println("Upload song Panel UI method Error: "+e);
            e.getStackTrace()[0].getLineNumber();
        }
    }
    private void setUpCoverImgBtn() {
        try {
            coverImgBtn = new JButton("Cover Image");
            coverImgBtn.setPreferredSize(new Dimension(200, 200));
            coverImgBtn.setBackground(new Color(216, 191, 216));
            coverImgBtn.setFocusPainted(false);
            coverImgBtn.setBorderPainted(false);
            coverImgBtn.setActionCommand("uploadCoverImage");
            coverImgBtn.addActionListener(new AUploadSongBtnActions(coverImgBtn,null));
            add(coverImgBtn, gbc);
        }catch (Exception e){
            System.out.println("Upload Song panel cover Image method Error: "+e);
        }
    }
    private void setUpUploadBtn() {
        try {
            uploadBtn = new JButton("Upload Your Song");
            uploadBtn.setBackground(Color.WHITE);
            uploadBtn.setFocusPainted(false);
            uploadBtn.setBorderPainted(false);
            uploadBtn.setActionCommand("UploadSong");
            uploadBtn.addActionListener(new AUploadSongBtnActions(null,uploadBtn));
            gbc.gridy = 1;
            add(uploadBtn, gbc);
        }catch (Exception e){
            System.out.println("Upload Song Panel uploadBtn method Error: "+e);
        }
    }
    private void setUpBottomBtnPanel(){
        try {
            this.buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
            buttonPanel.setBackground(new Color(58, 65, 74));

            // Release Button
            releaseBtn = new JButton("Release");
            releaseBtn.setBackground(new Color(224, 143, 255));
            releaseBtn.setFocusPainted(false);
            releaseBtn.setBorderPainted(false);
            releaseBtn.setActionCommand("Release");
            releaseBtn.addActionListener(new AUploadSongBtnActions(songNameTxt));
            buttonPanel.add(releaseBtn);

            // Cancel Button
            cancelBtn = new JButton("Cancel");
            cancelBtn.setBackground(new Color(224, 143, 255));
            cancelBtn.setBorderPainted(false);
            cancelBtn.setFocusPainted(false);
            buttonPanel.add(cancelBtn);

            gbc.gridx = 1;
            gbc.gridy = 5;
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.SOUTHEAST;
            gbc.fill = GridBagConstraints.NONE;
            add(buttonPanel, gbc);
        }
        catch (Exception e){
            System.out.println("Upload SOng Panel Bottom panel method Error: "+e);
        }
    }
    private void setUpSongUploading(){
        try {
            titleLbl = new JLabel("Title");
            titleLbl.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.WEST;
            add(titleLbl, gbc);

            songNameTxt = new JTextField();
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add(songNameTxt, gbc);
        }catch (Exception e){
            System.out.println("Upload Song Panel Upload Song method Error: "+e);
        }
    }
}
