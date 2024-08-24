package SoundWave.App.ListenerUI.Actions;

import SoundWave.App.ListenerUI.LListenSongPanel;
import SoundWave.Music.Song;
import SoundWave.User.Listener;
import SoundWave.User.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.Timer;

public class ListenSongBtnsActions implements ActionListener {
    private LListenSongPanel panel;
    private static Timer progressTimer;
    private int elapsedSeconds;
    Song song = new Song();
    Listener listener = new Listener();
    String listenerId;

    public ListenSongBtnsActions(LListenSongPanel panel,String listenerId) {
        this.panel = panel;
        this.listenerId = listenerId;
        System.out.println("Listener Id: " + listenerId);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if(cmd =="Play"){
            System.out.println("Play btn work!!");
            panel.getPlayBtn().setVisible(false);
            panel.getPauseBtn().setVisible(true);
            startProgressBar();
        }
        else if(cmd=="Back"){
            System.out.println("Back btn work!!");
        }
        else if(cmd=="Next"){
            System.out.println("Next btn work!!");
        }
        else if(cmd.equals("Stop")){
            System.out.println("Stop btn work!!");
            panel.getPlayBtn().setVisible(true);
            panel.getPauseBtn().setVisible(false);
            stopProgressBar();
        }
        else if(cmd=="Like"){
            System.out.println("Like btn work!!");
            try {
               listener.unlikeSong(panel.getSongDetails()[0],listenerId);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            panel.getLikedBtn().setVisible(false);
            panel.getDisLikedBtn().setVisible(true);
        }
        else if(cmd=="DisLike"){
            System.out.println("Dis Like btn work!!");
            boolean status = false;
            try {
                status = listener.likeSong(panel.getSongDetails()[0],listenerId);
                System.out.println(status);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println(status);
            panel.getLikedBtn().setVisible(true);
            panel.getDisLikedBtn().setVisible(false);
        }
    }
    private void startProgressBar() {
        elapsedSeconds = 0;

        song.start("C:/Chanuka/NIBM/EAD/EAD-CW/SoundWave/src/Images/Songs/"+ panel.getSongDetails()[2]);
        progressTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int durationInSeconds = (int) Float.parseFloat(panel.getSongDetails()[3]);
                if (elapsedSeconds <= durationInSeconds) {

                    int progress = (int) ((elapsedSeconds / (float) durationInSeconds) * 100);
                    panel.getSongProgressBar().setValue(progress);
                    elapsedSeconds++;
                } else {
                    song.stop();
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        progressTimer.start();
    }
    private void stopProgressBar() {
        if (progressTimer != null) {
            if (progressTimer.isRunning()) {
                System.out.println("Stopping timer...");
                song.stop();
                System.out.println("Song stopped.");
                progressTimer.stop();
            } else {
                System.out.println("Timer is not running.");
            }
        } else {
            System.out.println("ProgressTimer is null.");
        }
    }


}
