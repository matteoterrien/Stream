import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class databaseAccess {
    private static Connection connection; // Initialize and manage your database connection
    static {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/musicGroup?user=musicGroup&password=musicCSC365!");
        } catch (Exception e) {
            // might need to catch other exceptions found in slides code
            e.printStackTrace();
        }
    }
    // Other database configuration and setup code...

    public static List<List<String>> getSongListForAlbum(String albumName) {
        List<List<String>> songs = new ArrayList<>();
        System.out.println("We got here");
        String selectSQL = "SELECT AR.name as artistName, S.name as songName, S.length, S.date\n" + //
                "FROM Albums A\n" + //
                "JOIN AlbumSongs ASs on A.albumID = ASs.albumID\n" + //
                "JOIN Songs S on S.songID = ASs.albumSongID\n" + //
                "JOIN Artists AR on AR.artistID = ASs.albumArtistID\n" + //
                "WHERE A.albumName = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, albumName);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next() && songs.size() <= 12) {
                // Use the correct column names to retrieve values
                List<String> song = new ArrayList<>();
                song.add("artist: " + resultSet.getString("artistName"));
                song.add("song: " + resultSet.getString("songName"));
                song.add("length: " + formatTime(resultSet.getInt("length")));
                song.add("date: " + resultSet.getString("date"));

            songs.add(song);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }

        return songs;
    }

    public static String getAlbumCover(String albumName){
        String cover = "";
        String selectSQL = "SELECT A.imageURL FROM Albums A WHERE A.albumName = ?;";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, albumName);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // If there is a result, retrieve the imageURL
                    cover = resultSet.getString("imageURL");
                } else {
                    // If there is no result, throw an exception or return a specific value
                    //throw new SQLException("No album found with the given name: " + albumName);
                    // Alternatively, you can return a default value or handle it in another way
                    cover = "noIMG.jpg";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //throw e; // Re-throw the exception to signal the error to the caller
        }
        return cover;
    }

    public static List<List<String>> sortAlbumsBy(String albumName, String sortType, String order, int limit) {
        List<List<String>> songs = new ArrayList<>();
        String selectSQL = "SELECT AR.name as artistName, S.name as songName, S.length, S.date\n" +
                "FROM Albums A\n" +
                "JOIN AlbumSongs ASs on A.albumID = ASs.albumID\n" +
                "JOIN Songs S on S.songID = ASs.albumSongID\n" +
                "JOIN Artists AR on AR.artistID = ASs.albumArtistID\n" +
                "WHERE A.albumName = ? ORDER BY " + sortType + " " + order + " LIMIT ?";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, albumName);
            preparedStatement.setInt(2, limit); // Set the limit parameter
    
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                List<String> song = new ArrayList<>();
                song.add("artist: " + resultSet.getString("artistName"));
                song.add("song: " + resultSet.getString("songName"));
                song.add("length: " + formatTime(resultSet.getInt("length")));
                song.add("date: " + resultSet.getString("date"));
    
                songs.add(song);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    
        return songs;
    }

    public static String formatTime(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int remainingSeconds = seconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }
}
