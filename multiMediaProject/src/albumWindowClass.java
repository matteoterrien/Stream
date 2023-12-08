import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class albumWindowClass extends Application {

    private String albumName;
    private Stage stage;

    public albumWindowClass() {
        // empty to make code compile
    }

    public albumWindowClass(String albumName) {
        this.albumName = albumName;
    }

    public albumWindowClass(String albumName, Stage stage) {
        this.stage = stage;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("albumView.fxml"));
        Parent root = loader.load();

        // Get the controller instance
        albumWindow controller = loader.getController();
        controller.initialize();
        controller.setAlbumName(albumName);
        controller.setAlbumData(albumName, controller);

        // controller.clearAll();
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle(albumName);
        primaryStage.show();
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumName() {
        return this.albumName;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
