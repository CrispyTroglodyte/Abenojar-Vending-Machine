import javax.swing.*;

/**
 * This class is the Main File where everything happens. When you compile and run RamenApp, it initializes a vending machine so that you can order
 * a customized ramen menu!
 * 
 * @author      Fredrikzen Abenoajar <fredrikzen_abenojar@dlsu.edu.ph>
 * @version     1.0
 * @since       1.0
 */

public class RamenApp{

    /**
     * Sets up and initializes the Ramen Vending Machine applications. 
     * Schedules the GUI initialization on the Swing event dispatch thread, and starting the Ramen Controller to handle user interactions.
     * 
     * @param args Signature for the main function.
     * @param main where everything happens.
     */

    public static void main( String[] args ){

        VendingMachine vendingMachine = initializeVendingMachine();

        SwingUtilities.invokeLater( () ->{

            RamenView view = new RamenView( vendingMachine );
            RamenController controller = new RamenController( view );

            controller.start();

        } );

    }

    /**
     * This initializes the Vending Machine.
     * 
     * @param initializeVendingMachine is the function to be called to initialize the Vending Machine. It is the one to create a 
     *                                 Vending Machine Object. It adds the ingredients and its respective information.
     * 
     * @return vendingMachine
     */

    public static VendingMachine initializeVendingMachine(){

        VendingMachine vendingMachine = new VendingMachine();
    
        // Adds ingredients
        Ingredient noodles = new Ingredient("Noodles", 300, 20, "ramen.jpg");
        Ingredient egg = new Ingredient("Egg", 100, 30, "egg.jpg");
        Ingredient chashuPork = new Ingredient("Chashu Pork", 400, 100, "chasupork.jpg");
        Ingredient friedTofu = new Ingredient("Fried Tofu", 150, 20, "friedTofu.jpg");
        Ingredient negi = new Ingredient("Negi", 50, 10, "negi.jpg");
        Ingredient tonkotsuBroth = new Ingredient("Tonkotsu Broth", 200, 60, "tonkotsuBroth.jpg");
        Ingredient ukokkeiBroth = new Ingredient("Ukokkei Broth", 180, 60, "ukokkeiBroth.jpg");
        Ingredient misoBroth = new Ingredient("Miso Broth", 250, 60, "misoBroth.jpg");
        Ingredient shioBroth = new Ingredient("Shio Broth", 220, 60, "shioBroth.jpg");
    
        vendingMachine.addStockedIngredient( noodles );
        vendingMachine.addStockedIngredient( egg );
        vendingMachine.addStockedIngredient( chashuPork );
        vendingMachine.addStockedIngredient( friedTofu );
        vendingMachine.addStockedIngredient( negi );
        vendingMachine.addStockedIngredient( tonkotsuBroth );
        vendingMachine.addStockedIngredient( ukokkeiBroth );
        vendingMachine.addStockedIngredient( misoBroth );
        vendingMachine.addStockedIngredient( shioBroth );
    
        // Allows custom ramen orders
        Ramen Ramen = new Ramen();
        Ramen.setCustomizable( true );
        vendingMachine.addStockedRamen( Ramen );
    
        return vendingMachine;

    }
    
}