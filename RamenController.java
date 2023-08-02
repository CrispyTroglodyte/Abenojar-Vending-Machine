/**
 * Manages interactions between the RamenView. It follows the Model-View-Controller (MVC) architectural pattern ( Hopefully :> ).
 * 
 * @author      Fredrikzen Abenoajar <fredrikzen_abenojar@dlsu.edu.ph>
 * @version     1.0
 * @since       1.0
 */

public class RamenController{

    private RamenView view;

    /**
     * Constructs a new RamenController with the provided RamenView.
     *
     * @param view The RamenView to associate with this controller.
     * @param RamenController The method to call the RamenController to initialize.
     */

    public RamenController( RamenView view ){

        this.view = view;

    }

    /**
     * Starts the interaction with the RamenView by setting its visibility to true,
     * making the GUI visible and usable to the user.
     * 
     * @param start Starts the program.
     */

    public void start(){

        view.setVisible( true );

    }

}