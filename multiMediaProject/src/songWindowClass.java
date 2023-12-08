import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class songWindowClass extends Application {

    private String songSearch;
    private Stage stage;

    public songWindowClass() {
        // empty to make code compile
    }

    public songWindowClass(String songSearch) {
        this.songSearch = songSearch;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("songWindow.fxml"));
        Parent root = loader.load();

        // Get the controller instance
        songWindow controller = loader.getController();
        
        controller.populateListViews(databaseAccess.getSongListWithPrefixAndLimit(songSearch, 8)); //8 is default data returned


        //controller.clearAll();
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Song Search");
        primaryStage.show();
    }

    public void setSongSearch(String songSearch) {
        this.songSearch = songSearch;
    }

    public String getSongSearch() {
        return this.songSearch;
    }

    public static void main(String[] args) {
        launch(args);
    }

}