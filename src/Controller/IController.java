package Controller;
import Model.IModel;
import View.IView;
import java.util.List;

/**
 * Das Interface der Klasse MyController. Hier werden die Bewegungen des Spielers erstellt und Methoden übergeben.
 *
 * @author Nico De Luca
 * @since 1.0
 * @version 1.0
 */

public interface IController {


    void setView(IView view);


    void setModel(IModel model);


    void moveRight();


    void moveLeft();


    float getPlayerWidth();


    int getLeben();


    int getScore();


    boolean isWinning();


    void resetGameLoose();


    void resetGameWin();


    void runGame();


    IModel getModel();


    List<float[]> getItems();


    List<float[]> getBalls();


    List<float[]> getShots();


    boolean ballOutOfBounce();
}
