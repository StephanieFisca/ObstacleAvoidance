package com.example.obstacleavoidanceproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.Random;


public class GameView extends View {

    Bitmap background, ground, ghost;
    Rect rectBackground, rectGround;
    Context context;
    Handler handler;
    final long UPDATE_MILLIS = 30;
    Runnable runnable;
    Paint textPaint = new Paint();
    Paint healthPaint = new Paint();
    float TEXT_SIZE = 120;
    int points = 0;
    int life = 3;
    static int dWidth, dHeight;
    Random random;
    float ghostX, ghostY;
    float oldX, oldGhostX;
    ArrayList<Bullet>bullets;
    ArrayList<Explosion>explosions;

    public GameView(Context context) {
        super(context);
        this.context = context;
        //Deschide resursa data, imaginea background
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        ghost = BitmapFactory.decodeResource(getResources(), R.drawable.ghost);
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        //Creaza un nou rectangle cu coordonatele date
        rectBackground = new Rect(0,0,dWidth,dHeight);
        handler = new Handler();
        runnable = () -> invalidate();
        textPaint.setColor(Color.rgb(203, 108, 230));
        textPaint.setTextSize(TEXT_SIZE);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTypeface(ResourcesCompat.getFont(context,R.font.fredokaone));
        healthPaint.setColor(Color.GREEN);
        random = new Random();
        ghostX = dWidth / 2 - ghost.getWidth() / 2;
        ghostY = dHeight - 80 - ghost.getHeight();
        bullets = new ArrayList<>();
        explosions = new ArrayList<>();
        //Adaug 3 bullet-uri
        for (int i=0;i<3;i++){
            Bullet bullet = new Bullet(context);
            bullets.add(bullet);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background,null,rectBackground,null);
        canvas.drawBitmap(ghost,ghostX,ghostY,null);
        //Desenarea bullet-urilor
        for (int i=0;i<bullets.size();i++){
            canvas.drawBitmap(bullets.get(i).getBullet(bullets.get(i).bulletFrame),bullets.get(i).bulletX, bullets.get(i).bulletY,null);
            bullets.get(i).bulletFrame++;
            //Exista un singur bullet frame trecut
            if(bullets.get(i).bulletFrame > 0){
                bullets.get(i).bulletFrame = 0;
            }
            bullets.get(i).bulletY += bullets.get(i).bulletVelocity;
            //Daca bullet-ul a ajuns la nivelul pamantului
            if(bullets.get(i).bulletY + bullets.get(i).getBulletHeight() >= dHeight - 80){
                points +=10;
                //Se creaza explozie
                Explosion explosion = new Explosion(context);
                //Se obtin coordonatele bullet-ului respectiv pentru a stii unde
                //se va efectua explozia
                explosion.explosionX = bullets.get(i).bulletX;
                explosion.explosionY = bullets.get(i).bulletY;
                explosions.add(explosion);
                //Resetam pozitia bullet-ului
                bullets.get(i).resetPosition();
            }
        }

        //S-a lovit ghost-u;
        for (int i=0;i< bullets.size();i++){
            if (bullets.get(i).bulletX + bullets.get(i).getBulletWidth() >= ghostX
                    && bullets.get(i).bulletX <= ghostX + ghost.getWidth()
            && bullets.get(i).bulletY + bullets.get(i).getBulletWidth() >= ghostY
            && bullets.get(i).bulletY + bullets.get(i).getBulletWidth() <= ghostY + ghost.getHeight()){
                //Decrementam viata
                life--;
                bullets.get(i).resetPosition();
                //Nu mai are jucatorul vieti
                if (life == 0){
                    Intent intent = new Intent(context, GameOver.class);
                    intent.putExtra("points",points);
                    context.startActivity(intent);
                    ((Activity)context).finish();
                }
            }
        }
        //Desenarea explozilor
        for (int i=0;i<explosions.size();i++){
            canvas.drawBitmap(explosions.get(i).getExplosion(explosions.get(i).explosionFrame),explosions.get(i).explosionX,
                    explosions.get(i).explosionY,null);
            explosions.get(i).explosionFrame++;
            if (explosions.get(i).explosionFrame > 2){
                explosions.remove(i);
            }
        }

        //Se face bara galbena la o lovitura
        if (life == 2){
            healthPaint.setColor(Color.YELLOW);
        }else if (life == 1){
            //Se face bara rosie la o viata ramasa
            healthPaint.setColor(Color.RED);
        }
        //Desenam bara care indica viata jucatorului si numarul de puncte obtinute
        canvas.drawRect(dWidth-200,30,dWidth-200+60*life,80,healthPaint);
        canvas.drawText(""+points,20,TEXT_SIZE,textPaint);
        handler.postDelayed(runnable,UPDATE_MILLIS);

    }

    //La apasarea pe exran
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Se obtin coordonaatele unde a fost apasat
        float touchX = event.getX();
        float touchY = event.getY();
        //Daca s-a apasat pe ghost
        if(touchY >= ghostY){
            int action = event.getAction();
            if(action == MotionEvent.ACTION_DOWN){
                oldX = event.getX();
                oldGhostX = ghostX;
            }
            if (action == MotionEvent.ACTION_MOVE){
                //Se misca ghost-ul
                float shift = oldX - touchX;
                float newGhostX = oldGhostX - shift;
                if(newGhostX <= 0){
                    ghostX = 0;
                }else if (newGhostX >= dWidth - ghost.getWidth()){
                    ghostX = dWidth - ghost.getWidth();
                }else{
                    ghostX = newGhostX;
                }
            }
        }
        return true;
    }
}
