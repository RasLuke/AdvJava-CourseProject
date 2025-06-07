package InventoryProgram;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

// Import logging classes
import java.util.logging.Logger;

public class SmartStockApp extends Application {
    // ObservableList to keep TableView updated automatically
    private ObservableList<Product> inventory = FXCollections.observableArrayList();

    // Get logger from LogManagerUtil
    private static final Logger logger = LogManagerUtil.getLogger();

    @Override
    public void start(Stage stage) {
        logger.info("Application started");

        // Create TableView and columns
        TableView<Product> table = new TableView<>(inventory);

        TableColumn<Product, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Integer> qtyCol = new TableColumn<>("Quantity");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Product, Double> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.getColumns().addAll(nameCol, qtyCol, priceCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Input fields for adding new products
        TextField nameField = new TextField();
        nameField.setPromptText("Product Name");

        TextField qtyField = new TextField();
        qtyField.setPromptText("Quantity");

        TextField priceField = new TextField();
        priceField.setPromptText("Price");

        // Add product button
        Button addButton = new Button("Add Product");
        addButton.setOnAction(e -> {
            try {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    showAlert(Alert.AlertType.WARNING, "Validation Error", "Product name cannot be empty.");
                    logger.warning("Attempted to add product with empty name");
                    return;
                }
                int qty = Integer.parseInt(qtyField.getText().trim());
                if (qty < 0) {
                    showAlert(Alert.AlertType.WARNING, "Validation Error", "Quantity must be 0 or greater.");
                    logger.warning("Attempted to add product with negative quantity: " + qty);
                    return;
                }
                double price = Double.parseDouble(priceField.getText().trim());
                if (price < 0) {
                    showAlert(Alert.AlertType.WARNING, "Validation Error", "Price must be 0 or greater.");
                    logger.warning("Attempted to add product with negative price: " + price);
                    return;
                }

                Product newProduct = new Product(name, qty, price);
                inventory.add(newProduct);

                logger.info("Added product: " + name + ", Qty: " + qty + ", Price: " + price);

                // Clear inputs
                nameField.clear();
                qtyField.clear();
                priceField.clear();

            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Quantity and price must be valid numbers.");
                logger.warning("Invalid number input for quantity or price: " + ex.getMessage());
            }
        });

        // Delete selected product button
        Button deleteButton = new Button("Delete Selected");
        deleteButton.setOnAction(e -> {
            Product selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                inventory.remove(selected);
                logger.info("Deleted product: " + selected.getName());
            } else {
                showAlert(Alert.AlertType.INFORMATION, "No Selection", "Please select a product to delete.");
                logger.warning("Delete attempted with no product selected");
            }
        });

        // Layout input controls horizontally
        HBox inputBox = new HBox(10, nameField, qtyField, priceField, addButton, deleteButton);
        inputBox.setStyle("-fx-padding: 10;");

        // Main layout
        VBox root = new VBox(10, table, inputBox);
        root.setStyle("-fx-padding: 10;");

        Scene scene = new Scene(root, 700, 400);

        stage.setTitle("SmartStock Inventory Management");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(e -> logger.info("Application closed"));
    }

    // Helper method for showing alerts
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
