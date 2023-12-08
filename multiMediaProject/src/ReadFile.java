import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ReadFile {

    public static Songs songs = new Songs();
    public static Artists artists = new Artists();
    public static Albums albums = new Albums();
    public static Playlists playlists = new Playlists();

    BufferedReader reader = new BufferedReader(new FileReader("songs.txt"));

    public void readFile() throws IOException, ParseException {
        String[] info;
        String line;
        Set<Artists.Artist> uniqueArtists = new HashSet<>();
        Set<Albums.Album> uniqueAlbums = new HashSet<>();

        Albums.Album album = albums.createAlbum();
        HashMap<String, Integer> ArtistID = new HashMap<String, Integer>();

        while ((line = reader.readLine()) != null) {
            if (line.length() == 0)
                continue;

            info = line.split(",");

            String[] strLength = info[3].split(":");
            int length = (Integer.parseInt(strLength[0]) * 60) + Integer.parseInt(strLength[1]);

            Songs.Song song = songs.createSong();
            song.setSongID(songs.getSongs().size() + 1);
            song.setName(info[0]);
            song.setLength(length);
            song.setDate(info[4]);
            songs.addSongs(song);

            Artists.Artist artist = artists.createArtist();
            artist.setArtistID(0);
            artist.setName(info[1]);
            uniqueArtists.add(artist);
            if (!ArtistID.containsKey(artist.getName()))
                ArtistID.put(artist.getName(), uniqueArtists.size());

            if (album.getName() == null || !album.getName().equals(info[2])) {
                Albums.Album newAlbum = albums.createAlbum();
                album = newAlbum;
                album.setName(info[2]);
                album.setArtistID(ArtistID.get(info[1]));
                album.setReleaseDate(info[4]);
                album.setGenre(info[5]);
                album.addSong(song);
                uniqueAlbums.add(album);
            } else {
                album.addSong(song);
            }
        }

        for (Artists.Artist value : uniqueArtists) {
            value.setArtistID(ArtistID.get(value.getName()));
            artists.addArtists(value);
            // System.out.println(value.getName() + ", " + value.getArtistID());
        }
        System.out.println("\n");
        for (Albums.Album value : uniqueAlbums) {
            // System.out.println(value.getName() + ", " + value.getArtistID());
            value.setAlbumID(albums.getAlbums().size() + 1);
            albums.addAlbum(value);
            // for (Songs.Song song : value.getSongs())
            // System.out.println(song.getName());
            // System.out.println("\n");
        }

        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            Playlists.Playlist playlist = playlists.createPlaylist();
            playlist.setPlaylistID(playlists.getPlaylists().size() + 1);
            playlist.setPlaylistTitle("\'" + "Playlist" + playlist.getPlaylistID() + "\'");
            for (int j = 0; j < 15; j++) {
                int songID = random.nextInt(songs.getSongs().size() + 1) + 1;
                System.out.println(songID);
                System.out.println(songID + "," + songs.getSongs().get(songID).getName());
                playlist.addSong(songs.getSongs().get(songID));
                // System.out.println(songs.getSongs().get(songID).getName());
            }
            playlists.addPlaylist(playlist);
        }
    }

    public ReadFile() throws IOException {
        System.err.println("Error fool");
    }
}