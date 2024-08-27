package SoundWave.App.ListenerUI.Actions;

import SoundWave.App.ListenerUI.*;
import SoundWave.App.UserUI.LogInPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;



public class LSideBarBtnActions implements ActionListener, MouseListener {
    private LMainContent mc;
    private String playlistId,userName,listenerId;
    private static ListenerMainPanel lmp;

    public LSideBarBtnActions(LMainContent mc,String playListId,String listenerId){
        try {
            this.mc = mc;
            this.playlistId = playListId;
            this.listenerId = listenerId;
            System.out.println("ListenerId:"+listenerId);
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
    public LSideBarBtnActions(ListenerMainPanel lmp) {
        this.lmp = lmp;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch(command){
            case "CreatePlayList":
                mc.setContentPanel(new LCreatePlayListPanel(mc,listenerId),"Create PlayList");
                break;
            case "Home":
                mc.setContentPanel(new LExplorePanel(mc,listenerId),"Explore Songs");
                break;
            case "PlayList":
                mc.setContentPanel(new LViewPlayListPanel(mc,playlistId),"PlayList");
                break;
            case "LogOut":
                lmp.dispose();
                new LogInPanel();
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
