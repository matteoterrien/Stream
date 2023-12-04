import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DynamicTableViewExample extends Application {

    private final ObservableList<CustomOrderedListItem> data = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        TableView<CustomOrderedListItem> tableView = new TableView<>();
        addColumns(tableView);

        // Example data
        data.add(new CustomOrderedListItem("Field 1", "Field 2"));
        data.add(new CustomOrderedListItem("Field A", "Field B", "Field C"));

        tableView.getItems().addAll(data);

        // Add a row for user input
        addInputRow(tableView);

        VBox vbox = new VBox(tableView);
        Scene scene = new Scene(vbox, 500, 300);

        primaryStage.setTitle("Dynamic TableView Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addColumns(TableView<CustomOrderedListItem> tableView) {
        CustomOrderedListItem sampleItem = new CustomOrderedListItem(); // Empty item for getting field count

        for (int i = 0; i < sampleItem.getFields().size(); i++) {
            final int colIndex = i;
            TableColumn<CustomOrderedListItem, String> column = new TableColumn<>("Field " + (i + 1));
            column.setCellValueFactory(new PropertyValueFactory<>(String.valueOf(colIndex))); // Bind to SimpleStringProperty
            column.setCellFactory(TextFieldTableCell.forTableColumn()); // Use TextFieldTableCell for editing
            tableView.getColumns().add(column);
        }
    }

    private void addInputRow(TableView<CustomOrderedListItem> tableView) {
        // Add a row with text fields for user input
        CustomOrderedListItem emptyItem = new CustomOrderedListItem(); // Empty item for getting field count

        // Create a new row for input
        CustomOrderedListItem inputItem = new CustomOrderedListItem();
        tableView.getItems().add(inputItem);

        // Set the initial values for the input fields
        for (int i = 0; i < emptyItem.getFields().size(); i++) {
            TextField textField = new TextField();
            final int colIndex = i;
            textField.textProperty().bindBidirectional(inputItem.getFields().get(colIndex));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}