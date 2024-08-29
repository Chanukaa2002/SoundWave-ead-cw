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
                viewPlayListPanel.getPlayBtn().setVisible(false);
                viewPlayListPanel.getStopBtn().setVisible(true);

                playlistThread = new Thread(playList);
                playlistThread.start();

            } else if ("Stop".equals(command)) {
                viewPlayListPanel.getPlayBtn().setVisible(true);
                viewPlayListPanel.getStopBtn().setVisible(false);

                if (playlistThread != null && playlistThread.isAlive()) {
                    playList.stopAll();
                }
            }
        } catch (Exception ex) {
            System.out.println("LViewPlaylistBtnActions actionPerformed Error: " + ex);
        }
    }
}
