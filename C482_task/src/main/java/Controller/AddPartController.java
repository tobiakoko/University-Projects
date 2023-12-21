package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;

/**
 * The `AddPartController` class controls the addition of new parts.
 * It handles user interactions to add either InHouse or Outsourced parts
 * to the inventory and provides validation for input data.
 */
public class AddPartController {


    @FXML private RadioButton inHouseRadioButton;
    @FXML private ToggleGroup radioButtonGroup;
    @FXML private RadioButton outsourcedRadioButton;
    @FXML private TextField addPartId;
    @FXML private TextField addPartName;
    @FXML private TextField addPartInv;
    @FXML private TextField addPartPrice;
    @FXML private TextField addPartMax;
    @FXML private TextField addPartMachineId;
    @FXML private TextField addPartMin;
    @FXML private Label machineIdOrCompanyName;
    @FXML private Button addPartSaveButton;
    @FXML private Button addPartCancelButton;

    /**
     * Event handler for the "inHouse" radio button click.
     *
     * @param actionEvent The ActionEvent associated with the event.
     */
    @FXML
    void onInHouse(ActionEvent actionEvent) {

        machineIdOrCompanyName.setText("Machine ID");
    }

    /**
     * Event handler for the "Outsourced" radio button click.
     *
     * @param actionEvent The ActionEvent associated with the event.
     */
    @FXML
    void onOutsourced(ActionEvent actionEvent) {

        machineIdOrCompanyName.setText("Company Name");
    }

    /**
     * Event handler for the "Cancel" button click.
     *
     * @param actionEvent The ActionEvent associated with the event.
     * @throws IOException If there is an error loading the modify part view.
     */
    @FXML
    public void onAddPartCancel(ActionEvent actionEvent) throws IOException {

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
     * @throws IOException If there is an error loading the modify part view.
     */
    @FXML
    void onAddPartSave(ActionEvent actionEvent) throws IOException {

        Random random = new Random();
        int randomNumber = random.nextInt();

        int id = (Math.abs(randomNumber) % 900) + 100;
        String name = addPartName.getText();
        double price = Double.parseDouble(addPartPrice.getText());
        int stock = Integer.parseInt(addPartInv.getText());
        int min = Integer.parseInt(addPartMin.getText());
        int max = Integer.parseInt(addPartMax.getText());

        try{
            if(min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Max is not greater than Min");
                alert.showAndWait();
            }
            else if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory value must be between Min and Max values");
                alert.showAndWait();
            }
            else {
                if (inHouseRadioButton.isSelected()) {
                    int machineId = Integer.parseInt(addPartMachineId.getText());
                    InHouse addPart = new InHouse(id, name, price, stock, min, max, machineId);
                    Inventory.addPart(addPart);
                } else {
                    String companyName = addPartMachineId.getText();
                    Outsourced addPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.addPart(addPart);
                }
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(getClass().getResource("/View/main-view.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();
            }
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setContentText("Appropriate field value not found");
            alert.showAndWait();
        }
    }
}
