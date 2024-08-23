package SoundWave.App.ArtistUI.Actions;

import SoundWave.App.ArtistUI.AMainContentPanel;
import SoundWave.App.ArtistUI.ASongGridPanel;
import SoundWave.Music.Song;
import SoundWave.User.Artist;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ASongManageBtnAction implements ActionListener {

    private AMainContentPanel mcp;
    private String songId,artistId;
    public ASongManageBtnAction(AMainContentPanel mcp,String songId,String artistId){
        this.mcp = mcp;
        this.songId = songId;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String command = e.getActionCommand();
            if ("Delete".equals(command)) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this song?", "Confirm Deletion",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (response == JOptionPane.YES_OPTION) {
                    Artist user = new Artist();
                    Song song = new Song();
                    boolean status = user.removeSong(songId);

                    if (status) {
                        System.out.println("Pass");
                        mcp.setContentPanel(new ASongGridPanel(mcp, artistId), "My Song");
                    } else {
                        System.out.println("Fail");
                    }
                } else {
                    System.out.println("Deletion canceled");
                }
            }
        } catch (Exception ex) {
            System.out.println("Song Manage Btn Action actionPerformed method Error: " + ex);
        }
    }

}
