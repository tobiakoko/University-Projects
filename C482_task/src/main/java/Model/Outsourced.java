package Model;


/**
 * Model package class outsourced.java
 * The Outsourced class represents a part that is outsourced to a company.
 * It extends the Part class and includes additional information about the company name.
 */
public class Outsourced extends Part{
    String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);

        this.companyName = companyName;
    }

    /**
     * Sets the name of the company that supplies the part.
     *
     * @param companyName The company name to set.
     */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    /**
     * Retrieves the name of the company that supplies the part.
     *
     * @return The company name.
     */
    public String getCompanyName(){
        return companyName;
    }
}
