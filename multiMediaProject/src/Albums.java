import java.util.Date;
import java.util.HashMap;

public class Albums {
    public HashMap<Integer, Album> albums = new HashMap<>();
    public class Album {
        private int albumID;
        private String name;
        private int artistID;
        private Date releaseDate;
        private String imageURL;
        private String genre;

        public void setAlbumID(int albumID) { this.albumID = albumID; }
        public void setName(String name) { this.name = name; }
        public void setArtistID(int artistID) {
            this.artistID = artistID;
        }
        public void setReleaseDate(Date releaseDate) {
            this.releaseDate = releaseDate;
        }
        public void setImageURL(String imageURL) {
            this.imageURL = imageURL;
        }
        public void setGenre(String genre) {
            this.genre = genre;
        }
        public int getAlbumID() { return albumID; }
        public String getName() { return name; }
        public int getArtistID() {
            return artistID;
        }
        public Date getReleaseDate() {
            return releaseDate;
        }
        public String getImageURL() {
            return imageURL;
        }
        public String getGenre() {
            return genre;
        }
    }

    public Album createAlbum() { return new Album(); }
    public void setAlbums(HashMap<Integer, Album> albums) {
        this.albums = albums;
    }
    public void addAlbum(Album A) {
        albums.put(A.albumID, A);
    }
    public HashMap<Integer, Album> getAlbums() {
        return albums;
    }
    public Album getAlbum(int id) {
        return albums.get(id);
    }
}