package SoundWave.App.UserUI.Actions;

import SoundWave.App.ArtistUI.ArtistMainPanel;
import SoundWave.App.ListenerUI.ListenerMainPanel;
import SoundWave.App.UserUI.LogInPanel;
import SoundWave.App.UserUI.RegisterPanel;
import SoundWave.User.Artist;
import SoundWave.User.Listener;
import SoundWave.User.User;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;


public class LoginPageActions implements ActionListener {
    private JTextField userNameTxt;
    private JPasswordField passwordTxt;
    private JFrame loginFrame;


    //from clicking create btn
    public LoginPageActions(JFrame loginFrame){
        this.loginFrame = loginFrame;
    }

    //form clicking login btn
    public LoginPageActions(JTextField userNameTxt, JPasswordField passwordTxt, JFrame loginFrame) {
        this.userNameTxt = userNameTxt;
        this.passwordTxt = passwordTxt;
        this.loginFrame = loginFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        User user;
        String command = e.getActionCommand();
        if(command=="LogIn"){
            String userName = userNameTxt.getText();
            String password = passwordTxt.getText();
            try {
                user = new Artist();
                boolean isArtistLoggedIn = user.login(userName,password);
                boolean artist = user.isUser(userName);

                user = new Listener();
                boolean isListenerLoggedIn = user.login(userName,password);
                boolean listener = user.isUser(userName);

                if(isArtistLoggedIn || isListenerLoggedIn){
                    if(artist){
                        loginFrame.setVisible(false);
                        new ArtistMainPanel(userName);
                    }
                    else if(listener){
                        loginFrame.setVisible(false);
                        new ListenerMainPanel(userName);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Login Fail.");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        else if(command=="Create"){
            loginFrame.setVisible(false);
            new RegisterPanel();
        }
    }
}
