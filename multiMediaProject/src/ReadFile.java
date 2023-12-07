import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;

public class ReadFile {

    public static Songs songs = new Songs();
    public static Artists artists = new Artists();
    public static Albums albums = new Albums();
    public static Playlists playlists = new Playlists();

    BufferedReader reader = new BufferedReader(new FileReader("songs.txt"));

    public void readFile() throws IOException, ParseException {
        String[] info;
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.length() == 0)
                continue;

            info = line.split(",");
            // for (int i = 0; i < info.length; i++)
            //     System.out.println(info[i]);

            String[] strLength = info[3].split(":");
            int length = Integer.parseInt(strLength[0]) * Integer.parseInt(strLength[1]);

            Songs.Song song = songs.createSong();
            song.setName(info[0]);

            song.setLength(length);
            song.setSongID(songs.getSongs().size());
            song.setDate(info[4]);

            Artists.Artist artist = artists.createArtist();
            artist.setName(info[1]);
            artist.setArtistID(artists.getArtists().size());

            song.setArtistID(artist.getArtistID());

            Albums.Album album = albums.createAlbum();
            album.setName(info[2]);
            album.setAlbumID(albums.getAlbums().size());
            album.setReleaseDate(info[4]);
            album.setGenre(info[5]);

            songs.addSongs(song);
            artists.addArtists(artist);
            albums.addAlbum(album);
        }
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

    public ReadFile() throws IOException {
        System.err.println("Error fool");
    }
}