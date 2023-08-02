import java.util.HashMap;
import java.util.Map;

/**
 * This is the class that represents a Ramen Dish.
 * 
 * @author      Fredrikzen Abenoajar <fredrikzen_abenojar@dlsu.edu.ph>
 * @version     1.0
 * @since       1.0
 */

public class Ramen{

    private Map< String, Integer > ingredients;
    private int calorieCount;
    private boolean customizable;

    /**
     * Is the constructor of the Ramen class.
     *
     * @param Ramen The constructor method.
     */

    public Ramen(){

        ingredients = new HashMap<>();
        calorieCount = 0;
        customizable = false;

    }

    /**
     * Gets the map of ingredients and their quantities in the ramen dish.
     *
     * @return A map containing ingredient names as keys and their quantities as values.
     */

    public Map< String, Integer > getIngredients(){

        return ingredients;

    }

    /**
     * Gets the total calorie count of the dish.
     *
     * @return The total calorie count of the dish.
     */

    public int getCalorieCount(){

        return calorieCount;

    }

    /**
     * It is a boolean which sets whether the ramen dish is customizable or not.
     *
     * @param customizable If true, then the dish is customizable. Else, it is not.
     */

    public void setCustomizable( boolean customizable ){

        this.customizable = customizable;

    }

    /**
     * Checks if the ramen dish is customizable.
     *
     * @return True if the dish is customizable. If not, then False.
     */

    public boolean isCustomizable(){

        return customizable;

    }

    /**
     * Gets a string representation of the ingredients in the ramen dish.
     *
     * @return A string containing the names and quantities of ingredients in the dish.
     */

    public String getIngredientsAsString() {
        StringBuilder builder = new StringBuilder();
    
        for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
            builder.append(entry.getKey()).append(" (").append(entry.getValue()).append(" orders), ");
        }
    
        if (builder.length() > 2) {
            builder.setLength(builder.length() - 2); // Removes the trailing comma and space
        }
    
        return builder.toString();
    }
    

}
