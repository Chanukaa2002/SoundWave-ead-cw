package SoundWave.App.ArtistUI.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import SoundWave.App.ArtistUI.AMainContentPanel;
import SoundWave.App.ArtistUI.ASongGridPanel;
import SoundWave.App.ArtistUI.AUploadSongPanel;

public class ASideBarBtnActions implements ActionListener {
    private AMainContentPanel mcp;
    private String artistId;

    public ASideBarBtnActions(AMainContentPanel mcp, String artistId){
        try {
            this.mcp = mcp;
            this.artistId = artistId;
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
                mcp.setContentPanel(new ASongGridPanel(mcp,artistId), "My Songs");
            }
            else if ("Upload".equals(clickedBtnCommand)) {
                mcp.setContentPanel(new AUploadSongPanel(artistId), "Upload Song");
            }
        }
        catch(Exception ex){
            System.out.println("Side Bar Btn Actions actionPerformed override method Error: "+ex);
            ex.getStackTrace()[0].getLineNumber();
        }
    }
}
