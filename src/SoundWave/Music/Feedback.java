package SoundWave.Music;

import SoundWave.DBConnection.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Feedback {
    public String feedbackId,userId,songId;
    public double rating;
    Connection conn;

    public Feedback(){
        try{
            conn= DBConnection.getConnection();
        }
        catch(SQLException e){
            System.out.println("Error: "+e);
        }
    }
    //methods
    public ArrayList<String[]> getFeedbackDetails(String artistId) throws SQLException {
        ArrayList<String[]> FeedbackList = new ArrayList<>();
        try{
            String sql ="SELECT s.Title,SUM(f.Likes) AS TotalLikes FROM feedback f INNER JOIN song s ON s.SongId = f.SongId where s.ArtistId=? GROUP BY s.Title";

            PreparedStatement selectStatement = conn.prepareStatement(sql);
            selectStatement.setString(1,artistId);

            ResultSet result = selectStatement.executeQuery();
            while(result.next()){
                String[] details = new String[2];
                details[0] = result.getString("Title");
                details[1] = result.getString("TotalLikes");
                FeedbackList.add(details);
            }
        }
        catch(Exception e){
            System.out.println("Error: "+e);
        }
        finally{
            conn.close();
        }
        return FeedbackList;
    }
}
