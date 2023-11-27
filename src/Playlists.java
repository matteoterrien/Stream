import java.util.HashMap;

public class Playlists {
    public HashMap<Integer, Playlist> playlists = new HashMap<>();
    public class Playlist {
        private int playlistID;
        private String playlistTitle;
        private int userID;
        private String imageURL;

        public void setPlaylistID(int playlistID) {
            this.playlistID = playlistID;
        }
        public void setPlaylistTitle(String playlistTitle) {
            this.playlistTitle = playlistTitle;
        }
        public void setUserID(int userID) {
            this.userID = userID;
        }
        public void setImageURL(String imageURL) {
            this.imageURL = imageURL;
        }
        public int getPlaylistID() {
            return playlistID;
        }
        public String getPlaylistTitle() {
            return playlistTitle;
        }
        public int getUserID() {
            return userID;
        }
        public String getImageURL() {
            return imageURL;
        }
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
