import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class playlistWindow {

    @FXML
    private ObservableList<ImageView> imageViews = FXCollections.observableArrayList();

    @FXML
    private ObservableList<ListView<String>> listViews = FXCollections.observableArrayList();

    @FXML
    private Button createPlaylist;

    @FXML
    private Menu handleBackButton;

    @FXML
    private ImageView img1;

    @FXML
    private ImageView img2;

    @FXML
    private ImageView img3;

    @FXML
    private ImageView img4;

    @FXML
    private ImageView img5;

    @FXML
    private ImageView img6;

    @FXML
    private ImageView img7;

    @FXML
    private ImageView img8;

    @FXML
    private TextField limit;

    @FXML
    private ListView<String> list1;

    @FXML
    private ListView<String> list2;

    @FXML
    private ListView<String> list3;

    @FXML
    private ListView<String> list4;

    @FXML
    private ListView<String> list5;

    @FXML
    private ListView<String> list6;

    @FXML
    private ListView<String> list7;

    @FXML
    private ListView<String> list8;

    @FXML
    private TextField playlist;

    @FXML
    private ComboBox<String> selectPlaylist;

    @FXML
    private Button search;

    @FXML
    private Button deletePlaylist;

    @FXML 
    private ComboBox<String> populatedSongs;



    @FXML
    private ComboBox<String> sortSongs;

   public void initialize() {
        // Add your ImageView and ListView instances to the observable lists during
        // initialization
        imageViews.addAll(img1, img2, img3, img4, img5, img6, img7, img8);
        listViews.addAll(list1, list2, list3, list4, list5, list6, list7, list8);

        // Hide all ImageViews and ListViews initially
        hideAll();
        sortSongs.getItems().addAll(
                "Duration Ascending",
                "Duration Descending",
                "Newest Songs",
                "Oldest Songs",
                "Alphabetically Ascending",
                "Alphabetically Descending");
        populatePlaylists();
    }

    private void hideAll() {
        // Hide all ImageViews and ListViews
        for (ImageView imageView : imageViews) {
            imageView.setVisible(false);
        }

        for (ListView<?> listView : listViews) {
            listView.setVisible(false);
        }
    }

    public void populatePlaylists(){
        selectPlaylist.getItems().clear();
        List<String> playlists = databaseAccess.getPlaylists();
        selectPlaylist.getItems().addAll(playlists);

    }

    public void handleBackButton() {
        // Handle back button click
        Stage stage = (Stage) createPlaylist.getScene().getWindow(); // Get the current stage
        stage.close(); // Close the current stage
        homeWindowClass homeWindow = new homeWindowClass();
        try {
            homeWindow.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleSearchPlaylist() {
        String playlist = selectPlaylist.getSelectionModel().getSelectedItem();
        if (playlist != null) {
            int limit = getLimitValue();
            String searchCriteria = sortSongs.getSelectionModel().getSelectedItem();
            List<List<String>> songs;
            if (limit != -1) {

                if (searchCriteria != null) {
                    songs = databaseAccess.getSongListInPlaylist(playlist, searchCriteria, limit);
                } else {
                    songs = databaseAccess.getSongListInPlaylist(playlist, limit);
                }
            } else {
                if (searchCriteria != null) {
                    songs = databaseAccess.getSongListInPlaylist(playlist, searchCriteria, 8);
                } else {
                    songs = databaseAccess.getSongListInPlaylist(playlist, 8);
                }
            }
            populateListViews(songs);

        } else {
            System.out.println("insert search text");
        }
    }

    public void insertData(int index, String data) {
        System.out.println("data is" + index + data);
        Platform.runLater(() -> {
            // Insert data into the corresponding ListView based on the index
            listViews.get(index).getItems().add(data);

            // Set visibility to true for the corresponding ImageView and ListView
            showData(index, true);
        });
    }

    public void showData(int index, boolean show) {
        if (index >= 0) {
            listViews.get(index).setVisible(show);
        }
    }

    public Integer getLimitValue() {
        // Get the value from the TextField
        String limitValue = limit.getText();

        // Convert the value to an integer if needed
        int limitInt = -1;
        try {
            limitInt = Integer.parseInt(limitValue);

        } catch (NumberFormatException e) {
            // Handle the case where the entered text is not a valid integer
            System.out.println("Invalid limit value: " + limitValue);

        }
        return limitInt;
    }

    public void populateListViews(List<List<String>> songs) {
        clearAll();
        List<String> songList = new ArrayList<String>();
        for (int i = 0; i < songs.size(); i++) {
            List<String> curSongData = songs.get(i);
            System.out.println("got here");
            songList.add(curSongData.get(1).split(":")[1].trim());

            for (String data : curSongData) {
                final int index = i; // need to make it final to use inside runLater
                Platform.runLater(() -> {
                    insertData(index, data);
                });
            }
        }
        populatedSongs.getItems().clear();
        for (int i = 0; i < songList.size(); i++){
            populatedSongs.getItems().add(songList.get(i));
        }
        System.out.println("NOw going to insert image?");
        List<String> imageURLs = databaseAccess.getImageURLsForSongs(songList, 8);
        for (int i = 0; i < imageURLs.size(); i++){
            System.out.println(imageURLs.get(i));
            insertImage(i, imageURLs.get(i));
        }
    }

    public void insertImage(int index, String imagePath) {
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        System.out.println(imageStream);
        if (imageStream == null) {
            System.err.println("Image not found: " + imagePath);
        }

        Image image = new Image(imageStream);
        imageViews.get(index).setImage(image);

        // Set visibility to true for the corresponding ImageView
        showData(index, true);
    }

    public void handleCreatePlaylist() {
        // Retrieve the playlist name from the text field
        String playlistName = playlist.getText().trim();

        // Check if the playlist name is not empty
        if (!playlistName.isEmpty()) {
            // Call the method to create the playlist (replace the comments with your logic)
            // databaseAccess.createPlaylist(playlistName);
            System.out.println("Playlist created: " + playlistName);
            if (!databaseAccess.playlistExists(playlistName)) {
                // Playlist does not exist, so insert it
                databaseAccess.addPlaylist(playlistName);
                selectPlaylist.getItems().add(playlistName);
                System.out.println("playlist created successfully");
            } else {
                // Playlist already exists
                System.out.println("Playlist already exists: " + playlistName);
            }
        } else {
            // Playlist name is empty, print a message to the console
            System.out.println("Please enter a playlist name.");
        }
    }

    public void handleDeletePlaylist(){
        String selectedPlaylist = selectPlaylist.getSelectionModel().getSelectedItem();
        if (selectPlaylist != null){
            databaseAccess.deletePlaylist(selectedPlaylist);
            populatePlaylists();
        }else{
            System.out.println("select playlist to delete");

        }
        clearAll();
    }

    public void handleDeleteSong(){
        String songToDelete = populatedSongs.getSelectionModel().getSelectedItem();
        String playlistToDeleteFrom = selectPlaylist.getSelectionModel().getSelectedItem();

        if (songToDelete != null && playlistToDeleteFrom != null){
            databaseAccess.deleteSongFromPlaylist(playlistToDeleteFrom, songToDelete);
        } else{
            System.out.println("select song to delete and playlist to delete from");
        }
    }

    public void clearAll() {
        // Clear data from all ListViews
        for (ListView<?> listView : listViews) {
            listView.getItems().clear();
        }

        // Clear images from all ImageViews
        for (ImageView imageView : imageViews) {
            imageView.setImage(null);
        }

        // Hide all ImageViews and ListViews
        hideAll();
    }
}

