package SoundWave.App.UserUI;

import SoundWave.App.UserUI.Actions.LoginPageActions;

import javax.swing.*;
import java.awt.*;

public class LogInPanel extends JFrame {
    private JLabel dpImage, appName, userNameLbl, passwordLbl;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JButton logInBtn, createAccountBtn;
    private JPanel contentPanel;

    public LogInPanel() {
        UI();
    }

    private void UI() {
        try {
            setTitle("Log In");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setPreferredSize(new Dimension(400, 500));

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

            // Username Label
            userNameLbl = new JLabel("Username:");
            userNameLbl.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            contentPanel.add(userNameLbl, gbc);

            userNameField = new JTextField(15);
            gbc.gridx = 1;
            gbc.gridy = 1;
            contentPanel.add(userNameField, gbc);

            passwordLbl = new JLabel("Password:");
            passwordLbl.setForeground(Color.WHITE);
            gbc.gridx = 0;
            gbc.gridy = 2;
            contentPanel.add(passwordLbl, gbc);

            passwordField = new JPasswordField(15);
            gbc.gridx = 1;
            gbc.gridy = 2;
            contentPanel.add(passwordField, gbc);

            logInBtn = new JButton("Log In");
            logInBtn.setBackground(new Color(224, 143, 255));
            logInBtn.setFocusPainted(false);
            logInBtn.setBorderPainted(false);
            logInBtn.setActionCommand("LogIn");
            logInBtn.addActionListener(new LoginPageActions(userNameField,passwordField));
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            contentPanel.add(logInBtn, gbc);

            createAccountBtn = new JButton("Create Account");
            createAccountBtn.setBackground(new Color(224, 143, 255));
            createAccountBtn.setFocusPainted(false);
            createAccountBtn.setBorderPainted(false);
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            contentPanel.add(createAccountBtn, gbc);

            add(contentPanel);
            pack();
            setVisible(true);
        } catch (Exception e) {
            System.out.println("LogIn UI Error: " + e);
        }
    }

    public static void main(String[] args) {
        new LogInPanel();
    }
}
