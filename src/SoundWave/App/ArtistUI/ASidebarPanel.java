package SoundWave.App.ArtistUI;

import SoundWave.App.ArtistUI.Actions.ASideBarBtnActions;
import SoundWave.User.Artist;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ASidebarPanel extends JPanel {
    GridBagConstraints gbc;
    AMainContentPanel mainContentPanel;
    JLabel artistLabel;
    JButton homeButton,uploadButton,logoutButton;

    private String userName,artistId;
    public ASidebarPanel(AMainContentPanel mainContentPanel, String userName,String artistId){
        this.mainContentPanel = mainContentPanel;
        this.userName = userName;
        this.artistId = artistId;
        UI();
    }
    private void UI(){
        try {
            setLayout(new GridBagLayout());
            setBackground(new Color(76, 83, 93));
            setPreferredSize(new Dimension(200, 600));

            this.gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            this.artistLabel = new JLabel("Welcome "+userName, SwingConstants.CENTER);
            artistLabel.setForeground(Color.WHITE);
            add(artistLabel, gbc);

            gbc.gridy++;
            add(Box.createVerticalStrut(20), gbc);

            gbc.gridy++;
            homeBtn();
            uploadBtn();

            gbc.weighty = 1.0;
            gbc.gridy++;
            add(Box.createVerticalGlue(), gbc);
            logOutBtn();
            revalidate();
            repaint();
        }
        catch(Exception e){
            System.out.println("Side Bar Panel UI function Error: "+e);
        }
    }
    private void homeBtn(){
        try {
            this.homeButton = new JButton("Home");

            homeButton.setMargin(new Insets(7, 10, 7, 10));
            homeButton.setBackground(new Color(224, 143, 255));
            homeButton.setForeground(Color.BLACK);
            homeButton.setBorderPainted(false);
            homeButton.setFocusPainted(false);
            homeButton.setActionCommand("Home");
            homeButton.addActionListener(new ASideBarBtnActions(mainContentPanel,artistId));
            add(homeButton, gbc);
        }
        catch (Exception e){
            System.out.println("SIde Bar Panel Home Btn function Error: "+e);
        }
    }
    private void uploadBtn(){
        try {
            gbc.gridy++;
            this.uploadButton = new JButton("Upload Song");
            uploadButton.setMargin(new Insets(7, 10, 7, 10));
            uploadButton.setBackground(new Color(224, 143, 255));
            uploadButton.setForeground(Color.BLACK);
            uploadButton.setBorderPainted(false);
            uploadButton.setFocusPainted(false);
            uploadButton.setActionCommand("Upload");
            uploadButton.addActionListener(new ASideBarBtnActions(mainContentPanel,artistId));
            add(uploadButton, gbc);
        }
        catch (Exception e){
            System.out.println("Side Bar Panel uploadBtn function Error: "+e);
        }
    }
    private void logOutBtn() {
        try {
            gbc.weighty = 0;
            gbc.gridy++;
            this.logoutButton = new JButton("LogOut");
            logoutButton.setMargin(new Insets(7, 10, 7, 10));
            logoutButton.setBackground(new Color(224, 143, 255));
            logoutButton.setForeground(Color.BLACK);
            logoutButton.setBorderPainted(false);
            logoutButton.setFocusPainted(false);
            logoutButton.setActionCommand("LogOut");
            logoutButton.addActionListener(new ASideBarBtnActions(mainContentPanel,artistId));
            add(logoutButton, gbc);
        }catch (Exception e){
            System.out.println("Side Bar Panel logoutBtn method Error: "+e);
        }
    }
}

