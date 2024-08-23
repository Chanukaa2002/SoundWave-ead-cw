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
    private Artist artist;

    private String userName,artistId;
    public ASidebarPanel(AMainContentPanel mainContentPanel, String userName,String artistId) throws SQLException {
        this.mainContentPanel = mainContentPanel;
        this.userName = userName;
        this.artistId = artistId;
        UI();
    }
    private void UI(){
        try {
            setLayout(new GridBagLayout());
            setBackground(new Color(76, 83, 93)); // Dark grey background
            setPreferredSize(new Dimension(200, 600));

            this.gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0; // Start from the first row
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Artist Username
            this.artistLabel = new JLabel("Welcome "+userName, SwingConstants.CENTER);
            artistLabel.setForeground(Color.WHITE);
            add(artistLabel, gbc);

            // Spacer
            gbc.gridy++; // Move to the next row
            add(Box.createVerticalStrut(20), gbc);

            // Home Button
            gbc.gridy++; // Move to the next row
            homeBtn();
            // Upload Song Button
            uploadBtn();
            // Add vertical glue to push the LogOut button to the bottom
            gbc.weighty = 1.0;
            gbc.gridy++;
            add(Box.createVerticalGlue(), gbc);
            logOutBtn();
            // Force revalidate and repaint to ensure proper layout
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
            homeButton.setBackground(new Color(224, 143, 255)); // Light purple background
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
            gbc.gridy++; // Move to the next row
            this.uploadButton = new JButton("Upload Song");
            uploadButton.setMargin(new Insets(7, 10, 7, 10));
            uploadButton.setBackground(new Color(224, 143, 255)); // Light purple background
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
            logoutButton.setBackground(new Color(224, 143, 255)); // Light purple background
            logoutButton.setForeground(Color.BLACK);
            logoutButton.setBorderPainted(false);
            logoutButton.setFocusPainted(false);
            add(logoutButton, gbc);
        }catch (Exception e){
            System.out.println("Side Bar Panel logoutBtn method Error: "+e);
        }
    }
}

