package com.LosSiedlosProductions.GalaktycznaPrzygoda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import java.util.ArrayList;
import java.util.Random;

/**
 * Klasa zarządzająca generacją oraz umiejscowieniem przeszkód.
 */
public class ObstacleManager {
    private final ArrayList<Obstacle> obstacles;
    private final ArrayList<Rect> shots;
    private final int minObstacleSize = 150, maxObstacleSize = 300, maxStartX = Constants.SCREEN_WIDTH - minObstacleSize/2, minStartX = -minObstacleSize/2;
    private int obstacleSize;
    int currY = -15*Constants.SCREEN_HEIGHT/20, xStart, yStart, obstaclePerRow = 6;
    private long startTime;
    Random r = new Random();
    Bitmap laser = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.shot);

    public ObstacleManager() {
        startTime = System.currentTimeMillis();
        obstacles = new ArrayList<>();
        shots = new ArrayList<>();
        populateObstacles();
    }

    /**
     * Funkcja wywołująca sprawdzenie kolizji z graczem oraz działająca wedle odpowiedzi.
     * @param player prostokąt gracza
     * @return 1 - nastąpiła kolizja, 0 - nie nastąpiła kolizja
     */
    public boolean playerCollide(Player player) {
        for(Obstacle ob : obstacles) {
            if (ob.playerCollide(player) ) {
                if (Constants.PLAYER_LIFE != 1 || Constants.PLAYER_INVINCIBILITY) {
                    obstacles.remove(ob);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Funkcja wywołująca sprawdzenie kolizji ze strzałem oraz działająca wedle odpowiedzi.
     * @return 1 - nastąpiła kolizja, 0 - nie nastąpiła kolizja
     */
    public boolean shotCollide() {
        for (int i = 0; i < shots.size(); i++) {
            for (Obstacle ob : obstacles) {
                if (ob.shotCollide(shots.get(i))) {
                    obstacles.remove(ob);
                    shots.remove(shots.get(i));
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Funkcja generująca przeszkody o losowych parametrach.
     */
    private void populateObstacles () {
        for(int y = 0; y < 1; y++) {
            for(int  x = 0; x < obstaclePerRow; x++) {
                obstacleSize = r.nextInt(maxObstacleSize - minObstacleSize) + minObstacleSize;
                xStart = r.nextInt(((x + 1) * (maxStartX-minStartX)/obstaclePerRow) - (x * (maxStartX-minStartX)/obstaclePerRow)) + (x * (maxStartX-minStartX)/obstaclePerRow);
                yStart = r.nextInt((-currY + maxObstacleSize) - (-currY)) - currY;
                if (xStart + obstacleSize >= ((x + 2) * (maxStartX-minStartX)/obstaclePerRow) - (( x + 1 ) * (maxStartX-minStartX)/obstaclePerRow))
                    obstacleSize = (( x + 1 ) * (maxStartX-minStartX)/obstaclePerRow) - xStart;
                if (obstacleSize >= 50)
                    obstacles.add(new Obstacle(obstacleSize, obstacleSize, xStart, -yStart ));
            }
        }
    }

    /**
     * Dodanie strzału do tablicy w miejscu jego oddania.
     * @param playerPointX - współrzędna x gracza w momencie oddania strzału
     * @param playerPointY - współrzędna y gracza w momencie oddania strzału
     */
    public void addShot(int playerPointX, int playerPointY) {
        shots.add(new Rect(playerPointX - 12, playerPointY - 149, playerPointX + 9, playerPointY + 33 ));
    }

    /**
     * Metoda aktualizująca przeszkody
     */
    public void update() {
        if (startTime < Constants.INIT_TIME)
            startTime = Constants.INIT_TIME;

        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = Constants.SCREEN_HEIGHT/1500.0f;
        for(Obstacle ob : obstacles) {
            ob.incrementY(speed * elapsedTime);
        }
        for(Rect shot : shots) {
            shot.top -= speed * elapsedTime;
            shot.bottom -= speed * elapsedTime;
        }
        if(obstacles.size() > 0 && System.currentTimeMillis() - Constants.INIT_TIME < Constants.LEVEL_TIME - 3000) {
            if (obstacles.get(obstacles.size() - 1 ).getRectangle().top >= currY + Constants.SCREEN_HEIGHT - Constants.PLAYER_GAP_WIDTH) {
                populateObstacles();
            }
            if (obstacles.get(0).getRectangle().top >= Constants.SCREEN_HEIGHT) {
                obstacles.remove(0);
            }
        }
        if(shots.size() > 0) {
            if (shots.get(shots.size() - 1 ).top >= Constants.SCREEN_HEIGHT) {
                shots.remove(shots.size() - 1);
            }
        }
    }

    /**
     * Metoda rysująca przeszkody i strzały
     * @param canvas przestrzeń do rysowania
     */
    public void draw(Canvas canvas) {
        for(Obstacle ob : obstacles) {
            ob.draw(canvas);
        }
        for(Rect shot: shots) {
            canvas.drawBitmap(laser, shot.left, shot.top, null );
        }

    }
}
