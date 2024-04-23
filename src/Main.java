import processing.core.*;
import View.*;
import Model.*;
import Controller.*;

/**
 * Die Klasse wird zum Starten des Spiels gebraucht.
 *
 * @since 1.0
 * @author Nico De Luca
 * @version 1.0
 */

public class Main {

    /**
     * Main Methode, die zum Starten des Programms benötigt wird.
     * @param args
     */

    public static void main(String[] args) {

        var model = new MyModel();
        var view = new MyView();
        var controller = new MyController(view, model);



        // Connect M, V and C
        controller.setModel(model);
        controller.setView(view);
        view.setController(controller);



        // Starts the processing application
        PApplet.runSketch(new String[]{"MyView"}, view);
    }
}