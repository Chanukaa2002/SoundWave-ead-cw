package SoundWave.App.ListenerUI.Actions;

import SoundWave.App.ListenerUI.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

public class LSideBarBtnActions implements ActionListener, MouseListener {
    private LMainContent mc;
    private String playlistId,userName,listenerId;

    public LSideBarBtnActions(LMainContent mc,String playListId,String listenerId){
        try {
            this.mc = mc;
            this.playlistId = playListId;
            this.listenerId = listenerId;
        }
        catch(Exception e){
            System.out.println("Listener Side Bar Btn Actions constructor Error: "+e);
        }
    }
    public LSideBarBtnActions(String userName,LMainContent mc){
        try {
            this.mc = mc;
            this.userName = userName;
        }
        catch(Exception e){
            System.out.println("Listener Side Bar Btn Actions constructor Error: "+e);
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch(command){
            case "CreatePlayList":
                System.out.println("Clicked");
                mc.setContentPanel(new LCreatePlayListPanel(mc),"Create PlayList");//listenerId
                break;
            case "Home":
                System.out.println("Clicked Home");
                mc.setContentPanel(new LExplorePanel(mc,listenerId),"Explore Songs");
                break;
            case "PlayList":
                System.out.println("Clicked PlayList");
                mc.setContentPanel(new LViewPlayListPanel(mc),playlistId);
                break;
            case "LogOut":
                System.out.println("Clicked LogOut");
                //mc.setContentPanel(new LExplorePanel(mc),"Explore Songs");
                break;
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() instanceof JLabel){
            try {
                mc.setContentPanel(new UpdateProfilePanel(mc,userName),"Update Profile");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
