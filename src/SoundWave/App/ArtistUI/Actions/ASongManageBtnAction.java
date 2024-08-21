package SoundWave.App.ArtistUI.Actions;

import SoundWave.App.ArtistUI.AMainContentPanel;
import SoundWave.App.ArtistUI.ASongUpdatePanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ASongManageBtnAction implements ActionListener {

    private AMainContentPanel mcp;
    public ASongManageBtnAction(AMainContentPanel mcp){
        this.mcp = mcp;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            String command = e.getActionCommand();
            if("Edit".equals(command)){
                mcp.setContentPanel(new ASongUpdatePanel(),"Update Song");
            }
        }catch(Exception ex){
            System.out.println("Song Manage Btn Action action Performed override method Error: " +ex);
        }
    }
}
