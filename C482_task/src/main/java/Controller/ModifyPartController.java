package Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
/**
 * The `ModifyPartController` class controls the modification of part details.
 * It handles user interactions and updates the part information accordingly.
 */
public class ModifyPartController {
    @FXML private RadioButton modifyInHouse;
    @FXML private ToggleGroup modifyRadioGroup;
    @FXML private RadioButton modifyOutsourced;
    @FXML private TextField modifyPartId;
    @FXML private TextField modifyPartName;
    @FXML private TextField modifyPartInv;
    @FXML private TextField modifyPartPrice;
    @FXML private TextField modifyPartMax;
    @FXML private TextField modifyPartMachineId;
    @FXML private TextField modifyPartMin;
    @FXML private Label machineIdOrCompanyName;

    private Part selectedPart; // Add a field to store the selected part
    private int selectedIndex; // Add a field to store the selected part's index


    /**
     * Event handler for the "InHouse" radio button click.
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field " +
                "values, do you want to continue?");
        Optional<ButtonType> button = alert.showAndWait();
        if(button.isPresent() && button.get() == ButtonType.OK) {
            // Redirect to the main view
            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/View/main-view.fxml"));
            stage.setScene(new Scene((Parent) scene));
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
        try{
            int id = Integer.parseInt(modifyPartId.getText());
            String name = modifyPartName.getText();
            double price = Double.parseDouble(modifyPartPrice.getText());
            int stock = Integer.parseInt(modifyPartInv.getText());
            int min = Integer.parseInt(modifyPartMin.getText());
            int max = Integer.parseInt(modifyPartMax.getText());

            if(min >= max) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Min value cannot be greater than Max value.");
                alert.showAndWait();
            }
            else if (stock > max || stock < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory amount must be between minimum and maximum values.");
                alert.showAndWait();
            }
            else {
                if (modifyInHouse.isSelected()) {
                    int machineId = Integer.parseInt(modifyPartMachineId.getText());
                    InHouse modifiedPart = new InHouse(id, name, price, stock, min, max, machineId);
                    Inventory.modifyPart(id, modifiedPart);
                } else {
                    String companyName = modifyPartMachineId.getText();
                    Outsourced modifiedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.modifyPart(id, modifiedPart);
                }
                // Redirect to the main view
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(getClass().getResource("/View/main-view.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show();
            }
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error Adding Part");
            alert.setContentText("All fields are required.");
            alert.showAndWait();
        }
    }

/**
 * Populates the controller with data for a selected part.
 *
 * @param part The selected part to modify.
 * @param index The index of the selected part.
 */
    public void partChosen(int index, Part part) {
        selectedPart = part;
        selectedIndex = index;
        modifyPartId.setText(Integer.toString(part.getId()));
        if (part instanceof InHouse inHousePart) {
            modifyInHouse.setSelected(true);
            machineIdOrCompanyName.setText("Machine ID");
            modifyPartMachineId.setText(Integer.toString(inHousePart.getMachineID()));
        } else if (part instanceof Outsourced outsourcedPart) {
            modifyOutsourced.setSelected(true);
            machineIdOrCompanyName.setText("Company Name");
            modifyPartMachineId.setText(outsourcedPart.getCompanyName());
        }
        modifyPartName.setText(part.getName());
        modifyPartInv.setText(Integer.toString(part.getStock()));
        modifyPartPrice.setText(Double.toString(part.getPrice()));
        modifyPartMax.setText(Integer.toString(part.getMax()));
        modifyPartMin.setText(Integer.toString(part.getMin()));
        }
}

