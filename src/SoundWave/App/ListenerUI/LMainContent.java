package SoundWave.App.ListenerUI;
import javax.swing.*;
import java.awt.*;

public class LMainContent extends JPanel {

    private JPanel contentPanel;
    private JScrollPane scrollPane;
    private JLabel headerLabel;
    private String listenerId;

    public LMainContent(String listenerId) {
        this.listenerId = listenerId;
        UI();
    }
    private void UI(){
        try {
            setLayout(new BorderLayout());
            setBackground(new Color(58, 65, 74));

            this.headerLabel = new JLabel("Explore Songs", SwingConstants.CENTER);
            headerLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 24));
            headerLabel.setForeground(Color.WHITE);
            add(headerLabel, BorderLayout.NORTH);


            this.contentPanel = new LExplorePanel(this,listenerId);
            scrollPane = new JScrollPane(contentPanel);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setBorder(BorderFactory.createEmptyBorder());
            add(scrollPane, BorderLayout.CENTER);
        }
        catch(Exception e){
            System.out.println("Main Content Panel UI method  Error: "+e);
        }
    }
    public void setContentPanel(JPanel newPanel,String titleName) {
        try {
            this.scrollPane.setViewportView(newPanel);
            this.contentPanel = newPanel;
            this.headerLabel.setText(titleName);

            revalidate();
            repaint();
        }
        catch(Exception e){
            System.out.println("Main Content Panel set Content Panel  Error: "+e);
        }
    }
}
