package SoundWave.Music;

import SoundWave.DBConnection.DBConnection;

import java.sql.*;

public class Feedback {
    private String feedbackId, userId, songId;
    private double rating;
    private Connection conn;

    public Feedback() {
        try {
            conn = DBConnection.getConnection();
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }

    // Method to get feedback details for a specific song
    public String getFeedbackDetails(String songId) throws SQLException {
        String totCount="0";
        try {
            String sql = "SELECT  SUM(Likes) AS TotalLikes from feedback WHERE SongId = ? GROUP BY SongId";

            PreparedStatement selectStatement = conn.prepareStatement(sql);
            selectStatement.setString(1, songId);

            ResultSet result = selectStatement.executeQuery();
            if (result.next()) {
                totCount = result.getString("TotalLikes");
            }
        } catch (Exception e) {
            System.out.println("Error getFeedbackDetails: " + e);
        } finally {
            conn.close();
        }
        return totCount;
    }
    public boolean isLiked(String songId,String listenerId) throws  SQLException{
        boolean isLiked = false;
        try {
            String sql = "Select * from feedback where SongId=? and ListenerId=?";

            PreparedStatement selectStatement = conn.prepareStatement(sql);
            selectStatement.setString(1, songId);
            selectStatement.setString(2, listenerId);

            ResultSet result = selectStatement.executeQuery();
            if (result.next()) {
                isLiked = true;
            }
        } catch (Exception e) {
            System.out.println("Error Is Liked: " + e);
        } finally {
            conn.close();
        }
        return  isLiked;
    }
}
