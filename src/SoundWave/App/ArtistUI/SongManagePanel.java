package SoundWave.App.ArtistUI;

import SoundWave.App.ArtistUI.Actions.SongManageBtnAction;

import javax.swing.*;
import java.awt.*;

public class SongManagePanel extends JPanel {
    private JLabel titleLbl,likeLbl,likeCountLbl;
    private JButton coverImgBtn,editBtn,deleteBtn;
    private GridBagConstraints gbc;
    private MainContentPanel mcp;

    public SongManagePanel(MainContentPanel mcp){
        this.mcp = mcp;
        UI();
    }
    private void UI(){
        try {
            //setup layout
            setLayout(new GridBagLayout());
            setBackground(new Color(58, 65, 74));
            gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;
            gbc.gridy = 0;
            coverImg();
            titleLbl();
            likes();
            bottomPanel();
        }
        catch(Exception e){
            System.out.println("SongManagePanel UI Function Error: "+e);
        }
    }
    private void titleLbl(){
        try{
        titleLbl = new JLabel("Title");
        titleLbl.setFont(new Font(Font.SERIF,Font.BOLD,20));
        titleLbl.setForeground(Color.WHITE);
        gbc.gridx=1;
        gbc.gridy=0;
        add(titleLbl,gbc);
    }catch(Exception e){
            System.out.println("SOng Model Panel titleLbl function Error: "+e);
        }
    }
    private void likes(){
        try {
            //like label
            likeLbl = new JLabel("Likes");
            likeLbl.setForeground(Color.WHITE);
            likeLbl.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 15));
            gbc.gridx = 0;
            gbc.gridy = 1;
            add(likeLbl, gbc);

            //count label
            likeCountLbl = new JLabel("5");
            likeCountLbl.setForeground(Color.WHITE);
            likeCountLbl.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
            gbc.gridx = 1;
            gbc.gridy = 1;
            add(likeCountLbl, gbc);
        }
        catch(Exception e){
            System.out.println("Song Manage Panel Like function Error: "+e);
        }
    }
    private void coverImg(){
        try {
//        ImageIcon i = new ImageIcon("C:/Chanuka/NIBM/EAD/test.png");
            coverImgBtn = new JButton("Cover Image");
            coverImgBtn.setPreferredSize(new Dimension(200, 200));
            coverImgBtn.setBackground(new Color(216, 191, 216));//replace in img
            coverImgBtn.setFocusPainted(false);
            coverImgBtn.setBorderPainted(false);

            add(coverImgBtn, gbc);
        }
        catch (Exception e){
            System.out.println("Song Manage Panel Cover Image Function Error: "+e);
        }
    }
    private void bottomPanel(){
        try {
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
            buttonPanel.setBackground(new Color(58, 65, 74));

            // Release Button
            editBtn = new JButton("Edit");
            editBtn.setBackground(new Color(224, 143, 255));
            buttonPanel.add(editBtn);
            editBtn.setFocusPainted(false);
            editBtn.setBorderPainted(false);
            editBtn.setActionCommand("Edit");
            editBtn.addActionListener(new SongManageBtnAction(mcp));
            buttonPanel.add(editBtn);

            // Cancel Button
            deleteBtn = new JButton("Delete");
            deleteBtn.setBackground(new Color(224, 143, 255));
            deleteBtn.setBorderPainted(false);
            deleteBtn.setFocusPainted(false);
            buttonPanel.add(deleteBtn);

            gbc.gridx = 1;
            gbc.gridy = 5;
            gbc.gridwidth = 1;
            gbc.anchor = GridBagConstraints.SOUTHEAST;
            gbc.fill = GridBagConstraints.NONE;
            add(buttonPanel, gbc);
        }
        catch (Exception e){
            System.out.println("Song Manage Panel Bottom Panel function Error: "+e);
        }
    }
}
