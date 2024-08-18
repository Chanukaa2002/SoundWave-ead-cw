package SoundWave.App.ArtistUI;

import SoundWave.App.ArtistUI.Actions.SideBarBtnActions;

import javax.swing.*;
import java.awt.*;

public class SidebarPanel extends JPanel {
    GridBagConstraints gbc;
    MainContentPanel mainContentPanel;
    JLabel artistLabel;
    JButton homeButton,uploadButton,logoutButton;

    public SidebarPanel(MainContentPanel mainContentPanel) {
        this.mainContentPanel = mainContentPanel;
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
            this.artistLabel = new JLabel("Artist UserName", SwingConstants.CENTER);
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
            homeButton.addActionListener(new SideBarBtnActions(mainContentPanel));
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
            uploadButton.addActionListener(new SideBarBtnActions(mainContentPanel));
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

