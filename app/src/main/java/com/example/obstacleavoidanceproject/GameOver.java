package com.example.obstacleavoidanceproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {

    TextView tvPoints;
    TextView tvHighest;
    SharedPreferences sharedPreferences;
    ImageView ivNewHighest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        tvPoints = findViewById(R.id.tvPoints);
        tvHighest = findViewById(R.id.tvHighest);
        ivNewHighest = findViewById(R.id.ivNewHighest);
        //Obtinem numarul de puncte obtinute din meciull terminat
        int points = getIntent().getExtras().getInt("points");
        tvPoints.setText(""+points);
        sharedPreferences = getSharedPreferences("my_pref",0);
        int highest = sharedPreferences.getInt("highest",0);
        //Daca s-au obtinut mai multe puncte ca recordul deja obtinut
        if (points>highest){
            //Facem vizibil icon-ul de new highest
            ivNewHighest.setVisibility(View.VISIBLE);
            highest = points;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            //salvam noul high score
            editor.putInt("highest",highest);
            editor.commit();
        }
        tvHighest.setText(""+highest);

    }

    //Restarteaza jocul
    public void restart(View view){
        Intent intent = new Intent(GameOver.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    //Inchide jocul
    public void exit(View view){
        stopService(new Intent(getApplicationContext(),BackgroundMusic.class));
        finish();
    }
}
