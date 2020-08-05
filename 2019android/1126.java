package com.example.a1126;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public MyView vw;
    public MoveFunctions movefunc;
    static SoundPool sdPool;
    static int shoot, MyExpl, EnemExpl;
    Bitmap background, MyPlane, EnemyPlane, MyMissile, EnemyMissile;
    ArrayList<MyMiss> MyMiss = new ArrayList<MyMiss>();
    ArrayList<EnemyPlane> EnemyPl = new ArrayList<EnemyPlane>();
    ArrayList<EnemMiss> EnemMiss = new ArrayList<EnemMiss>();
    float BGY = -400;
    float myX, myY;
    int count = 0;
    int Enemycount = 0;
    int Misscount = 0;
    int score = 0;
    int deadflag = 1;
    Handler myMissHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(deadflag == 1){
                Missilegame();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Your Score is :" + score)
                    .setMessage("Do you want to continue?")
                    .setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deadflag = 1;
                        }
                    })
                    .setNegativeButton("아니오", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    public void Missilegame(){
        vw = new MyView(this);
        movefunc = new MoveFunctions();
        setContentView(vw);
        Resources res = getResources();
        sdPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        shoot = sdPool.load(this, R.raw.shooting, 1);
        EnemExpl = sdPool.load(this, R.raw.enemyexplode, 1);
        MyExpl = sdPool.load(this, R.raw.myexplode, 1);

        background = BitmapFactory.decodeResource(res, R.drawable.universe480x800);
        MyPlane = BitmapFactory.decodeResource(res, R.drawable.spaceship80x80);
        MyMissile = BitmapFactory.decodeResource(res, R.drawable.missile15x20);
        EnemyPlane = BitmapFactory.decodeResource(res, R.drawable.enemyship70x60);
        EnemyMissile = BitmapFactory.decodeResource(res, R.drawable.bullet12x48);
        EnemyPl.add(new EnemyPlane());
        myMissHandler = new Handler() {
            public void handleMessage(Message msg) {
                if(msg.what == 0){ //MISSILE MOVE
                    BGY++;
                    Enemycount++;
                    Misscount++;
                    if(BGY >= 1800){
                        BGY = 0;
                    } // 배경
                    movefunc.EnemyAdd();
                    movefunc.EnemyKill();
                    movefunc.PlayerKill();
                    movefunc.ShootMiss();
                    movefunc.EnemyMove();
                    movefunc.EnemyMiss();
                    vw.invalidate();
                    myMissHandler.sendEmptyMessageDelayed(0, 10);
                }
            }
        };
        myMissHandler.sendEmptyMessage(0);
    }

    public class MoveFunctions{
        //적 추가 및 한칸씩 땡기기
        public void EnemyAdd(){
            if(Enemycount >= 500){
                if(EnemyPl.size() != 0){
                    for(int k = 0; k < EnemyPl.size(); k++){
                        EnemyPl.get(k).Enemy_moveY();
                    }
                }
                EnemyPl.add(new EnemyPlane());
                Enemycount = 0;
            }
        }

        // 미사일이 적에 닿았는지 체크
        public void EnemyKill(){
            if(EnemyPl.size() > 0 && MyMiss.size() > 1) {
                for (int k = 0; k < EnemyPl.size(); k++) {
                    int shoot_flag =0;
                    for (int l = 0; l < MyMiss.size(); l++) {
                        if ((EnemyPl.get(k).X - 40 < MyMiss.get(l).X) && (EnemyPl.get(k).X + 40 > MyMiss.get(l).X) &&
                                (EnemyPl.get(k).Y - 30 < MyMiss.get(l).Y) && (EnemyPl.get(k).Y + 30 > MyMiss.get(l).Y)) {
                            EnemyPl.remove(k);
                            MyMiss.remove(l);
                            sdPool.play(EnemExpl, 1, 1, 0, 0, 1);
                            shoot_flag = 1;
                            score += 10;
                            break;
                        }
                    }
                    if(shoot_flag == 1){
                        break;
                    }
                }
            }
        }

        // 미사일이 나에 닿았는지 체크
        public void PlayerKill(){
            if(EnemMiss.size() > 0) {
                for (int k = 0; k < EnemMiss.size(); k++) {
                    if((EnemMiss.get(k).X - 20 < myX) && (EnemMiss.get(k).X + 20 > myX) &&
                            (EnemMiss.get(k).Y - 30 < myY) && (EnemMiss.get(k).Y + 30 > myY)){
                        deadflag = 0;
                        break;
                    }
                }
            }
        }

        // 미사일 쏘기
        public void ShootMiss(){
            if (MyMiss.size() != 0) {
                for (int i = 0; i < MyMiss.size(); i++) {
                    MyMiss.get(i).Miss_move();
                    if (MyMiss.get(i).Y < 0) {
                        MyMiss.remove(i);
                        break; //이거 없으면 remove된 미사일을 인식 못해서 죽을수도 있음
                    }
                }
            }
        }

        //적기 움직임
        public void EnemyMove(){
            if(EnemyPl.size() != 0){
                for(int j = 0; j < EnemyPl.size(); j++){
                    if(EnemyPl.get(j).X < 0){
                        EnemyPl.get(j).flag = 1;
                    }
                    else if(EnemyPl.get(j).X >= 700){
                        EnemyPl.get(j).flag = -1;
                    }
                    if(EnemyPl.get(j).Y >= 1200){
                        EnemyPl.remove(j);
                        break;
                    }
                    EnemyPl.get(j).Enemy_moveX();
                }
            }
        }

        //적기 미사일 쏘기
        public void EnemyMiss(){
            //미사일 추가
            if(EnemyPl.size() != 0 && (Misscount % 30 == 29)){
                for(int k = 0; k < EnemyPl.size(); k++){
                    if(EnemyPl.get(k).X <= myX) {
                        EnemMiss.add(new EnemMiss(EnemyPl.get(k).X, EnemyPl.get(k).Y, 1));
                        Misscount = 0;
                    }
                    else{
                        EnemMiss.add(new EnemMiss(EnemyPl.get(k).X, EnemyPl.get(k).Y, 0));
                        Misscount = 0;
                    }
                }
            }
            //미사일 삭제
            if(EnemMiss.size() > 0) {
                for (int i = 0; i < EnemMiss.size(); i++) {
                    EnemMiss.get(i).Miss_move();
                    if (EnemMiss.get(i).Y > 1200) {
                        EnemMiss.remove(i);
                        break;
                    }
                }
            }
        }
    }

    public class MyMiss {
        MyMiss(float x, float y) {
            X = x;
            Y = y;
        }
        float X, Y;
        public void Miss_move(){
            X += 0;
            Y -= 10;
        }
    }

    public class EnemyPlane{
        float X = 0;
        float Y = 0;
        int flag = 1;
        int n = new Random().nextInt(10) + 1;
        public void Enemy_moveX(){
            X += flag * n;
        }
        public void Enemy_moveY(){
            Y += 100;
        }
    }

    public class EnemMiss {
        EnemMiss(float x, float y, int flag) {
            X = x;
            Y = y;
            Flag = flag;
        }
        float X, Y;
        int Flag;
        public void Miss_move(){
            if(Flag == 1){
                X += 2;
                Y += 10;
            }
            else{
                X -= 2;
                Y += 10;
            }
        }
    }

    class MyView extends View {
        public MyView(Context context) {
            super(context);
        }

        public void onDraw(Canvas canvas) { //onDraw 함수는 시스템에 변화가 생겼을 때만 리로드된다
            canvas.drawColor(Color.BLACK);
            canvas.drawBitmap(background, 0, BGY, null); //(0, Y)
            canvas.drawBitmap(background, 0, BGY - 1800, null); //(0, Y+800)
            canvas.drawBitmap(MyPlane, myX - 80, myY - 80, null);
            if (MyMiss.size() != 0) {
                for (int i = 0; i < MyMiss.size(); i++) {
                    canvas.drawBitmap(MyMissile, MyMiss.get(i).X - 40, MyMiss.get(i).Y - 40, null);
                }
            }
            if(EnemyPl.size() != 0){
                for(int j = 0; j < EnemyPl.size(); j++){
                    canvas.drawBitmap(EnemyPlane, EnemyPl.get(j).X - 40, EnemyPl.get(j).Y - 40, null);
                }
            }
            if(EnemMiss.size() != 0){
                for(int k = 0; k < EnemMiss.size(); k++){
                    canvas.drawBitmap(EnemyMissile, EnemMiss.get(k).X - 40, EnemMiss.get(k).Y - 40, null);
                }
            }
        }

        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                myX = event.getX();
                myY = event.getY();
                MyMiss.add(new MyMiss(event.getX(), event.getY()));
                sdPool.play(shoot, 1, 1, 0, 0, 1);
                invalidate();
                return true;
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                count++;
                myX = event.getX();
                myY = event.getY();
                if(count % 10 == 0){
                    MyMiss.add(new MyMiss(event.getX(), event.getY()));
                    count = 0;
                }
                return true;
            }
            return false;
        }
    }
}