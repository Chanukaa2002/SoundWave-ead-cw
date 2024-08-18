package SoundWave.App.ArtistUI.Actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import SoundWave.App.ArtistUI.MainContentPanel;
import SoundWave.App.ArtistUI.SongGridPanel;
import SoundWave.App.ArtistUI.UploadSongPanel;

public class SideBarBtnActions implements ActionListener {
    private MainContentPanel mcp;

    public SideBarBtnActions(MainContentPanel mcp){
        try {
            this.mcp = mcp;
        }
        catch(Exception e){
            System.out.println("Side Bar Btn Actions constructor Error: "+e);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String clickedBtnCommand = e.getActionCommand();

            if ("Home".equals(clickedBtnCommand)) {
                mcp.setContentPanel(new SongGridPanel(mcp), "My Songs");
            } else if ("Upload".equals(clickedBtnCommand)) {
                mcp.setContentPanel(new UploadSongPanel(), "Upload Song");
            }
        }
        catch(Exception ex){
            System.out.println("Side Bar Btn Actions actionPerformed override method Error: "+ex);
            ex.getStackTrace()[0].getLineNumber();
        }
    }
}
