package com.LosSiedlosProductions.GalaktycznaPrzygoda;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Button implements GameObject {

    private final Rect rectangle;
    Bitmap bitmap;

    public Rect getRectangle() {
        return rectangle;
    }

    public Button(Rect rectangle, Bitmap bitmap) {
        this.rectangle = rectangle;
        this.bitmap = bitmap;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, rectangle.left, rectangle.top, null);
    }

    @Override
    public void update() {  }
}
