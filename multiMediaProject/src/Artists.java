import java.util.HashMap;

public class Artists {
    public HashMap<Integer, Artist> artists = new HashMap<>();

    public class Artist {
        private int artistID;
        private String name;

        public void setName(String name) {
            this.name = name;
        }
        public void setArtistID(int artistID) {
            this.artistID = artistID;
        }
        public String getName() {
            return name;
        }
        public int getArtistID() {
            return artistID;
        }
    }

    public Artist createArtist() { return new Artist(); }
    public void setArtists(HashMap<Integer, Artist> artists) {
        this.artists = artists;
    }
    public void addArtists(Artist A) {
        artists.put(A.artistID, A);
    }
    public HashMap<Integer, Artist> getArtists() {
        return artists;
    }
    public Artist getArtist(int id) {
        return artists.get(id);
    }
}