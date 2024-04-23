

package Model;
import java.util.*;

/**
 * Die Klasse die für die Logik des Spiels verantwortlich ist.
 *
 * @since 1.0
 * @author Nico De Luca
 * @version 1.0
 */
public class MyModel extends Thread implements IModel{

    private  List<float[]> blocks, balls, items, shots;
    ArrayList<Thread> threads;
    private float playerX ,playerWidth ,ballX ,ballY, ballSpeedX, ballSpeedY, ballRadius, itemX, itemY, itemSpeedY;
    private final float playerY, playerHeight;

    private int life,score;

    /**
     * Der Konstruktor der Model Klasse. Hier werden viele Startwerte vergeben.
     */
    public MyModel() {
        itemSpeedY = 3;
        ballX = 440;
        ballY = 300;
        ballSpeedX = 0;
        ballSpeedY = 3;
        ballRadius = 10;
        playerX = 370.0F;
        playerY = 570.0F;
        playerWidth = 150;
        playerHeight = 20;
        life = 3;
        score = 0;
        items = new ArrayList<>();
        blocks = new ArrayList<>();
        threads = new ArrayList<>();
        balls = new ArrayList<>();
        shots = new ArrayList<>();
        balls.add(new float[]{ballX, ballY, ballSpeedX, ballSpeedY, ballRadius});
        createBlockRows();
    }

    /**
     * Erstellt die Blockreihen des Spiels.
     */
    public void createBlockRows() {
        for(int temp = 0; temp <= 6; temp++){
            float k = 10 + (float) temp * 25;
            for(float j = k; j <= k + 15f; j += 25f){
                for (float i = 17; i <= 733; i+=55f){
                    float[] l = {i, j, (float) temp};
                    createBlock(l);
                }
            }
        }
    }

    /**
     * Erstellt einzelne Blöcke
     * @param blockcords übergibt die Blockkoordinaten.
     */
    public void createBlock(float[] blockcords){
        blocks.add(new float[]{blockcords[0], blockcords[1], 50f, 20f, 0f,blockcords[2]});
    }



    /**
     * Lässt die Bälle sich Bewegen. Teilt dafür die Geschwindigkeit, was eine bessere Kollisionsabfrage gewährleisten soll.
     */

    public void moveBall() {

        for (float[] ball : balls) {

            int steps = Math.max(Math.round(Math.abs(ball[2])), Math.round(Math.abs(ball[3])));
            float dx = ball[2] / steps;
            float dy = ball[3] / steps;


            for (int i = 0; i < steps; i++) {
                ball[0] += dx;
                ball[1] += dy;


                checkCollision(ball);
                checkBlockCollisions();
            }
        }
    }

    /**
     * Schaut, ob die Bälle mit den 3 wänden (oben, links, recht) oder dem Spieler kollidieren.
     *
     * @param ball übergibt die Koordinaten der Bälle.
     */
    public void checkCollision(float[] ball) {
        if (ball[0] - ball[4] < 0) {
            ball[2] = Math.abs(ball[2]);
        } else if (ball[0] + ball[4] > 800) {
            ball[2] = -Math.abs(ball[2]);
        }

        if (ball[1] - ball[4] < 0) {
            ball[3] = Math.abs(ball[3]);
        } else if (ball[1] + ball[4] > playerY && ball[0] + ball[4] > playerX && ball[0] - ball[4] < playerX + playerWidth) {
            float ballPlayerHit = (ball[0] - (playerX + playerWidth / 2)) / (playerWidth / 2);

            float bounceAngle = ballPlayerHit * 75;
            ball[2] = (float) (7 * Math.sin(Math.toRadians(bounceAngle)));
            ball[3] = (float) (7 * -Math.abs(Math.cos(Math.toRadians(bounceAngle))));
        }

    }

    /**
     * Überprüft die Kollision zwischen Ball und Blöcken.
     * Erhöht den Score, wenn ein Block von einem Ball getroffen wurde.
     * Lässt ein Item fallen, wenn die shouldDropItem Methode true zurückgibt.
     */

