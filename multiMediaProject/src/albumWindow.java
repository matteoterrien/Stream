import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class albumWindow{

    @FXML
    private ObservableList<ListView<String>> listViews = FXCollections.observableArrayList();

    private String albumName;
    @FXML
    private Button AtoZ;

    @FXML
    private Button ZtoA;

    @FXML
    private Button add;

    @FXML
    private ImageView albumImg;

    @FXML
    private Button ascending;

    @FXML
    private Menu backButton;

    @FXML
    private Label consoleOutput;

    @FXML
    private Button descending;

    @FXML
    private Label errorMessage;

    @FXML
    private ComboBox<String> fieldComboBox;

    @FXML
    private Menu homeButton;

    @FXML
    private TextField limit;

    @FXML
    private ListView<String> list1;

    @FXML
    private ListView<String> list10;

    @FXML
    private ListView<String> list11;

    @FXML
    private ListView<String> list12;

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
    private ListView<String> list9;

    @FXML
    private TextField playlist;

    @FXML
    private TextField songText;
    
    public void initialize() {
        // Add your ListView instances to the observable lists during initialization
        listViews.addAll(list1, list2, list3, list4, list5, list6, list7, list8, list9, list10, list11, list12);

        // Hide all ImageViews and ListViews initially
        hideAll();

    }

    private void hideAll() {
        // Hide all ImageViews and ListViews
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

    public void handleAtoZButton() {
        List<List<String>> songs = new ArrayList<>();
        int limit = getLimitValue();
        if (limit != -1) {
            songs = databaseAccess.sortAlbumsBy(albumName, "songName", "ASC", limit);
        } else {
            songs = databaseAccess.sortAlbumsBy(albumName, "songName", "ASC", 12);
        }
        System.out.println(songs);
        populateListViews(songs);
    }

    public void handleZtoAButton() {
        List<List<String>> songs = new ArrayList<>();
        int limit = getLimitValue();
        if (limit != -1) {
            songs = databaseAccess.sortAlbumsBy(albumName, "songName", "DESC", limit);
        } else {
            songs = databaseAccess.sortAlbumsBy(albumName, "songName", "DESC", 12);
        }
        System.out.println(songs);
        populateListViews(songs);
    }

    public void handleAscendingButton() {
        List<List<String>> songs = new ArrayList<>();
        int limit = getLimitValue();
        if (limit != -1) {
            songs = databaseAccess.sortAlbumsBy(albumName, "length", "ASC", limit);
        } else {
            songs = databaseAccess.sortAlbumsBy(albumName, "songName", "ASC", 12);
        }
        System.out.println(songs);
        populateListViews(songs);
    }

    public void handleDescendingButton() {
        List<List<String>> songs = new ArrayList<>();
        int limit = getLimitValue();
        if (limit != -1) {
            songs = databaseAccess.sortAlbumsBy(albumName, "length", "DESC", limit);
        } else {
            songs = databaseAccess.sortAlbumsBy(albumName, "length", "DESC", 12);
        }
        System.out.println(songs);
        populateListViews(songs);
    }

    public void setAlbumData(String albumName, albumWindow controller) {
        List<List<String>> songs = new ArrayList<>();
        songs = databaseAccess.getSongListForAlbum(albumName);
        System.out.println("got here before populateList");
        populateListViews(songs);
        Image albumImage = new Image(getClass().getResourceAsStream(databaseAccess.getAlbumCover(albumName)));
        albumImg.setImage(albumImage);
        System.out.println(songs);
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

    public void showData(int index, boolean show) {
        if (index >= 0) {
            listViews.get(index).setVisible(show);
        }
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void clearAll() {
        // Clear data from all ListViews
        for (ListView<String> listView : listViews) {
            listView.getItems().clear();
        }
    }

    public void populateListViews(List<List<String>> songs) {
        clearAll();
        for (int i = 0; i < songs.size(); i++) {
            List<String> curSongData = songs.get(i);
            System.out.println("got here");
            for (String data : curSongData) {
                final int index = i; // need to make it final to use inside runLater
                Platform.runLater(() -> {
                    insertData(index, data);
                });
            }
        }
    }

    public void handleAddButton(ActionEvent event) {
        String playlistName = playlist.getText();
        String songName = songText.getText();

        if (playlistName.isEmpty() || songName.isEmpty()) {
            // Display an error message or take appropriate action if fields are empty
            consoleOutput.setText("Both Playlist and Song Text must be provided.");
            // You might want to show a label or display a dialog to inform the user.
        } else {
            // Both fields have data, proceed with your query or other actions
            // Example: Execute your database query or perform other actions
            consoleOutput.setText("Insert successfull");
            // databaseAccess.addSongToPlaylist(playlistName, songName);
        }
    }

}
