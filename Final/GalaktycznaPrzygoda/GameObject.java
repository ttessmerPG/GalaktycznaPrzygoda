package com.LosSiedlosProductions.GalaktycznaPrzygoda;

import android.graphics.Canvas;

/**
 * Interfejs definiujący obiekty gry.
 */
public interface GameObject {
    public void draw(Canvas canvas);
    public void update();
}
