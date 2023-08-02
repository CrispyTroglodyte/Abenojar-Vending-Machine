import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a ramen vending machine that manages the stocked ingredients and ramen dishes available for purchase.
 *
 * @author Fredrikzen Abenoajar <fredrikzen_abenojar@dlsu.edu.ph>
 * @version 1.0
 * @since 1.0
 */

public class VendingMachine{

    private List<Ingredient> stockedIngredients;
    private List<Ramen> stockedRamen;
    private double totalMoneyCollected;
    private List<String> orderHistory;

    /**
     * Constructs empty lists for stocked ingredients and ramen dishes.
     * @param VendingMachine the constructor for the this class.
     */

    public VendingMachine(){

        stockedIngredients = new ArrayList<>();
        stockedRamen = new ArrayList<>();
        orderHistory = new ArrayList<>();

    }
    
    /**
     * Returns the list of stocked ingredients in the vending machine.
     *
     * @param getStockedIngredients Gets stocked ingredients
     * @return a list of Ingredient representing the stocked ingredients.
     */

    public List<Ingredient> getStockedIngredients(){

        return stockedIngredients;

    }

    /**
     * Adds an ingredient to the stocked ingredients list and sets the default quantity to 10.
     *
     * @param ingredient the Ingredient to be added to the stocked ingredients.
     * @param addStockedIngredient the method to add the ingredient.
     */

    public void addStockedIngredient( Ingredient ingredient ){

        stockedIngredients.add( ingredient );
        ingredient.setQuantity(10); // Sets the default quantity to 10 for each ingredient.

    }

    /**
     * Returns the list of stocked ramen dishes in the vending machine.
     * 
     * @param getStockedRamen Gets stocked ingredients
     * @return the stocked ramen dishes.
     */

    public List<Ramen> getStockedRamen(){

        return stockedRamen;

    }

    /**
     * Allows the ramen customization.
     *
     * @param ramen the Ramen to be added.
     * @param addStockedRamen the Ramen to be added to allow the customization.
     */

    public void addStockedRamen( Ramen ramen ){

        stockedRamen.add( ramen );

    }

    /**
     * Returns the price of the specified ingredient by its name.
     * If ingredient is not found in the stocked ingredients, it returns 0.
     *
     * @param ingredientName The name of the ingredient whose price is to be retrieved.
     * @param getIngredientPrice Method to get the Ingredient price.
     * @return The price of the specified ingredient, or 0.0 if not found.
     */

    public double getIngredientPrice( String ingredientName ){

        for( Ingredient ingredient : stockedIngredients ){

            if( ingredient.getName().equalsIgnoreCase( ingredientName ) ){

                return ingredient.getPrice();

            }

        }

        return 0.0; // Return 0 if ingredient not found

    }

    /**
     * Gets the total money collected.
     *
     * @param getTotalMoneyCollected The total money collected.
     * @return The total money collected.
     */

    public double getTotalMoneyCollected(){

        return totalMoneyCollected;

    }

    /**
     * Collects the total amount of money.
     *
     * @param amount The culmination of money to be collected.
     * @param collectMoney Method to call and collect the amount.
     */

    public void collectMoney( double amount ){

        totalMoneyCollected += amount;

    }

    /**
     * Resets the total money collected to zero after admin collects the money. 
     *
     * @param resetTotalMoneyCollected The name of the ingredient whose price is to be retrieved.
     * @return 0 after the admin collects the money.
     */

    public void resetTotalMoneyCollected(){

        totalMoneyCollected = 0.0;

    }

    /**
     * Records the order in the order history.
     *
     * @param order The order details to be recorded.
     * @param recordOrderHistory The method to call the order details to be recorded.
     */

     public void recordOrder(String order) {

        orderHistory.add(order);

    }
    

    /**
     * Gets the order history.
     *
     * @param getOrderHistory gets the order history.
     * @return The list of order history.
     */

    public List<String> getOrderHistory(){

        return orderHistory;

    }

}