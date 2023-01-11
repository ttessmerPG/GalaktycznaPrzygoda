package com.LosSiedlosProductions.GalaktycznaPrzygoda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    String filename = "";
    String filepath = "";
    char fileContent = '0';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Constants.SCREEN_WIDTH = displayMetrics.widthPixels;
        Constants.SCREEN_HEIGHT = displayMetrics.heightPixels;

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

    @Override
    protected void onStop() {
        super.onStop();
    }

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