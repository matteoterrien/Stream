import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Albums {
    public HashMap<Integer, Album> albums = new HashMap<>();

    public class Album {
        private int albumID;
        private String name;
        private int artistID;
        private String releaseDate;
        private String imageURL;
        private String genre;
        private List<Songs.Song> songs;

        public Album() {
            this.songs = new ArrayList<>();
        }

        public void setAlbumID(int albumID) {
            this.albumID = albumID;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setArtistID(int artistID) {
            this.artistID = artistID;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public void setImageURL(String imageURL) {
            this.imageURL = imageURL;
        }

        public void setGenre(String genre) {
            this.genre = genre;
        }

        public void setSongs(List<Songs.Song> songs) {
            this.songs = songs;
        }

        public void addSong(Songs.Song song) {
            this.songs.add(song);
        }

        public int getAlbumID() {
            return albumID;
        }

        public String getName() {
            return name;
        }

        public int getArtistID() {
            return artistID;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public String getImageURL() {
            return imageURL;
        }

        public String getGenre() {
            return genre;
        }

        public List<Songs.Song> getSongs() {
            return songs;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Album album = (Album) o;
            return Objects.equals(this.name, album.name) &&
                    Objects.equals(this.releaseDate, album.releaseDate) &&
                    Objects.equals(this.genre, album.genre);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, releaseDate, genre);
        }
    }

    public Album createAlbum() {
        return new Album();
    }

    public void setAlbums(HashMap<Integer, Album> albums) {
        this.albums = albums;
    }

    public void addAlbum(Album A) {
        albums.put(A.albumID, A);
    }

    public HashMap<Integer, Album> getAlbums() {
        return albums;
    }

    public Album getAlbum(Integer id) {
        return albums.get(id);
    }
}