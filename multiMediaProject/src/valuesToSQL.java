import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.lang.String;

public class valuesToSQL {

    Songs songs = ReadFile.songs;
    Artists artists = ReadFile.artists;
    Albums albums = ReadFile.albums;
    Playlists playlists = ReadFile.playlists;

    public void sendSongValues() throws SQLException {
        String str = "INSERT INTO Songs (songID, name, length, date) VALUES ";
        for (int i = 1; i < songs.getSongs().size() + 1; i++) {
            Songs.Song song = songs.getSong(i);
            str += "(" + song.getSongID() + ", "
                    + song.getName() + ", "
                    + song.getLength() + ", "
                    + song.getDate() + ")";
            if (i < songs.getSongs().size())
                str += ", ";
        }
        Connection connection; // Initialize and manage your database connection
        try {
            // Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/musicGroup?user=musicGroup&password=musicCSC365!");

            Statement statement = connection.createStatement();
            statement.executeUpdate(str);
            connection.close();
        } catch (Exception e) {
            // might need to catch other exceptions found in slides code
            e.printStackTrace();
        }
    }

    public void sendArtistValues() throws SQLException {
        String str = "INSERT INTO Artists (artistID, name) VALUES ";
        for (int i = 1; i < artists.getArtists().size() + 1; i++) {
            Artists.Artist artist = artists.getArtist(i);
            str += ("(" + artist.getArtistID() + ", " + artist.getName() + ")");
            if (i < artists.getArtists().size())
                str += ", ";
        }
        Connection connection; // Initialize and manage your database connection
        try {
            // Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/musicGroup?user=musicGroup&password=musicCSC365!");

            Statement statement = connection.createStatement();
            statement.executeUpdate(str);
            connection.close();
        } catch (Exception e) {
            // might need to catch other exceptions found in slides code
            e.printStackTrace();
        }
    }

    public void sendSongToArtist() throws SQLException {
        String str = "INSERT INTO SongsToArtist (songID, artistID) VALUES ";
        for (int i = 1; i < albums.getAlbums().size() + 1; i++) {
            Albums.Album album = albums.getAlbum(i);
            List<Songs.Song> songs = album.getSongs();
            for (int j = 0; j < songs.size(); j++) {
                str += ("(" + songs.get(j).getSongID() + ", " + album.getArtistID() + ")");
                if (j < songs.size() - 1)
                    str += ", ";
            }
            if (i < albums.getAlbums().size())
                str += ", ";
        }
        Connection connection; // Initialize and manage your database connection
        try {
            // Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/musicGroup?user=musicGroup&password=musicCSC365!");

            Statement statement = connection.createStatement();
            statement.executeUpdate(str);
            connection.close();
        } catch (Exception e) {
            // might need to catch other exceptions found in slides code
            e.printStackTrace();
        }
    }

    public void sendAlbumValues() throws SQLException {
        String str = "INSERT INTO Albums (albumID, artistID, releaseDate, albumName, genre) VALUES ";
        for (int i = 1; i < albums.getAlbums().size() + 1; i++) {
            Albums.Album album = albums.getAlbum(i);
            str += ("(" + album.getAlbumID() + ", "
                    + album.getArtistID() + ", "
                    + album.getReleaseDate() + ", "
                    + album.getName() + ", "
                    + album.getGenre() + ")");
            if (i < albums.getAlbums().size())
                str += ", ";
        }
        Connection connection; // Initialize and manage your database connection
        try {
            // Class.forName("com.mysql.jdbc.Drive r");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/musicGroup?user=musicGroup&password=musicCSC365!");

            Statement statement = connection.createStatement();
            statement.executeUpdate(str);
            connection.close();
        } catch (Exception e) {
            // might need to catch other exceptions found in slides code
            e.printStackTrace();
        }
    }

    public void sendAlbumSongsValues() throws SQLException {
        String str = "INSERT INTO AlbumSongs (albumID, albumSongID, albumArtistID) VALUES ";
        for (int i = 1; i < albums.getAlbums().size() + 1; i++) {
            Albums.Album album = albums.getAlbum(i);
            List<Songs.Song> songs = album.getSongs();
            for (int j = 0; j < songs.size(); j++) {
                str += ("(" + album.getAlbumID() + ", " + songs.get(j).getSongID() + ", " + album.getArtistID() + ")");
                if (j < songs.size() - 1)
                    str += ", ";
            }
            if (i < albums.getAlbums().size())
                str += ", ";
        }
        Connection connection; // Initialize and manage your database connection
        try {
            // Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/musicGroup?user=musicGroup&password=musicCSC365!");

            Statement statement = connection.createStatement();
            statement.executeUpdate(str);
            connection.close();
        } catch (Exception e) {
            // might need to catch other exceptions found in slides code
            e.printStackTrace();
        }
    }

    public void sendPaylistValues() throws SQLException {
        String str = "INSERT INTO Playlists (playlistID, playlistTitle) VALUES ";
        for (int i = 1; i < playlists.getPlaylists().size() + 1; i++) {
            Playlists.Playlist playlist = playlists.getPlaylist(i);
            str += "(" + playlist.getPlaylistID() + ", " + playlist.getPlaylistTitle() + ")";
            if (i < playlists.getPlaylists().size())
                str += ", ";

        }
        System.out.println(str);
        Connection connection; // Initialize and manage your database connection
        try {
            // Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/musicGroup?user=musicGroup&password=musicCSC365!");

            Statement statement = connection.createStatement();
            statement.executeUpdate(str);
            connection.close();
        } catch (Exception e) {
            // might need to catch other exceptions found in slides code
            e.printStackTrace();
        }
    }

    public void sendPlaylistSongsValues() throws SQLException {
        String str = "INSERT INTO PlaylistSongs (playlistID, songID) VALUES ";
        System.out.println("\n");
        for (int i = 1; i < playlists.getPlaylists().size() + 1; i++) {
            Playlists.Playlist playlist = playlists.getPlaylist(i);
            for (int j = 0; j < playlist.getPlaylist().size(); j++) {
                Songs.Song song = playlist.getPlaylist().get(j);
                if (song == null)
                    continue;
                str += ("(" + playlist.getPlaylistID() + ", " + song.getSongID() + ")");
                if (i < playlist.getPlaylist().size() - 1 && song != null)
                    str += ", ";
            }
            // if (i < playlists.getPlaylists().size() - 1)
            // str += ", ";
        }
        System.out.println(str);
        Connection connection; // Initialize and manage your database connection
        try {
            // Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/musicGroup?user=musicGroup&password=musicCSC365!");

            Statement statement = connection.createStatement();
            statement.executeUpdate(str);
            connection.close();
        } catch (Exception e) {
            // might need to catch other exceptions found in slides code
            e.printStackTrace();
        }
    }
}
