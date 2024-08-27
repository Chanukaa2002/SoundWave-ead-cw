package SoundWave.App.ListenerUI.Actions;

import SoundWave.App.ListenerUI.LListenSongPanel;
import SoundWave.App.UserUI.FilePath;
import SoundWave.Music.Song;
import SoundWave.User.Listener;
import SoundWave.User.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ListenSongBtnsActions implements ActionListener, ChangeListener {
    private LListenSongPanel panel;
    private static Timer progressTimer;
    private int elapsedSeconds;
    Song song = new Song();
    Listener listener = new Listener();
    String listenerId;

    public ListenSongBtnsActions(LListenSongPanel panel,String listenerId) {
        this.panel = panel;
        this.listenerId = listenerId;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if(cmd =="Play"){
            panel.getPlayBtn().setVisible(false);
            panel.getPauseBtn().setVisible(true);
            startProgressBar();
        }
        else if(cmd=="Back"){
        }
        else if(cmd=="Next"){
        }
        else if(cmd.equals("Stop")){
            panel.getPlayBtn().setVisible(true);
            panel.getPauseBtn().setVisible(false);
            stopProgressBar();
        }
        else if(cmd=="Like"){

            try {
               listener.unlikeSong(panel.getSongDetails()[0],listenerId);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            panel.getLikedBtn().setVisible(false);
            panel.getDisLikedBtn().setVisible(true);
        }
        else if(cmd=="DisLike"){
            boolean status = false;
            try {
                status = listener.likeSong(panel.getSongDetails()[0],listenerId);
                status=true;
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

        song.start(FilePath.getSongPath()+ panel.getSongDetails()[2]);
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
                    panel.getPlayBtn().setVisible(true);
                    panel.getPauseBtn().setVisible(false);
                    panel.getSongProgressBar().setValue(0);
                    ((Timer) e.getSource()).stop();
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

    @Override
    public void stateChanged(ChangeEvent e) {
        int sliderValue = panel.getVolumeSlider().getValue(); // Get slider value (0-100)

        // Map slider value (0-100) to FloatControl range (-80.0 to 6.0 dB)
        float minDb = -80.0f;
        float maxDb = 6.0f;
        float volumeValue = minDb + (maxDb - minDb) * (sliderValue / 2000.0f); // Map slider value

        // Ensure fc is not null and set the volume

            song.fc.setValue(volumeValue); // Set the volume

    }

}
