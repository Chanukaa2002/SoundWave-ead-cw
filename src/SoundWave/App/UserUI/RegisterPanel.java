package SoundWave.App.UserUI;

import SoundWave.App.UserUI.Actions.RegisterPageActions;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JFrame {
    private JLabel dpImage, appName, userNameLbl, passwordLbl,nameLbl,emailLbl,confirmPasswordlbl,contactNoLbl,userTypeLbl;
    private JTextField userNameField,nameTxt,emailTxt,contactNoTxt;
    private JPasswordField passwordField,confirmPasswordTxt;
    private JButton registerBtn, logInBtn,importImgBtn;
    private JPanel contentPanel;
    private JComboBox userTypeComboBox;

    public RegisterPanel() {
        UI();
    }

    private void UI() {
        try {
            setTitle("Register");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setPreferredSize(new Dimension(400, 600));

            contentPanel = new JPanel();
            contentPanel.setBackground(new Color(58, 65, 74));
            contentPanel.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.anchor = GridBagConstraints.CENTER;


            appName = new JLabel("SoundWave");
            appName.setFont(new Font("Arial", Font.BOLD, 24));
            appName.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            contentPanel.add(appName, gbc);

            dpImage = new JLabel();
            dpImage.setForeground(Color.WHITE);
            dpImage.setPreferredSize(new Dimension(75, 75));
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            contentPanel.add(dpImage, gbc);

            importImgBtn = new JButton("Import");
            importImgBtn.setBackground(new Color(224, 143, 255));
            importImgBtn.setFocusPainted(false);
            importImgBtn.setBorderPainted(false);
            importImgBtn.setActionCommand("ImportDP");
            importImgBtn.addActionListener(new RegisterPageActions(dpImage));
            gbc.gridx = 1;
            gbc.gridy = 1;
            contentPanel.add(importImgBtn, gbc);

            // name Label
            nameLbl = new JLabel("Name:");
            nameLbl.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 2;
            gbc.gridwidth = 1;
            contentPanel.add(nameLbl, gbc);

            nameTxt = new JTextField(15);
            gbc.gridx = 1;
            gbc.gridy = 2;
            contentPanel.add(nameTxt, gbc);

            // Username Label
            userNameLbl = new JLabel("Username:");
            userNameLbl.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 1;
            contentPanel.add(userNameLbl, gbc);

            userNameField = new JTextField(15);
            gbc.gridx = 1;
            gbc.gridy = 3;
            contentPanel.add(userNameField, gbc);

            // password Label
            passwordLbl = new JLabel("Password:");
            passwordLbl.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 1;
            contentPanel.add(passwordLbl, gbc);

            passwordField = new JPasswordField(15);
            gbc.gridx = 1;
            gbc.gridy = 4;
            contentPanel.add(passwordField, gbc);

            // confirm password Label
            confirmPasswordlbl = new JLabel("Confirm Password:");
            confirmPasswordlbl.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 5;
            gbc.gridwidth = 1;
            contentPanel.add(confirmPasswordlbl, gbc);

            confirmPasswordTxt = new JPasswordField(15);
            gbc.gridx = 1;
            gbc.gridy = 5;
            contentPanel.add(confirmPasswordTxt, gbc);

            // Username Label
            emailLbl = new JLabel("Email:");
            emailLbl.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 6;
            gbc.gridwidth = 1;
            contentPanel.add(emailLbl, gbc);

            emailTxt= new JTextField(15);
            gbc.gridx = 1;
            gbc.gridy = 6;
            contentPanel.add(emailTxt, gbc);


            // Username Label
            contactNoLbl = new JLabel("ContactNo:");
            contactNoLbl.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 7;
            gbc.gridwidth = 1;
            contentPanel.add(contactNoLbl, gbc);

            contactNoTxt= new JTextField(15);
            gbc.gridx = 1;
            gbc.gridy = 7;
            contentPanel.add(contactNoTxt, gbc);

            // Username Label
            userTypeLbl = new JLabel("User Type:");
            userTypeLbl.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 8;
            gbc.gridwidth = 1;
            contentPanel.add(userTypeLbl, gbc);


            String[] type = {"Listener","Artist"};
            userTypeComboBox= new JComboBox(type);
            gbc.gridx = 1;
            gbc.gridy = 8;
            contentPanel.add(userTypeComboBox, gbc);

            registerBtn = new JButton("Sign In");
            registerBtn.setBackground(new Color(224, 143, 255));
            registerBtn.setFocusPainted(false);
            registerBtn.setBorderPainted(false);
            registerBtn.setActionCommand("Register");
            registerBtn.addActionListener(new RegisterPageActions(userNameField,nameTxt,emailTxt,passwordField,confirmPasswordTxt,contactNoTxt,userTypeComboBox,this));
            gbc.gridx = 0;
            gbc.gridy = 9;
            gbc.gridwidth = 2;
            contentPanel.add(registerBtn, gbc);

            logInBtn = new JButton("Log In");
            logInBtn.setBackground(new Color(224, 143, 255));
            logInBtn.setForeground(Color.WHITE);
            logInBtn.setFocusPainted(false);
            logInBtn.setBorderPainted(false);
            gbc.gridx = 1;
            gbc.gridy = 9;
            gbc.gridwidth = 2;
            contentPanel.add(logInBtn, gbc);

            add(contentPanel);
            pack();
            setVisible(true);
        } catch (Exception e) {
            System.out.println("LogIn UI Error: " + e);
        }
    }

    public static void main(String[] args) {
        new RegisterPanel();
    }
}
