package SoundWave.App.ListenerUI.Actions;

import SoundWave.App.ListenerUI.LListenSongPanel;
import SoundWave.App.UserUI.FilePath;
import SoundWave.Music.Song;
import SoundWave.User.Listener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ListenSongBtnsActions implements ActionListener, ChangeListener {
    private LListenSongPanel panel;
    private static Timer progressTimer;
    private int elapsedSeconds;
    private Song song = new Song();
    private Listener listener = new Listener();
    private String listenerId;

    //for event actions
    public ListenSongBtnsActions(LListenSongPanel panel,String listenerId) {
        this.panel = panel;
        this.listenerId = listenerId;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if(cmd =="Play"){
            try{
                panel.getPlayBtn().setVisible(false);
                panel.getPauseBtn().setVisible(true);
                startProgressBar();

            }catch(Exception ex){
                System.out.println("ListenSongBtnActions action performed  Play command Error: "+ex);
            }
        }
        else if(cmd=="Back"){
        }
        else if(cmd=="Next"){
        }
        else if(cmd.equals("Stop")){
            try{
                panel.getPlayBtn().setVisible(true);
                panel.getPauseBtn().setVisible(false);
                stopProgressBar();
            }catch(Exception ex){
                System.out.println("ListenSongBtnActions action performed  Stop command Error: "+ex);
            }
        }
        else if(cmd=="Like"){
            try {
                String songId = panel.getSongDetails()[0];
                listener.unlikeSong(songId,listenerId);
                panel.getLikedBtn().setVisible(false);
                panel.getDisLikedBtn().setVisible(true);

            } catch (SQLException ex) {
                System.out.println("ListenSongBtnActions action performed  Like command Error: "+ex);
            }
        }
        else if(cmd=="DisLike"){
            try {
                String songId = panel.getSongDetails()[0];
                listener.likeSong(songId,listenerId);
                panel.getLikedBtn().setVisible(true);
                panel.getDisLikedBtn().setVisible(false);

            } catch (SQLException ex) {
                System.out.println("ListenSongBtnActions action performed  DisLike command Error: "+ex);
            }
        }
    }
    private void startProgressBar() {
        elapsedSeconds = 0;

        song.start(FilePath.getSongPath()+ panel.getSongDetails()[2]);
        progressTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //set up the progress bar
                try{
                    int durationInSeconds = (int) Float.parseFloat(panel.getSongDetails()[3]);
                    if (elapsedSeconds <= durationInSeconds) {

                        int progress = (int) ((elapsedSeconds / (float) durationInSeconds) * 100);
                        panel.getSongProgressBar().setValue(progress);
                        elapsedSeconds++;
                    } else {
                        song.stop();
                        panel.getPlayBtn().setVisible(true);
                        panel.getPauseBtn().setVisible(false);
                        panel.getSongProgressBar().setValue(0);
                        ((Timer) e.getSource()).stop();
                    }
                }catch(Exception ex){
                    System.out.println("ListenSOng Action startProgressBar Error: "+ex);
                }
            }
        });
        progressTimer.start();
    }
    private void stopProgressBar() {
        if (progressTimer != null) {
            if (progressTimer.isRunning()) {
                song.stop();
                progressTimer.stop();
            }
        }
    }

    //volume
    @Override
    public void stateChanged(ChangeEvent e) {
        int sliderValue = panel.getVolumeSlider().getValue();

        float minDb = -80.0f;
        float maxDb = 6.0f;
        float volumeValue = minDb + (maxDb - minDb) * (sliderValue / 2000.0f);
        song.fc.setValue(volumeValue);

    }

}
