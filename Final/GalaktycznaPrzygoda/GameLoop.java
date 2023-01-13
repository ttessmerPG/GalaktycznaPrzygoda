package com.LosSiedlosProductions.GalaktycznaPrzygoda;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Klasa tworząca główną pętle programu.
 */
public class GameLoop extends Thread{
    private final SurfaceHolder surfaceHolder;
    private final GamePanel gamePanel;
    public static Canvas canvas;
    private boolean running;
    private static final int MAX_FPS = 120;

    public GameLoop(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * Funkcja cyklicznie wywołuje rysowanie i aktualizacji gamePanel'u
     */
    @Override
    public void run() {
        long startTime, waitTime, elapsedTime;
        long targetTime = 1000/MAX_FPS;

        while (running) {
            startTime = System.nanoTime();
            canvas = null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                if(canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            elapsedTime = (System.nanoTime() - startTime)/1000000;
            waitTime = targetTime - elapsedTime;

            try {
                if(waitTime > 0)
                    sleep(waitTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
