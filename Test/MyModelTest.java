import Model.MyModel;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


class MyModelTest {


    @Test
    void getLife() {
        MyModel model = new MyModel();
        assertEquals(3, model.getLife(), "Leben sollten am Anfang 3 sein.");
    }

    @Test
    void createBlockRows() {

        MyModel model = new MyModel();

        assertEquals(model.getBlocks().size(), 7*14);

    }


    @Test
    void createBlock() {
        MyModel model = new MyModel();
        int initialBlockSize = model.getBlocks().size();

        float[] blockCords = {700f, 150.0f, 6.0f};
        model.createBlock(blockCords);

        List<float[]> blocks = model.getBlocks();


        assertEquals(initialBlockSize + 1, blocks.size());


    }




    @Test
    void getScore() {

        MyModel model = new MyModel();

        assertEquals(0, model.getScore(), "Score sollte am Anfang 0 sein");
    }

    @Test
    void getPlayerX() {
        MyModel model = new MyModel();

        assertEquals(370.0F, model.getPlayerX());
    }


    @Test
    void setPlayerX() {
        MyModel model = new MyModel();

        float newPlayerX = 400.0F;
        model.setPlayerX(newPlayerX);

        assertEquals(newPlayerX, model.getPlayerX());
    }


    @Test
    void getPlayerY() {
        MyModel model = new MyModel();

        assertEquals(570.0F, model.getPlayerY());

    }

    @Test
    void getPlayerWidth() {
        MyModel model = new MyModel();

        assertEquals(150, model.getPlayerWidth());
    }

    @Test
    void getPlayerHeight() {
        MyModel model = new MyModel();

        assertEquals(20, model.getPlayerHeight());
    }



    @Test
    public void moveBall() {
            MyModel model = new MyModel();

            float originalBallY = model.getBalls().getFirst()[1];

            model.moveBall();

            assertNotEquals(originalBallY, model.getBalls().getFirst()[1], "Die Position des Balls hat sich nicht verändert.");

    }


    @Test
    public void checkCollision() {

        MyModel model = new MyModel();

        model.getBalls().clear();

        model.getBalls().add(new float[]{model.getPlayerX(), model.getPlayerY() , 0, 5, 10});

        float originalBallSpeedY = model.getBalls().get(0)[3];

        model.checkCollision(model.getBalls().get(0));

        assertTrue(model.getBalls().get(0)[3] < originalBallSpeedY, "Die Kollision hat nicht funktioniert.");
    }



    @Test
    public void CheckBlockCollisions() {

        MyModel model = new MyModel();

        model.getBlocks().clear();

        model.getBalls().add(new float[]{500, 500, 10, 10, 5});

        model.getBlocks().add(new float[]{500, 500, 20, 20, 0});

        float itemX = model.getBlocks().getFirst()[1] + model.getBlocks().getFirst()[2] / 2;
        float itemY = model.getBlocks().getFirst()[1] + model.getBlocks().getFirst()[3];
        model.getItems().add(new float[]{itemX, itemY, 5});



        model.checkBlockCollisions();


        assertEquals(0, model.getBlocks().size(), "Der Block wurde nicht entfernt");
        assertEquals(100, model.getScore(), "Der Score wurde nicht erhöht.");
        assertEquals(500 + (float) 20 / 2, itemX, "Der item ist nicht an der richtigen x koordinate entstanden");
        assertEquals(500 + 20, itemY, "Der item ist nicht an der richtigen x koordinate entstanden");

    }

    @Test
    void getBlocks() {
        MyModel model = new MyModel();

        float[] block = new float[]{100, 100, 50, 20, 0, 0};
        model.getBlocks().add(block);

        List<float[]> blocks = model.getBlocks();

        assertTrue(blocks.contains(block), "Es wurde kein Block hinzugefügt");
    }


    @Test
    void isWinning() {
        MyModel model = new MyModel();

        model.getBlocks().clear();

        assertTrue(model.isWinning(), "Methode sollte true zurückgeben.");

        model.getBlocks().add(new float[]{0, 0, 50f, 20f, 0f, 0f});

        assertFalse(model.isWinning(), "Methode sollte false zurückgeben.");
    }

    @Test
    void shouldDropItem() {
        MyModel model = new MyModel();

        int trueCount = 0;
        for (int i = 0; i < 1000; i++) {
            if (model.shouldDropItem()) {
                trueCount++;
            }
        }

        assertTrue(trueCount > 150 && trueCount < 250, "Die Methode hat nicht ungefähr 20% der Zeit true returned.");
    }

