import java.math.BigInteger;
import java.util.HashMap;

public class Songs {
    public HashMap<Integer, Song> songs = new HashMap<>();

    public class Song {
        private int songID;
        private String name;
        private int length;
        private String date;
        private BigInteger audioFile;

        public void setSongID(int songID) {
            this.songID = songID;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public void setAudioFile(BigInteger audioFile) {
            this.audioFile = audioFile;
        }

        public int getSongID() {
            return songID;
        }

        public String getName() {
            return name;
        }

        public int getLength() {
            return length;
        }

        public String getDate() {
            return date;
        }

        public BigInteger getAudioFile() {
            return audioFile;
        }
    }

    public Song createSong() {
        return new Song();
    }

    public void setSongs(HashMap<Integer, Song> songs) {
        this.songs = songs;
    }

    public void addSongs(Song S) {
        songs.put(S.songID, S);
    }

    public HashMap<Integer, Song> getSongs() {
        return songs;
    }

    public Song getSong(int id) {
        return songs.get(id);
    }
}