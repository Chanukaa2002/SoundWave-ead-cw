package SoundWave.App.ListenerUI;

import SoundWave.App.ListenerUI.Actions.LCreatePlayListBtnActions;

import javax.swing.*;
import java.awt.*;

public class LCreatePlayListPanel extends JPanel {
    private JButton coverImageBtn,createBtn,cancelBtn;
    private LMainContent mc;
    private GridBagConstraints gbc;
    private JPanel bottomPanel;
    private JLabel imgLabel;
    private JTextField playListText;

    public LCreatePlayListPanel(LMainContent mc){
        this.mc = mc;
        UI();
    }
    private void UI(){
        try{
            setLayout(new GridBagLayout());
            setBackground(new Color(58, 65, 74));
            this.gbc = new GridBagConstraints();
            gbc.insets = new Insets(10,10,10,10);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx=0;
            gbc.gridy=0;

            // cover img
            coverImg();
            gbc.gridy++;

            // txt
            txt();
            gbc.gridy++;

            // bottomPanel
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
            // add image now use btn for that!

            //btn
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
            gbc.gridx--;
            bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
            bottomPanel.setBackground(new Color(58,65,74));

            //create btn
            createBtn = new JButton("Create");
            createBtn.setBackground(new Color(224, 143, 255));
            createBtn.setFocusPainted(false);
            createBtn.setBorderPainted(false);
            createBtn.setActionCommand("Create");
            createBtn.addActionListener(new LCreatePlayListBtnActions(playListText));
            bottomPanel.add(createBtn);

            //cancel btn
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
