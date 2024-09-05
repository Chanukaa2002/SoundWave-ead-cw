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

            GridBagConstraints gridBag = new GridBagConstraints();
            gridBag.insets = new Insets(10, 10, 10, 10);
            gridBag.anchor = GridBagConstraints.CENTER;


            appName = new JLabel("SoundWave");
            appName.setFont(new Font("Arial", Font.BOLD, 24));
            appName.setForeground(Color.WHITE);
            gridBag.gridx = 0;
            gridBag.gridy = 0;
            gridBag.gridwidth = 2;
            contentPanel.add(appName, gridBag);

            dpImage = new JLabel();
//            dpImage.setForeground(Color.WHITE);
            dpImage.setPreferredSize(new Dimension(75, 75));
            gridBag.gridx = 0;
            gridBag.gridy = 1;
            gridBag.gridwidth = 1;
            contentPanel.add(dpImage, gridBag);

            importImgBtn = new JButton("Import");
            importImgBtn.setBackground(new Color(224, 143, 255));
            importImgBtn.setFocusPainted(false);
            importImgBtn.setBorderPainted(false);
            importImgBtn.setActionCommand("ImportDP");
            importImgBtn.addActionListener(new RegisterPageActions(dpImage));
            gridBag.gridx = 1;
            gridBag.gridy = 1;
            contentPanel.add(importImgBtn, gridBag);

            nameLbl = new JLabel("Name:");
            nameLbl.setForeground(Color.WHITE);
            gridBag.gridx = 0;
            gridBag.gridy = 2;
            gridBag.gridwidth = 1;
            contentPanel.add(nameLbl, gridBag);

            nameTxt = new JTextField(15);
            gridBag.gridx = 1;
            gridBag.gridy = 2;
            contentPanel.add(nameTxt, gridBag);

            userNameLbl = new JLabel("Username:");
            userNameLbl.setForeground(Color.WHITE);
            gridBag.gridx = 0;
            gridBag.gridy = 3;
            gridBag.gridwidth = 1;
            contentPanel.add(userNameLbl, gridBag);

            userNameField = new JTextField(15);
            gridBag.gridx = 1;
            gridBag.gridy = 3;
            contentPanel.add(userNameField, gridBag);

            passwordLbl = new JLabel("Password:");
            passwordLbl.setForeground(Color.WHITE);
            gridBag.gridx = 0;
            gridBag.gridy = 4;
            gridBag.gridwidth = 1;
            contentPanel.add(passwordLbl, gridBag);

            passwordField = new JPasswordField(15);
            gridBag.gridx = 1;
            gridBag.gridy = 4;
            contentPanel.add(passwordField, gridBag);

            confirmPasswordlbl = new JLabel("Confirm Password:");
            confirmPasswordlbl.setForeground(Color.WHITE);
            gridBag.gridx = 0;
            gridBag.gridy = 5;
            gridBag.gridwidth = 1;
            contentPanel.add(confirmPasswordlbl, gridBag);

            confirmPasswordTxt = new JPasswordField(15);
            gridBag.gridx = 1;
            gridBag.gridy = 5;
            contentPanel.add(confirmPasswordTxt, gridBag);


            emailLbl = new JLabel("Email:");
            emailLbl.setForeground(Color.WHITE);
            gridBag.gridx = 0;
            gridBag.gridy = 6;
            gridBag.gridwidth = 1;
            contentPanel.add(emailLbl, gridBag);

            emailTxt= new JTextField(15);
            gridBag.gridx = 1;
            gridBag.gridy = 6;
            contentPanel.add(emailTxt, gridBag);

            contactNoLbl = new JLabel("ContactNo:");
            contactNoLbl.setForeground(Color.WHITE);
            gridBag.gridx = 0;
            gridBag.gridy = 7;
            gridBag.gridwidth = 1;
            contentPanel.add(contactNoLbl, gridBag);

            contactNoTxt= new JTextField(15);
            gridBag.gridx = 1;
            gridBag.gridy = 7;
            contentPanel.add(contactNoTxt, gridBag);


            userTypeLbl = new JLabel("User Type:");
            userTypeLbl.setForeground(Color.WHITE);
            gridBag.gridx = 0;
            gridBag.gridy = 8;
            gridBag.gridwidth = 1;
            contentPanel.add(userTypeLbl, gridBag);


            String[] type = {"Listener","Artist"};
            userTypeComboBox= new JComboBox(type);
            gridBag.gridx = 1;
            gridBag.gridy = 8;
            contentPanel.add(userTypeComboBox, gridBag);

            registerBtn = new JButton("Sign Up");
            registerBtn.setBackground(new Color(224, 143, 255));
            registerBtn.setFocusPainted(false);
            registerBtn.setBorderPainted(false);
            registerBtn.setActionCommand("Register");
            registerBtn.addActionListener(new RegisterPageActions(userNameField,nameTxt,emailTxt,passwordField,confirmPasswordTxt,contactNoTxt,userTypeComboBox,this));
            gridBag.gridx = 0;
            gridBag.gridy = 9;
            gridBag.gridwidth = 2;
            contentPanel.add(registerBtn, gridBag);

            logInBtn = new JButton("Log In");
            logInBtn.setBackground(new Color(224, 143, 255));
            logInBtn.setFocusPainted(false);
            logInBtn.setBorderPainted(false);
            logInBtn.setActionCommand("LogIn");
            logInBtn.addActionListener(new RegisterPageActions(this));
            gridBag.gridx = 1;
            gridBag.gridy = 9;
            gridBag.gridwidth = 2;
            contentPanel.add(logInBtn, gridBag);

            add(contentPanel);
            pack();
            setVisible(true);
        } catch (Exception e) {
            System.out.println("Register UI Error: " + e);
        }
    }
}
