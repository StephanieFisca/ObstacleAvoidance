package com.example.obstacleavoidanceproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {


    //Adaug un intent pe nume music, care va avea rolul de a lansa un Activity
    private Intent music;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Indicator de fereastra: atata timp cat aceasta fereastra este vizibila pentru utilizator, mențin ecranul dispozitivului pornit
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //Creez un nou intent catre clasa BackgroundMusic
        music = new Intent(getApplicationContext(),BackgroundMusic.class);
    }

    //Porinrea jocului
    public void startGame(View view) {
        //Instanta catre clasa GameView
        GameView gameView = new GameView(this);
        //Pornesc serviciul pentru background music
        startService(new Intent(getApplicationContext(),BackgroundMusic.class));
        setContentView(gameView);
    }
}