import java.sql.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PlayWithMySQL extends Application implements EventHandler<ActionEvent> {

  static Connection connect;

  public static void main(String[] args) {
    launch(args);
  }

  private Button button;
  private Button submit;

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Title of the Window");
    button = new Button();
    button.setText("Click me");
    button.setOnAction(this);

    TextField textField = new TextField();
    textField.setPrefWidth(100);
    textField.setMaxWidth(100);
    textField.setLayoutX(150);
    textField.setLayoutY(150);

    Button submit = new Button("Submit");
    submit.setOnAction(e -> {
      String userInput = textField.getText();
      System.out.println(userInput);
    });
    submit.setLayoutY(150);

    Pane layout = new Pane();
    layout.getChildren().add(button);
    layout.getChildren().addAll(textField, submit);

    Scene scene = new Scene(layout, 1200, 900);
    primaryStage.setScene(scene);
    primaryStage.show();
    try {
      Class.forName("com.mysql.jdbc.Driver");
      connect = DriverManager.getConnection(
          "jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/musicGroup?user=musicGroup&password=musicCSC365!");
    } catch (Exception e) {
      // might need to catch other exceptions found in slides code
      e.printStackTrace();
    }

    String insertSQL = "INSERT INTO Artists(name) VALUES (?)";
    try (PreparedStatement preparedStatement = connect.prepareStatement(insertSQL)) {
      // Set the value for the parameter at index 1
      preparedStatement.setString(1, "Beyonce");

      // Execute the update
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace(); // Handle exceptions appropriately
    }

    try (Statement statement = connect.createStatement()) {
      ResultSet rs;
      rs = statement.executeQuery("SELECT * FROM Artists");
      System.out.println("Im here");
      while (rs.next()) {
        String artist = rs.getString("name");
        System.out.println("Artist = " + artist);
      }
      connect.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void handle(ActionEvent event) {
    if (event.getSource() == button) {
      System.out.println("button pressed");
    } else if (event.getSource() == submit) {
      System.out.println("Submit pressed");
    }
  }
}
