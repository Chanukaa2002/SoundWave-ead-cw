package SoundWave.App.ArtistUI.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import SoundWave.App.ArtistUI.AMainContentPanel;
import SoundWave.App.ArtistUI.ASongGridPanel;
import SoundWave.App.ArtistUI.AUploadSongPanel;
import SoundWave.App.ArtistUI.ArtistMainPanel;
import SoundWave.App.ListenerUI.ListenerMainPanel;
import SoundWave.App.UserUI.LogInPanel;

import javax.swing.*;

public class ASideBarBtnActions implements ActionListener {
    private AMainContentPanel mcp;
    private String artistId;
    private static ArtistMainPanel amp;

    public ASideBarBtnActions(AMainContentPanel mcp, String artistId){
        try {
            this.mcp = mcp;
            this.artistId = artistId;
        }
        catch(Exception e){
            System.out.println("Artist Side Bar Btn Actions constructor Error: "+e);
        }
    }
    public ASideBarBtnActions(ArtistMainPanel amp) {
        try{
            this.amp = amp;
        }catch (Exception e){
            System.out.println("Artist Side Bar Btn Actions constructor Error: "+e);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String clickedBtnCommand = e.getActionCommand();

            if ("Home".equals(clickedBtnCommand)) {
                mcp.setContentPanel(new ASongGridPanel(mcp,artistId), "My Songs");
            }
            else if ("Upload".equals(clickedBtnCommand)) {
                mcp.setContentPanel(new AUploadSongPanel(artistId), "Upload Song");
            }
            else if(clickedBtnCommand =="LogOut"){
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this song?", "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if(response == JOptionPane.YES_OPTION){
                    amp.dispose();
                    new LogInPanel();
                }
            }
        }
        catch(Exception ex){
            System.out.println("Side Bar Btn Actions actionPerformed override method Error: "+ex);
        }
    }
}
