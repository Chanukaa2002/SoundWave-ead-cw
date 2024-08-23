package SoundWave.App.ArtistUI.Actions;

import SoundWave.App.ArtistUI.AMainContentPanel;
import SoundWave.App.ArtistUI.ASongManagePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ASongGridBtnActions implements ActionListener {
    private AMainContentPanel mcp;
    private String songId;
    private String songName;

    public ASongGridBtnActions(AMainContentPanel mcp,String songId,String songName) {
        try {
            this.songId = songId;
            this.mcp = mcp;
        }
        catch(Exception e){
            System.out.println("Song Grid Btn Actions constructor Error: "+e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String clickedBtnCommand = e.getActionCommand();
            mcp.setContentPanel(new ASongManagePanel(mcp,songId), songName);//change here titleName
        }
        catch(Exception ex){
            System.out.println("Song Grid Btn Actions action Performed override method Error: "+ex);
        }
    }
}
