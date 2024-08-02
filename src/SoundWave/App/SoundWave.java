package SoundWave.App;

import SoundWave.User.Artist;
import SoundWave.User.Listener;
import SoundWave.User.User;

public class SoundWave {
    public static void main(String[] args){
        //login
        boolean isSet=false;
        User u = new Listener();
        //isSet= u.login("Chanux2002","1234");
        //isSet = u.register("Dinuka1990","1234","Dinuka Madushan","mdinuka77@gmail.com","0772663816","Nothing");
        //isSet = u.forgetPassword("Chanux2002","Chanuka@20021004");
        //u.editProfile("Chanux2002","Chanuka@20021004","Chanuka DIlshan Marambage","mchanuka72@gmail.com","0702140396","non");
        if(isSet){
            System.out.println("Okay");
        }
        else{
            System.out.println("Not Okay");
        }
//        u.viewProfile("Dinuka1994");
//        System.out.println(u.getUserName());
//        System.out.println(u.getDP());
//        System.out.println(u.getName());
    }
}
