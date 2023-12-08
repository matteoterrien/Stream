import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class artistWindowClass {
    private String artistName;
    private Stage stage;

    public artistWindowClass() {
        // empty to make code compile
    }

    public artistWindowClass(String artistName) {
        this.artistName = artistName;
    }

    public artistWindowClass(String artistName, Stage stage) {
        this.stage = stage;
    }

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("artistsView.fxml"));
        Parent root = loader.load();

        // Get the controller instance
        artistWindow controller = loader.getController();
        controller.initialize();
        controller.setArtistName(artistName);
        // controller.setArtistData(artistName, controller);

        // controller.clearAll();
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle(artistName);
        primaryStage.show();
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistName() {
        return this.artistName;
    }

}
