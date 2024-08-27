package SoundWave.App.ListenerUI.Actions;

import SoundWave.App.ListenerUI.LViewPlayListPanel;
import SoundWave.Music.PlayList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LViewPlaylistBtnActions implements ActionListener {

    String songId, playListId;
    static LViewPlayListPanel viewPlayListPanel;
    public LViewPlaylistBtnActions(String songId, String playListId, LViewPlayListPanel viewPlayListPanel){

        this.songId = songId;
        this.playListId = playListId;
        this.viewPlayListPanel = viewPlayListPanel;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if(command == "Add"){

            PlayList playList = new PlayList();
           boolean isChecked =  playList.addSong(songId,playListId);
           System.out.println("Add song: " + isChecked);
           if(isChecked){
               viewPlayListPanel.refreshPanel();
           }
        }
        else if(command == "Delete"){

            PlayList playList = new PlayList();
            boolean isChecked =  playList.removeSong(songId,playListId);
            if(isChecked){
                viewPlayListPanel.refreshPanel();
            }
        }

        else if(command =="Play"){
            viewPlayListPanel.getPlayBtn().setVisible(false);
            viewPlayListPanel.getStopBtn().setVisible(true);
            PlayList playList = new PlayList();
            playList.playAll(playListId);
        }
        else if(command =="Stop"){
            viewPlayListPanel.getPlayBtn().setVisible(true);
            viewPlayListPanel.getStopBtn().setVisible(false);
            PlayList playList = new PlayList();
            playList.stopAll();
        }
    }
}