    public void checkBlockCollisions() {
        for (float[] ball : balls) {
            Iterator<float[]> iterator = blocks.iterator();
            while (iterator.hasNext()) {
                float[] block = iterator.next();
                if (block[4] == 0) {

                    float testX = (ball[0] < block[0]) ? block[0] : Math.min(ball[0], block[0] + block[2]);
                    float testY = (ball[1] < block[1]) ? block[1] : Math.min(ball[1], block[1] + block[3]);

                    float distX = ball[0]-testX;
                    float distY = ball[1]-testY;

                    float distance = (float)Math.sqrt( (distX*distX) + (distY*distY));

                    if (distance <= ball[4]) {
                        iterator.remove();
                        score += 100;

                        if (shouldDropItem()) {
                            itemX = block[0] + block[2] / 2;
                            itemY = block[1] + block[3];
                            items.add(new float[]{itemX, itemY, itemSpeedY});
                        }

                        if (Math.abs(distX) > Math.abs(distY)) {
                            ball[2] = (distX > 0) ? Math.abs(ball[2]) : -Math.abs(ball[2]);
                        } else {
                            ball[3] = (distY > 0) ? Math.abs(ball[3]) : -Math.abs(ball[3]);
                        }
                    }
                }
            }
        }
    }


    /**
     * Schaut, ob alle Blöcke vom ball getroffen wurden.
     * @return gibt true zurück, wenn alle Blöcke zerstört wurden und false wenn nicht.
     */


    public boolean isWinning() {
        boolean blocksOn = true;

        for (float[] block : blocks) {
            if (Math.abs(block[4]) == 0) {
                blocksOn = false;
                break;
            }
        }
        return blocksOn;
    }

    /**
     * Gibt zu 20 Prozent den Wert true zurück, um damit festzulegen wie wahrscheinlich ein Block beim zerstört werden ein item fallen lässt.
     * @return gibt true zurück, wenn die Random Zahl kleiner als 2 ist.
     */
    public boolean shouldDropItem() {
        Random rand = new Random();
        int n = rand.nextInt(10);
        return n < 2;
    }

    /**
     * Lässt das item sich bewegen und überprüft die Kollision zwischen Spieler und Item.
     */

    public void itemCollision(){
        Random random = new Random();
        Iterator<float[]> iterator = items.iterator();

        while (iterator.hasNext()) {
            float[] item = iterator.next();

            item[1] += item[2];

            if (item[1] >= playerY && item[0] >= playerX && item[0] <= playerX + playerWidth) {

                int randomNum = random.nextInt(3);

                switch (randomNum) {
                    case 0:
                        item1();
                        break;
                    case 1:
                        item2();
                        break;
                    case 2:
                        item3();
                        break;
                }
                iterator.remove();
            } else if (item[1] > 610) {

                iterator.remove();
            }
        }
    }
    /**
     * Erstellt das erste Item welches die Länge, des Spieler verlängert.
     */
    public void item1(){
        playerWidth = playerWidth +10;
    }

    /**
     * Erstellt das zweite Item welches einen weiteren Ball hinzufügt.
     */
    public void item2() {
        float middleOfPlayer = playerX + playerWidth / 2;
        balls.add(new float[]{middleOfPlayer, playerY, 0, -5, 10});
    }

