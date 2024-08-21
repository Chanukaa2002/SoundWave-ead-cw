package SoundWave.App.ListenerUI.Actions;

import SoundWave.App.ListenerUI.LListenSongPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenSongBtnsActions implements ActionListener {
    private LListenSongPanel panel; // Reference to the panel

    public ListenSongBtnsActions(LListenSongPanel panel) {
        this.panel = panel;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if(cmd =="Play"){
            System.out.println("Play btn work!!");
            panel.getPlayBtn().setVisible(false);
            panel.getPauseBtn().setVisible(true);
        }
        else if(cmd=="Back"){
            System.out.println("Back btn work!!");
        }
        else if(cmd=="Next"){
            System.out.println("Next btn work!!");
        }
        else if(cmd=="Stop"){
            System.out.println("Stop btn work!!");
            panel.getPlayBtn().setVisible(true);
            panel.getPauseBtn().setVisible(false);
        }
        else if(cmd=="Like"){
            System.out.println("Like btn work!!");
            panel.getLikedBtn().setVisible(false);
            panel.getDisLikedBtn().setVisible(true);
        }
        else if(cmd=="DisLike"){
            System.out.println("Dis Like btn work!!");
            panel.getLikedBtn().setVisible(true);
            panel.getDisLikedBtn().setVisible(false);
        }
    }
}
