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
            setBackground(new Color(58, 65, 74)); // Darker grey background

            // Header
            headerLabel = new JLabel("Explore Songs", SwingConstants.CENTER);
            headerLabel.setFont(new Font(Font.SERIF, Font.PLAIN, 24));
            headerLabel.setForeground(Color.WHITE);
            add(headerLabel, BorderLayout.NORTH);

            // Initialize the scroll pane and add it to the center
            contentPanel = new LExplorePanel(this,listenerId);
            scrollPane = new JScrollPane(contentPanel);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove the border
            add(scrollPane, BorderLayout.CENTER);
        }
        catch(Exception e){
            System.out.println("Main Content Panel UI method  Error: "+e);
        }
    }
    public void setContentPanel(JPanel newPanel,String titleName) {
        try {
            System.out.println("Come to Here");
            // Remove the old content from the scroll pane
            scrollPane.setViewportView(newPanel);
            System.out.println(titleName);
            // Update the reference to the new content panel
            contentPanel = newPanel;
            headerLabel.setText(titleName);

            // Revalidate and repaint the panel to reflect the changes
            revalidate();
            repaint();
        }
        catch(Exception e){
            System.out.println("Main Content Panel set Content Panel  Error: "+e);
        }
    }
}
