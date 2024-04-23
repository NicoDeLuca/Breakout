package Model;
import java.util.List;

/**
 * Das Interface der Klasse MyModel. Hier stehen public Methoden der Model klasse, welche essenziell fürs Spiel sind.
 *
 * @author Nico De Luca
 * @since 1.0
 * @version 1.0
 */

public interface IModel {


     void createBlockRows();


    void createBlock(float[] blockcords);


    int getLife();


    int getScore();


    float getPlayerX();


    void setPlayerX(float playerX);


    float getPlayerY();

    float getPlayerWidth();

    float getPlayerHeight();

    void moveBall();

    void checkCollision(float[] ball);

    List<float[]> getItems();

    List<float[]> getBalls();

    List<float[]> getShots();

    List<float[]> getBlocks();

    boolean isWinning();

    boolean shouldDropItem();

    void itemCollision();

    void item1();

    void item2();

    void item3();

    void moveShots();

    void checkShotBlockCollisions();

    boolean ballOutOfBounce();

    void resetGameLoose();

    void resetGameWin();

}

