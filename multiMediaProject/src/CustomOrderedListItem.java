import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CustomOrderedListItem {

    private final ObservableList<SimpleStringProperty> fields;

    public CustomOrderedListItem(String... initialValues) {
        fields = FXCollections.observableArrayList();
        for (String value : initialValues) {
            fields.add(new SimpleStringProperty(value));
        }
    }

    public ObservableList<SimpleStringProperty> getFields() {
        return fields;
    }

    public void setField(int index, String value) {
        if (index >= 0 && index < fields.size()) {
            fields.get(index).set(value);
        } else {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    @Override
    public String toString() {
        // Customize the string representation as needed
        StringBuilder sb = new StringBuilder("CustomOrderedListItem: ");
        for (SimpleStringProperty field : fields) {
            sb.append(field.get()).append(" ");
        }
        return sb.toString().trim();
    }
}