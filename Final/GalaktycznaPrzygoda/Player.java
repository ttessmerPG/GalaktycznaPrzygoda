package com.LosSiedlosProductions.GalaktycznaPrzygoda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Klasa odpowiedzialna za rysowanie gracza oraz ustalanie jego pozycji.
 */
public class Player implements GameObject{
    private final Rect rectangle;
    private final AnimationManager animManager;

    public Player(Rect rectangle) {
        this.rectangle = rectangle;
        Animation idle;
        Animation flyRight;
        Animation flyLeft;
        Bitmap idleImg = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.player);
        Bitmap Right = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.playerright);
        Bitmap Left = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.playerleft);
        idle = new Animation(new Bitmap[]{idleImg}, 1f);
        flyRight = new Animation(new Bitmap[]{Right}, 1f);
        flyLeft = new Animation(new Bitmap[]{Left}, 1f);
        animManager = new AnimationManager(new Animation[]{idle, flyRight, flyLeft});
    }
    public Rect getRectangle() {
        return rectangle;
    }

    @Override
    public void draw(Canvas canvas) {
        animManager.draw(canvas, rectangle);
    }

    @Override
    public void update() {
        animManager.update();
    }

    /**
     * Funkcja aktualizująca pozycję gracza oraz ustala kierunek poruszania w celu animacji.
     * @param point współrzędne gracza
     */
    public void update(Point point) {
        float oldLeft = rectangle.left;
        rectangle.set(point.x - rectangle.width()/2, point.y - rectangle.height()/2, point.x + rectangle.width()/2, point.y + rectangle.height()/2);
        int state = 0;
        if(rectangle.left - oldLeft > 20)
            state = 1;
        else if (rectangle.left - oldLeft < -20)
            state = 2;
        animManager.playAnim(state);
        animManager.update();
    }
}
