import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class albumWindow {

    @FXML
    private ObservableList<ListView<String>> listViews = FXCollections.observableArrayList();

    private String albumName;
    @FXML
    private Button AtoZ;

    @FXML
    private Button ZtoA;

    @FXML
    private Button add1;

    @FXML
    private Button add10;

    @FXML
    private Button add2;

    @FXML
    private Button add3;

    @FXML
    private Button add4;

    @FXML
    private Button add5;

    @FXML
    private Button add6;

    @FXML
    private Button add7;

    @FXML
    private Button add8;

    @FXML
    private Button add9;

    @FXML
    private ImageView albumImg;

    @FXML
    private Button ascending;

    @FXML
    private Menu backButton;

    @FXML
    private Button descending;

    @FXML
    private Label errorMessage;

    @FXML
    private Menu homeButton;

    @FXML
    private ListView<String> list1;

    @FXML
    private ListView<String> list10;

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
    private TextField playlist1;

    @FXML
    private TextField playlist10;

    @FXML
    private TextField playlist2;

    @FXML
    private TextField playlist3;

    @FXML
    private TextField playlist4;

    @FXML
    private TextField playlist5;

    @FXML
    private TextField playlist6;

    @FXML
    private TextField playlist7;

    @FXML
    private TextField playlist8;

    @FXML
    private TextField playlist9;

    public void initialize() {
        // Add your ListView instances to the observable lists during initialization
        listViews.addAll(list1, list2, list3, list4, list5, list6, list7, list8, list9, list10);

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
        Stage stage = (Stage) add1.getScene().getWindow(); // Get the current stage
        stage.close(); // Close the current stage

        // Platform.runLater(() -> {
        // try {
        // new homeWindowClass().start(new Stage());
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // });
        // Launch a new instance of HomeWindowClass
        homeWindowClass homeWindow = new homeWindowClass();
        try {
            homeWindow.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAlbumData(String albumName, albumWindow controller){
        List<List<String>> songs = new ArrayList<>();
        songs = databaseAccess.getSongListForAlbum(albumName);
        System.out.println("got here before populateList");
        populateListViews(songs, controller);
        System.out.println(songs);
    }

    public void insertData(int index, String data, albumWindow controller) {
        System.out.println("data is" + index + data + controller);
        Platform.runLater(() -> {
            // Insert data into the corresponding ListView based on the index
            System.out.println(controller.listViews.get(index).getItems().add(data));
    
            // Set visibility to true for the corresponding ImageView and ListView
            controller.showData(index, true);
        });
    }

    public void showData(int index, boolean show) {
        if (index >= 0) {
            listViews.get(index).setVisible(show);
        }
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public void populateListViews(List<List<String>> songs, albumWindow controller) {
        for (int i = 0; i < songs.size(); i++) {
            List<String> curSongData = songs.get(i);
            System.out.println("got here");
            for (String data : curSongData) {
                final int index = i; // need to make it final to use inside runLater
                Platform.runLater(() -> {
                insertData(index, data, controller);
                });
            }
        }

    }
}
