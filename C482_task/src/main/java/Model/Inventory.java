package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model package class inventory.java
 * The Inventory class represents the inventory management system and stores all parts and products.
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a new part to the inventory.
     *
     * @param newPart the part to add
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * Adds a new product to the inventory.
     *
     * @param newProduct the product to add
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * Searches for a part by its ID.
     *
     * @param partId the id to search for
     * @return The found part, or null if not found.
     */
    public static Part lookupPart(int partId){
        for(Part part : allParts){
            if (part.getId() == partId){
                return part;
            }
        }
        return null;
    }

    /**
     * Searches for a product by its ID.
     *
     * @param productId The id to search for
     * @return The found product, or null if not found.
     */
    public static Product lookupProduct(int productId){
        for(Product product : allProducts){
            if (product.getId() == productId){
                return product;
            }
        }
        return null;
    }

    /**
     * Searches for parts by their name.
     *
     * @param partName The name to search for
     * @return A list of parts with matching names.
     */
    public static ObservableList<Part> lookupPart(String partName){

        ObservableList<Part> part_found = FXCollections.observableArrayList();
        for(Part part : allParts){
            if (part.getName().equals(partName)){
                part_found.add(part);
            }
        }
        return part_found;
    }

    /**
     *Searches for products by their name
     *
     * @param productName The name to search for
     * @return A list of products with matching names.
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> product_found = FXCollections.observableArrayList();

        for(Product product : allProducts){
            if (product.getName().equals(productName)){
                product_found.add(product);
            }
        }
        return product_found;
    }

    /**
     *Modifies an existing part in the inventory.
     *
     * @param id The id of the part to modify.
     * @param selectedPart The updated part.
     */
    public static void modifyPart(int id, Part selectedPart){
        int index = -1;
        for (Part part : allParts) {
            index++;
            if (part.getId() == id) {
                getAllParts().set(index, selectedPart);
                break;
            }
        }
    }

    /**
     * Modifies an existing part in the inventory.
     *
     * @param id The id of the product to modify.
     * @param selectedProduct The updated product.
     */
    public static void modifyProduct(int id, Product selectedProduct){
        int index = -1;
        for (Product product : allProducts) {
            index++;
            if (product.getId() == id) {
                allProducts.set(index, selectedProduct);
            }
        }

    }

    /**
     * Deletes a part from the inventory.
     *
     * @param selectedPart The part to delete.
     * @return True if the part is successfully deleted, false otherwise.
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Deletes a part from the inventory.
     *
     * @param selectedProduct The product to delete
     * @return True if the product is successfully deleted, false otherwise.
     */
    public static boolean deleteProduct(Product selectedProduct){
        //if (Product.getAllAssociatedParts().isEmpty()){
            return allProducts.remove(selectedProduct);
       // return false;
    }

    /**
     * Retrieves all parts in the inventory.
     *
     * @return An observable list containing all parts.
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     *Retrieves all products in the inventory.
     *
     * @return An observable list containing all products.
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}
