package com.LosSiedlosProductions.GalaktycznaPrzygoda;

import android.graphics.Canvas;

import android.view.SurfaceHolder;

public class GameLoop extends Thread{

    private final SurfaceHolder surfaceHolder;
    private final GamePanel gamePanel;
    public static Canvas canvas;

    private boolean running;
    private static final int MAX_FPS = 120;

    public void setRunning(boolean running) {
        this.running = running;
    }

    public GameLoop(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        long startTime, waitTime, elapsedTime;
        long targetTime = 1000/MAX_FPS;
        long totalTime = 0;
        int frameCount = 0;

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
            totalTime += System.nanoTime() - startTime;
            frameCount++;

            if(frameCount == MAX_FPS) {
                int averageFPS = (int)(1000 /((totalTime/frameCount)/1000000));
                frameCount = 0;
                totalTime = 0;
                System.out.println("average FPS: "+averageFPS);
            }
        }
    }
}
