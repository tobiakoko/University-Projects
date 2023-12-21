package Controller;

import Model.*;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static Model.Inventory.deleteProduct;


/**
 * The MainController class controls the main application user interface.
 */
public class MainController implements Initializable {


    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> partIdColumn;
    @FXML private TableColumn<Part, String> partNameColumn;
    @FXML private TableColumn<Part, Integer> partInventoryColumn;
    @FXML private TableColumn<Part, Double> partPriceColumn;
    @FXML private Button addPartButton;
    @FXML private Button modifyPartButton;
    @FXML private Button deletePartButton;
    @FXML private TextField partSearchField;
    @FXML private TextField productSearchField;
    @FXML private TableView<Product> productTableView;
    @FXML private TableColumn<Product, Integer> productIdColumn;
    @FXML private TableColumn<Product, String> productNameColumn;
    @FXML private TableColumn<Product, Integer> productInventoryColumn;
    @FXML private TableColumn<Product, Double> productPriceColumn;
    @FXML private Button addProductButton;
    @FXML private Button modifyProductButton;
    @FXML private Button deleteProductButton;
    @FXML private Button exitButton;

    /**
     * Event handler for the "Add Part" button click.
     *
     * @param actionEvent The ActionEvent associated with the event.
     * @throws IOException If there is an error loading the add part view.
     */
    @FXML
    void onAddPart(ActionEvent actionEvent) throws IOException {
        loadScene("/View/add-part-view.fxml", actionEvent);
    }

    /**
     * Event handler for the "Modify Part" button click.
     *
     * @param actionEvent The ActionEvent associated with the event.
     * @throws IOException If there is an error loading the modify part view.
     */
    @FXML
    void onModifyPart(ActionEvent actionEvent) throws IOException {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            loadModifyPartScene(selectedPart, actionEvent);
        } else {
            showAlert("Error Message", "Part not found");
        }
    }

    /**
     * Event handler for the "Delete Part" button click.
     *
     * @param actionEvent The ActionEvent associated with the event.
     */
    @FXML
    void onDeletePart(ActionEvent actionEvent) {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            showDeletePartConfirmation(selectedPart);
        } else {
            showAlert("Selection Error", "No part selected");
        }
    }

    /**
     * Event handler for the "Search Part" button click.
     *
     * @param actionEvent The ActionEvent associated with the event.
     */
    @FXML
    void onSearchPart(ActionEvent actionEvent) {
        String searchedPart = partSearchField.getText().toLowerCase();
        ObservableList<Part> foundPart = Inventory.lookupPart(searchedPart);
        if (foundPart.isEmpty()) {
            showAlert("Error Message", "Part not found");
        }
        partTableView.setItems(foundPart);
    }

    /**
     * Event handler for the "Search Product" button click.
     *
     * @param actionEvent The ActionEvent associated with the event.
     */
    @FXML
    void onSearchProduct (ActionEvent actionEvent){
        String searchedProduct = productSearchField.getText().toLowerCase();
        ObservableList<Product> foundProducts = Inventory.lookupProduct(searchedProduct);
        if (foundProducts.isEmpty()) {
            showAlert("Error Message", "Product not found");
        }
        productTableView.setItems(foundProducts);
    }

    /**
     * Event handler for the "Add Product" button click.
     *
     * @param actionEvent The ActionEvent associated with the event.
     * @throws IOException If there is an error loading the add product view.
     */
    @FXML
    void onAddProduct (ActionEvent actionEvent) throws IOException {
        loadScene("/View/add-product-view.fxml", actionEvent);
    }

    /**
     * Event handler for the "Modify Product" button click.
     *
     * @param actionEvent The ActionEvent associated with the event.
     * @throws IOException If there is an error loading the modify product view.
     */
    @FXML
    void onModifyProduct (ActionEvent actionEvent) throws IOException {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            loadModifyProductScene(selectedProduct, actionEvent);
        } else {
            showAlert("Error Message", "Product not found");
        }
    }

    /**
     * Event handler for the "Delete Part" button click.
     *
     * @param actionEvent The ActionEvent associated with the event.
     */
    @FXML
    void onDeleteProduct (ActionEvent actionEvent) {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("Do you want to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Product selectedDeleteProduct = productTableView.getSelectionModel().getSelectedItem();

            if (!selectedDeleteProduct.getAllAssociatedParts().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Product has associated parts");
                alert.setContentText("Product has associated parts");
                alert.showAndWait();
                return;
            }
            Inventory.deleteProduct(selectedProduct);
        }
    }

    /**
     * Event handler for the "Exit" button click.
     *
     * @param actionEvent The ActionEvent associated with the event.
     */
    public void onExit (ActionEvent actionEvent){
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     *Initializes the controller.
     *
     * @param url The URL to the FXML file.
     * @param resourceBundle The ResourceBundle for localization.
     */
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){

        partTableView.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productTableView.setItems(Inventory.getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Load a new scene with the given FXML file path.
     *
     * @param fxmlPath    The FXML file path.
     * @param actionEvent The ActionEvent associated with the event.
     * @throws IOException If there is an error loading the scene.
     */
    private void loadScene(String fxmlPath, ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource(fxmlPath));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Load the modify part scene with the selected part.
     *
     * @param selectedPart The selected part to modify.
     * @param actionEvent  The ActionEvent associated with the event.
     * @throws IOException If there is an error loading the scene.
     */
    private void loadModifyPartScene(Part selectedPart, ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/modify-part-view.fxml"));
        Parent parent = loader.load();
        ModifyPartController modPart = loader.getController();
        int index = partTableView.getSelectionModel().getSelectedIndex();

        if (selectedPart instanceof InHouse) {
            modPart.partChosen(index, selectedPart);
        } else if (selectedPart instanceof Outsourced) {
            modPart.partChosen(index, selectedPart);
        }

        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Show a confirmation dialog for deleting a part.
     *
     * @param selectedPart The selected part to delete.
     */
    private void showDeletePartConfirmation(Part selectedPart) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete?");
        alert.setTitle("Delete Part");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Inventory.deletePart(selectedPart);
        }
    }

    /**
     * Load the modify product scene with the selected product.
     *
     * @param selectedProduct The selected product to modify.
     * @param actionEvent     The ActionEvent associated with the event.
     * @throws IOException If there is an error loading the scene.
     */
    private void loadModifyProductScene(Product selectedProduct, ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/modify-product-view.fxml"));
        Parent parent = loader.load();
        ModifyProductController modProd = loader.getController();
        modProd.productChosen(productTableView.getSelectionModel().getSelectedIndex(), selectedProduct);

        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Display an alert dialog with the given title and content.
     *
     * @param title   The title of the alert.
     * @param content The content text of the alert.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

