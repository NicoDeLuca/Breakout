package Controller;
import View.IView;
import Model.IModel;
import java.util.List;

/**
 * Die Klasse die für as Kontrollieren des Spielers und die Kommunikation zwischen Model und View verantwortlich ist.
 *
 * @since 1.0
 * @author Nico De Luca
 * @version 1.0
 */
public class MyController implements IController{

    private IView view;

    private IModel model;

    /**
     * Setzt die View und das Model fest.
     *
     * @param view übergibt die view
     * @param model übergibt das model
     */
    public MyController(IView view, IModel model) {

        this.view = view;
        this.model = model;
    }

    /**
     * Verknüpft die View mit dem Controller.
     * @param view setzt die View.
     */

    public void setView(IView view) {
        this.view = view;
    }

    /**
     * Verknüpft das Model mit dem Controller.
     * @param model setzt das Model.
     */
    public void setModel(IModel model) {
        this.model = model;
    }


    /**
     *  Lässt den Spieler sich mach rechts bewegen, bis zum Rand des Spielfelds.
     */

    public void moveRight() {
        float playerX = model.getPlayerX();
        float playerWidth = model.getPlayerWidth();
        if (playerX + playerWidth < 790)
            model.setPlayerX(playerX + 10);

    }

    /**
     *  Lässt den Spieler sich mach links bewegen, bis zum Rand des Spielfelds.
     */
    public void moveLeft() {
        float playerX = model.getPlayerX();
        if (playerX > 10)
            model.setPlayerX(playerX - 10);
    }


    /**
     * Übergibt die Weite des Spielers, um sie in der View Klasse nutzbar zu machen.
     * @return model.getPlayerWidth()
     */
    public float getPlayerWidth() {
        return model.getPlayerWidth();
    }

    /**
     * Übergibt die Leben des Spielers, um sie in der View Klasse nutzbar zu machen.
     * @return  model.getLife()
     */

    public int getLeben(){
        return model.getLife();
    }

    /**
     * Übergibt den Score des Spielers, um sie in der View Klasse nutzbar zu machen.
     * @return model.getScore()
     */

    public int getScore(){
        return model.getScore();
    }

    /**
     * Übergibt die isWinning Methode aus dem Model, um sie in der View Klasse nutzbar zu machen.
     * @return model.isWinning().
     */
    public boolean isWinning(){
        return model.isWinning();
    }

    /**
     * übergibt die resetGameLoose Methode aus dem Model, um sie in der View Klasse nutzbar zu machen.
     */
    public void resetGameLoose() {
        model.resetGameLoose();
    }

    /**
     * übergibt die resetGameWin Methode aus dem Model, um sie in der View Klasse nutzbar zu machen.
     */
    public void resetGameWin() {
        model.resetGameWin();
    }

    /**
     * übergibt verschiedene Methoden die in der Draw Methode aufgerufen werden müssen damit das Spiel läuft.
     */
    public void runGame() {
        model.moveBall();
        model.itemCollision();
        model.moveShots();
        model.checkShotBlockCollisions();
    }


    /**
     * übergibt das Model, um es in der View Klasse nutzbar zu machen.
     * @return model
     */
    public IModel getModel() {
        return model;
    }

    /**
     * Übergibt die getItems Methode aus dem Model, um sie in der View Klasse nutzbar zu machen.
     * @return model.getItems()
     */

    public List<float[]> getItems() {
        return model.getItems();
    }

    /**
     * Übergibt die getBalls Methode aus dem Model, um sie in der View Klasse nutzbar zu machen.
     * @return model.getBalls()
     */
    public List<float[]> getBalls() {
        return model.getBalls();
    }

    /**
     * Übergibt die getShots Methode aus dem Model, um sie in der View Klasse nutzbar zu machen.
     * @return model.getShots()
     */
    public List<float[]> getShots() {
        return model.getShots();
    }

    /**
     * übergibt die ballOutOfBounce Methode aus dem Model, um sie in der View Klasse nutzbar zu machen.
     * @return model.ballOutOfBounce()
     */
    public boolean ballOutOfBounce() {
            return model.ballOutOfBounce();
        }


    }


