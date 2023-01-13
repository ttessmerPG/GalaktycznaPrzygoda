package com.LosSiedlosProductions.GalaktycznaPrzygoda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Klasa konfigurująca ustawienia i uruchamiająca rozgrywke
 */
public class MainActivity extends AppCompatActivity {
    String filename = "", filepath = "";
    char fileContent = '0';

    /**
     * Funkcja pobierająca wymiary ekranu
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Constants.SCREEN_WIDTH = displayMetrics.widthPixels;
        Constants.SCREEN_HEIGHT = displayMetrics.heightPixels;
        setContentView(new GamePanel(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * Funkcja zapisująca numer ostatniego rozegranego poziomu do pliku tekstowego.
     */
    @Override
    protected void onStop() {
        filename = "myFile.txt";
        filepath = "MyFileDir";
        File myExternalFile = new File(getExternalFilesDir(filepath), filename);
        FileOutputStream fos;
        fileContent = Constants.CURRENT_LEVEL;
        try {
            fos = new FileOutputStream(myExternalFile);
            fos.write(fileContent);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onStop();
    }

    /**
     * Funkcja zapisująca numer ostatniego rozegranego poziomu do pliku tekstowego.
     */
    @Override
    protected void onDestroy() {
        filename = "myFile.txt";
        filepath = "MyFileDir";
        File myExternalFile = new File(getExternalFilesDir(filepath), filename);
        FileOutputStream fos;
        fileContent = Constants.CURRENT_LEVEL;
        try {
            fos = new FileOutputStream(myExternalFile);
            fos.write(fileContent);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}