    /**
     * Erstellt das dritte Item welches 10 Schüsse aus der Spieler mitte abfeuert.
     */
    public void item3() {
        new Thread(() -> {
            for(int i = 0; i < 10; i++){

                float middleOfPlayer = playerX + playerWidth / 2;
                shots.add(new float[]{middleOfPlayer, playerY, 0, -5, i});
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Lässt die Schüsse des dritten Items sich bewegen und entfernt sie, wenn sie das Spielfeld verlassen.
     */
    public void moveShots() {
        Iterator<float[]> iterator = shots.iterator();

        while (iterator.hasNext()) {
            float[] shot = iterator.next();
            shot[1] += shot[3];
            if (shot[1] < 0) {

                iterator.remove();
            }
        }
    }
    /**
     * Checkt die Kollision zwischen Schüssen und Blöcken und entfernt beide, wenn sie treffen. Wenn die Schüsse außerhalb des Spielfelds sind werden sie auch entfernt.
     */
    public void checkShotBlockCollisions() {
        Iterator<float[]> shotIterator = shots.iterator();

        while (shotIterator.hasNext()) {
            float[] shot = shotIterator.next();
            Iterator<float[]> blockIterator = blocks.iterator();

            while (blockIterator.hasNext()) {
                float[] block = blockIterator.next();


                if (block[4] == 0 && shot[0] >= block[0] && shot[0] <= block[0] + block[2] &&
                        shot[1] >= block[1] && shot[1] <= block[1] + block[3]) {

                    shotIterator.remove();
                    blockIterator.remove();
                    score = score +100;


                    break;
                }
            }
        }
    }


    /**
     * Schaut, ob der Ball im Spielfeld ist.
     *
     * @return gibt true zurück, sollte der Ball aus dem Spielfeld fliegen und der Spieler hat noch leben,
     * und false, wenn der Spieler keine Leben mehr hat. Vorher wird noch geschaut ob der ball der das Spielfeld verlässt
     * der letzte in der liste ist.
     */
    public boolean ballOutOfBounce() {
        for (int i = 0; i < balls.size(); i++) {
            float[] ball = balls.get(i);
            if (ball[1] + ball[4] > 610) {
                balls.remove(i);
                i--;
            }
        }

        if (balls.isEmpty()) {
            life = life - 1;
            if (life == 0){


                return true;
            } else {
                float middleOfPlayer = playerX + playerWidth / 2;
                balls.add(new float[]{middleOfPlayer, playerY, 0, -5, 10});
            }
        }
        return false;
    }

    /**
     * resettet das Game, wenn der Spieler das Spiel verloren hat.
     */
    public void resetGameLoose() {
        score = 0;
        playerWidth = 150;
        blocks.clear();
        items.clear();
        createBlockRows();
        float middleOfPlayer = playerX + playerWidth / 2;
        balls.add(new float[]{middleOfPlayer, playerY, 0, -5, 10});
        life = 3;
    }
    /**
     * resettet das Game, wenn der Spieler gewonnen hat.
     */
    public void resetGameWin() {

        createBlockRows();
        float middleOfPlayer = playerX + playerWidth / 2;
        balls.clear();
        items.clear();
        balls.add(new float[]{middleOfPlayer, playerY, 0, -5, 10});
    }
    /**
     * Übergibt die momentane Anzahl der Leben.
     * @return Life.
     */
    public int getLife() {
        return life;
    }

    /**
     * übergibt den Score des Spielers.
     * @return Score.
     */
    public int getScore() {
        return score;
    }

    /**
     * übergibt die X-Koordinate des Spielers.
     * @return playerX.
     */
    public float getPlayerX() {
        return playerX;
    }

    /**
     * setzt die X-Koordinate des Spielers.
     */
    public void setPlayerX(float playerX) {
        this.playerX = playerX;
    }

    /**
     * übergibt die Y-Koordinate des Spielers.
     * @return playerY.
     */
    public float getPlayerY() {
        return playerY;
    }

    /**
     * übergibt die Weite des Spielers.
     * @return playerWidth.
     */
    public float getPlayerWidth() {
        return playerWidth;
    }

    /**
     * übergibt die Höhe des Spielers.
     * @return playerHeight.
     */
    public float getPlayerHeight() {
        return playerHeight;
    }

    /**
     * übergibt die Blöcke.
     * @return ArrayList blocks.
     */
    public List<float[]> getBlocks () {
        return blocks;
    }

    /**
     * übergibt die Items.
     * @return ArrayList items.
     */
    public List<float[]> getItems() {
        return items;
    }

    /**
     * Übergibt die Bälle.
     * @return ArrayList balls.
     */
    public List<float[]> getBalls() {
        return balls;
    }

    /**
     * Übergibt die Schüsse.
     * @return ArrayList shots.
     */
    public List<float[]> getShots() {
        return shots;
    }
}


