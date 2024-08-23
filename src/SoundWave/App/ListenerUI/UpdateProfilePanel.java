package SoundWave.App.ListenerUI;

import javax.swing.*;
import java.awt.*;

public class UpdateProfilePanel extends JPanel {
    private JButton dpBtn,updateBtn;
    private JTextField nameTxt,userNameTxt,passwordTxt,confirmPasswordTxt,emailTxt;
    private JLabel nameLbl,userNameLbl,passwordLbl,confirmPasswordLbl,emailLbl,dpLbl;
    private GridBagConstraints gbc;
    private LMainContent mc;

    public UpdateProfilePanel(LMainContent mc){
        this.mc = mc;
        UI();
    }
    private void UI(){
        try{
            setLayout(new GridBagLayout());
            setBackground(new Color(58, 65, 74));
            this.gbc = new GridBagConstraints();
            gbc.insets = new Insets(10,10,10,10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            dp();
            name();
            userName();
            password();
            confirmPassword();
            email();
            updateBtn();


        }catch(Exception e){
            System.out.println("Update Profile UI Error: "+e);
        }
    }
    private void dp(){
        try{
            //lbl
            gbc.gridx=0;
            gbc.gridy=0;
            dpLbl = new JLabel("Insert DP");
            dpLbl.setForeground(Color.WHITE);
            dpLbl.setFont(new Font("Font.SERIF",Font.BOLD,14));
            add(dpLbl,gbc);

            //btn
            gbc.gridy=0;
            gbc.gridx=1;
            dpBtn = new JButton();
            dpBtn.setBackground(new Color(216,191,216));
            dpBtn.setPreferredSize(new Dimension(100,100));
            dpBtn.setFocusPainted(false);
            dpBtn.setBorderPainted(false);
            add(dpBtn,gbc);
        }
        catch(Exception e){
            System.out.println("Update Profile dp Error: "+e);
        }
    }
    private void name(){
        try{
            //lbl
            gbc.gridx=0;
            gbc.gridy=1;
            nameLbl = new JLabel("Name:");
            nameLbl.setForeground(Color.WHITE);
            nameLbl.setFont(new Font("Font.SERIF",Font.BOLD,14));
            add(nameLbl,gbc);

            //txt
            gbc.gridy=1;
            gbc.gridx=1;
            nameTxt = new JTextField(20);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add(nameTxt,gbc);

        }
        catch(Exception e){
            System.out.println("Update Profile name Error: "+e);
        }
    }
    private void userName(){
        try{
            //lbl
            gbc.gridx=0;
            gbc.gridy=2;
            userNameLbl = new JLabel("User Name:");
            userNameLbl.setForeground(Color.WHITE);
            userNameLbl.setFont(new Font("Font.SERIF",Font.BOLD,14));
            add(userNameLbl,gbc);

            //txt
            gbc.gridy=2;
            gbc.gridx=1;
            userNameTxt = new JTextField(20);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add(userNameTxt,gbc);
        }catch(Exception e){
            System.out.println("Update Profile userName Error: "+e);
        }
    }
    private void password(){
        try{
            //lbl
            gbc.gridx=0;
            gbc.gridy=3;
            passwordLbl = new JLabel("Password:");
            passwordLbl.setForeground(Color.WHITE);
            passwordLbl.setFont(new Font("Font.SERIF",Font.BOLD,14));
            add(passwordLbl,gbc);

            //txt
            gbc.gridy=3;
            gbc.gridx=1;
            passwordTxt = new JTextField(20);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add(passwordTxt,gbc);
        }
        catch(Exception e){
            System.out.println("Update Profile password Error: "+e);
        }
    }
    private void confirmPassword(){
        try{
            //lbl
            gbc.gridx=0;
            gbc.gridy=4;
            confirmPasswordLbl = new JLabel("Confirm Password:");
            confirmPasswordLbl.setForeground(Color.WHITE);
            confirmPasswordLbl.setFont(new Font("Font.SERIF",Font.BOLD,14));
            add(confirmPasswordLbl,gbc);

            //txt
            gbc.gridy=4;
            gbc.gridx=1;
            confirmPasswordTxt = new JTextField(20);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add(confirmPasswordTxt,gbc);
        }catch(Exception e){
            System.out.println("Update Profile confirm password Error: "+e);
        }
    }
    private void email(){
        try{
            //lbl
            gbc.gridx=0;
            gbc.gridy=5;
            emailLbl = new JLabel("Email:");
            emailLbl.setForeground(Color.WHITE);
            emailLbl.setFont(new Font("Font.SERIF",Font.BOLD,14));
            add(emailLbl,gbc);

            //txt
            gbc.gridy=5;
            gbc.gridx=1;
            emailTxt = new JTextField(20);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add(emailTxt,gbc);
        }catch(Exception e){
            System.out.println("Update Profile email Error: "+e);
        }
    }
    private void updateBtn(){
        try{
            gbc.gridy=6;
            gbc.gridx=2;
            updateBtn = new JButton("Update");
            updateBtn.setBackground(new Color(224, 143, 255));
            updateBtn.setFocusPainted(false);
            updateBtn.setBorderPainted(false);
            add(updateBtn,gbc);
        }
        catch(Exception e){
            System.out.println("Update Profile update Btn Error: "+e);
        }
    }
}