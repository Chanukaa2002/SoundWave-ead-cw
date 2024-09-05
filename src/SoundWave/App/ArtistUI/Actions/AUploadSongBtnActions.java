package SoundWave.App.ArtistUI.Actions;

import SoundWave.App.Validations;
import SoundWave.User.Artist;
import SoundWave.User.User;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class AUploadSongBtnActions implements ActionListener {

    private static String coverImgPath,songPath,fileName,artistId;
    private static String imgFileExtension = "",songFileExtension="";
    private static float durationInSeconds;
    private JButton coverImage,song;
    private JTextField titleTxt;
    private FileInputStream coverImgInputStream,songInputStream;

    public AUploadSongBtnActions(JButton coverImage,JButton song) {
        try{
            this.coverImage = coverImage;
            this.song = song;

        }catch(Exception e){
            System.out.println("Upload song btn actions constructor  Error: "+e);
        }
    }
    public AUploadSongBtnActions(JTextField titleTxt,String artistId) {
        try{
            this.titleTxt = titleTxt;
            this.artistId = artistId;

        }catch(Exception e){
            System.out.println("Upload song btn actions constructor  Error: "+e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command == "uploadCoverImage") {
            try {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    BufferedImage img = ImageIO.read(selectedFile);

                    this.coverImgPath = selectedFile.getAbsolutePath();
                    Image scaledImg = img.getScaledInstance(215, 200, Image.SCALE_SMOOTH);

                    coverImage.setIcon(new ImageIcon(scaledImg));
                    String fileName = selectedFile.getName();

                    int dotIndex = fileName.lastIndexOf('.');
                    if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
                        imgFileExtension = fileName.substring(dotIndex + 1).toLowerCase();
                    }
                }
            }
            catch (Exception ex) {
                System.out.println("Upload Song Actions UploadCoverImg Error: "+ex);
            }
        }
        else if (command == "UploadSong") {

            try {
                JFileChooser fileChooser = new JFileChooser();


                fileChooser.setFileFilter(new FileNameExtensionFilter("WAV Files", "wav"));
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    fileName = selectedFile.getName();

                    int dotIndex = fileName.lastIndexOf('.');
                    if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
                        songFileExtension = fileName.substring(dotIndex + 1);

                        if (songFileExtension.equals("wav")) {

                            this.songPath = selectedFile.getAbsolutePath();
                            song.setText(fileName);

                            //duration calculation
                            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(selectedFile);
                            AudioFormat format = audioInputStream.getFormat();
                            long audioFileLength = selectedFile.length();
                            int frameSize = format.getFrameSize();
                            float frameRate = format.getFrameRate();
                            durationInSeconds = (audioFileLength / (frameSize * frameRate));

                        } else {
                            JOptionPane.showMessageDialog(null, "Please Select Wav File!.");
                        }
                    }
                }
            } catch (Exception ex) {
                System.out.println("Upload Song Actions UploadSong Error: " + ex);
            }
        }
        else if(command =="Release"){
            try{
                this.coverImgInputStream = new FileInputStream(coverImgPath);
                this.songInputStream = new FileInputStream(songPath);

                //validation
                if (coverImgInputStream == null || songInputStream==null) {
                    JOptionPane.showMessageDialog(null, "Not selected!.");
                    return;
                }
                if(Validations.isFieldEmpty(titleTxt)){
                    JOptionPane.showMessageDialog(null, "Title is required.");
                    return;
                }
                Artist user = new Artist();

                String title = titleTxt.getText();

                boolean isRegister;
                isRegister=user.uploadSong(title,durationInSeconds,coverImgInputStream,artistId,songInputStream,fileName,imgFileExtension);
                if (isRegister) {
                    JOptionPane.showMessageDialog(null, "New Song has been Released!");
                    titleTxt.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Song Not Uploaded!.");
                }
            }
            catch (Exception ex) {
                System.out.println("Upload Song Actions Release Error: " + ex);
            }

        }
        else if(command=="Clear"){
            titleTxt.setText("");

        }
    }
}


