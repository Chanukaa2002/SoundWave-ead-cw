package SoundWave.App.UserUI;

import SoundWave.App.UserUI.Actions.LoginPageActions;

import javax.swing.*;
import java.awt.*;

public class LogInPanel extends JFrame {
    private JLabel  appName, userNameLbl, passwordLbl;
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

            userNameLbl = new JLabel("Username:");
            userNameLbl.setForeground(Color.WHITE);
            gridBag.gridx = 0;
            gridBag.gridy = 1;
            gridBag.gridwidth = 1;
            contentPanel.add(userNameLbl, gridBag);

            userNameField = new JTextField(15);
            gridBag.gridx = 1;
            gridBag.gridy = 1;
            contentPanel.add(userNameField, gridBag);

            passwordLbl = new JLabel("Password:");
            passwordLbl.setForeground(Color.WHITE);
            gridBag.gridx = 0;
            gridBag.gridy = 2;
            contentPanel.add(passwordLbl, gridBag);

            passwordField = new JPasswordField(15);
            gridBag.gridx = 1;
            gridBag.gridy = 2;
            contentPanel.add(passwordField, gridBag);

            logInBtn = new JButton("Log In");
            logInBtn.setBackground(new Color(224, 143, 255));
            logInBtn.setFocusPainted(false);
            logInBtn.setBorderPainted(false);
            logInBtn.setActionCommand("LogIn");
            logInBtn.addActionListener(new LoginPageActions(userNameField, passwordField, this));
            gridBag.gridx = 0;
            gridBag.gridy = 3;
            gridBag.gridwidth = 2;
            contentPanel.add(logInBtn, gridBag);

            createAccountBtn = new JButton("Create Account");
            createAccountBtn.setBackground(new Color(224, 143, 255));
            createAccountBtn.setFocusPainted(false);
            createAccountBtn.setBorderPainted(false);
            createAccountBtn.setActionCommand("Create");
            createAccountBtn.addActionListener(new LoginPageActions(this));
            gridBag.gridx = 0;
            gridBag.gridy = 4;
            gridBag.gridwidth = 2;
            contentPanel.add(createAccountBtn, gridBag);

            add(contentPanel);
            pack();
            setVisible(true);

            getRootPane().setDefaultButton(logInBtn);
        } catch (Exception e) {
            System.out.println("LogIn UI Error: " + e);
        }
    }

    public static void main(String[] args) {
        new LogInPanel();
    }
}
//