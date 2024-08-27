package SoundWave.App.ListenerUI;

import SoundWave.App.ListenerUI.Actions.LExploreSongBtnActions;
import SoundWave.App.UserUI.FilePath;
import SoundWave.User.Listener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LExplorePanel extends JPanel {
    LMainContent mc;
    JTextField searchBarTxt;
    JButton songBtn;
    BorderLayout borderLayout;
    JPanel searchPanel,gridPanel;
    String listenerId;
    JLabel titleLbl;

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

            this.searchPanel = new JPanel();
            searchPanel.setBackground(new Color(58,65,74));
            searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

            //search bar
            searchBar();
            //grid
            songGrid();

        }
        catch (Exception e){
            System.out.println("Explore Song UI Method error: "+e);
        }
    }
    private void searchBar(){
        try{
            searchBarTxt = new JTextField(20);
            searchBarTxt.setPreferredSize(new Dimension(250,30));
            searchBarTxt.setBackground(Color.WHITE);
            searchBarTxt.setForeground(Color.BLACK);
            searchBarTxt.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            searchBarTxt.setText("Search Song");
            searchPanel.add(searchBarTxt);
            add(searchPanel,borderLayout.NORTH);
        }catch (Exception e){
            System.out.println("Explore Panel search bar Error: "+e);
        }
    }
    private void songGrid(){
        try{
            this.gridPanel = new JPanel();
            gridPanel.setBackground(new Color(58, 65, 74)); // Match the background
            gridPanel.setLayout(new GridLayout(0, 4, 20, 20));

            Listener listener = new Listener();
            ArrayList<String[]> songs = listener.exploreSong();
            for (String[] i : songs) {
                String songId = i[0];
                String songName = i[1];
                ImageIcon originalIcon = new ImageIcon(FilePath.getSongCoverImgPath() + i[4]);

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
