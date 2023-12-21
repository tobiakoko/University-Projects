package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The ModifyProductController class controls the modification of product details.
 */
public class ModifyProductController implements Initializable {

    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();
    private int currentIndex = 0;

    @FXML private TableView<Product> productTableView;
    @FXML private TextField modifyProductSearchField;
    @FXML private TextField modifyProductId;
    @FXML private TextField modifyProductName;
    @FXML private TextField modifyProductInv;
    @FXML private TextField modifyProductPrice;
    @FXML private TextField modifyProductMax;
    @FXML private TextField modifyProductMin;
    @FXML private Button modifyProductCancelButton;
    @FXML private Button modifyProductSaveButton;
    @FXML private Button removeAssociatedPart;
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    @FXML private TableView<Part> associatedPartTableView;
    @FXML private TableColumn<Part, Integer> associatedPartId;
    @FXML private TableColumn<Part, String> associatedPartName;
    @FXML private TableColumn<Part, Integer> associatedPartInventory;
    @FXML private TableColumn<Part, Double> associatedPartPrice;
    @FXML private Button addButton;

    /**
     * Method to populate the controller with data for a selected product.
     *
     * @param selectedProduct The selected product to modify.
     */
    public void productChosen (int index, Product selectedProduct) {

        // Populate the fields with product data
        currentIndex = index;
        modifyProductId.setText(Integer.toString(selectedProduct.getId()));
        modifyProductName.setText(selectedProduct.getName());
        modifyProductInv.setText(Integer.toString(selectedProduct.getStock()));
        modifyProductPrice.setText(Double.toString(selectedProduct.getPrice()));
        modifyProductMax.setText(Integer.toString(selectedProduct.getMax()));
        modifyProductMin.setText(Integer.toString(selectedProduct.getMin()));
        associatedPartsList.setAll(selectedProduct.getAllAssociatedParts());

        for (Part part: selectedProduct.getAllAssociatedParts()) {
            associatedPartsList.add(part);
        }
    }

    /**
     * Initializes the controller.
     *
     * @param url The URL to the FXML file.
     * @param resourceBundle The ResourceBundle for localization.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTableView.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //add parts to associated  table (bottom)
        associatedPartTableView.setItems(associatedPartsList);
        associatedPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Event handler for the "Search" button click.
     *
     * @param actionEvent The ActionEvent associated with the event.
     */
    @FXML
    void onModifyProductSearch(ActionEvent actionEvent) {
        String searchField = modifyProductSearchField.getText();
        ObservableList<Part> displayedResult = Inventory.lookupPart(searchField);
        try {
            while (displayedResult.isEmpty()) {
                int partID = Integer.parseInt(searchField);
                displayedResult.add(Inventory.lookupPart(partID));
            }
            partTableView.setItems(displayedResult);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Part not found");
            alert.showAndWait();
        }
    }

    /**
     * Event handler for the "Cancel" button click.
     *
     * @param actionEvent The ActionEvent associated with the event.
     * @throws IOException If there is an error loading the main view.
     */
    @FXML
    public void onModifyProductCancel(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel this task?");
        Optional<ButtonType> button = alert.showAndWait();
        if(button.isPresent() && button.get() == ButtonType.OK) {

            Parent parent = FXMLLoader.load(getClass().getResource("/View/main-view.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Event handler for the "Save" button click to save the modified product.
     *
     * @param actionEvent The ActionEvent associated with the event.
     */
    @FXML
    void onModifyProductSave(ActionEvent actionEvent) throws IOException {
        try {
            // Retrieve the selected product
            int id = Integer.parseInt(modifyProductId.getText());
            String name = modifyProductName.getText();
            double price = Double.parseDouble(modifyProductPrice.getText());
            int stock = Integer.parseInt(modifyProductInv.getText());
            int max = Integer.parseInt(modifyProductMax.getText());
            int min = Integer.parseInt(modifyProductMin.getText());

            // Validate input data
            if (min >= max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Max must be greater than Min");
                alert.showAndWait();
            } else if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory value must be between Min and Max values");
                alert.showAndWait();
            } else if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Required field 'Name' is empty");
                alert.showAndWait();
            } else {

                // Update the selected product with the new values
                Product modifiedProduct = new Product(id, name, price, stock, min, max);
                Inventory.modifyProduct(id, modifiedProduct);

                for (Part part: associatedPartsList) {
                    if (part != associatedPartsList)
                        modifiedProduct.addAssociatedPart(part);
                }

                // Redirect back to the main view
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/View/main-view.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error Modifying Product");
            alert.setContentText("All fields are required.");
            alert.showAndWait();
        }
    }


    /**
     * Event handler for the "Remove Associated Part" button click.
     *
     * @param actionEvent The ActionEvent associated with the event.
     */
    @FXML
    void onRemoveAssociatedParts(ActionEvent actionEvent) {
        Part selectedPart = associatedPartTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("No part selected from the associated lis");
            alert.showAndWait();
        }
        else {
            boolean confirmed = showConfirmationDialog("Remove Associated Part", "Are you sure you want to remove this part?");
            if (confirmed) {
                associatedPartsList.remove(selectedPart);
                associatedPartTableView.setItems(associatedPartsList);
            }
        }
    }

    /**
     * Display a confirmation dialog.
     *
     * @param title   The title of the dialog.
     * @param message The confirmation message.
     * @return True if the user confirms, false otherwise.
     */
    private boolean showConfirmationDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * Event handler for the "Add" button click.
     *
     * @param actionEvent The ActionEvent associated with the event.
     */
    @FXML
    void onAddButton(ActionEvent actionEvent) {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selection Error");
            alert.setContentText("No parts selected");
            alert.showAndWait();
        } else if (!associatedPartsList.contains(selectedPart)) {
            associatedPartsList.add(selectedPart);
            associatedPartTableView.setItems(associatedPartsList);
        }
    }
}
