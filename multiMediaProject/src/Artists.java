import java.util.HashMap;
import java.util.Objects;

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

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Artist artist = (Artist) o;
            return artistID == artist.artistID && Objects.equals(name, artist.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(artistID, name);
        }
    }

    public Artist createArtist() {
        return new Artist();
    }

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

    public Boolean artistExists(int id) {
        if (artists.get(id) != null)
            return true;
        return false;
    }
}