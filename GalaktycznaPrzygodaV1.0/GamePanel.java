package com.LosSiedlosProductions.GalaktycznaPrzygoda;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private GameLoop gameLoop;
    private final Player player;
    private final Button shoot, shield, options;
    private Point playerPoint;
    private ObstacleManager obstacleManager;
    private final OrientationData orientationData;
    private boolean movingPlayer = false;
    private boolean gameOver = false, paused = false;
    private long gameOverTime, startTime;
    Bitmap background, scaledBackground, lifeImg, shootImg, shieldImg, scaledShoot, scaledShield, optionsImg, scaledOptions, shieldingImg, scaledShielding;
    Bitmap frame, progress, ship, scaledFrame, scaledProgress, scaledShip;
    public boolean shielding = false, shieldDown = false, shotDown = false;
    public long shieldTime = 0, shieldDownTime = 0, shotDownTime = 0;
    private double timeScale;

    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        Constants.CURRENT_CONTEXT = context;
        gameLoop = new GameLoop(getHolder(), this);
        player = new Player(new Rect(150, 150, 300, 300));
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, Constants.SCREEN_HEIGHT - 75);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager();
        orientationData = new OrientationData();
        orientationData.register();

        lifeImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.life);
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.level1);
        scaledBackground = Bitmap.createScaledBitmap(background, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, true);
        shootImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.shoot);
        shieldImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.shield);
        shieldingImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.shielding);
        scaledShoot = Bitmap.createScaledBitmap(shootImg, Constants.SCREEN_WIDTH/8, 2*Constants.SCREEN_HEIGHT/8, true);
        scaledShield = Bitmap.createScaledBitmap(shieldImg, Constants.SCREEN_WIDTH/8, 2*Constants.SCREEN_HEIGHT/8, true);
        optionsImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.options);
        scaledOptions = Bitmap.createScaledBitmap(optionsImg, 8*optionsImg.getWidth()/10, 8*optionsImg.getHeight()/10, true);
        scaledShielding = Bitmap.createScaledBitmap(shieldingImg, 151 + 30, 118 + 30, true);

        shoot = new Button(new Rect(6*Constants.SCREEN_WIDTH/8, 5*Constants.SCREEN_HEIGHT/8, 7*Constants.SCREEN_WIDTH/8, 7*Constants.SCREEN_HEIGHT/8), scaledShoot);
        shield = new Button(new Rect(Constants.SCREEN_WIDTH/8, 5*Constants.SCREEN_HEIGHT/8, 2*Constants.SCREEN_WIDTH/8, 7*Constants.SCREEN_HEIGHT/8), scaledShield);
        options = new Button(new Rect(Constants.SCREEN_WIDTH - scaledOptions.getWidth() - 20, 20, scaledOptions.getWidth(), 20 + optionsImg.getHeight()), scaledOptions);

        frame = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.greyprogressbar);
        scaledFrame = Bitmap.createScaledBitmap(frame, 440, 44, true);
        progress = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.greenprogressbar);
        ship = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.progressbarship);
        scaledShip = Bitmap.createScaledBitmap(ship, ship.getWidth()/7, ship.getHeight()/7, true);
        startTime = System.currentTimeMillis();

        setFocusable(true);
    }

    public void reset() {
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 9*Constants.SCREEN_HEIGHT/10);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager();
        movingPlayer = false;
        Constants.PLAYER_LIFE = 3;
        shielding = false;
        shieldDown = false;
        Constants.PLAYER_INVINCIBILITY = false;
        shotDown = false;
        Constants.INIT_TIME = System.currentTimeMillis();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        gameLoop = new GameLoop(getHolder(), this);
        Constants.INIT_TIME = System.currentTimeMillis();
        gameLoop.setRunning(true);
        gameLoop.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while(retry) {
            try {
                gameLoop.setRunning(false);
                gameLoop.join();
            } catch(Exception e) {e.printStackTrace();}
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(!gameOver && player.getRectangle().contains((int)motionEvent.getX(), (int)motionEvent.getY()))
                    movingPlayer = true;
                if(gameOver && System.currentTimeMillis() - gameOverTime >= 2000) {
                    reset();
                    gameOver = false;
                    orientationData.newGame();
                }
                if(!shotDown && !gameOver && shoot.getRectangle().contains((int)motionEvent.getX(), (int)motionEvent.getY())) {
                    obstacleManager.addShot(playerPoint.x, playerPoint.y);
                    MediaPlayer shotSound = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.shot_sound);
                    shotSound.start();
                    shotDown = true;
                    shotDownTime = System.currentTimeMillis();
                }
                if(!shieldDown && !shielding && !gameOver && shield.getRectangle().contains((int)motionEvent.getX(), (int)motionEvent.getY())) {
                    shielding = true;
                    MediaPlayer shieldUpSound = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.shield_up_sound);
                    shieldUpSound.start();
                    shieldDown = true;
                    Constants.PLAYER_INVINCIBILITY = true;
                    shieldTime = System.currentTimeMillis();
                }
                if(!gameOver && options.getRectangle().contains((int)motionEvent.getX(), (int)motionEvent.getY())) {
                    paused = true;
                }
            case MotionEvent.ACTION_MOVE:
                if(!gameOver && movingPlayer)
                    playerPoint.set((int)motionEvent.getX(), (int)motionEvent.getY());
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;
        }
        return true;
    }

    public void update() {
        if(paused) gameLoop.setRunning(false);
        if(!gameOver) {
            if(System.currentTimeMillis() - Constants.INIT_TIME > Constants.LEVEL_TIME)
                gameOver = true;
            if (startTime < Constants.INIT_TIME)
                startTime = Constants.INIT_TIME;
            int elapsedTime = (int)(System.currentTimeMillis() - startTime);
            startTime = System.currentTimeMillis();
            if (orientationData.getOrientation() != null && orientationData.getStartOrientation() != null) {
                float pitch = orientationData.getOrientation()[1] - orientationData.getStartOrientation()[1];
                float xSpeed = 2 * pitch * Constants.SCREEN_HEIGHT / 300f;
                playerPoint.x += Math.abs(xSpeed*elapsedTime) > 5 ? -xSpeed*elapsedTime : 0;
            }
            if(playerPoint.x < 75)
                playerPoint.x = 75;
            else if(playerPoint.x > Constants.SCREEN_WIDTH - 75)
                playerPoint.x = Constants.SCREEN_WIDTH - 75;
            if(playerPoint.y < 75)
                playerPoint.y = 75;
            else if(playerPoint.y > Constants.SCREEN_HEIGHT - 75)
                playerPoint.y = Constants.SCREEN_HEIGHT - 75;

            player.update(playerPoint);
            obstacleManager.update();
            if (obstacleManager.playerCollide(player)) {
                if (Constants.PLAYER_INVINCIBILITY) {
                    Constants.PLAYER_INVINCIBILITY = false;
                    shielding = false;
                    MediaPlayer shieldDownSound = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.shield_down_sound);
                    shieldDownSound.start();
                    shieldDown = true;
                    shieldDownTime = System.currentTimeMillis();
                } else {
                    Constants.PLAYER_LIFE -= 1;
                }
                if (Constants.PLAYER_LIFE == 0) {
                    gameOver = true;
                    gameOverTime = System.currentTimeMillis();
                }
            }
            if(obstacleManager.shotCollide()) {

            }
            if(shieldDown && System.currentTimeMillis() - shieldDownTime >= 5000) {
                shieldDown = false;
            }
            if(shotDown && System.currentTimeMillis() - shotDownTime >= 1000) {
                shotDown = false;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawBitmap(scaledBackground, 0, 0, null);
        player.draw(canvas);
        if (Constants.PLAYER_INVINCIBILITY && System.currentTimeMillis() - shieldTime < 3000) {
            canvas.drawBitmap(scaledShielding,  player.getRectangle().left - 16, player.getRectangle().top - 25, null);
        } else if (Constants.PLAYER_INVINCIBILITY && System.currentTimeMillis() - shieldTime > 3000) {
            shielding = false;
            Constants.PLAYER_INVINCIBILITY = false;
            MediaPlayer shieldDownSound = MediaPlayer.create(Constants.CURRENT_CONTEXT, R.raw.shield_down_sound);
            shieldDownSound.start();
            shieldDown = true;
            shieldDownTime = System.currentTimeMillis();
        }
        obstacleManager.draw(canvas);
        for(int i=0; i<Constants.PLAYER_LIFE; i++){
            canvas.drawBitmap(lifeImg,  20 +(lifeImg.getWidth() + 20) * i, 20, null);
        }
        shoot.draw(canvas);
        shield.draw(canvas);
        options.draw(canvas);
        if(!gameOver) {
            timeScale = (double) (System.currentTimeMillis() - Constants.INIT_TIME) / Constants.LEVEL_TIME;
            scaledProgress = Bitmap.createScaledBitmap(progress, (int)(timeScale * 424), 36, true);
        }
        canvas.drawBitmap(scaledFrame, Constants.SCREEN_WIDTH/2 - scaledFrame.getWidth()/2, 20, null);
        canvas.drawBitmap(scaledProgress, Constants.SCREEN_WIDTH/2 - scaledFrame.getWidth()/2 + 7, 24, null);
        canvas.drawBitmap(scaledShip, (Constants.SCREEN_WIDTH/2 - scaledFrame.getWidth()/2 - 7) + (int)(timeScale * 440), 24, null);
    }
}
