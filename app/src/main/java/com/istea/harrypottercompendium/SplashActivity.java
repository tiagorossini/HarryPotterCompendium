package com.istea.harrypottercompendium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

// Esta clase es unicamente para mostrar el "Splash"(animación) del inicio de la app
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //getSupportActionBar().hide();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        TextView textSplash = findViewById(R.id.textSplashScreen);
        textSplash.animate().translationX(1200).setDuration(1000).setStartDelay(4500);

        Thread thread = new Thread(){

            public void run(){
                try {
                    Thread.sleep(6500);
                }
                catch (Exception e){
                    e.printStackTrace();
                }

                finally {
                    Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        thread.start();
    }
}