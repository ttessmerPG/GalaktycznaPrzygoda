package com.LosSiedlosProductions.GalaktycznaPrzygoda;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

/**
 * Klasa wyświetlająca samouczek
 */
public class TutorialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tutorial);
        ImageView titbit0 = findViewById(R.id.titbit0);
        new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                titbit0.animate().alpha(0f).setDuration(1000);
                final Handler handler2 = new Handler();
                Runnable runnable2 = new Runnable() {
                    @Override
                    public void run() {
                        titbit0.setVisibility(View.GONE);
                        ImageView tutorial0 = findViewById(R.id.tutorialbackground);
                        tutorial0.animate().alpha(1f).setDuration(1000);
                    }
                };
                handler2.postDelayed(runnable2, 1000);
            }
        }.start();
    }

    /**
     * Przejście do kolejnego poziomu
     */
    public void startGame(View view) {
        Intent mainActivityIntent = new Intent("com.LosSiedlosProductions.GalaktycznaPrzygoda.MAIN_ACTIVITY");
        startActivity(mainActivityIntent);
        finish();
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
    protected void onDestroy() { super.onDestroy(); }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
