package SoundWave.App.ListenerUI.Actions;

import SoundWave.App.ListenerUI.LViewPlayListPanel;
import SoundWave.Music.PlayList;
import SoundWave.User.Listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LViewPlaylistBtnActions implements ActionListener {

    private String songId, playListId;
    private LViewPlayListPanel viewPlayListPanel;
    private static PlayList playList;
    private static Thread playlistThread;

    public LViewPlaylistBtnActions(String songId, String playListId, LViewPlayListPanel viewPlayListPanel) {
        this.songId = songId;
        this.playListId = playListId;
        this.viewPlayListPanel = viewPlayListPanel;
        this.playList = new PlayList();
        playList.setPlaylistId(playListId);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        try {
            if ("Add".equals(command)) {
                Listener listener = new Listener();
                boolean isChecked = listener.addSongToPlayList(playListId, songId);
                if (isChecked) {
                    viewPlayListPanel.refreshPanel();
                }
            } else if ("Delete".equals(command)) {
                Listener listener = new Listener();
                boolean isChecked = listener.removeSongFromPlayList(playListId, songId);
                if (isChecked) {
                    viewPlayListPanel.refreshPanel();
                }
            } else if ("Play".equals(command)) {
                System.out.println("Play command received. Initializing playlistThread.");
                viewPlayListPanel.getPlayBtn().setVisible(false);
                viewPlayListPanel.getStopBtn().setVisible(true);

                playlistThread = new Thread(playList);
                System.out.println("Starting playlistThread.");
                playlistThread.start();
                System.out.println("playlistThread state after start: " + playlistThread.getState());

            } else if ("Stop".equals(command)) {

                System.out.println("Stop command received.");
                viewPlayListPanel.getPlayBtn().setVisible(true);
                viewPlayListPanel.getStopBtn().setVisible(false);

                if (playlistThread != null) {
                    System.out.println("playlistThread is not null.");
                    if (playlistThread.isAlive()) {
                        System.out.println("Stopping playlistThread.");
                        playList.stopAll();
                    } else {
                        System.out.println("Playlist thread has already completed.");
                    }
                } else {
                    System.out.println("No active playlist thread. Play the playlist first.");
                }
            }


        } catch (Exception ex) {
            System.out.println("LViewPlaylistBtnActions actionPerformed Error: " + ex);
        }
    }
}
