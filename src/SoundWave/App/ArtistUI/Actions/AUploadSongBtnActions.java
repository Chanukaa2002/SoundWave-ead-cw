package SoundWave.App.ArtistUI.Actions;

import SoundWave.App.Validations;
import SoundWave.User.Artist;
import SoundWave.User.User;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
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

    private JButton coverImage,song;
    private static String coverImgPath,songPath,fileName;
    private static String imgFileExtension = "",songFileExtension="";
    private static float durationInSeconds;
    private JTextField titleTxt;
    private FileInputStream coverImgInputStream,songInputStream;

    public AUploadSongBtnActions(JButton coverImage,JButton song) {
        this.coverImage = coverImage;
        this.song = song;
    }

    public AUploadSongBtnActions(JTextField titleTxt) {
        this.titleTxt = titleTxt;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        //clear
        if (command == "uploadCoverImage") {
            try {
            //choose file
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                        // Load the image
                    BufferedImage img = ImageIO.read(selectedFile);
                    //getPath
                    this.coverImgPath = selectedFile.getAbsolutePath();

                    Image scaledImg = img.getScaledInstance(215, 200, Image.SCALE_SMOOTH);

                    // Set the image as the icon of the JLabel
                    coverImage.setIcon(new ImageIcon(scaledImg));
                    String fileName = selectedFile.getName();

                    int dotIndex = fileName.lastIndexOf('.');
                    if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
                        imgFileExtension = fileName.substring(dotIndex + 1).toLowerCase();
                        System.out.println(coverImgPath);
                        System.out.println(imgFileExtension);
                        System.out.println(song);
                    }


                }
            }
            catch (Exception ex) {
                System.out.println("Upload Song Actions UploadCoverImg Error: "+ex);
            }
        }
        else if (command == "UploadSong") {
            // Choose file
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("WAV Audio Files", "wav"));
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    fileName = selectedFile.getName();

                    int dotIndex = fileName.lastIndexOf('.');
                    if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
                        songFileExtension = fileName.substring(dotIndex + 1).toLowerCase();
                        System.out.println(songFileExtension);
                        if (songFileExtension.equals("wav")) {
                            this.songPath = selectedFile.getAbsolutePath();
                            song.setText(fileName);
                            System.out.println("Song Path: " + songPath);
                            System.out.println("File Extension: " + songFileExtension);

                            //calculation for get duration of audio file(for make progressBar)
                            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(selectedFile);
                            AudioFormat format = audioInputStream.getFormat();
                            long audioFileLength = selectedFile.length();
                            int frameSize = format.getFrameSize();
                            float frameRate = format.getFrameRate();
                            durationInSeconds = (audioFileLength / (frameSize * frameRate));

                            System.out.println(durationInSeconds);
                        } else {
                            System.out.println("Selected file is not a .wav file.");
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
                    System.out.println("Error:  not selected.");
                    return;
                }
                if(Validations.isFieldEmpty(titleTxt)){
                    JOptionPane.showMessageDialog(null, "Title is required.");
                }
                Artist user = new Artist();

                String title = titleTxt.getText();
                String artistId = user.getArtistId();

                boolean isRegister=false;
                isRegister=user.uploadSong(title,durationInSeconds,coverImgInputStream,artistId,songInputStream,fileName,imgFileExtension);
                if (isRegister) {
                    System.out.println("done");
                    titleTxt.setText("");
                } else {
                    System.out.println("fail");
                }
            }
            catch (Exception ex) {
                System.out.println("Upload Song Actions Release Error: " + ex);
            }

        }
    }
}


