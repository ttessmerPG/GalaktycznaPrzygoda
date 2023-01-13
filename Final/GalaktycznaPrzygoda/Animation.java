package com.LosSiedlosProductions.GalaktycznaPrzygoda;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 *  Klasa odpowiedzialna za tworzenie animacji.
 */
public class Animation {
    private final Bitmap[] frames;
    private int frameIndex;
    private final float frameTime;
    private long lastFrame;
    private boolean isPlaying = false;

    public Animation(Bitmap[] frames, float animTime) {
        this.frames = frames;
        frameIndex = 0;
        frameTime = animTime/frames.length;
        lastFrame = System.currentTimeMillis();
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    /**
     * Metoda rozpoczynająca odtwarzanie animacji.
     */
    public void play() {
        isPlaying = true;
        frameIndex = 0;
        lastFrame = System.currentTimeMillis();
    }

    /**
     * Metoda zakańczająca odtwarzanie animacji.
     */
    public void stop() {
        isPlaying = false;
    }

    /**
     * Metoda rysująca klatke animacji.
     */
    public void draw(Canvas canvas, Rect destination) {
        if(!isPlaying)
            return;
        scaleRect(destination);
        canvas.drawBitmap(frames[frameIndex], null, destination, null);
    }

    /**
     * Metoda skalująca prostokąt, na którym wyświetlana jest klatka animacji.
     */
    private void scaleRect(Rect rect) {
        float whRatio = (float)(frames[frameIndex].getWidth())/frames[frameIndex].getHeight();
        if(rect.width() > rect.height())
            rect.left = rect.right - (int)(rect.height() * whRatio);
        else
            rect.top = rect.bottom - (int)(rect.width() * (1/whRatio));
    }

    /**
     * Metoda aktualizująca indeks oraz czas ostatniej klatki animacji.
     */
    public void update() {
        if(!isPlaying)
            return;
        if(System.currentTimeMillis() - lastFrame > frameTime * 1000) {
            frameIndex++;
            frameIndex = frameIndex >= frames.length ? 0 : frameIndex;
            lastFrame = System.currentTimeMillis();
        }
    }
}
