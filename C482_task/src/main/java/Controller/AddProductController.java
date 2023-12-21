package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
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
import java.util.Random;
import java.util.ResourceBundle;

/**
 * The `AddProductController` class controls the addition of new products to the inventory.
 * It allows users to search for and select associated parts for a product and provides
 * validation for input data during the product creation process.
 */
public class AddProductController implements Initializable {

    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();

    @FXML private TextField addProductSearchField;
    @FXML private TextField addProductId;
    @FXML private TextField addProductName;
    @FXML private TextField addProductInv;
    @FXML private TextField addProductPrice;
    @FXML private TextField addProductMax;
    @FXML private TextField addProductMin;
    @FXML private Button addProductCancelButton;
    @FXML private Button addProductSaveButton;
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

        //add parts to associated table (bottom)
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
    void onAddProductSearch(ActionEvent actionEvent) {
        String searchField = addProductSearchField.getText();
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
    public void onAddProductCancel(ActionEvent actionEvent) throws IOException {
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
     * Event handler for the "Save" button click.
     *
     * @param actionEvent The ActionEvent associated with the event.
     * @throws IOException If there is an error loading the main view.
     */
    @FXML
    void onAddProductSave(ActionEvent actionEvent) throws IOException{
        Random random = new Random();
        int randomNumber = random.nextInt();
        try {
            int id = (Math.abs(randomNumber) % 900) + 100;
            String name = addProductName.getText();
            int stock = Integer.parseInt(addProductInv.getText());
            double price = Double.parseDouble(addProductPrice.getText());
            int max = Integer.parseInt(addProductMax.getText());
            int min = Integer.parseInt(addProductMin.getText());

            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Name Empty");
                alert.setContentText("Name cannot be empty.");
                alert.showAndWait();
            } else {
                if (min > stock || max < stock) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory requirements: Inventory must be within min and max.");
                    alert.showAndWait();
                } else if (min >= max) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory requirements: maximum must be greater than minimum");
                    alert.showAndWait();
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Object scene = FXMLLoader.load(getClass().getResource("/View/add-product-view.fxml"));
                    stage.setScene(new Scene((Parent) scene));
                    stage.show();
                } else {

                    Product product = new Product(id, name, price, stock, min, max);
                    for (Part part : associatedPartsList) {
                        if (part != associatedPartsList)
                            product.addAssociatedPart(part);
                    }

                    Inventory.getAllProducts().add(product);

                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Object scene = FXMLLoader.load(getClass().getResource("/View/main-view.fxml"));
                    stage.setScene(new Scene((Parent) scene));
                    stage.show();
                }
            }
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Please enter a valid value for each text field.");
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
            alert.setTitle("Input Error");
            alert.setContentText("Select part from list");
            alert.showAndWait();
        }
        else if (associatedPartsList.contains(selectedPart))
        {
            associatedPartsList.remove(selectedPart);
            associatedPartTableView.setItems(associatedPartsList);
        }
    }

    /**
     * Event handler for the "Add" button click to associate a part with the product.
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
