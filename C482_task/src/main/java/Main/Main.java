package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Model.*;


import java.io.IOException;

/**
 * Main class for the Inventory Management System.
 *
 */
public class Main extends Application {

    /**
     * The entry point for the JavaFX application.
     *
     * @param stage The primary stage for the application.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Load the main FXML view
        Parent root = FXMLLoader.load(getClass().getResource("/View/main-view.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root, 1200, 564));
        stage.show();
    }

    /**
     * Main method to run the Inventory Management System.
     *
     * <p><b>
     * FUTURE ENHANCEMENT: Add additional features such as editing, undo deleting, and reporting the individual
     *                     Parts and Products
     * </b></p>
     * <p><b>
     * RUNTIME ERROR: Occurs in the onAddPartSave function in the ModifyPartController class. If the user enters invalid input
     *            (e.g., non-numeric characters in numeric fields),
     *            the code throws a NumberFormatException, but it does not provide a user-friendly error message.
     *            Enhancements could be made to handle and display validation errors more gracefully.
     * </b></p>
     * @author Daniel Akoko
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        // Initialize the inventory with some sample parts and products
        Inventory.addPart(new InHouse(101, "CPU Processor", 120.00, 50, 10, 100, 1234));

        Inventory.addPart(new InHouse(102, "RAM Memory", 40.00, 100, 50, 200, 5679));

        Inventory.addPart(new Outsourced(103, "Motherboard", 299.99, 75, 25, 150, "ASUS"));

        Inventory.addPart( new Outsourced(104, "Graphics Card", 79.99, 150, 10, 500, "NVIDIA"));

        Inventory.addProduct(new Product(202, "Laptop", 800.00, 5, 1, 30));

        Inventory.addProduct( new Product(203, "Gaming PC", 1500.00, 10, 5, 20));

        // Launch the JavaFX application
        launch(args);
    }
}