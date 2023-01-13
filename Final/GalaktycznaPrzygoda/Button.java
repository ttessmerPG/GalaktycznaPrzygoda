package com.LosSiedlosProductions.GalaktycznaPrzygoda;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Klasa związana z rysowaniem przycisków na ekranie
 */
public class Button implements GameObject {
    private final Rect rectangle;
    Bitmap bitmap;

    public Button(Rect rectangle, Bitmap bitmap) {
        this.rectangle = rectangle;
        this.bitmap = bitmap;
    }

    public Rect getRectangle() {
        return rectangle;
    }

    /**
     * Metoda rysująca przycisk na ekranie.
     * @param canvas przestrzeń na której rysowane są przyciski
     */
    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, rectangle.left, rectangle.top, null);
    }

    @Override
    public void update() {  }
}
