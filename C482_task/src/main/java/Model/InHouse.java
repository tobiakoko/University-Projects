package Model;

/**
 * The InHouse class represents a part that is produced in-house with a specific machine.
 *  It extends the Part class and adds machine-related functionality.
 */
public class InHouse extends Part{
    private int machineID;

    /**
     * Constructs an InHouse part with the given attributes.
     *
     * @param id       The unique identifier of the part.
     * @param name     The name or description of the part.
     * @param price    The price of the part.
     * @param stock    The current stock or inventory level of the part.
     * @param min      The minimum stock level allowed for the part.
     * @param max      The maximum stock level allowed for the part.
     * @param machineID The identifier of the machine used for production.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID){
        super(id, name, price, stock, min, max);

        this.machineID = machineID;
    }

    /**
     * Sets the machine identifier for this in-house part.
     * @param machineId The identifier of the machine used for production.
     */
    public void setMachineID(int machineId) {
        this.machineID = machineId;
    }

    /**
     * Retrieves the machine identifier for this in-house part.
     *
     * @return The identifier of the machine used for production.
     */
    public int getMachineID(){
        return machineID;
    }
}
