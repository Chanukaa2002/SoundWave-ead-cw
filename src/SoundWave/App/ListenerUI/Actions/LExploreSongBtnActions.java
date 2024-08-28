package SoundWave.App.ListenerUI.Actions;

import SoundWave.App.ListenerUI.LListenSongPanel;
import SoundWave.App.ListenerUI.LMainContent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LExploreSongBtnActions implements ActionListener {
    private LMainContent mc;
    private String songId,songName,listenerId;

    public LExploreSongBtnActions(LMainContent mc,String songId,String songName,String listenerId){
        try{
            this.mc = mc;
            this.songName = songName;
            this.songId = songId;
            this.listenerId = listenerId;

        }catch(Exception e){
            System.out.println("LExploreSongBtnActions constructor Error: "+e);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch(command){
            case "Song":
                try {
                    mc.setContentPanel(new LListenSongPanel(mc,songId,listenerId),songName);
                } catch (SQLException ex) {
                    System.out.println("LExploreSongBtnActions actionPerformed method Error: "+ex);
                }
                break;
        }

    }
}
