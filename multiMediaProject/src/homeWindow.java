import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class homeWindow {

    @FXML
    private ObservableList<ImageView> imageViews = FXCollections.observableArrayList();
    @FXML
    private ObservableList<ListView<String>> listViews = FXCollections.observableArrayList();

    @FXML
    private Button createPlaylist;

    @FXML
    private TextField createPlaylistField;

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
    private Button searchAlbum;

    @FXML
    private TextField searchAlbumField;

    @FXML
    private Button searchArtist;

    @FXML
    private TextField searchArtistField;

    @FXML
    private Button searchSong;

    @FXML
    private TextField searchSongField;

    public void handleSearchArtist() {
        String artistToSearch = searchArtistField.getText();
        openArtistWindow(artistToSearch, (Stage) list1.getScene().getWindow());
    }

    public void openArtistWindow(String artistName, Stage currentWindow) {
        // Open the albumWindow with the corresponding album name
        String selectSQL = "SELECT A.artistID\n" + //
                "FROM Artists A\n" + //

                "WHERE A.name = (?);";
        try (PreparedStatement preparedStatement = homeWindowClass.connect.prepareStatement(selectSQL)) {
            // Set the value for the parameter at index 1
            preparedStatement.setString(1, artistName);

            // Execute the update
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // If there is a result, create and show the albumWindow
                Platform.runLater(() -> currentWindow.close());
                currentWindow.close();
                artistWindowClass artistWindow = new artistWindowClass(artistName);
                try {
                    Stage stage = new Stage();
                    artistWindow.start(stage);
                    System.out.println(artistWindow);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // If there is no result, print a message to the terminal
                System.out.println("Artist not found: " + artistName);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }

    }

    public void initialize() {
        // Add your ImageView and ListView instances to the observable lists during
        // initialization
        imageViews.addAll(img1, img2, img3, img4, img5, img6, img7, img8);
        listViews.addAll(list1, list2, list3, list4, list5, list6, list7, list8);

        // Hide all ImageViews and ListViews initially
        hideAll();
    }

    public void showData(int index, boolean show) {
        if (index >= 0 && index < imageViews.size()) {
            imageViews.get(index).setVisible(show);
            listViews.get(index).setVisible(show);
        }
    }

    public void insertData(int index, String data) {
        // Insert data into the corresponding ListView based on the index
        // ...
        listViews.get(index).getItems().add(data);

        // Set visibility to true for the corresponding ImageView and ListView
        showData(index, true);
    }

    public void insertImage(int index, String imagePath) {
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream == null) {
            System.err.println("Image not found: " + imagePath);
            return;
        }

        Image image = new Image(imageStream);
        imageViews.get(index).setImage(image);

        // Set visibility to true for the corresponding ImageView
        showData(index, true);
    }

    public void createPlaylist() {
        // Retrieve the playlist name from the text field
        String playlistName = createPlaylistField.getText().trim();

        // Check if the playlist name is not empty
        if (!playlistName.isEmpty()) {
            // Call the method to create the playlist (replace the comments with your logic)
            // databaseAccess.createPlaylist(playlistName);
            System.out.println("Playlist created: " + playlistName);
        } else {
            // Playlist name is empty, print a message to the console
            System.out.println("Please enter a playlist name.");
        }
    }

    public void handleSearchAlbum() {
        String albumToSearch = searchAlbumField.getText();

        openAlbumWindow(albumToSearch, (Stage) list1.getScene().getWindow());
        // Check if the entered album is in the list
        // if (albumList.contains(albumToSearch)) {
        // openAlbumWindow(albumToSearch);
        // } else {
        // // Album not found, show an error message
        // System.out.print("Album Not Found " + albumToSearch);
        // }
    }

    public void handleSearchSong() {
        String songPrefix = searchSongField.getText().trim();
        if (!songPrefix.isEmpty()) {
            Stage currentWindow = (Stage) list1.getScene().getWindow();
            currentWindow.close();
            songWindowClass songWindow = new songWindowClass(songPrefix);
            try {
                Stage stage = new Stage();
                songWindow.start(stage);
                System.out.println(songWindow);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("insert search text");
        }
    }

    public void openAlbumWindow(String albumName, Stage currentWindow) {
        // Open the albumWindow with the corresponding album name
        String selectSQL = "SELECT albumID\n" + //
                "FROM Artists A\n" + //
                "JOIN Albums AA on A.artistID = AA.artistID\n" + //
                "WHERE AA.albumName = (?);";
        try (PreparedStatement preparedStatement = homeWindowClass.connect.prepareStatement(selectSQL)) {
            // Set the value for the parameter at index 1
            preparedStatement.setString(1, albumName);

            // Execute the update
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // If there is a result, create and show the albumWindow
                Platform.runLater(() -> currentWindow.close());
                currentWindow.close();
                albumWindowClass albumWindow = new albumWindowClass(albumName);
                try {
                    Stage stage = new Stage();
                    albumWindow.start(stage);
                    System.out.println(albumWindow);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // If there is no result, print a message to the terminal
                System.out.println("Album not found: " + albumName);
            }
        } catch (

        SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
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

    private void hideAll() {
        // Hide all ImageViews and ListViews
        for (ImageView imageView : imageViews) {
            imageView.setVisible(false);
        }

        for (ListView<?> listView : listViews) {
            listView.setVisible(false);
        }
    }
}
