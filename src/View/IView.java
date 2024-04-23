package View;
import Controller.IController;


/**
 * Das Interface der Klasse MyView. Hier stehen alle Methoden, die Informationen des Controllers verarbeiten.
 *
 * @author Nico De Luca
 * @since 1.0
 * @version 1.0
 */

public interface IView {

    void setController(IController controller);
    void drawPlayer();

    void playerMovement();

    void drawBalls();

    void drawShots();

    void drawItem(float[] item);

    void drawBlocks();
}
