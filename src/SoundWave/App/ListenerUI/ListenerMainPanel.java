package SoundWave.App.ListenerUI;

import SoundWave.App.ArtistUI.AMainContentPanel;
import SoundWave.App.ListenerUI.Actions.LSideBarBtnActions;
import SoundWave.User.Listener;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ListenerMainPanel extends JFrame{
    private String listenerId,userName;
    Listener listener;
    public ListenerMainPanel(String userName) throws SQLException {
        this.userName = userName;
        listener = new Listener();
        listenerId = listener.getId(userName);
        UI();
    }
    private void UI(){
        try {
            setTitle("Listener");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1000, 700);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout());

            LMainContent mainContentPanel = new LMainContent(listenerId);
            new LSideBarBtnActions(this);
            add(mainContentPanel, BorderLayout.CENTER);


            LSideBar sidebarPanel = new LSideBar(mainContentPanel,userName);
            JScrollPane sideBarScroll = new JScrollPane(sidebarPanel);

            sideBarScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            sideBarScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            sideBarScroll.setBorder(BorderFactory.createEmptyBorder());

            add(sideBarScroll, BorderLayout.WEST);
            setVisible(true);
        }
        catch(Exception e){
            System.out.println("Artist Main Panel UI method Error: "+e);
            e.getStackTrace()[0].getLineNumber();
        }
    }
}
