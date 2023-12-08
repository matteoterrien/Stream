import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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

    public static List<List<String>> sortSongsByArtist(String artistName, String order, int limit) {
        List<List<String>> songs = new ArrayList<>();
        String selectSQL = "SELECT AR.name as artistName, S.name as songName, S.length, S.date " +
                "FROM Artists AR " +
                "JOIN AlbumSongs ASs on AR.artistID = ASs.albumArtistID " +
                "JOIN Songs S on S.songID = ASs.albumSongID " +
                "WHERE AR.name = ? ORDER BY S.name " + order + " LIMIT ?";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, artistName);
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


    public static List<List<String>> getSongListWithPrefixAndLimit(String prefix, int limit) {
        List<List<String>> songs = new ArrayList<>();
        String selectSQL = "SELECT AR.name as artistName, S.name as songName, S.length, S.date\n" +
                "FROM Albums A\n" +
                "JOIN AlbumSongs ASs on A.albumID = ASs.albumID\n" +
                "JOIN Songs S on S.songID = ASs.albumSongID\n" +
                "JOIN Artists AR on AR.artistID = ASs.albumArtistID\n" +
                "WHERE S.name LIKE ?\n" +  // Use LIKE for pattern matching
                "LIMIT ?;";  // Add LIMIT clause
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, "%" + prefix + "%");  // Set the parameter for the LIKE clause
            preparedStatement.setInt(2, limit);  // Set the limit parameter
            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
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

    public static List<List<String>> getSongListWithPrefixAndLimit(String prefix, int limit, String sortCriteria) {
        List<List<String>> songs = new ArrayList<>();
        String selectSQL = "SELECT AR.name as artistName, S.name as songName, S.length, S.date\n" +
                "FROM Albums A\n" +
                "JOIN AlbumSongs ASs on A.albumID = ASs.albumID\n" +
                "JOIN Songs S on S.songID = ASs.albumSongID\n" +
                "JOIN Artists AR on AR.artistID = ASs.albumArtistID\n" +
                "WHERE S.name LIKE ?\n" +
                "ORDER BY ";
    
        switch (sortCriteria.toLowerCase()) {
            case "duration descending":
                selectSQL += "S.length DESC";
                break;
            case "duration ascending":
                selectSQL += "S.length ASC";
                break;
            case "newest songs":
                selectSQL += "S.date DESC";
                break;
            case "oldest songs":
                selectSQL += "S.date ASC";
                break;
            case "alphabetically ascending":
                selectSQL += "S.name ASC";
                break;
            case "alphabetically descending":
                selectSQL += "S.name DESC";
                break;
            default:
                // Default to alphabetical ascending if no valid sort criteria provided
                selectSQL += "S.name ASC";
                break;
        }
    
        selectSQL += "\nLIMIT ?;";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, "%" + prefix + "%");
            preparedStatement.setInt(2, limit);
            System.out.println(preparedStatement);
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
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    
        return songs;
    }

    public static List<List<String>> getSongListInPlaylist(String playlistName, String sortCriteria, int limit) {
        List<List<String>> songs = new ArrayList<>();
        String selectSQL = "SELECT AR.name AS artistName, S.name AS songName, S.length, S.date\n" +
                    "FROM Playlists P\n" +
                    "JOIN PlaylistsSongs PS ON P.playlistID = PS.playlistID\n" +
                    "JOIN Songs S ON S.songID = PS.songID\n" +
                    "JOIN SongsToArtist SA ON S.songID = SA.songID\n" +
                    "JOIN Artists AR ON AR.artistID = SA.artistID\n" +
                    "WHERE P.playlistTitle = ?\n" +
                    "ORDER BY ";
    
        switch (sortCriteria.toLowerCase()) {
            case "duration descending":
                selectSQL += "S.length DESC";
                break;
            case "duration ascending":
                selectSQL += "S.length ASC";
                break;
            case "newest songs":
                selectSQL += "S.date DESC";
                break;
            case "oldest songs":
                selectSQL += "S.date ASC";
                break;
            case "alphabetically ascending":
                selectSQL += "S.name ASC";
                break;
            case "alphabetically descending":
                selectSQL += "S.name DESC";
                break;
            default:
                // Default to alphabetical ascending if no valid sort criteria provided
                selectSQL += "S.name ASC";
                break;
        }
    
        selectSQL += "\nLIMIT ?;";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, playlistName);
            preparedStatement.setInt(2, limit);
            System.out.println(preparedStatement);
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
            e.printStackTrace();
            // Handle exceptions appropriately
        }
        // Execute the query and populate the 'songs' list
        // (Assume you have a method like executeQueryWithParameters that takes the SQL and parameters)
    
        return songs;
    }

    public static List<List<String>> getSongListInPlaylist(String playlistName, int limit) {
        List<List<String>> songs = new ArrayList<>();
        String selectSQL = "SELECT AR.name AS artistName, S.name AS songName, S.length, S.date\n" +
                   "FROM Playlists P\n" +
                   "JOIN PlaylistsSongs PS ON P.playlistID = PS.playlistID\n" +
                   "JOIN Songs S ON S.songID = PS.songID\n" +
                   "JOIN SongsToArtist SA ON S.songID = SA.songID\n" +
                   "JOIN Artists AR ON AR.artistID = SA.artistID\n" +
                   "WHERE P.playlistTitle = ?\n" +
                   "LIMIT ?;";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, playlistName);
            preparedStatement.setInt(2, limit);
            System.out.println(preparedStatement);
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
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    
        return songs;
    }

    public static List<String> getPlaylists() {
        List<String> playlists = new ArrayList<>();
        String selectSQL = "SELECT playlistTitle FROM Playlists";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
    
            while (resultSet.next()) {
                // Retrieve the playlist name and add it to the list
                playlists.add(resultSet.getString("playlistTitle"));
            }
    
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    
        return playlists;
    }

    public static boolean playlistExists(String playlistName) {
        String selectSQL = "SELECT COUNT(*) FROM Playlists WHERE playlistTitle = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, playlistName);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // If count is greater than 0, the playlist already exists
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }

        return false; // Return false if an exception occurred or if the count is 0
    }

    public static void addPlaylist(String playlistName) {
        String insertSQL = "INSERT INTO Playlists (playlistTitle) VALUES (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, playlistName);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }

    public static void addSongToPlaylist(String playlistName, String songName) {
        // Check if the playlist and song exist
        if (!playlistExists(playlistName)) {
            System.out.println("Playlist does not exist: " + playlistName);
            return;
        }
    
        String selectPlaylistIDSQL = "SELECT playlistID FROM Playlists WHERE playlistTitle = ?";
        String selectSongIDSQL = "SELECT songID FROM Songs WHERE name = ?";
        String insertSongToPlaylistSQL = "INSERT INTO PlaylistsSongs (playlistID, songID) VALUES (?, ?)";
    
        try (PreparedStatement playlistIDStatement = connection.prepareStatement(selectPlaylistIDSQL);
             PreparedStatement songIDStatement = connection.prepareStatement(selectSongIDSQL);
             PreparedStatement insertStatement = connection.prepareStatement(insertSongToPlaylistSQL)) {
    
            // Get playlist ID
            playlistIDStatement.setString(1, playlistName);
            ResultSet playlistIDResultSet = playlistIDStatement.executeQuery();
            
            if (playlistIDResultSet.next()) { // Check if there is a result
                int playlistID = playlistIDResultSet.getInt("playlistID");
    
                // Get song ID
                songIDStatement.setString(1, songName);
                ResultSet songIDResultSet = songIDStatement.executeQuery();
    
                if (songIDResultSet.next()) { // Check if there is a result
                    int songID = songIDResultSet.getInt("songID");
    
                    // Insert into PlaylistsSongs
                    insertStatement.setInt(1, playlistID);
                    insertStatement.setInt(2, songID);
                    insertStatement.executeUpdate();
                } else {
                    System.out.println("Song not found: " + songName);
                }
            } else {
                System.out.println("Playlist ID not found for playlist: " + playlistName);
            }
    
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }


    public static List<String> getPlaylistImageURLs(String playlistName, int limit) {
        List<String> imageURLs = new ArrayList<>();
        String selectSQL = "SELECT A.imageURL " +
                           "FROM Albums A " +
                           "JOIN AlbumSongs ASs ON A.albumID = ASs.albumID " +
                           "JOIN PlaylistsSongs PS ON ASs.albumSongID = PS.songID " +
                           "JOIN Playlists P ON P.playlistID = PS.playlistID " +
                           "WHERE P.playlistTitle = ? " +
                           "LIMIT ?;";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            preparedStatement.setString(1, playlistName);
            preparedStatement.setInt(2, limit);
    
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    // Add each imageURL to the list
                    imageURLs.add(resultSet.getString("imageURL"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // throw e; // Re-throw the exception to signal the error to the caller
        }
        return imageURLs;
    }

    public static List<String> getImageURLsForSongs(List<String> songNames, int limit) {
    List<String> imageURLs = new ArrayList<>();
    
    // Create a placeholder for the list of song parameters in the SQL query
    String songNamePlaceholders = String.join(",", Collections.nCopies(songNames.size(), "?"));

    String selectSQL = "SELECT A.imageURL " +
                       "FROM Albums A " +
                       "JOIN AlbumSongs ASs ON A.albumID = ASs.albumID " +
                       "JOIN Songs S ON S.songID = ASs.albumSongID " +
                       "WHERE S.name IN (" + songNamePlaceholders + ") " +
                       "LIMIT ?;";

    try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
        // Set the parameters for song names
        for (int i = 0; i < songNames.size(); i++) {
            preparedStatement.setString(i + 1, songNames.get(i));
        }
        
        // Set the parameter for the limit
        preparedStatement.setInt(songNames.size() + 1, limit);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                // Add each imageURL to the list
                imageURLs.add(resultSet.getString("imageURL"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle exceptions appropriately, throw or return a default value
    }
    return imageURLs;
}

public static void deleteSongFromPlaylist(String playlistTitle, String songName) {
    // Check if the playlist exists

    String deleteSongSQL = "DELETE FROM PlaylistsSongs " +
                           "WHERE playlistID = (SELECT playlistID FROM Playlists WHERE playlistTitle = ?) " +
                           "AND songID = (SELECT songID FROM Songs WHERE name = ?)";

    try (PreparedStatement preparedStatement = connection.prepareStatement(deleteSongSQL)) {
        preparedStatement.setString(1, playlistTitle);
        preparedStatement.setString(2, songName);

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Song deleted from playlist successfully: " + songName);
        } else {
            System.out.println("Song not found in playlist: " + songName);
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Handle exceptions appropriately
    }
}

    public static String formatTime(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int remainingSeconds = seconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds);
    }

    public static void deletePlaylist(String playlistName) {
        String deletePlaylistSQL = "DELETE FROM Playlists WHERE playlistTitle = ?";
    
        try (PreparedStatement preparedStatement = connection.prepareStatement(deletePlaylistSQL)) {
            preparedStatement.setString(1, playlistName);
    
            int rowsAffected = preparedStatement.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Playlist deleted successfully: " + playlistName);
            } else {
                System.out.println("Playlist not found: " + playlistName);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }
}
