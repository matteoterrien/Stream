import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Playlists {

    public HashMap<Integer, Playlist> playlists = new HashMap<>();

    public class Playlist {
        private int playlistID;
        private String playlistTitle;
        private List<Songs.Song> songs;

        public Playlist() {
            this.songs = new ArrayList<>();
        }

        public void setPlaylistID(int playlistID) {
            this.playlistID = playlistID;
        }

        public void setPlaylistTitle(String playlistTitle) {
            this.playlistTitle = playlistTitle;
        }

        public void setSongs(List<Songs.Song> songs) {
            this.songs = songs;
        }

        public int getPlaylistID() {
            return playlistID;
        }

        public String getPlaylistTitle() {
            return playlistTitle;
        }

        public List<Songs.Song> getPlaylist() {
            return songs;
        }

        public void addSong(Songs.Song s) {
            this.songs.add(s);
        }
    }

    public Playlist createPlaylist() {
        return new Playlist();
    }

    public void setPlaylists(HashMap<Integer, Playlist> playlists) {
        this.playlists = playlists;
    }

    public void addPlaylist(Playlist P) {
        playlists.put(P.playlistID, P);
    }

    public HashMap<Integer, Playlist> getPlaylists() {
        return playlists;
    }

    public Playlist getPlaylist(int id) {
        return playlists.get(id);
    }
}