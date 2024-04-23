package View;
import Controller.IController;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse die für alle Grafischen Sachen des Spiels verantwortlich ist.
 *
 * @since 1.0
 * @author Nico De Luca
 * @version 1.0
 */

public class MyView extends PApplet implements IView{
    private IController controller;
    PImage BallPicture;
    PImage PlayerPicture;
    PImage Bg;
    PImage block_01,block_02,block_03,block_04,block_05,block_06,block_07,block_08,item_01;

    private int gameHasStarted;

    private boolean left, right;

    /**
     *
     * @param controller übergibt den Kontroller.
     */
    public void setController(IController controller) {
        this.controller = controller;
    }

    /**
     * Setzt die größe des Fensters des Spieles auf 600 * 800.
     */
    public void settings() {
        size(800, 600);
    }

    /**
     * Läd alle Variablen und Bilder.
     */
    public void setup() {
        noLoop();
        gameHasStarted = 1;
        frameRate(60);
        BallPicture = loadImage("Pictures/Ball.png");
        PlayerPicture = loadImage("Pictures/Spieler.png");
        Bg = loadImage("Pictures/Background.png");
        block_01 = loadImage("Pictures/block_01.png");
        block_02 = loadImage("Pictures/block_02.png");
        block_03 = loadImage("Pictures/block_03.png");
        block_04 = loadImage("Pictures/block_04.png");
        block_05 = loadImage("Pictures/block_05.png");
        block_06 = loadImage("Pictures/block_06.png");
        block_07 = loadImage("Pictures/block_07.png");
        block_08 = loadImage("Pictures/block_08.png");
        item_01 = loadImage("Pictures/item_01.png");
        left = false;
        right = false;
    }

    /**
     * Zeichnet alles was im Spiel passiert.
     */
    public void draw() {

        background(Bg);
        drawPlayer();
        drawBalls();
        controller.runGame();
        drawBlocks();
        playerMovement();
        drawShots();
        textSize(15);
        text("Score: "+controller.getScore(), 30, 560);
        gameStart();

        for (float[] item : controller.getItems()) {
            drawItem(item);
        }
        winOrLoose();



    }

    /**
     * Zeichnet den Spieler
     *
     */
    public void drawPlayer() {

        rect(controller.getModel().getPlayerX(), controller.getModel().getPlayerY(), controller.getModel().getPlayerWidth(), controller.getModel().getPlayerHeight(), 7); // Zeichnet den Spieler //mit gettern Arbeiten und Variablen in Privat ändern!
        image(PlayerPicture,controller.getModel().getPlayerX(),controller.getModel().getPlayerY(),controller.getModel().getPlayerWidth(),controller.getModel().getPlayerHeight());

    }

    /**
     * überprüft, ob bestimmte Tasten gedrückt werden.
     */
    public void keyPressed() {
        switch (keyCode) {
            case(68)-> right = true;
            case(65)-> left = true;

        }
        if(key == 27){
            key = 0;
        }

        if(keyCode == ESC){
            if(looping){
                noLoop();
                textSize(40);
                text("Pause",370,300);
            }
            else{
                loop();
                gameHasStarted = 0;
            }
        }


    }

    /**
     * Schaut, ob die Tasten A und D gedrückt sind, um die Steuerung zu verbessern.
     */
    public void keyReleased() {
        switch (keyCode) {
            case(68)-> right = false;
            case(65)-> left = false;
        }
    }

    /**
     * Verknüpft die Bewegung des Spielers mit den Tasten A und D.
     */
    public void playerMovement(){
        if(right)
            controller.moveRight();
        if(left)
            controller.moveLeft();
    }

    /**
     * Zeichnet die Bälle als Threads, um effizienter zu sein.
     */
    public void drawBalls() {
        List<Thread> threads = new ArrayList<>();
        for (float[] ball : controller.getBalls()) {
            Thread thread = new Thread(() -> {
                float ballX = ball[0];
                float ballY = ball[1];
                synchronized (threads) {
                    image(BallPicture, ballX - 10, ballY - 10);
                }
            });
            thread.start();
            threads.add(thread);
        }


        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Zeichnet die Schüsse des 3. Items
     */

    public void drawShots() {
        List<float[]> Shots;
        synchronized (controller) {
            Shots = new ArrayList<>(controller.getShots());
        }
        for (float[] shot : Shots) {
            float shotX = shot[0];
            float shotY = shot[1];
            fill(222,159,223);
            ellipse(shotX, shotY, 10, 10);
            fill(255);
        }
    }

    /**
     * Zeichnet die Items.
     *
     * @param item übergibt die Koordinaten des Items.
     */
    public void drawItem(float[] item) {
        image(item_01,item[0] -10,item[1]-10);
    }

    /**
     * Zeichnet die Blöcke.
     */
    public void drawBlocks() {
        List<float[]> blocks = controller.getModel().getBlocks();
        for (float[] block : blocks) {
            if (block[4] == 0 && block[5] == 0) {
                rect(block[0], block[1], block[2], block[3],3);
                image(block_01,block[0], block[1]);

            }
            if (block[4] == 0 && block[5] == 1) {
                rect(block[0], block[1], block[2], block[3],3);
                image(block_02,block[0], block[1]);

            }
            if (block[4] == 0 && block[5] == 2) {
                rect(block[0], block[1], block[2], block[3],3);
                image(block_03,block[0], block[1]);

            }
            if (block[4] == 0 && block[5] == 3) {
                rect(block[0], block[1], block[2], block[3],3);
                image(block_04,block[0], block[1]);

            }
            if (block[4] == 0 && block[5] == 4) {
                rect(block[0], block[1], block[2], block[3],3);
                image(block_05,block[0], block[1]);

            }
            if (block[4] == 0 && block[5] == 5) {
                rect(block[0], block[1], block[2], block[3],3);
                image(block_06,block[0], block[1]);

            }
            if (block[4] == 0 && block[5] == 6) {
                rect(block[0], block[1], block[2], block[3],3);
                image(block_07,block[0], block[1]);

            }
            if (block[4] == 0 && block[5] == 7) {
                rect(block[0], block[1], block[2], block[3],3);
                image(block_08,block[0], block[1]);

            }
        }
    }

    /**
     * Schaut ob der Spieler gewinnt oder verliert und gibt dementsprechend einen Text aus.
     * Da das Spiel theoretisch unendlich lange laufen könnte, wird ab einer zu großen Spieler größe ein Sonderfall eintreten, der das Spiel zurücksetzt.
     */
    public void winOrLoose(){
        if (controller.ballOutOfBounce()) {
            text("Life: "+controller.getLeben(), 730, 560);
            controller.resetGameLoose();
            textSize(40);
            noLoop();
            text("You loose",300,300);


        }
        else{
            textSize(15);
            text("Life: "+controller.getLeben(), 730, 560);
        }
        if (controller.isWinning()){
            noLoop();
            textSize(40);
            text("You win",350,300);
            controller.resetGameWin();}

        if (controller.getPlayerWidth() > 770){
            noLoop();
            textSize(40);
            text("Now you have the final Win",110,300);
            controller.resetGameLoose();}
    }

    /**
     * Schreibt den Text, der am Anfang infos gibt.
     */
    public void gameStart(){
        if(gameHasStarted == 1){
            textSize(40);
            text("Use A and D to move",230,230);
            text("Press ESC to Start/Stop",220,280);


        }
    }



}