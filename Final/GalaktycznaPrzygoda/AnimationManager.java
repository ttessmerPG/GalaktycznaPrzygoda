package com.LosSiedlosProductions.GalaktycznaPrzygoda;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Klasa zarządzająca odtwarzanymi animacjami.
 */
public class AnimationManager {
    private final Animation[] animations;
    private int animationIndex = 0;

    public AnimationManager(Animation[] animations) {
        this.animations = animations;
    }

    /**
     * Metoda odtwarzająca animacje.
     */
    public void playAnim(int index) {
        for(int i = 0; i < animations.length; i++) {
            if(i == index) {
                if(!animations[index].isPlaying())
                    animations[i].play();
            } else
                animations[i].stop();
        }
        animationIndex = index;
    }

    /**
     * Metoda inicjująca rysowanie klatki.
     */
    public void draw(Canvas canvas, Rect rect) {
        if(animations[animationIndex].isPlaying())
            animations[animationIndex].draw(canvas, rect);
    }

    /**
     * Metoda inicjująca aktualizacje rysowanej klatki.
     */
    public void update() {
        if(animations[animationIndex].isPlaying())
            animations[animationIndex].update();
    }
}