package SoundWave.App;

import SoundWave.User.Artist;
import SoundWave.User.Listener;
import SoundWave.User.User;

public class SoundWave {
    public static void main(String[] args)  {
        try {
          boolean isSet = false;
           User u = new Listener();
           User a = new Artist();
//           isSet= u.login("Chanux2002","1234");
//            String tempImagePath = "C:/Chanuka/NIBM/EAD/test.PNG";
//           String tempSongPath = "C:/Chanuka/NIBM/EAD/sample.wav";
//           FileInputStream dpInputStream = new FileInputStream(tempImagePath);
//            FileInputStream songInputStream = new FileInputStream(tempSongPath);
//            isSet = u.register("Chanux2000", "1234", "Chanuka dilshan", "bchanuka72@gmail.com", "0702140396", "dp.JPG", dpInputStream);
//            isSet = u.forgetPassword("Chanux2002","Chanuka@20021004");
//            u.editProfile("hiruni","Chanuka@20021004","Chanuka DIlshan Marambage","mchanuka72@gmail.com","0702140396","dp.JPG",dpInputStream);
//
//
            // isSet = ((Artist) a).uploadSong("DemoSong","2.0","test.PNG",dpInputStream,"A001",songInputStream,"Sample.wav");
//            ((Artist) a).updateSong("S002","TestSong","5","dp.jpg",dpInputStream);
//            ((Listener) u).createPlayList("DemoPlayList","dp.JPG",dpInputStream,"L001");
//           ((Listener) u).likeSong("S001","L001");
//            isSet=((Listener) u).addSongToPlayList("P001","S003");
//
//           int count = u.viewLikeCount("S001");
//            System.out.println(count);
//            PlayList p = new PlayList();
//            List<String> songs = p.getSongList("P001");
//            for(String i :songs){
//                System.out.println(i);
//            }
//            isSet = ((Artist) a).removeSong("S003");
//            if (isSet) {
//                System.out.println("Okay");
//            } else {
//                System.out.println("Not Okay");
//            }
//        u.viewProfile("Chanux2002");
//        System.out.println(u.getUserName());
//        System.out.println(u.getDP());
//        System.out.println(u.getName());
//            ArrayList<String[]> songList = ((Listener) u).exploreSong();
//            for (String[] song : songList) {
//                System.out.println("SongId: " + song[0] + ", Title: " + song[1] + ", Song: " + song[2] + ", Duration: " + song[3] + ", CoveImg: " + song[4]+ ", Name: " + song[5]);
//            }
//
//            String songPath = "C:/Chanuka/NIBM/EAD/ghost.wav";
//            String songPath ="https://www.youtube.com/watch?v=mRD0-GxqHVo&list=RDmRD0-GxqHVo&start_radio=1";
//
//            ((Listener) u).unlikeSong("S002","L003");
//            Feedback fb = new Feedback();
//
//            ArrayList<String[]> feedbackList = fb.getFeedbackDetails("A001");
//            for(String[] feedback :feedbackList){
//                System.out.println("Song Title: "+feedback[0]+ ", Likes: " + feedback[1]);
//            }
//            ArrayList<String[]> List =u.viewProfile("adhajdhajsd");
//            if (List == null) {
//                System.out.println("No user found with the username: ");
//            }
//            else{
//                for(String[] details: List){
//                System.out.println("UserName: "+ details[0]);
//                System.out.println("Password: "+ details[1]);
//                System.out.println("Name: "+ details[2]);
//                System.out.println("Email: "+ details[3]);
//                System.out.println("ContactNo: "+ details[4]);
//                System.out.println("DP: "+ details[5]);
//                }
//            }
//            boolean isSet = false;
//            isSet = ((Artist) a).removeSong("S002");
//            if (isSet) {
//                System.out.println("Okay");
//            } else {
//                System.out.println("Not Okay");
//            }

        }catch(Exception e){
            System.out.println(e);
        }
    }
}
