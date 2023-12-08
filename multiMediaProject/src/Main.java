public class Main {
    public static void main(String[] args) throws Exception {
        ReadFile readFile = new ReadFile();
        readFile.readFile();
        valuesToSQL values = new valuesToSQL();
        // values.sendSongValues();
        // values.sendArtistValues();
        // values.sendSongToArtist();
        // values.sendAlbumValues();
        // values.sendAlbumSongsValues();
        // values.sendPaylistValues();
        values.sendPlaylistSongsValues();
    }
}