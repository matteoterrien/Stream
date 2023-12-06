import java.util.ArrayList;
import java.util.HashMap;

public class Playlists {
    public HashMap<Integer, Playlist> playlists = new HashMap<>();
    public class Playlist {
        private int playlistID;
        private String playlistTitle;
        private ArrayList<Songs.Song> songs;

        public void setPlaylistID(int playlistID) {
            this.playlistID = playlistID;
        }
        public void setPlaylistTitle(String playlistTitle) {
            this.playlistTitle = playlistTitle;
        }
        public void setSongs(ArrayList<Songs.Song> songs) { this.songs = songs; }
        public int getPlaylistID() {
            return playlistID;
        }
        public String getPlaylistTitle() {
            return playlistTitle;
        }
        public ArrayList<Songs.Song> getPlaylist() {
            return songs;
        }
    }

    public Playlist createPlaylist() { return new Playlist(); }
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
