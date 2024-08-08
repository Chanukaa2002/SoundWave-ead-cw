package SoundWave.App;

import SoundWave.User.Artist;
import SoundWave.User.Listener;
import SoundWave.User.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SoundWave {
    public static void main(String[] args)  {
        //login
        try {
           boolean isSet = false;
           User u = new Listener();
            //User a = new Artist();
//            //isSet= u.login("Chanux2002","1234");
            String tempImagePath = "C:/Chanuka/NIBM/EAD/dp.JPG";
           // String tempSongPath = "C:/Chanuka/NIBM/EAD/sample.wav";
            FileInputStream dpInputStream = new FileInputStream(tempImagePath);
            //FileInputStream songInputStream = new FileInputStream(tempSongPath);
            //isSet = u.register("Chanux2000", "1234", "Chanuka dilshan", "bchanuka72@gmail.com", "0702140396", "dp.JPG", dpInputStream);
//            //isSet = u.forgetPassword("Chanux2002","Chanuka@20021004");
//            //u.editProfile("hiruni","Chanuka@20021004","Chanuka DIlshan Marambage","mchanuka72@gmail.com","0702140396","dp.JPG",dpInputStream);
//

             //isSet = ((Artist) a).uploadSong("SongTitle","2.0","test.PNG",dpInputStream,"A001",songInputStream,"Sample.wav");
            //((Artist) a).updateSong("S002","TestSong","5","dp.jpg",dpInputStream);
            //((Listener) u).createPlayList("DemoPlayList","dp.JPG",dpInputStream,"L001");
            //((Listener) u).likeSong("S002","L002");
//            isSet=((Listener) u).removeSongFromPlayList("P001","S002");
//            if (isSet) {
//                System.out.println("Okay");
//            } else {
//                System.out.println("Not Okay");
//            }
            int count = u.viewLikeCount("S002");
            System.out.println(count);
//        u.viewProfile("Chanux2002");
//        System.out.println(u.getUserName());
//        System.out.println(u.getDP());
//        System.out.println(u.getName());

        }catch(Exception e){
            System.out.println(e);
        }
    }
}
