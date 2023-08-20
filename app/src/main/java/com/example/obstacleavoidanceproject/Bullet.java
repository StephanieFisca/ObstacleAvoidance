package com.example.obstacleavoidanceproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Bullet {
    Bitmap bullet[] = new Bitmap[1];
    int bulletFrame = 0;
    int bulletX, bulletY, bulletVelocity;
    Random random;

    //Instantiem un bullet
    public Bullet(Context context){
        bullet[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.bullet);
        random = new Random();
        resetPosition();

    }

    //Gettere
    public Bitmap getBullet(int bulletFrame){
        return bullet[bulletFrame];
    }

    public int getBulletWidth(){
        return bullet[0].getWidth();
    }

    public int getBulletHeight(){
        return bullet[0].getHeight();
    }

    //Resetarea pozitie bullet-ului
    public void resetPosition() {
        bulletX = random.nextInt(GameView.dWidth - getBulletWidth());
        bulletY = -200 +random.nextInt(600)* -1;
        bulletVelocity = 35 + random.nextInt(16);
    }
}
