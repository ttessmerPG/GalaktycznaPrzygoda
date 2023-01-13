package com.LosSiedlosProductions.GalaktycznaPrzygoda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

/**
 * Klasa tworząca przeszkody jako elementy gry.
 */
public class Obstacle implements GameObject {
    private final AnimationManager animManager;
    Rect rectangle;
    Bitmap meteorBig1 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.meteorbig);
    Bitmap idleImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player);
    Bitmap laserImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.shot);

    public Obstacle(int obstacleHeight, int obstacleWidth , int startX, int startY) {
        rectangle = new Rect(startX, startY, startX+obstacleWidth, startY + obstacleHeight);
        Animation meteor = new Animation(new Bitmap[]{meteorBig1}, 1f);
        animManager = new AnimationManager(new Animation[]{meteor});
    }

    public Rect getRectangle() {
        return rectangle;
    }

    /**
     * Funkcja przesuwająca przeszkody w dół ekranu.
     * @param y aktualna współrzędna y przeszkody.
     */
    public void incrementY(float y) {
        rectangle.top += y;
        rectangle.bottom += y;
    }

    /**
     * Funkcja wykrywająca kolizje między graczem, a przeszkodą.
     * @param player prostokąt gracza
     * @return 1 - prawda wystąpiła kolizja, 0 - fałsz nie nastąpiła kolizja
     */
    public boolean playerCollide(Player player) {
            if (!Rect.intersects(rectangle, player.getRectangle())) {
                return false;
            }
            int left = Math.max(player.getRectangle().left, rectangle.left);
            int top = Math.max(player.getRectangle().top, rectangle.top);
            int right = Math.min(player.getRectangle().right, rectangle.right);
            int bottom = Math.min(player.getRectangle().bottom, rectangle.bottom);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(meteorBig1, rectangle.width(), rectangle.height(), true);
            Bitmap scaledPlayer = Bitmap.createScaledBitmap(idleImg, player.getRectangle().width(), player.getRectangle().height(), true);
            for (int y = top; y < bottom; y++) {
                for (int x = left; x < right; x++) {
                    int pixel1 = scaledPlayer.getPixel(x - player.getRectangle().left, y - player.getRectangle().top);
                    int pixel2 = scaledBitmap.getPixel(x - rectangle.left, y - rectangle.top);
                    if (pixel1 != Color.TRANSPARENT && pixel2 != Color.TRANSPARENT) {
                        return true;
                    }
                }
            }
            return false;
    }

    /**
     * Funkcja wykrywająca kolizje między strzałem, a przeszkodą.
     * @param shot prostokąt strzału
     * @return 1 - prawda wystąpiła kolizja, 0 - fałsz nie nastąpiła kolizja
     */
    public boolean shotCollide(Rect shot) {
        if (!Rect.intersects(rectangle, shot)) {
            return false;
        }
        int left2 = Math.max(shot.left, rectangle.left);
        int top2 = Math.max(shot.top, rectangle.top);
        int right2 = Math.min(shot.right, rectangle.right);
        int bottom2 = Math.min(shot.bottom, rectangle.bottom);
        Bitmap scaledBitmap2 = Bitmap.createScaledBitmap(meteorBig1, rectangle.width(), rectangle.height(), true);
        Bitmap scaledShot = Bitmap.createScaledBitmap(laserImg, shot.width(),shot.height(), true);
        for (int y2 = top2; y2 < bottom2; y2++) {
            for (int x2 = left2; x2 < right2; x2++) {
                int pixel3 = scaledShot.getPixel(x2 - shot.left, y2 - shot.top);
                int pixel4 = scaledBitmap2.getPixel(x2 - rectangle.left, y2 - rectangle.top);
                if (pixel3 != Color.TRANSPARENT && pixel4 != Color.TRANSPARENT) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Funkcja rysująca przeszkody na ekranie.
     * @param canvas przestrzeń do rysowania
     */
    @Override
    public void draw(Canvas canvas) {
        animManager.draw(canvas, rectangle);
        animManager.playAnim(0);
    }

    /**
     * Funkcja aktualizująca przeszkody.
     */
    @Override
    public void update() {
        animManager.update();
    }
}
