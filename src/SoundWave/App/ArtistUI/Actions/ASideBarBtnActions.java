package SoundWave.App.ArtistUI.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import SoundWave.App.ArtistUI.AMainContentPanel;
import SoundWave.App.ArtistUI.ASongGridPanel;
import SoundWave.App.ArtistUI.AUploadSongPanel;

public class ASideBarBtnActions implements ActionListener {
    private AMainContentPanel mcp;

    public ASideBarBtnActions(AMainContentPanel mcp){
        try {
            this.mcp = mcp;
        }
        catch(Exception e){
            System.out.println("Artist Side Bar Btn Actions constructor Error: "+e);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String clickedBtnCommand = e.getActionCommand();

            if ("Home".equals(clickedBtnCommand)) {
                mcp.setContentPanel(new ASongGridPanel(mcp), "My Songs");
            } else if ("Upload".equals(clickedBtnCommand)) {
                mcp.setContentPanel(new AUploadSongPanel(), "Upload Song");
            }
        }
        catch(Exception ex){
            System.out.println("Side Bar Btn Actions actionPerformed override method Error: "+ex);
            ex.getStackTrace()[0].getLineNumber();
        }
    }
}
