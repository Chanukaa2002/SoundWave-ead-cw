package SoundWave.App.UserUI.Actions;

import SoundWave.User.Artist;
import SoundWave.User.Listener;
import SoundWave.User.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginPageActions implements ActionListener {
    private JTextField userNameTxt;
    private JPasswordField passwordTxt;

    public LoginPageActions(JTextField userNameTxt,JPasswordField passwordTxt){
        this.userNameTxt= userNameTxt;
        this.passwordTxt = passwordTxt;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        User user = null;
        String userName = userNameTxt.getText();
        String password = passwordTxt.getText();
        String command = e.getActionCommand();
        if(command=="LogIn"){
            try {
                user = new Artist();
                boolean isArtistLoggedIn = user.login(userName,password);
                boolean artist = user.isUser(userName);

                user = new Listener();
                boolean isListenerLoggedIn = user.login(userName,password);
                boolean listener = user.isUser(userName);

                if(isArtistLoggedIn || isListenerLoggedIn){
                    System.out.println("Pass!");
                    if(artist){
                        //load artist panels
                    }
                    else if(listener){
                        //load listener panels
                    }
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
