package SoundWave.App.ArtistUI;

import javax.swing.*;
import java.awt.*;

public class ASongUpdatePanel_remove_that extends JPanel {
    private JButton uploadBtn, coverImgBtn, updateBtn, cancelBtn;
    private JTextField songNameTxt;
    private JLabel titleLbl;
    GridBagConstraints gbc;

    public ASongUpdatePanel_remove_that() {
       UI();

    }
    private void UI(){
        try{
            setLayout(new GridBagLayout());
            setBackground(new Color(58, 65, 74));
            this.gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = 0;

            // Cover Image Button
            coverImg();

            // Upload  Song Button
//            uploadSongBtn();

            // Song details
            songDetails();

            // Spacer
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add(Box.createVerticalStrut(20), gbc);

            // Create a panel
            bottomPanel();
        }
        catch(Exception e){
            System.out.println("Song Update Panel UI function Error: " + e);
        }
    }
    private void coverImg(){
        try{
            coverImgBtn = new JButton("Cover Image");
            coverImgBtn.setPreferredSize(new Dimension(200, 200));
            coverImgBtn.setBackground(new Color(216, 191, 216));
            coverImgBtn.setFocusPainted(false);
            coverImgBtn.setBorderPainted(false);
            add(coverImgBtn, gbc);
        }catch(Exception e){
            System.out.println("Song Update panel cover Image function Error:  "+e);
        }
    }
    private void bottomPanel(){
        try{
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
            buttonPanel.setBackground(new Color(58, 65, 74));

            // update Button
            updateBtn = new JButton("Update");
            updateBtn.setBackground(new Color(224, 143, 255));
            buttonPanel.add(updateBtn);
            updateBtn.setFocusPainted(false);
            updateBtn.setBorderPainted(false);

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
            System.out.println("Song Update Panel Bottom Panel Error: "+e);
        }
    }
    private void songDetails(){
        try{
            titleLbl  = new JLabel("Title");
            titleLbl.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.WEST;
            add(titleLbl, gbc);

            // Song Title Text Field
            songNameTxt = new JTextField();
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add(songNameTxt, gbc);
        }
        catch(Exception e){
            System.out.println("Song Update Panel Song Detail function Error: "+e);
        }
    }
//    private void uploadSongBtn(){
//        try{
//            uploadBtn = new JButton("Upload Your Song");
//            uploadBtn.setBackground(Color.WHITE);
//            uploadBtn.setFocusPainted(false);
//            uploadBtn.setBorderPainted(false);
//            gbc.gridy = 1;
//            add(uploadBtn, gbc);
//        }catch(Exception e){
//            System.out.println("Song Update Panel upload Song FUnction Error: "+e);
//        }
//    }
}
