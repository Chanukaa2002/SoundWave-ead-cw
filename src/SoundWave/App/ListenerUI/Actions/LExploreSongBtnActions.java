package SoundWave.App.ListenerUI.Actions;

import SoundWave.App.ListenerUI.LListenSongPanel;
import SoundWave.App.ListenerUI.LMainContent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LExploreSongBtnActions implements ActionListener {
    LMainContent mc;

    public LExploreSongBtnActions(LMainContent mc){
        this.mc = mc;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch(command){
            case "Song":
                System.out.println("Clicked");
                mc.setContentPanel(new LListenSongPanel(mc),"Song");
        }

    }
}
