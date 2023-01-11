package com.LosSiedlosProductions.GalaktycznaPrzygoda;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StartUp extends AppCompatActivity {
    //MediaPlayer startUpMusic;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    startUpMusic = MediaPlayer.create(StartUp.this, R.raw.holiznacc0_cosmic_waves);
    //    startUpMusic.start();

        setContentView(R.layout.start_up);
        ImageView firmLogo = findViewById(R.id.firmlogo);

        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                // Code to be run at each tick
            }
            public void onFinish() {
                firmLogo.animate().alpha(0f).setDuration(1000);

                final Handler handler2 = new Handler();
                Runnable runnable2 = new Runnable() {
                    @Override
                    public void run() {
                        firmLogo.setVisibility(View.GONE);

                        ImageView startUpBackground = findViewById(R.id.startupbackground);
                        startUpBackground.animate().alpha(1f).setDuration(1000);
                    }
                };
                handler2.postDelayed(runnable2, 1000);
            }
        }.start();


    }

    public void startGame(View view) {

    //    startUpMusic.release();
        switch (Constants.CURRENT_LEVEL) {
            case '0':
                Intent tutorialActivityIntent = new Intent("com.LosSiedlosProductions.GalaktycznaPrzygoda.TUTORIAL_ACTIVITY");
                startActivity(tutorialActivityIntent);
                finish();
                break;
            case '1':
                Intent mainActivityIntent = new Intent("com.LosSiedlosProductions.GalaktycznaPrzygoda.MAIN_ACTIVITY");
                startActivity(mainActivityIntent);
                finish();
                break;
        }
    }
}