    @Test
    void itemCollision() {
        MyModel model = new MyModel();

        model.getItems().add(new float[]{model.getPlayerX(), model.getPlayerY(), 0});
        model.getItems().add(new float[]{model.getPlayerX(), model.getPlayerY(), 1});
        model.getItems().add(new float[]{model.getPlayerX(), model.getPlayerY(), 2});

        int originalSize = model.getItems().size();

        model.itemCollision();

        assertTrue(model.getItems().size() < originalSize, "Das Item wurde nicht entfernt");

        model.getItems().add(new float[]{100, 611, 1});
        model.itemCollision();


        assertEquals(0,model.getItems().size());
    }

    @Test
    void getItems() {
        MyModel model = new MyModel();

        float[] item = new float[]{100, 100, 0};
        model.getItems().add(item);

        List<float[]> items = model.getItems();

        assertTrue(items.contains(item), "Das Item wurde nicht hinzugefügt");
    }

    @Test
    void getBalls() {
        MyModel model = new MyModel();

        float[] ball = new float[]{100, 100, 0, 5, 10};
        model.getBalls().add(ball);

        List<float[]> balls = model.getBalls();

        assertTrue(balls.contains(ball), "Der Ball wurde nicht hinzugefügt.");
    }

    @Test
    void getShots() {
        MyModel model = new MyModel();

        float[] shot = new float[]{100, 100, 0, 5, 10};
        model.getShots().add(shot);

        List<float[]> shots = model.getShots();

        assertTrue(shots.contains(shot), "Der Schuss wurde nicht hinzugefügt");
    }

    @Test
    void item1() {
        MyModel model = new MyModel();

        float originalPlayerWidth = model.getPlayerWidth();

        model.item1();

        assertTrue(model.getPlayerWidth() > originalPlayerWidth, "Die weite des Spielers wurde nicht erhöht.");
    }

    @Test
    void item2() {
        MyModel model = new MyModel();

        int originalBallCount = model.getBalls().size();

        model.item2();

        assertTrue(model.getBalls().size() > originalBallCount, "Es wurde kein Ball hinzugefügt");


    }

    @Test
    void item3() throws InterruptedException {
        MyModel model = new MyModel();

        int originalShotCount = model.getShots().size();

        model.item3();

        Thread.sleep(1000);

        assertEquals(originalShotCount + 10, model.getShots().size(), "Es wurden keine 10 Schüsse hinzugefügt");
    }

    @Test
    void moveShots() {
        MyModel model = new MyModel();

        float[] shot = new float[]{100, 100, 0, -5, 10};
        model.getShots().add(shot);

        float originalShotY = shot[1];

        model.moveShots();

        assertTrue(shot[1] < originalShotY, "Der Schuss hat sich nicht bewegt.");

        shot = new float[]{100, 1, 0, -5, 10};
        model.getShots().add(shot);

        model.moveShots();

        assertFalse(model.getShots().contains(shot), "Der Schuss wurde nicht entfernt");
    }

    @Test
    void checkShotBlockCollisions() {
        MyModel model = new MyModel();

        model.getBlocks().clear();
        model.getBlocks().add(new float[]{100, 100, 50f, 20f, 0f, 0f});

        model.getShots().add(new float[]{100, 100, 0, -5, 10});

        model.checkShotBlockCollisions();

        assertTrue(model.getBlocks().isEmpty(), "Der block wurde nicht entfernt");

        assertTrue(model.getShots().isEmpty(), "Der Schuss wurde nicht entfernt.");
    }


    @Test
    void ballOutOfBounce() {
        MyModel model = new MyModel();

        model.getBalls().add(new float[]{100, 620, 0, -5, 10});

        int originalBallCount = model.getBalls().size();

        model.ballOutOfBounce();

        assertTrue(model.getBalls().size() < originalBallCount, "Die Bälle wurden nicht weniger.");

        model.getBalls().clear();

        model.ballOutOfBounce();

        assertEquals(2,model.getLife());


        model.getBalls().clear();

        model.ballOutOfBounce();

        assertEquals(1,model.getLife());

        model.getBalls().clear();

        model.ballOutOfBounce();

    }



    @Test
    void resetGameLoose() {
        MyModel model = new MyModel();

        model.resetGameLoose();

        assertEquals(3, model.getLife(), "Die Leben wurden nicht zurückgesetzt.");
        assertEquals(0, model.getScore(), "Der Score wurden nicht zurückgesetzt.");
        assertEquals(150, model.getPlayerWidth(), "Die Weite des Spielers wurden nicht zurückgesetzt.");
        assertFalse(model.getBlocks().isEmpty(), "Die Blöcke wurden nicht neu aufgebaut.");
    }

    @Test
    void resetGameWin() {
        MyModel model = new MyModel();

        model.resetGameWin();

        assertFalse(model.getBlocks().isEmpty(), "Die Blöcke wurden nicht neu aufgebaut.");
        assertEquals(1, model.getBalls().size(), "Es wurde kein neuer Ball hinzugefügt.");
        assertTrue(model.getItems().isEmpty(), "Die Items wurden nicht gelöscht.");
    }
}