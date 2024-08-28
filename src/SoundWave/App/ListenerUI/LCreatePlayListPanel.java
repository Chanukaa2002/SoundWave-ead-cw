package SoundWave.App.ListenerUI;

import SoundWave.App.ListenerUI.Actions.LCreatePlayListBtnActions;

import javax.swing.*;
import java.awt.*;

public class LCreatePlayListPanel extends JPanel {
    private JButton coverImageBtn,createBtn,cancelBtn;
    private LMainContent mc;
    private GridBagConstraints gbc;
    private JPanel bottomPanel;
    private JTextField playListText;
    private String listenerId;

    public LCreatePlayListPanel(LMainContent mc,String listenerId){
        this.mc = mc;
        this.listenerId = listenerId;
        UI();
    }
    private void UI(){
        try{
            setLayout(new GridBagLayout());
            setBackground(new Color(58, 65, 74));
            this.gbc = new GridBagConstraints();
            gbc.insets = new Insets(10,10,10,10);
            gbc.fill = GridBagConstraints.HORIZONTAL;

            coverImg();
            txt();
            bottomBtnPanel();

            gbc.gridy++;
            gbc.gridwidth = 2;

        }
        catch (Exception e){
            System.out.println("Create PlayList panel UI Error: "+e);
        }
    }
    private void coverImg(){
        try{
            gbc.gridx=0;
            gbc.gridy=0;

            coverImageBtn = new JButton("Add ImageCover");
            coverImageBtn.setBackground(new Color(224, 143, 255));
            coverImageBtn.setPreferredSize(new Dimension(200,200));
            coverImageBtn.setForeground(Color.BLACK);
            coverImageBtn.setFocusPainted(false);
            coverImageBtn.setBorderPainted(false);
            coverImageBtn.setActionCommand("CoverImage");
            coverImageBtn.addActionListener(new LCreatePlayListBtnActions(coverImageBtn));
            add(coverImageBtn,gbc);

        }
        catch (Exception e){
            System.out.println("Create PlayList panel cover Img Error: "+e);
        }
    }
    private void bottomBtnPanel(){
        try{
            gbc.gridy++;
            gbc.gridx--;
            bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
            bottomPanel.setBackground(new Color(58,65,74));

            createBtn = new JButton("Create");
            createBtn.setBackground(new Color(224, 143, 255));
            createBtn.setFocusPainted(false);
            createBtn.setBorderPainted(false);
            createBtn.setActionCommand("Create");
            createBtn.addActionListener(new LCreatePlayListBtnActions(playListText,listenerId));
            bottomPanel.add(createBtn);

            cancelBtn = new JButton("Cancel");
            cancelBtn.setBackground(new Color(224, 143, 255));
            cancelBtn.setFocusPainted(false);
            cancelBtn.setBorderPainted(false);
            cancelBtn.setActionCommand("Cancel");
            bottomPanel.add(cancelBtn);
            add(bottomPanel,gbc);
        }
        catch (Exception e){
            System.out.println("Create PlayList panel bottom Panel method Error: "+e);
        }
    }
    private void txt(){
        try{
            gbc.gridy++;
            gbc.gridx--;
            playListText = new JTextField(15);
            playListText.setBackground(Color.WHITE);
            add(playListText,gbc);

        }
        catch (Exception e){
            System.out.println("Create PlayList panel Txt Method Error: "+e);
        }
    }
}
