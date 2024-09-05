package SoundWave.Music;

import SoundWave.DBConnection.DBConnection;
import java.sql.*;

public class Feedback {
    private Connection conn;

    public Feedback() {

    }

    public String getFeedbackDetails(String songId) throws SQLException {
        String totCount="0";
        try {
            conn = DBConnection.getConnection();

            String sql = "SELECT  SUM(Likes) AS TotalLikes from feedback WHERE SongId = ? GROUP BY SongId";
            PreparedStatement selectStatement = conn.prepareStatement(sql);
            selectStatement.setString(1, songId);

            ResultSet result = selectStatement.executeQuery();
            if (result.next()) {
                totCount = result.getString("TotalLikes");
            }
            result.close();
        } catch (Exception e) {
            System.out.println("Feedback Class getFeedbackDetails method Error: " + e);
        }
        finally{
            conn.close();
        }
        return totCount;
    }
    public boolean isLiked(String songId,String listenerId) throws  SQLException{
        boolean isLiked = false;
        try {
            conn = DBConnection.getConnection();

            String sql = "Select * from feedback where SongId=? and ListenerId=?";
            PreparedStatement selectStatement = conn.prepareStatement(sql);
            selectStatement.setString(1, songId);
            selectStatement.setString(2, listenerId);

            ResultSet result = selectStatement.executeQuery();
            if (result.next()) {
                isLiked = true;
            }
        } catch (Exception e) {
            System.out.println("Feedback class id Liked method Error: " + e);
        } finally {
            conn.close();
        }
        return  isLiked;
    }
}
