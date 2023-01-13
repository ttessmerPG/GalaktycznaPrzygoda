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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Klasa odpowiedzialna za rozruch aplikacji
 */
public class StartUp extends AppCompatActivity {
    MediaPlayer startUpMusic;
    String filename = "";
    String filepath = "";

    /**
     * Włączenie muzyki, wczytanie danych z pliku i wyświetlenei grafik.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startUpMusic = MediaPlayer.create(StartUp.this, R.raw.holiznacc0_cosmic_waves);
        startUpMusic.start();
        filename = "myFile.txt";
        filepath = "MyFileDir";
        FileReader fr = null;
        File myExternalFile = new File(getExternalFilesDir(filepath), filename);
        try {
            fr = new FileReader(myExternalFile);
            BufferedReader reader = new BufferedReader(fr);
            Constants.CURRENT_LEVEL = (char) reader.read();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.start_up);
        ImageView firmLogo = findViewById(R.id.firmlogo);
        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
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

    /**
     * Wybór poziomu
     * @param view
     */
    public void startGame(View view) {
        startUpMusic.release();
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
