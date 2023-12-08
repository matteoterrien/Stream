import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class songWindow {

    @FXML
    private ObservableList<ImageView> imageViews = FXCollections.observableArrayList();

    @FXML
    private ObservableList<ListView<String>> listViews = FXCollections.observableArrayList();

    @FXML
    private Button add;

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
    private Button search;

    @FXML
    private TextField searchSongField;

    @FXML
    private ComboBox<String> sortSongs;

    @FXML
    private ComboBox<String> populatedSongs;

    @FXML
    private ComboBox<String> playlists;

    public void initialize() {
        // Add your ImageView and ListView instances to the observable lists during
        // initialization
        imageViews.addAll(img1, img2, img3, img4, img5, img6, img7, img8);
        listViews.addAll(list1, list2, list3, list4, list5, list6, list7, list8);
        List<String> createdPlaylists = databaseAccess.getPlaylists();
        playlists.getItems().addAll(createdPlaylists);
        // Hide all ImageViews and ListViews initially
        hideAll();
        sortSongs.getItems().addAll(
                "Duration Ascending",
                "Duration Descending",
                "Newest Songs",
                "Oldest Songs",
                "Alphabetically Ascending",
                "Alphabetically Descending");
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

    public void handleBackButton() {
        // Handle back button click
        Stage stage = (Stage) add.getScene().getWindow(); // Get the current stage
        stage.close(); // Close the current stage
        homeWindowClass homeWindow = new homeWindowClass();
        try {
            homeWindow.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleSearchSong() {
        String songPrefix = searchSongField.getText().trim();
        if (!songPrefix.isEmpty()) {
            int limit = getLimitValue();
            String searchCriteria = sortSongs.getSelectionModel().getSelectedItem();
            System.out.println("SEARCH CRITERIA IS " + searchCriteria);
            List<List<String>> songs;
            if (limit != -1) {

                if (searchCriteria != null) {
                    songs = databaseAccess.getSongListWithPrefixAndLimit(songPrefix, limit, searchCriteria);
                } else {
                    songs = databaseAccess.getSongListWithPrefixAndLimit(songPrefix, limit);
                }
            } else {
                if (searchCriteria != null) {
                    songs = databaseAccess.getSongListWithPrefixAndLimit(songPrefix, 8, searchCriteria);
                } else {
                    songs = databaseAccess.getSongListWithPrefixAndLimit(songPrefix, 8);
                }
            }

            populateListViews(songs);

        } else {
            System.out.println("insert search text");
        }
    }

    public void handleAddSong(){
        String selectedPlaylist = playlists.getSelectionModel().getSelectedItem();
        String selectedSong = populatedSongs.getSelectionModel().getSelectedItem();
        // Check if both playlist and song are selected
        if (selectedPlaylist != null && selectedSong != null) {
            databaseAccess.addSongToPlaylist(selectedPlaylist, selectedSong);
            System.out.println("Song added to playlist successfully!");
        
        } else {
            System.out.println("Please select both a playlist and a song to add.");
        }
    }

    public void insertData(int index, String data) {
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
        populatedSongs.getItems().clear();
        for (int i = 0; i < songs.size(); i++){
            populatedSongs.getItems().add(songs.get(i).get(1));
        }
        for (int i = 0; i < songs.size(); i++) {
            List<String> curSongData = songs.get(i);
            for (String data : curSongData) {
                final int index = i; // need to make it final to use inside runLater
                Platform.runLater(() -> {
                    insertData(index, data);
                });
            }
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
