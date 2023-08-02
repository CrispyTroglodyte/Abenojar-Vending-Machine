/**
 * This class sets the ingredients and their respective information and implements it in the RamenApp class.
 * 
 * @author      Fredrikzen Abenoajar <fredrikzen_abenojar@dlsu.edu.ph>
 * @version     1.0
 * @since       1.0
 */

public class Ingredient{

    private String name;      // Adds a naming field
    private int calorieCount; // Adds a calorie count field
    private double price;     // Adds a price field
    private String imagePath; // Adds an image field
    private int quantity;     // Adds a quantity field

    /**
     * The constructor class of the Ingredient. It constructs the names, calories, price, images, and quantity of their respective ingredient.
     *
     * @param name The name of the ingredient.
     * @param calorieCount The calorie count of the ingredient.
     * @param price The price of the ingredient.
     * @param imagePath The imagePath of the ingredient.
     * @param Ingredient The name of the constructor.
     */

    public Ingredient( String name, int calorieCount, double price, String imagePath ){

        this.name = name;
        this.calorieCount = calorieCount;
        this.price = price;
        this.imagePath = imagePath;
        this.quantity = 10; // Sets the default quantity to 10.

    }

    /**
     * Sets the quantity of the ingredient, makes sure that the value is between 0 and 10.
     *
     * @param getName The new quantity of the ingredient.
     * @param setQuantity The method to set the quantity of the ingredients.
     * @return the name of the ingredient
     */

    public String getName(){

        return name;

    }

    /**
     * Gets the calorie count of the ingredient.
     *
     * @param getCalorieCount The method to get the calorie count of the ingredients.
     * @return the calorie count.
     */

    public int getCalorieCount(){

        return calorieCount;

    }

    /**
     * Gets the price of the ingredient.
     *
     * @param getPrice The method to get the price of the ingredients.
     * @return the price
     */

    public double getPrice(){

        return price;

    }

    /**
     * Sets the price of the ingredient.
     *
     * @param price The price of the ingredient.
     * @param setPrice The method to set the price of the ingredients.
     */

    public void setPrice( double price ){

        this.price = price;

    }

    /**
     * Gets the image path of the ingredient. It is so that I can use images in the Vending Machine to their corresponding Ingredients.
     *
     * @param getImagePath The method to get the image path of the ingredients.
     * @return the image path
     */

    public String getImagePath(){

        return imagePath;

    }

    /**
     * Sets the image path of the ingredient.
     *
     * @param imagePath the image path of the image. It is set in the Ramen App class and is used in the RamenView class.
     * @param setImagePath The method to set the image path of the ingredients.
     */

    public void setImagePath( String imagePath ){

        this.imagePath = imagePath;

    }

    /**
     * Gets the quantity of the ingredient. Which is set to 10.
     *
     * @param getQuantity The method to get the quantity of the ingredients.
     * @return the quanitity
     */

    public int getQuantity(){

        return quantity;

    }
    
    /**
     * Sets the quantity of the ingredient, makes sure that the value is between 0 and 10.
     *
     * @param quantity The new quantity of the ingredient.
     * @param setQuantity The method to set the quantity of the ingredients.
     */

    public void setQuantity( int quantity ){

        this.quantity = ( quantity > 10 ) ? 10 : Math.max( 0, quantity );

    }

}