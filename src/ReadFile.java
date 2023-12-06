import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import static java.lang.Float.parseFloat;

public class ReadFile {
    public Songs songs = new Songs();
    public Albums albums = new Albums();
    public Artists artists = new Artists();
    public Playlists playlists = new Playlists();
    BufferedReader reader = new BufferedReader(new FileReader("input.txt"));

    public void readFile() throws IOException, ParseException {
        String[] info;
        String line;
        while ((line = reader.readLine()) != null) {
            info = line.split(",");

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(info[4]);

            Songs.Song song = songs.createSong();
            song.setName(info[0]);
            song.setLength(parseFloat(info[3]));
            song.setSongID(songs.getSongs().size());
            song.setDate(date);

            Artists.Artist artist = artists.createArtist();
            artist.setName(info[1]);
            artist.setArtistID(artists.getArtists().size());

            song.setArtistID(artist.getArtistID());

            Albums.Album album = albums.createAlbum();
            album.setName(info[2]);
            album.setAlbumID(albums.getAlbums().size());
            album.setReleaseDate(date);
            album.setGenre(info[5]);

            songs.addSongs(song);
            artists.addArtists(artist);
            albums.addAlbum(album);

            Random random = new Random();
            for (int i = 0; i < 5; i++) {
                Playlists.Playlist playlist = playlists.createPlaylist();
                playlist.setPlaylistID(playlists.getPlaylists().size());
                playlist.setPlaylistTitle("Playlist: " + playlist.getPlaylistID());
                ArrayList<Songs.Song> songList = new ArrayList<>();
                for (int j = 0; j < 15; j++) {
                    int songID = random.nextInt(songs.songs.size());
                    songList.add(songs.songs.get(songID));
                }
                playlist.setSongs(songList);
                playlists.addPlaylist(playlist);
            }
        }
    }



    public ReadFile() throws IOException {
            System.err.println("Error fool");
        }
}
