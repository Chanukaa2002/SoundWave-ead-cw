package SoundWave.App.ListenerUI.Actions;

import SoundWave.App.ListenerUI.LSideBar;
import SoundWave.App.Validations;
import SoundWave.User.Listener;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

public class LCreatePlayListBtnActions implements ActionListener {

    private JButton coverImg;
    private JTextField playListTxt;
    private FileInputStream coverImgInputStream;
    private static String coverImgPath,imgExtension="",fileName;
    private static String listenerId;
    private static LSideBar sideBar;

    public LCreatePlayListBtnActions( String listenerId, LSideBar sideBar) {
        try{
            this.listenerId = listenerId;
            this.sideBar = sideBar;

        }catch(Exception e){
            System.out.println("LCreatePlayListBtnActions constructor Error: "+e);
        }
    }
    public LCreatePlayListBtnActions(JTextField playListTxt, String listenerId) {
        try{
            this.playListTxt = playListTxt;
            this.listenerId = listenerId;

        }catch(Exception e){
            System.out.println("LCreatePlayListBtnActions constructor Error: "+e);
        }
    }
    public LCreatePlayListBtnActions(JButton coverImg){
        this.coverImg = coverImg;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if(command == "CoverImage"){

            try{
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    BufferedImage img = ImageIO.read(selectedFile);

                    coverImgPath = selectedFile.getAbsolutePath();
                    Image scaledImg = img.getScaledInstance(215, 200, Image.SCALE_SMOOTH);

                    coverImg.setIcon(new ImageIcon(scaledImg));
                    fileName = selectedFile.getName();

                    int dotIndex = fileName.lastIndexOf('.');
                    if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
                        imgExtension = fileName.substring(dotIndex + 1).toLowerCase();
                    }
                }
            }
            catch(Exception ex){
                System.out.println("Create payList Action class Upload CoveImg Error: "+ex);
            }

        }
        else if(command == "Create"){
            try{
                this.coverImgInputStream = new FileInputStream(coverImgPath);

                if(Validations.isFieldEmpty(playListTxt)){
                    JOptionPane.showMessageDialog(null, "Title is required.");
                }
                Listener user = new Listener();

                String title = playListTxt.getText();
                boolean status = false;

                status = user.createPlayList(title,coverImgInputStream,listenerId,imgExtension);
                if (status) {
                    JOptionPane.showMessageDialog(null, "PlayList has been Created!.");;
                    playListTxt.setText("");
                    sideBar.refreshPlaylists();
                } else {
                    JOptionPane.showMessageDialog(null, "Playlist NOT Created!.");
                }
            }catch(Exception ex){
                System.out.println("Create payList Action class Create Playlist condition Error: ");
            }
        }
    }
}
