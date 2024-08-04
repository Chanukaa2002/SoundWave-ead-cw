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
////            //isSet= u.login("Chanux2002","1234");
            String tempImagePath = "C:/Chanuka/NIBM/EAD/dp.JPG";
            FileInputStream dpInputStream = new FileInputStream(tempImagePath);
            isSet = u.register("Chanux2000", "1234", "Chanuka dilshan", "bchanuka72@gmail.com", "0702140396", "dp.JPG", dpInputStream);
////            //isSet = u.forgetPassword("Chanux2002","Chanuka@20021004");
//            //u.editProfile("hiruni","Chanuka@20021004","Chanuka DIlshan Marambage","mchanuka72@gmail.com","0702140396","dp.JPG",dpInputStream);
            if (isSet) {
                System.out.println("Okay");
            } else {
                System.out.println("Not Okay");
            }
//        u.viewProfile("");
//        System.out.println(u.getUserName());
//        System.out.println(u.getDP());
//        System.out.println(u.getName());

        }catch(Exception e){
            System.out.println(e);
        }
    }
}
