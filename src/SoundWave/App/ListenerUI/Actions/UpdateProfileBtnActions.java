package SoundWave.App.ListenerUI.Actions;

import SoundWave.App.Validations;
import SoundWave.User.Listener;
import SoundWave.User.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class UpdateProfileBtnActions implements ActionListener {

    private JButton dpBtn;
    private static String dpPath,fileExtension,dp;
    private JTextField userNameTxt,nameTxt,emailTxt,passwordTxt,confirmPasswordTxt;
    private FileInputStream dpInputStream;
    private Listener user;
    public UpdateProfileBtnActions(JButton dpBtn){
        this.dpBtn = dpBtn;
    }

    public UpdateProfileBtnActions(JTextField userNameTxt, JTextField nameTxt, JTextField emailTxt, JTextField passwordTxt, JTextField confirmPasswordTxt) {
        this.userNameTxt = userNameTxt;
        this.nameTxt = nameTxt;
        this.emailTxt = emailTxt;
        this.passwordTxt = passwordTxt;
        this.confirmPasswordTxt = confirmPasswordTxt;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if(command == "UpdateDp"){

            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if(returnValue == JFileChooser.APPROVE_OPTION){
                File selectedFile = fileChooser.getSelectedFile();
                try{
                    BufferedImage img = ImageIO.read(selectedFile);
                    this.dpPath = selectedFile.getAbsolutePath();

                    Image scaledImg = img.getScaledInstance(75,75,Image.SCALE_SMOOTH);
                    dpBtn.setIcon(new ImageIcon(scaledImg));
                    String fileName = selectedFile.getName();

                    int dotIndex = fileName.lastIndexOf('.');
                    if(dotIndex>0 && dotIndex < fileName.length() -1){
                        fileExtension = fileName.substring(dotIndex+1).toLowerCase();
                    }
                }catch (Exception ex){
                    System.out.println("Update progile image uploading Error: "+ex);
                }
            }

        }
        else if (command =="UpdateProfile"){
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

            String userName = userNameTxt.getText();
            String name = nameTxt.getText();
            String password = passwordTxt.getText();
            String confirmPassword = confirmPasswordTxt.getText();
            String email = emailTxt.getText();
            boolean isUpdate=false;

            user = new Listener();
            try {
                isUpdate= user.editProfile(userName,password,name,email,dp,dpInputStream,fileExtension);
                if(isUpdate){
                    //dialog box
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        }
    }
}
