package SoundWave.App.ListenerUI;

import SoundWave.App.ListenerUI.Actions.LExploreSongBtnActions;
import SoundWave.App.UserUI.FilePath;
import SoundWave.User.Listener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LExplorePanel extends JPanel {
    private LMainContent mc;
    private JButton songBtn;
    private BorderLayout borderLayout;
    private JPanel gridPanel;
    private String listenerId;
    private JLabel titleLbl;

    public LExplorePanel(LMainContent mc,String listenerId){
        this.mc = mc;
        this.listenerId = listenerId;
        UI();
    }
    private void UI(){
        try{
            this.borderLayout = new BorderLayout();
            setLayout(borderLayout);
            setBackground(new Color(58,65,74));

            songGrid();

        }
        catch (Exception e){
            System.out.println("Explore Song UI Method error: "+e);
        }
    }
    private void songGrid(){
        try{
            this.gridPanel = new JPanel();
            gridPanel.setBackground(new Color(58, 65, 74));
            gridPanel.setLayout(new GridLayout(0, 4, 20, 20));

            Listener listener = new Listener();
            ArrayList<String[]> songs = listener.exploreSong();

            for (String[] i : songs) {
                String songId = i[0];
                String songName = i[1];
                String songImg = i[4];
                ImageIcon originalIcon = new ImageIcon(FilePath.getSongCoverImgPath() +songImg);

                Image scaledImg = originalIcon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImg);

                songBtn = new JButton(scaledIcon);
                songBtn.setPreferredSize(new Dimension(120, 120));
                songBtn.setBackground(new Color(235, 215, 255));
                songBtn.setForeground(Color.BLACK);
                songBtn.setBorderPainted(false);
                songBtn.setFocusPainted(false);
                songBtn.setActionCommand("Song");
                songBtn.addActionListener(new LExploreSongBtnActions(mc,songId,songName,listenerId));
                gridPanel.add(songBtn);

                titleLbl = new JLabel(songName);
                titleLbl.setForeground(Color.WHITE);
                gridPanel.add(titleLbl);
            }


            add(gridPanel, borderLayout.CENTER);
        }catch (Exception e){
            System.out.println("Explore panel song Grid method Error: "+e);
        }
    }
}
