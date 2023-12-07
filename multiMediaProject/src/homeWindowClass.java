import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class homeWindowClass extends Application {

    public static Connection connect;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homeWindow.fxml"));
            Parent root = loader.load();

            // Get the controller instance
            homeWindow controller = loader.getController();
            controller.initialize();
            controller.insertData(0, "name: Mirrors");
            controller.insertData(0, "length: 4:37");
            controller.insertData(0, "album: The 20/20 Experience");
            controller.insertData(0, "artist: Justin Timberlake");
            controller.insertImage(0, "The 20:20 experience.jpg");

            controller.insertData(1, "cat");

            // controller.clearAll();
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Your Application Title");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            // Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(
                    "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/musicGroup?user=musicGroup&password=musicCSC365!");
        } catch (Exception e) {
            // might need to catch other exceptions found in slides code
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void openAlbumWindow(String albumName, Stage currentWindow) {
        // Open the albumWindow with the corresponding album name
        String selectSQL = "SELECT albumID\n" + //
                "FROM Artists A\n" + //
                "JOIN Albums AA on A.artistID = AA.artistID\n" + //
                "WHERE AA.albumName = (?);";
        try (PreparedStatement preparedStatement = connect.prepareStatement(selectSQL)) {
            // Set the value for the parameter at index 1
            preparedStatement.setString(1, albumName);

            // Execute the update
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // If there is a result, create and show the albumWindow
                int albumID = resultSet.getInt("albumID");
                System.out.println(albumID);
                if (albumID > 0) {
                    Platform.runLater(() -> currentWindow.close());
                    // currentWindow.close();
                    // albumWindowClass albumWindow = new albumWindowClass(albumName);
                    try {
                        Stage stage = new Stage();
                        // albumWindow.start(stage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // If there is no result, print a message to the terminal
                    System.out.println("Album not found: " + albumName);
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
}
