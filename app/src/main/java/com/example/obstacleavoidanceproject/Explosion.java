package com.example.obstacleavoidanceproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Explosion {
    Bitmap explosion[] = new Bitmap[3];
    int explosionFrame = 0;
    int explosionX, explosionY;

    //Instantierea obiectelor bitmap cu pozele trecute in folderul drawable
    public Explosion(Context context){
        explosion[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explode);
        explosion[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explode1);
        explosion[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explode2);
    }

    public Bitmap getExplosion(int explosionFrame){
        return explosion[explosionFrame];
    }
}
