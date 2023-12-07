import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class valuesToSQL {


   public Songs songs = new Songs();
   public Artists artists = new Artists();
   public Albums albums = new Albums();
   public Playlists playlists = new Playlists();


   public void sendSongValues() throws SQLException {
       String str = "";
       for (int i = 0; i < songs.getSongs().size(); i++) {
           Songs.Song song = songs.songs.get(i);
           str += ("(" + song.getSongID() + ", "
                   + song.getName() + ", "
                   + song.getLength() + ", "
                   + song.getDate() + ")");
           if (i < songs.getSongs().size() - 1)
               str += ", ";
       }
       Connection connection; // Initialize and manage your database connection
       try {
           // Class.forName("com.mysql.jdbc.Driver");
           connection = DriverManager.getConnection(
                   "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/musicGroup?user=musicGroup&password=musicCSC365!");


           Statement statement = connection.createStatement();
           statement.executeUpdate("Insert into Songs (songID, name, length, date) values " + str);
           connection.commit();
           connection.close();
       } catch (Exception e) {
           // might need to catch other exceptions found in slides code
           e.printStackTrace();
       }
   }


   public void sendArtistValues() throws SQLException {
       String str = "";
       for (int i = 0; i < artists.getArtists().size(); i++) {
           Artists.Artist artist = artists.artists.get(i);
           str += ("(" + artist.getArtistID() + ", "
                   + artist.getName() + ")");
           if (i < artists.getArtists().size() - 1)
               str += ", ";
       }
       Connection connection; // Initialize and manage your database connection
       try {
           // Class.forName("com.mysql.jdbc.Driver");
           connection = DriverManager.getConnection(
                   "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/musicGroup?user=musicGroup&password=musicCSC365!");


           Statement statement = connection.createStatement();
           statement.executeUpdate("Insert into Artists (artistID, name) values " + str);
           connection.commit();
           connection.close();
       } catch (Exception e) {
           // might need to catch other exceptions found in slides code
           e.printStackTrace();
       }
   }


   public void sendAlbumValues() throws SQLException {
       String str = "";
       for (int i = 0; i < albums.getAlbums().size(); i++) {
           Albums.Album album = albums.albums.get(i);
           str += ("(" + album.getAlbumID() + ", "
                   + album.getArtistID() + ", "
                   + album.getReleaseDate() + ", "
                   + album.getName() + ", "
                   + album.getGenre() + ")");
           if (i < albums.getAlbums().size() - 1)
               str += ", ";
       }
       Connection connection; // Initialize and manage your database connection
       try {
           // Class.forName("com.mysql.jdbc.Driver");
           connection = DriverManager.getConnection(
                   "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/musicGroup?user=musicGroup&password=musicCSC365!");


           Statement statement = connection.createStatement();
           statement.executeUpdate("Insert into Albums (albumID, artistID, releaseDate, name, genre) values " + str);
           connection.commit();
           connection.close();
       } catch (Exception e) {
           // might need to catch other exceptions found in slides code
           e.printStackTrace();
       }
   }
}

