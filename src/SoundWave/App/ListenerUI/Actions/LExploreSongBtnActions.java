package SoundWave.App.ListenerUI.Actions;

import SoundWave.App.ListenerUI.LListenSongPanel;
import SoundWave.App.ListenerUI.LMainContent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LExploreSongBtnActions implements ActionListener {
    LMainContent mc;
    private String songId,songName,listenerId;

    public LExploreSongBtnActions(LMainContent mc,String songId,String songName,String listenerId){
        this.mc = mc;
        this.songName = songName;
        this.songId = songId;
        this.listenerId = listenerId;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch(command){
            case "Song":
                System.out.println("Clicked");
                try {
                    mc.setContentPanel(new LListenSongPanel(mc,songId,listenerId),songName);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                break;
        }

    }
}
