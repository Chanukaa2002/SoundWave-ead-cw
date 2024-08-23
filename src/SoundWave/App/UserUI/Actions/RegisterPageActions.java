package SoundWave.App.UserUI.Actions;
import SoundWave.App.UserUI.LogInPanel;
import SoundWave.App.Validations;
import SoundWave.User.Artist;
import SoundWave.User.Listener;
import SoundWave.User.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.SQLException;

public class RegisterPageActions implements ActionListener {

    private JTextField userNameTxt,nameTxt,emailTxt,contactNoTxt;
    private JPasswordField passwordTxt,confirmPasswordTxt;
    private static String dpPath;
    private FileInputStream dpInputStream;
    private JLabel dpImageLabel;
    private static String fileExtension = "";
    private JComboBox<String> userTypeCombo;
    User user;
    private JFrame regPanel;

    public RegisterPageActions(JLabel dpImage) {
        this.dpImageLabel = dpImage;
    }
    public RegisterPageActions(JTextField userNameTxt, JTextField nameTxt, JTextField emailTxt, JPasswordField passwordTxt, JPasswordField confirmPasswordTxt,JTextField contactNo,JComboBox<String> userTypeCombo,JFrame regPanel) {
        this.userNameTxt = userNameTxt;
        this.nameTxt = nameTxt;
        this.emailTxt = emailTxt;
        this.passwordTxt = passwordTxt;
        this.confirmPasswordTxt = confirmPasswordTxt;
        this.contactNoTxt = contactNo;
        this.userTypeCombo = userTypeCombo;
        this.regPanel = regPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("ImportDP".equals(command)) {
            //choose file
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    // Load the image
                    BufferedImage img = ImageIO.read(selectedFile);
                    //getPath
                    this.dpPath = selectedFile.getAbsolutePath();
                    System.out.println(dpPath);
                    // Scale the image to fit the JLabel
                    Image scaledImg = img.getScaledInstance(75, 75, Image.SCALE_SMOOTH);

                    // Set the image as the icon of the JLabel
                    dpImageLabel.setIcon(new ImageIcon(scaledImg));
                    String fileName = selectedFile.getName();

                    int dotIndex = fileName.lastIndexOf('.');
                    if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
                        fileExtension = fileName.substring(dotIndex + 1).toLowerCase();
                    }


                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        if(command == "Register"){
            // Make sure dpInputStream is not null before proceeding
            try {
                this.dpInputStream = new FileInputStream(dpPath);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            //validating
            if (dpInputStream == null) {
                System.out.println("Error: Display picture not selected.");
                return;
            }
            if(Validations.isFieldEmpty(nameTxt)){
                JOptionPane.showMessageDialog(null, "Name is required.");
                return;
            }
            if(Validations.isFieldEmpty(userNameTxt)){
                JOptionPane.showMessageDialog(null, "User Name is required.");
                return;
            }
            if(Validations.isFieldEmpty(passwordTxt)){
                JOptionPane.showMessageDialog(null, "Password is required.");
                return;
            }
            if(Validations.isFieldEmpty(confirmPasswordTxt)){
                JOptionPane.showMessageDialog(null, "Confirm Password is required.");
                return;
            }
            if(!Validations.PasswordsMatch(passwordTxt,confirmPasswordTxt)){
                JOptionPane.showMessageDialog(null, "Passwords not match!.");
            }

            if(Validations.isFieldEmpty(emailTxt)){
                JOptionPane.showMessageDialog(null, "Email not Match is required.");
                return;
            }
            if(Validations.isFieldEmpty(contactNoTxt)){
                JOptionPane.showMessageDialog(null, "Contact No is required.");
                return;
            }

            String userName = userNameTxt.getText();
            String name = nameTxt.getText();
            String password = passwordTxt.getText();
            String confirmPassword = confirmPasswordTxt.getText();
            String email = emailTxt.getText();
            String contactNo =  contactNoTxt.getText();
            String userType = (String) userTypeCombo.getSelectedItem();
            boolean isRegister=false;
            try {
                if(userType == "Artist") {
                    user= new Artist();
                    isRegister = user.register(userName, password, name, email, contactNo, dpInputStream, fileExtension);

                }
                else if(userType == "Listener") {
                    user= new Listener();
                    isRegister = user.register(userName, password, name, email, contactNo, dpInputStream, fileExtension);
                }
                if (isRegister) {
                    System.out.println("done");
                    regPanel.setVisible(false);
                    new LogInPanel();
                }
                else{
                    System.out.println("Fail");
                }

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
}