import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class playlistWindowClass extends Application {

    public playlistWindowClass() {
        // empty to make code compile
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("playlistWindow.fxml"));
        Parent root = loader.load();

        playlistWindow controller = loader.getController();
        // Get the controller instance

        //controller.clearAll();
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Playlist Viewer");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}