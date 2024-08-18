package SoundWave.App.ArtistUI.Actions;

import SoundWave.App.ArtistUI.MainContentPanel;
import SoundWave.App.ArtistUI.SongUpdatePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SongManageBtnAction implements ActionListener {

    private MainContentPanel mcp;
    public SongManageBtnAction(MainContentPanel mcp){
        this.mcp = mcp;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            String command = e.getActionCommand();
            if("Edit".equals(command)){
                mcp.setContentPanel(new SongUpdatePanel(),"Update Song");
            }
        }catch(Exception ex){
            System.out.println("Song Manage Btn Action action Performed override method Error: " +ex);
        }
    }
}
