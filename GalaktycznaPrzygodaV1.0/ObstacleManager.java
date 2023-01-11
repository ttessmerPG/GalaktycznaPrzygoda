package com.LosSiedlosProductions.GalaktycznaPrzygoda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import java.util.ArrayList;
import java.util.Random;

public class ObstacleManager {

    private final ArrayList<Obstacle> obstacles;
    private final ArrayList<Rect> shots;
    private final int minObstacleSize = 150, maxObstacleSize = 300;
    private int obstacleSize;
    private final int minStartX = 1, maxStartX = Constants.SCREEN_WIDTH - maxObstacleSize;
    int currY = -15*Constants.SCREEN_HEIGHT/20;
    int xStart, xStart2;
    private long startTime;
    Random r = new Random();
    Bitmap laser = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.shot);

    public ObstacleManager() {
        startTime = System.currentTimeMillis();
        obstacles = new ArrayList<>();
        shots = new ArrayList<>();
//        Constants.START_TIME = System.currentTimeMillis();
        populateObstacles();
    }

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

    private void populateObstacles () {
        for(int y = 0; y < 1; y++) {
            int playerGapLocation = r.nextInt(Constants.SCREEN_WIDTH - Constants.PLAYER_GAP_WIDTH );
            obstacleSize = 0;
            for(int  x = 0; x < 7; x++) {
                obstacleSize = r.nextInt(maxObstacleSize - minObstacleSize) + minObstacleSize;
                xStart = r.nextInt(((x + 1) * maxObstacleSize) - (x * maxObstacleSize)) + (x * maxObstacleSize );
                if(xStart >= playerGapLocation || xStart + obstacleSize >= playerGapLocation || xStart <= playerGapLocation - Constants.PLAYER_GAP_WIDTH)
                    obstacles.add(new Obstacle(obstacleSize, obstacleSize, xStart, currY ));
            }
        }
    }

    public void addShot(int playerPointX, int playerPointY) {
        shots.add(new Rect(playerPointX - 12, playerPointY - 149, playerPointX + 9, playerPointY + 33 ));
    }

    public void update() {
        if (startTime < Constants.INIT_TIME)
            startTime = Constants.INIT_TIME;

        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = Constants.SCREEN_HEIGHT/2000.0f;
        for(Obstacle ob : obstacles) {
            ob.incrementY(speed * elapsedTime);
        }
        for(Rect shot : shots) {
            shot.top -= speed * elapsedTime;
            shot.bottom -= speed * elapsedTime;
        }
        if(obstacles.size() > 0) {
            if (obstacles.get(obstacles.size() - 1).getRectangle().top >= -maxObstacleSize + 150) {
                populateObstacles();
            }
            if (obstacles.get(obstacles.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT) {
                obstacles.remove(obstacles.size() - 1);
            }
        }
        if(shots.size() > 0) {
            if (shots.get(shots.size() - 1 ).top >= Constants.SCREEN_HEIGHT) {
                shots.remove(shots.size() - 1);
            }
        }
    }

    public void draw(Canvas canvas) {
        for(Obstacle ob : obstacles) {
            ob.draw(canvas);
        }
        for(Rect shot: shots) {
            canvas.drawBitmap(laser, shot.left, shot.top, null );
        }

    }
}
