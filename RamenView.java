import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * This class is the GUI of the whole program. This is where everything in the vending machine is displayed and interactable.
 * 
 * @author      Fredrikzen Abenoajar <fredrikzen_abenojar@dlsu.edu.ph>
 * @version     1.0
 * @since       1.0
 */

public class RamenView extends JFrame{
    
    private JComboBox<String> ramenOptionsComboBox;
    private JButton prepareButton;
    private JButton adminButton;
    private Map< String, Integer > stockedIngredients;
    private VendingMachine vendingMachine;
    private Map< JCheckBox, Ingredient > ingredientCheckBoxes;

    /**
     * Constructor for the RamenView class. This is also initializes the GUI.
     * 
     * @param vendingMachine Starts the program.
     * @param RamenView constructor.
     */

    public RamenView( VendingMachine vendingMachine ){

        this.vendingMachine = vendingMachine;
        stockedIngredients = new HashMap<>();
        ingredientCheckBoxes = new HashMap<>();

        initializeGUI();
        RamenOptions( vendingMachine.getStockedRamen() );

    }

    /**
     * Initialization of the GUI. Welcomes the user or the admin to the Vending Machine. Gives
     * an option to Buy Ramen or to Maintenance.
     */

    public void initializeGUI(){

        setTitle("Breading Machines - Ramen Vending Machine");
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setLayout( new BorderLayout() );

        int topPadding;

        JLabel welcomeLabel = new JLabel("Welcome to Breading Machines!");
        welcomeLabel.setHorizontalAlignment( JLabel.CENTER );
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 15)); // Sets font style and size
        welcomeLabel.setForeground( Color.BLACK ); // Sets font color
        add( welcomeLabel, BorderLayout.NORTH );

        topPadding = 20; // You can adjust this value to set the desired space
        welcomeLabel.setBorder( BorderFactory.createEmptyBorder( topPadding, 0, 0, 0 ) );

        add( welcomeLabel, BorderLayout.NORTH );

        ramenOptionsComboBox = new JComboBox<>();
        prepareButton = new JButton("Buy Ramen");
        adminButton = new JButton("Maintenance");

        prepareButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed( ActionEvent e ){

                showCustomizeRamenWindow();

            }

        } );

        adminButton.addActionListener( new ActionListener(){

            @Override
            public void actionPerformed( ActionEvent e ){

                showMaintenanceWindow();

            }

        } );

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(prepareButton);
        buttonPanel.add(adminButton);

        add( buttonPanel, BorderLayout.SOUTH );

        int preferredWidth, preferredHeight; // Set the preferred width of the window
        preferredWidth = 350;
        preferredHeight = 200;
        setSize( preferredWidth, preferredHeight );

        setLocationRelativeTo( null );
        
    }

    /**
     * Gets the selected ramen.
     * 
     * @param getSelectedRamen Gets the selected ramen.
     * @return null
     */

    public Ramen getSelectedRamen(){

        int selectedOptionIndex;

        selectedOptionIndex = ramenOptionsComboBox.getSelectedIndex();

        if( selectedOptionIndex != -1 ){

            return vendingMachine.getStockedRamen().get( selectedOptionIndex );

        }

        return null;

    }

    /**
     * Gets the selected ramen. (!)
     * 
     * @param getSelectedRamen Gets the selected ramen.
     * @return selected ingredients
     */

    private Map< String, Integer > getSelectedIngredients(){

        Map< String, Integer > selectedIngredients = new HashMap<>();

        for ( Map.Entry< JCheckBox, Ingredient > entry : ingredientCheckBoxes.entrySet() ){

            JCheckBox checkBox = entry.getKey();
            Ingredient ingredient = entry.getValue();

            if ( checkBox.isSelected() ){

                int availableQuantity, quantity;

                availableQuantity = stockedIngredients.get( ingredient.getName() );
                quantity = getQuantityForIngredient( ingredient.getName(), availableQuantity );

                if( quantity > 0 ){

                    // Ensure that the selected quantity does not exceed the available quantity
                    quantity = Math.min( quantity, availableQuantity );
                    selectedIngredients.put( ingredient.getName(), quantity );

                }

            }

        }

        return selectedIngredients;

    }
    
    /**
     * Gets the selected ramen. (!)
     * 
     * @param getQuantityForIngredient Gets the quantity of the ingredients.
     * @return the quantity.
     */
    
    public int getQuantityForIngredient( String ingredientName, int availableQuantity ){

        while( true ){

            String quantityStr = JOptionPane.showInputDialog( this, "Enter quantity for " + ingredientName + ":" );

            if( quantityStr != null ){ // Check if the user didn't cancel input.

                try{

                    int quantity;

                    quantity = Integer.parseInt( quantityStr );

                    if( quantity >= 0 && quantity <= availableQuantity ){

                        return quantity;

                    }

                    else{

                        JOptionPane.showMessageDialog( this, "Invalid quantity. Please enter a value between 0 and " + availableQuantity + ".", "Error", JOptionPane.ERROR_MESSAGE );

                    }

                }catch( NumberFormatException e ){

                    JOptionPane.showMessageDialog( this, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE );

                }

            }
            
            else{

                // If the user cancels the input, return 0 (indicating no quantity is chosen)
                return 0;

            }

        }

    }

    /**
     * Options of the Ramen (!)
     * 
     * @param RamenOptions Ramen Options
     */

    public void RamenOptions( java.util.List<Ramen> ramenOptions ){

        for( Ramen ramen : ramenOptions ){

            if( ramen.isCustomizable() ){

                String optionText = "Custom Ramen with: " + ramen.getIngredientsAsString() + " - Total Calories: " + ramen.getCalorieCount();
                ramenOptionsComboBox.addItem(optionText);

            }

        }

    }

    /**
     * It is a GUI that shows the customization of the ramen. It is activated when user presses Buy Ramen.
     * 
     * @param showCustomizeRamenWindow shows all the ingredients with their respective information. Here you can customize your Ramen order.
     * @return to previous windows, and a boolean.
     * 
     */

    public void showCustomizeRamenWindow(){

        JFrame customizeRamenFrame = new JFrame("Customize Ramen");
        customizeRamenFrame.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
        customizeRamenFrame.setLayout( new BorderLayout() );

        // Clears the previous ingredient checkboxes and stocked ingredients.
        ingredientCheckBoxes.clear();
        stockedIngredients.clear();

        Map<String, Double> ingredientPrices = new HashMap<>();

        for( Ingredient ingredient : vendingMachine.getStockedIngredients() ){

            ingredientPrices.put( ingredient.getName(), ingredient.getPrice() );

        }

        Ramen selectedRamen = getSelectedRamen();

        if( selectedRamen != null ){

        JPanel ingredientPanel = new JPanel( new GridLayout(0, 1) );
        ingredientPanel.setBorder( BorderFactory.createTitledBorder("Please Choose What You Desire!") );
        addIngredientsToPanel( vendingMachine.getStockedIngredients(), ingredientPrices, ingredientPanel );

        JButton prepareRamenButton = new JButton("Prepare Order");
        prepareRamenButton.addActionListener( new ActionListener(){

            @Override
            public void actionPerformed( ActionEvent e ){

                Map< String, Integer > selectedIngredients = getSelectedIngredients();
                int totalCalories = 0;
                double totalCost = 0.0;

                StringBuilder preparationSteps = new StringBuilder();
                preparationSteps.append("Preparing your Order...\n");

                // if it contains the key, for example, egg and chasu pork, it will display the corresponding texts.
                if( selectedIngredients.containsKey("Noodles") ){

                    preparationSteps.append("Blanching noodles...\n");

                }

                if( selectedIngredients.containsKey("Egg") ){

                    preparationSteps.append("Cooking Egg...\n");

                }

                if( selectedIngredients.containsKey("Chashu Pork") ){

                    preparationSteps.append("Slicing Chashu Pork...\n");

                }

                if( selectedIngredients.containsKey("Fried Tofu") ){

                    preparationSteps.append("Frying Fried Tofu...\n");

                }

                if( selectedIngredients.containsKey("Negi") ){

                    preparationSteps.append("Chopping Negi (green onions)...\n");

                }

                if( selectedIngredients.containsKey("Tonkotsu Broth") ){

                    preparationSteps.append("Heating Tonkotsu broth...\n");

                }

                if( selectedIngredients.containsKey("Ukokkei Broth") ){

                    preparationSteps.append("Heating Ukokkei broth...\n");

                }

                if( selectedIngredients.containsKey("Miso Broth") ){

                    preparationSteps.append("Heating Miso broth...\n");

                }

                if( selectedIngredients.containsKey("Shio Broth") ){

                    preparationSteps.append("Heating Shio broth...\n");

                }

                for( Map.Entry< String, Integer > entry : selectedIngredients.entrySet() ){

                    String ingredient = entry.getKey();
                    int quantity = entry.getValue();
                    int calorieCount = stockedIngredients.get( ingredient );
                    double price = vendingMachine.getIngredientPrice( ingredient );

                    totalCalories += ( calorieCount * quantity );
                    totalCost += ( price * quantity );
                    
                    // preparation texts
                    preparationSteps.append("Adding ")
                            .append( quantity ).append(" units of ")
                            .append( ingredient ).append(" (")
                            .append( quantity * calorieCount ).append(" calories, \u20B1")
                            .append( quantity * price ).append(")\n");

                }

                boolean validInput = false;
                double cashProvided = 0.0;

                    while( !validInput ){

                        // Ask the user for cash input
                        String cashInputStr = JOptionPane.showInputDialog(RamenView.this, "Enter Amount of Cash:");

                        if( cashInputStr != null ) { // Check if the user didn't cancel the input

                            try{

                                cashProvided = Double.parseDouble( cashInputStr );

                                if( cashProvided >= totalCost ){

                                    validInput = true;

                                }
                                
                                else{

                                    double remainingBalance = totalCost - cashProvided;
                                    String remainingBalanceMessage = String.format("Remaining Balance Needed: \u20B1%.2f", remainingBalance);
                                    int option = JOptionPane.showOptionDialog(
                                        RamenView.this,
                                        "Insufficient cash. Dispensing Cash Back...\n" + remainingBalanceMessage,
                                        "Insufficient Cash",
                                        JOptionPane.DEFAULT_OPTION,
                                        JOptionPane.WARNING_MESSAGE,
                                        null,
                                        new Object[] {"Back", "Retry"},
                                        "Retry"
                                    );

                                    if( option == 0 ){

                                        // User pressed "Back" button
                                        return;

                                    }

                                }

                            }catch( NumberFormatException ex ){

                                JOptionPane.showMessageDialog(RamenView.this, "Invalid cash input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);

                            }

                        }
                        
                        else{

                            // User pressed "Cancel" button
                            return;

                        }

                    }

                // Calculates the change
                double change = cashProvided - totalCost;
                String changeMessage = String.format("Change: \u20B1%.2f", change);
                JOptionPane.showMessageDialog( RamenView.this, "Order placed successfully!\n" + changeMessage, "Order Placed", JOptionPane.INFORMATION_MESSAGE );

                vendingMachine.collectMoney(totalCost);

                // Displays total calorie count and total cost
                preparationSteps.append("\nTotal Calories: ").append( totalCalories ).append(" calories\n");
                preparationSteps.append("Total Cost: \u20B1").append( totalCost ).append("\n");

                // Checks if the selected ingredients are available for purchase ( quantity > 0 )
                boolean ingredientsAvailable = selectedIngredients.entrySet().stream().allMatch(entry -> {

                    String ingredientName = entry.getKey();
                    int quantityUsed = entry.getValue();
                    Ingredient ingredient = vendingMachine.getStockedIngredients().stream()

                        .filter( ing -> ing.getName().equals( ingredientName ) )
                        .findFirst()
                        .orElse( null );

                    return ingredient != null && ingredient.getQuantity() >= quantityUsed;

                } );
        
                if( ingredientsAvailable ){

                    String preparationText = preparationSteps.toString();
                    displayRamenPreparation( preparationText );

                    for( Map.Entry<String, Integer> entry : selectedIngredients.entrySet() ){
                        
                        String ingredientName;
                        int quantityUsed;

                        ingredientName = entry.getKey();
                        quantityUsed = entry.getValue();

                        Ingredient ingredient = vendingMachine.getStockedIngredients().stream()

                            .filter(ing -> ing.getName().equals(ingredientName))
                            .findFirst()
                            .orElse(null);

                        if( ingredient != null ){

                            int currentQuantity, newQuantity;

                            currentQuantity = ingredient.getQuantity();
                            newQuantity = Math.max( 0, currentQuantity - quantityUsed );
                            ingredient.setQuantity( newQuantity );

                        }

                    }
        
                    // Display a message to the user indicating that the order was prepared.
                    displayRamenPreparation("Your order and change has been dispensed!");

                    String orderDetails = "\nOrder: " + selectedRamen.getIngredientsAsString() + " Total Calories: " + totalCalories + " calories\nTotal Cost: \u20B1" + totalCost;
                    vendingMachine.recordOrder(orderDetails);

                    customizeRamenFrame.dispose();

                }

                else{

                    // Display an error message to the user indicating that the selected ingredients are not available.
                    JOptionPane.showMessageDialog( RamenView.this, "Some of the Ingredients you chose are not available. Please Choose another Ingredient!\n Dispensing Cash Inserted...", "Error", JOptionPane.ERROR_MESSAGE );

                }

            }

        } );

            JButton backButton = new JButton("Back");
            backButton.addActionListener( new ActionListener() {

                @Override
                public void actionPerformed( ActionEvent e ){

                    customizeRamenFrame.dispose(); // Close the customization window when back button is clicked

                }

            });

            JPanel mainPanel = new JPanel( new BorderLayout() );
            mainPanel.add( ingredientPanel, BorderLayout.CENTER );

            // Creates a panel for the buttons and add them in a horizontal layout
            JPanel buttonPanel = new JPanel( new FlowLayout() );
            buttonPanel.add( prepareRamenButton );
            buttonPanel.add( backButton );

            // Adds the button panel to the main panel
            mainPanel.add( buttonPanel, BorderLayout.SOUTH );

            customizeRamenFrame.add( mainPanel );
            customizeRamenFrame.pack();

            // Set the width and height of the window
            int preferredWidth = 1100; // Adjust this value to set the width
            int preferredHeight = 700; // Adjust this value to set the height

            customizeRamenFrame.setSize( preferredWidth, preferredHeight );

            customizeRamenFrame.setLocationRelativeTo( null );
            customizeRamenFrame.setVisible( true );

        }

    }

    /**
     * Restocks ingredients. It is called in the restockIngredients function.
     * 
     * @param ingredientName ingredient to restock.
     * @param quantityToAdd quantity/s to add to restock.
     * @param restockIngredient Method to restocks ingredients
     * 
     */

    private void restockIngredient( String ingredientName, int quantityToAdd ){

        Ingredient ingredient = vendingMachine.getStockedIngredients().stream()

            .filter( ing -> ing.getName().equals( ingredientName ) )
            .findFirst()
            .orElse(null);
    
        if( ingredient != null ){

            int currentQuantity, newQuantity;

            currentQuantity = ingredient.getQuantity();
            newQuantity = Math.min( 10, currentQuantity + quantityToAdd );
            ingredient.setQuantity( newQuantity );
            JOptionPane.showMessageDialog( this, ingredientName + " has been restocked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE );

        }

        else{

            JOptionPane.showMessageDialog(this, "Ingredient not found.", "Error", JOptionPane.ERROR_MESSAGE);

        }

    }
    
    /**
     * It is a GUI that shows the Maintenance Features. It is activated when user presses Maintenance.
     * 
     * @param showMaintenanceWindow shows the maintenance features.
     * 
     */

    private void showMaintenanceWindow(){

        String[] options = { "Restock Ingredients", "Collect Money", "Show Order History", "Cancel" };

        int option;

        option = JOptionPane.showOptionDialog(

            this,
            "Choose An Option:",
            "Maintenance Features",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]

        );
    
        if( option == 0 ){

            // Restocks Ingredients
            restockIngredients();

        }
        
        else if( option == 1 ){

            // Collects Money
            collectMoneyWindow();

        }
        
        else if( option == 2 ){

            // Collects Money
            showOrderHistory();

        }

        else{

            // Cancel - Does nothing, the user cancelled the dialog.
        }

    }

    /**
     * It is a GUI that is in the Maintenance Features. It is activated when user presses Maintenance then Restock Ingredients.
     * 
     * @param restockIngredients restocks the ingredients.
     * 
     */

    private void restockIngredients(){

        String[] ingredientNames = vendingMachine.getStockedIngredients().stream()
                .map(Ingredient::getName)
                .toArray(String[]::new);
    
        if( ingredientNames.length == 0 ){

            JOptionPane.showMessageDialog( this, "No ingredients available for restocking.", "Information", JOptionPane.INFORMATION_MESSAGE );
            return;

        }
    
        JComboBox<String> ingredientComboBox = new JComboBox<>( ingredientNames );
        JTextField quantityField = new JTextField(5);
    
        JPanel inputPanel = new JPanel();

        inputPanel.setLayout( new GridLayout(2, 2) );
        inputPanel.add( new JLabel("Select Ingredient: ") );
        inputPanel.add( ingredientComboBox );
        inputPanel.add( new JLabel("Enter Quantity (0 to 10): ") );
        inputPanel.add( quantityField );
        
        int result;

        result = JOptionPane.showConfirmDialog( this, inputPanel, "Restock Ingredients", JOptionPane.OK_CANCEL_OPTION );

        if( result == JOptionPane.OK_OPTION ){

            String selectedIngredientName = ( String ) ingredientComboBox.getSelectedItem();

            try{

                int quantityToAdd;

                quantityToAdd = Integer.parseInt( quantityField.getText() );

                if( quantityToAdd >= 0 && quantityToAdd <= 10 ){

                    restockIngredient( selectedIngredientName, quantityToAdd ); // calls the function to restock ingredients.

                }
                
                else{

                    JOptionPane.showMessageDialog( this, "Invalid quantity. Please enter a value between 0 and 10.", "Error", JOptionPane.ERROR_MESSAGE );
                    // If the quantity is invalid, loop back to the restock dialog
                    restockIngredients();

                }

            }catch( NumberFormatException ex ){

                JOptionPane.showMessageDialog( this, "Invalid input. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE );
                // If the input is invalid, loop back to the restock dialog
                restockIngredients();

            }

        }

    }

    /**
     * It is a GUI that is in the Maintenance Features. It is activated when user presses Maintenance then Collect Money.
     * 
     * @param collectMoneyWindow Collects the money the vending machine has culminated.
     * 
     */

    private void collectMoneyWindow(){

        double moneyToCollect = vendingMachine.getTotalMoneyCollected();
        String message = String.format("Total money collected: \u20B1%.2f", moneyToCollect);
        JOptionPane.showMessageDialog( this, message, "Money Collection", JOptionPane.INFORMATION_MESSAGE );
        vendingMachine.resetTotalMoneyCollected(); // Reset the total money collected after displaying

    }

    /**
     * It is a GUI that is in the Maintenance Features. It is activated when user presses Maintenance then Show Order History.
     * 
     * @param showOrderHistory Collects the money the vending machine has culminated.
     * 
     */
    
    private void showOrderHistory(){

        StringBuilder history = new StringBuilder();
        history.append("Order History:\n");
    
        List<String> orderHistory = vendingMachine.getOrderHistory();

        if( orderHistory.isEmpty() ){

            history.append("No orders yet.");

        }

        else{

            for( String order : orderHistory ){

                history.append(order).append("\n");

            }

        }
    
        JOptionPane.showMessageDialog(this, history.toString(), "Order History", JOptionPane.INFORMATION_MESSAGE);

    }

    /**
     * Displays the ramen preparation window.
     * 
     * @param displayRamenPreparation displays the ramen preparation.
     * 
     */

    public void displayRamenPreparation( String preparationSteps ){

        String message = preparationSteps + "\nPlease Enjoy Your Order!";
        JOptionPane.showMessageDialog( this, message, "Preparing Order!", JOptionPane.INFORMATION_MESSAGE );

    }
    
    /**
     * This is where the images get added into the GUI. It also shows the price of the ingredient and the quanitity left. 
     * 
     * @param addIngredientsToPanel displays the ramen preparation.
     * 
     */

    public void addIngredientsToPanel( List<Ingredient> ingredients, Map< String, Double > ingredientPrices, JPanel panel ){

        int rows = 2;
        int columns = 4;
        panel.setLayout( new GridLayout( rows, columns, 0, 0 ) );
    
        for( Ingredient ingredient : ingredients ){

            JCheckBox checkBox = new JCheckBox( ingredient.getName() );
            JLabel priceLabel = new JLabel("\u20B1" + ingredientPrices.get(ingredient.getName()));
            JLabel quantityLabel = new JLabel("Quantity: " + ingredient.getQuantity());
    
            // Load the image for the ingredient
            ImageIcon imageIcon = new ImageIcon( ingredient.getImagePath() );

            Image image = imageIcon.getImage();
            Image scaledImage = image.getScaledInstance( 200, 200, Image.SCALE_SMOOTH );
            ImageIcon scaledImageIcon = new ImageIcon( scaledImage );
            JLabel imageLabel = new JLabel( scaledImageIcon );
            imageLabel.setHorizontalAlignment( JLabel.CENTER ); // Centers the image horizontally

            JPanel labelPanel = new JPanel( new GridLayout(2, 1) );
            labelPanel.add( priceLabel );
            labelPanel.add( quantityLabel );

            JPanel ingredientEntryPanel = new JPanel( new BorderLayout() );
            ingredientEntryPanel.add( imageLabel, BorderLayout.CENTER );
            ingredientEntryPanel.add( labelPanel, BorderLayout.SOUTH ); // Add the price and quantity labels
            ingredientEntryPanel.add( checkBox, BorderLayout.NORTH ); // Move the checkbox to the top
    
            ingredientCheckBoxes.put( checkBox, ingredient );
            int calorieCount = ingredient.getCalorieCount();
            stockedIngredients.put( ingredient.getName(), calorieCount );

            panel.add( ingredientEntryPanel );

        }

    }

}