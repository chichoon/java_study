package com.example.a1126;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity_ extends AppCompatActivity {
    public MyView vw;
    public MoveFunctions movefunc;
    static SoundPool sdPool;
    static int shoot, MyExpl, EnemExpl, MyHeal;
    Bitmap background, MyPlane, MyMissile, MyHP, BossMissile, item1, item2, MyMissPwUp;
    Bitmap me1, me2, enem1_1, enem1_2, enem2_1, enem2_2, enem3_1, enem3_2, enemmiss1, enemmiss2, enemmiss3;
    Bitmap Boss1_1, Boss1_2, Boss1_3, Boss1_4, Boss1_5, Boss2_1, Boss2_2, Boss2_3, Boss2_4, Boss2_5, Boss1_6, Boss2_6;
    Bitmap HP120, HP110, HP100, HP90, HP80, HP70, HP60, HP50, HP40, HP30, HP20, HP10, HP0;
    ArrayList<MyMiss> MyMiss = new ArrayList<MyMiss>();
    ArrayList<EnemyPlane> EnemyPl = new ArrayList<EnemyPlane>();
    ArrayList<BossPlane> BossPl = new ArrayList<BossPlane>();
    ArrayList<EnemMiss> EnemMiss = new ArrayList<EnemMiss>();
    ArrayList<BossMiss> BossMiss = new ArrayList<BossMiss>();
    ArrayList<item> Item = new ArrayList<item>();
    float BGY = -400;
    float myX, myY;
    int count = 0;
    int Enemycount = 0, spriteflag2 = 0, spriteflag3 = 0, spriteflag4 = 0,  boss_dead = 0;
    int score = 0;
    int stage = 1;
    int damageup = 1;
    int damagecnt = 0;
    int deathflag = 0;
    double HP = 1000;
    Handler myMissHandler;
    Intent inIntent = getIntent();
    int width;
    int height;
    int DPI;
    int px_w;
    static Vibrator vibe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        DPI = metrics.densityDpi;
        px_w = DPI / 160;

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        Missilegame();
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    public void Missilegame(){
        vw = new MyView(this);
        movefunc = new MoveFunctions();
        setContentView(vw);
        Resources res = getResources();

        sdPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        shoot = sdPool.load(this, R.raw.pop, 1);
        EnemExpl = sdPool.load(this, R.raw.expl, 1);
        MyExpl = sdPool.load(this, R.raw.expl, 1);
        MyHeal = sdPool.load(this, R.raw.heal, 1);

        item1 = BitmapFactory.decodeResource(res, R.drawable.hw_buffitem);
        item2 = BitmapFactory.decodeResource(res, R.drawable.hw_debuffitem);

        me1 = BitmapFactory.decodeResource(res, R.drawable.hw_me1);
        me2 = BitmapFactory.decodeResource(res, R.drawable.hw_me2);
        enem1_1 = BitmapFactory.decodeResource(res, R.drawable.hw_enem1_1);
        enem1_2 = BitmapFactory.decodeResource(res, R.drawable.hw_enem1_2);
        enem2_1 = BitmapFactory.decodeResource(res, R.drawable.hw_enem2_1);
        enem2_2 = BitmapFactory.decodeResource(res, R.drawable.hw_enem2_2);
        enem3_1 = BitmapFactory.decodeResource(res, R.drawable.hw_enem3_1);
        enem3_2 = BitmapFactory.decodeResource(res, R.drawable.hw_enem3_2);
        Boss1_1 = BitmapFactory.decodeResource(res, R.drawable.hw_boss1_1);
        Boss1_2 = BitmapFactory.decodeResource(res, R.drawable.hw_boss1_2);
        Boss1_3 = BitmapFactory.decodeResource(res, R.drawable.hw_boss1_3);
        Boss1_4 = BitmapFactory.decodeResource(res, R.drawable.hw_boss1_defeat1);
        Boss1_5 = BitmapFactory.decodeResource(res, R.drawable.hw_boss1_defeat2);
        Boss1_6 = BitmapFactory.decodeResource(res, R.drawable.hw_boss1_shoot);
        Boss2_1 = BitmapFactory.decodeResource(res, R.drawable.hw_boss2_1);
        Boss2_2 = BitmapFactory.decodeResource(res, R.drawable.hw_boss2_2);
        Boss2_3 = BitmapFactory.decodeResource(res, R.drawable.hw_boss2_3);
        Boss2_4 = BitmapFactory.decodeResource(res, R.drawable.hw_boss2_defeat1);
        Boss2_5 = BitmapFactory.decodeResource(res, R.drawable.hw_boss2_defeat2);
        Boss2_6 = BitmapFactory.decodeResource(res, R.drawable.hw_boss2_shoot);
        background = BitmapFactory.decodeResource(res, R.drawable.hw_bg);
        MyMissile = BitmapFactory.decodeResource(res, R.drawable.hw_mymiss);
        MyMissPwUp = BitmapFactory.decodeResource(res, R.drawable.hw_mymisspwup);
        BossMissile = BitmapFactory.decodeResource(res, R.drawable.bullet12x48);
        enemmiss1 = BitmapFactory.decodeResource(res, R.drawable.hw_enem1_miss);
        enemmiss2 = BitmapFactory.decodeResource(res, R.drawable.hw_enem2_miss);
        enemmiss3 = BitmapFactory.decodeResource(res, R.drawable.hw_enem3_miss);

        HP120 = BitmapFactory.decodeResource(res, R.drawable.hw_hp_120);
        HP110 = BitmapFactory.decodeResource(res, R.drawable.hw_hp_110);
        HP100 = BitmapFactory.decodeResource(res, R.drawable.hw_hp_100);
        HP90 = BitmapFactory.decodeResource(res, R.drawable.hw_hp_90);
        HP80 = BitmapFactory.decodeResource(res, R.drawable.hw_hp_80);
        HP70 = BitmapFactory.decodeResource(res, R.drawable.hw_hp_70);
        HP60 = BitmapFactory.decodeResource(res, R.drawable.hw_hp_60);
        HP50 = BitmapFactory.decodeResource(res, R.drawable.hw_hp_50);
        HP40 = BitmapFactory.decodeResource(res, R.drawable.hw_hp_40);
        HP30 = BitmapFactory.decodeResource(res, R.drawable.hw_hp_30);
        HP20 = BitmapFactory.decodeResource(res, R.drawable.hw_hp_20);
        HP10 = BitmapFactory.decodeResource(res, R.drawable.hw_hp_10);
        HP0 = BitmapFactory.decodeResource(res, R.drawable.hw_hp_0);

        MyHP = HP120;

        EnemyPl.add(new EnemyPlane());
        HP += 100;
        myMissHandler = new Handler() {
            public void handleMessage(Message msg) {
                if(msg.what == 0){ //MISSILE MOVE\
                    BGY += (2*stage);
                    Enemycount++;
                    if(BGY >= 800 * px_w){
                        BGY = 0;
                    } // 배경
                    if(Enemycount % 15 == 14){
                        if(spriteflag2 == 0){
                            spriteflag2 = 1;
                        }
                        else if(spriteflag2 == 1){
                            spriteflag2 = 0;
                        }
                        if(spriteflag3 == 0){
                            spriteflag3 = 1;
                        }
                        else if(spriteflag3 == 1){
                            spriteflag3 = 2;
                        }
                        else if(spriteflag3 == 2){
                            spriteflag3 = 3;
                        }
                        else if(spriteflag3 == 3){
                            spriteflag3 = 0;
                        }
                        if(spriteflag4 == 0 && boss_dead == 1){
                            spriteflag4 = 1;
                        }
                        else if(spriteflag4 == 1 && boss_dead == 1){
                            spriteflag4 = 2;
                        }
                        else if(spriteflag4 == 2 && boss_dead == 1){
                            spriteflag4 = 3;
                        }
                    }
                    movefunc.SpriteMove();
                    movefunc.EnemyAdd();
                    movefunc.EnemyKill();
                    movefunc.PlayerKill();
                    movefunc.ShootMiss();
                    movefunc.EnemyMove();
                    movefunc.EnemyMiss();
                    movefunc.BossAdd();
                    movefunc.BossHP();
                    movefunc.BossMiss();
                    movefunc.ItemMove();
                    vw.invalidate();
                    if(deathflag == 0) {
                        myMissHandler.sendEmptyMessageDelayed(0, 10);
                    }
                }
            }
        };
        myMissHandler.sendEmptyMessage(0);
    }

    public class MoveFunctions{
        //스프라이트 움직이기
        public void SpriteMove() {
            Resources res = getResources();
            switch (spriteflag2) {
                case 0:
                    MyPlane = me1;
                    if (EnemyPl.size() != 0) {
                        for (int k = 0; k < EnemyPl.size(); k++) {
                            switch (EnemyPl.get(k).sprite) {
                                case 1:
                                    EnemyPl.get(k).Image = enem1_1;
                                    break;
                                case 2:
                                    EnemyPl.get(k).Image = enem2_1;
                                    break;
                                case 3:
                                    EnemyPl.get(k).Image = enem3_1;
                                    break;
                            }
                        }
                    }
                    break;
                case 1:
                    MyPlane = me2;
                    if (EnemyPl.size() != 0) {
                        for (int k = 0; k < EnemyPl.size(); k++) {
                            switch (EnemyPl.get(k).sprite) {
                                case 1:
                                    EnemyPl.get(k).Image = enem1_2;
                                    break;
                                case 2:
                                    EnemyPl.get(k).Image = enem2_2;
                                    break;
                                case 3:
                                    EnemyPl.get(k).Image = enem3_2;
                                    break;
                            }
                        }
                    }
            }
            if(boss_dead == 0 && BossPl.size() != 0){
                if((BossPl.get(0).HP / (stage * 1000) * 100 <= 100) && (BossPl.get(0).HP / (stage * 1000) * 100 > 92)){
                    BossPl.get(0).HPImage = HP120;
                }
                else if((BossPl.get(0).HP / (stage * 1000) * 100 <= 92) && (BossPl.get(0).HP / (stage * 1000) * 100 > 84)){
                    BossPl.get(0).HPImage = HP110;
                }
                else if((BossPl.get(0).HP / (stage * 1000) * 100 <= 84) && (BossPl.get(0).HP / (stage * 1000) * 100 > 73)){
                    BossPl.get(0).HPImage = HP90;
                }
                else if((BossPl.get(0).HP / (stage * 1000) * 100 <= 73) && (BossPl.get(0).HP / (stage * 1000) * 100 > 65)){
                    BossPl.get(0).HPImage = HP80;
                }
                else if((BossPl.get(0).HP / (stage * 1000) * 100 <= 65) && (BossPl.get(0).HP / (stage * 1000) * 100 > 57)){
                    BossPl.get(0).HPImage = HP70;
                }
                else if((BossPl.get(0).HP / (stage * 1000) * 100 <= 57) && (BossPl.get(0).HP / (stage * 1000) * 100 > 49)){
                    BossPl.get(0).HPImage = HP60;
                }
                else if((BossPl.get(0).HP / (stage * 1000) * 100 <= 49) && (BossPl.get(0).HP / (stage * 1000) * 100 > 41)){
                    BossPl.get(0).HPImage = HP50;
                }
                else if((BossPl.get(0).HP / (stage * 1000) * 100 <= 41) && (BossPl.get(0).HP / (stage * 1000) * 100 > 33)){
                    BossPl.get(0).HPImage = HP40;
                }
                else if((BossPl.get(0).HP / (stage * 1000) * 100 <= 33) && (BossPl.get(0).HP / (stage * 1000) * 100 > 25)){
                    BossPl.get(0).HPImage = HP30;
                }
                else if((BossPl.get(0).HP / (stage * 1000) * 100 <= 25) && (BossPl.get(0).HP / (stage * 1000) * 100 > 17)){
                    BossPl.get(0).HPImage = HP20;
                }
                else if((BossPl.get(0).HP / (stage * 1000) * 100 <= 17) && (BossPl.get(0).HP / (stage * 1000) * 100 > 9)){
                    BossPl.get(0).HPImage = HP10;
                }
                else if((BossPl.get(0).HP / (stage * 1000) * 100 <= 9)){
                    BossPl.get(0).HPImage = HP0;
                }
                switch(spriteflag3){
                    case 0:
                        switch(BossPl.get(0).sprite) {
                            case 0:
                                BossPl.get(0).Image = Boss1_1;
                                break;
                            case 1:
                                BossPl.get(0).Image = Boss2_1;
                                break;
                            }
                        break;
                    case 1:
                    case 3:
                        switch(BossPl.get(0).sprite){
                            case 0:
                                BossPl.get(0).Image = Boss1_2;
                                break;
                            case 1:
                                BossPl.get(0).Image = Boss2_2;
                                break;
                            }
                        break;
                    case 2:
                        switch(BossPl.get(0).sprite){
                            case 0:
                                BossPl.get(0).Image = Boss1_3;
                                break;
                            case 1:
                                BossPl.get(0).Image = Boss2_3;
                                break;
                        }
                        break;
                }
            }
            else if(boss_dead == 1 && BossPl.size() != 0){
                switch(spriteflag4){
                    case 0:
                        switch(BossPl.get(0).sprite){
                            case 0:
                                BossPl.get(0).Image = Boss1_6;
                                break;
                            case 1:
                                BossPl.get(0).Image =  Boss2_6;
                                break;
                            }
                    case 1:
                        switch(BossPl.get(0).sprite){
                            case 0:
                                BossPl.get(0).Image =  Boss1_4;
                                break;
                            case 1:
                                BossPl.get(0).Image =  Boss2_4;
                                break;
                            }
                        break;
                    case 2:
                        switch(BossPl.get(0).sprite){
                            case 0:
                                BossPl.get(0).Image =  Boss1_5;
                                break;
                            case 1:
                                BossPl.get(0).Image =  Boss2_5;
                                break;
                        }
                        break;
                    case 3:
                        switch(BossPl.get(0).sprite){
                            case 0:
                                BossPl.get(0).Image =  Boss1_5;
                                boss_dead = 2;
                                break;
                            case 1:
                                BossPl.get(0).Image =  Boss2_5;
                                boss_dead = 2;
                                break;
                        }
                        break;
                }
            }
            if((HP / (1000 * stage) * 100 <= 100) && (HP / (1000 * stage) * 100 > 92)){
                MyHP = HP120;
            }
            else if((HP / (1000 * stage)* 100 <= 92) && (HP / (1000 * stage) * 100 > 84)){
                MyHP = HP110;
            }
            else if((HP / (1000 * stage) * 100 <= 84) && (HP / (1000 * stage) * 100 > 73)){
                MyHP = HP90;
            }
            else if((HP / (1000 * stage) * 100 <= 73) && (HP / (1000 * stage) * 100 > 65)){
                MyHP = HP80;
            }
            else if((HP / (1000 * stage) * 100 <= 65) && (HP / (1000 * stage) * 100 > 57)){
                MyHP = HP70;
            }
            else if((HP / (1000 * stage) * 100 <= 57) && (HP / (1000 * stage) * 100 > 49)){
                MyHP = HP60;
            }
            else if((HP / (1000 * stage)* 100 <= 49) && (HP /(1000 * stage) * 100 > 41)){
                MyHP = HP50;
            }
            else if((HP / (1000 * stage) * 100 <= 41) && (HP / (1000 * stage) * 100 > 33)){
                MyHP = HP40;
            }
            else if((HP/ (1000 * stage) * 100 <= 33) && (HP / (1000 * stage) * 100 > 25)){
                MyHP = HP30;
            }
            else if((HP / (1000 * stage) * 100 <= 25) && (HP / (1000 * stage)* 100 > 17)){
                MyHP = HP20;
            }
            else if((HP / (1000 * stage) * 100 <= 17) && (HP / (1000 * stage) * 100 > 9)){
                MyHP = HP10;
            }
            else if((HP / (1000 * stage) * 100 <= 9)){
                MyHP = HP0;
            }
        }

        //적 추가 및 한칸씩 땡기기
        public void EnemyAdd(){
            if(stage < 6){
                if(Enemycount % (150 - (stage * 10)) == 149 - (stage * 10)){
                        for(int i = 0; i < stage; i++) {
                            EnemyPl.add(new EnemyPlane());
                            Enemycount = 0;
                        }
                }
            }
            else{
                    if(Enemycount % 90 == 89) {
                        for(int i = 0; i < 5; i++) {
                            EnemyPl.add(new EnemyPlane());
                            Enemycount = 0;
                        }
                    }
                }
            if(EnemyPl.size() != 0 && Enemycount == 50){
                for(int k = 0; k < EnemyPl.size(); k++){
                    EnemyPl.get(k).Enemy_moveY();
                }
            }
        }

        //보스 추가
        public void BossAdd(){
            if(score >= Math.pow(stage, 2) * 100 && BossPl.size() == 0){
                BossPl.add(new BossPlane());
            }
        }

        //보스 체력
        public void BossHP(){
            if(BossPl.size() != 0){
                if(MyMiss.size() != 0) {
                    for (int i = 0; i < MyMiss.size(); i++) {
                        if ((BossPl.get(0).X - (66 * px_w) < MyMiss.get(i).X) && (BossPl.get(0).X + (66 * px_w) > MyMiss.get(i).X) &&
                                (BossPl.get(0).Y - (66 * px_w) < MyMiss.get(i).Y) && (BossPl.get(0).Y + (66 * px_w) > MyMiss.get(i).Y)) {
                            BossPl.get(0).HP -= (MyMiss.get(i).damage);
                            vibe.vibrate(20);
                            MyMiss.remove(i);
                            break;
                        }
                        if (BossPl.get(0).HP < 0) {
                            boss_dead = 1;
                            break;
                        }
                    }
                }
                if(boss_dead == 2){
                    BossPl.remove(0);
                    sdPool.play(EnemExpl, 1, 1, 0, 0, 1);
                    vibe.vibrate(100);
                    boss_dead = 0;
                    stage++;
                    HP = 1000 * stage;
                }
            }
        }

        //보스 미사일발사
        public void BossMiss(){
            //미사일 추가
            if(stage <= 10){
                if(boss_dead == 0 && BossPl.size() != 0 && (Enemycount % (30 - stage) == (29 - stage))){
                    BossMiss.add(new BossMiss(BossPl.get(0).X, BossPl.get(0).Y));
                }
            }
            else if(stage > 10){
                if(boss_dead == 0 && BossPl.size() != 0 && (Enemycount % 20 == 19)){
                    BossMiss.add(new BossMiss(BossPl.get(0).X, BossPl.get(0).Y));
                }
            }
            //미사일 삭제
            if(BossMiss.size() > 0) {
                for (int i = 0; i < BossMiss.size(); i++) {
                    if (stage <= 10) {
                        BossMiss.get(i).Miss_move10();
                        if (BossMiss.get(i).Y2 > height) {
                            BossMiss.remove(i);
                            break;
                        }
                    }
                    else if (stage > 10) {
                        BossMiss.get(i).Miss_move20();
                        if (BossMiss.get(i).Y3 > height) {
                            BossMiss.remove(i);
                            break;
                        }
                    }
                    else if (boss_dead == 1){
                        BossMiss.remove(i);
                        break;
                    }
                }
            }
        }

        // 미사일이 적에 닿았는지 체크
        public void EnemyKill(){
            if(EnemyPl.size() > 0 && MyMiss.size() > 1) {
                for (int k = 0; k < EnemyPl.size(); k++) {
                    int shoot_flag =0;
                    for (int l = 0; l < MyMiss.size(); l++) {
                        if ((EnemyPl.get(k).X - (32 * px_w) < MyMiss.get(l).X) && (EnemyPl.get(k).X + (32 * px_w) > MyMiss.get(l).X) &&
                                (EnemyPl.get(k).Y - (32 * px_w) < MyMiss.get(l).Y - (9 * px_w)) && (EnemyPl.get(k).Y + (32 * px_w) > MyMiss.get(l).Y - (9 * px_w))) {
                            EnemyPl.remove(k);
                            MyMiss.remove(l);
                            vibe.vibrate(50);
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

        // 미사일 및 적이 나에 닿았는지 체크
        public void PlayerKill(){
            if(EnemMiss.size() > 0) {
                for (int k = 0; k < EnemMiss.size(); k++) {
                    if((myX + (25 * px_w) >= EnemMiss.get(k).X) && (myX - (25 * px_w) <= EnemMiss.get(k).X) &&
                            (myY + (25 * px_w) >= EnemMiss.get(k).Y) && (myY - (25 * px_w) <= EnemMiss.get(k).Y)){
                        HP -= EnemMiss.get(k).damage;
                        vibe.vibrate(50);
                        EnemMiss.remove(k);
                        break;
                    }
                }
            }
            if(EnemyPl.size() > 0){
                for (int k = 0; k < EnemyPl.size(); k++) {
                    if((myX + (25 * px_w) >= EnemyPl.get(k).X) && (myX - (25 * px_w) <= EnemyPl.get(k).X) &&
                            (myY + (25 * px_w) >= EnemyPl.get(k).Y) && (myY - (25 * px_w) <= EnemyPl.get(k).Y)){
                        HP -= 100 * stage;
                        vibe.vibrate(50);
                        EnemyPl.remove(k);
                        break;
                    }
                }
            }
            if(BossMiss.size() > 0){
                if(stage <= 10) {
                    for (int k = 0; k < BossMiss.size(); k++) {
                        if (((myX + (25 * px_w) >= BossMiss.get(k).X1) && (myX - (25 * px_w) <= BossMiss.get(k).X1) &&
                                (myY + (25 * px_w) >= BossMiss.get(k).Y1 + (24 * px_w)) && (myY - (25 * px_w) <= BossMiss.get(k).Y1+ (24 * px_w))) ||
                                ((myX + (25 * px_w) >= BossMiss.get(k).X2) && (myX - (25 * px_w) <= BossMiss.get(k).X2) &&
                                        (myY + (25 * px_w) >= BossMiss.get(k).Y2+ (24 * px_w)) && (myY - (25 * px_w) <= BossMiss.get(k).Y2+ (24 * px_w))) ||
                                ((myX + (25 * px_w) >= BossMiss.get(k).X3) && (myX - (25 * px_w) <= BossMiss.get(k).X3) &&
                                        (myY + (25 * px_w) >= BossMiss.get(k).Y3+ (24 * px_w)) && (myY - (25 * px_w) <= BossMiss.get(k).Y3+ (24 * px_w)))) {
                            HP -= 50 + (30 * stage);
                            vibe.vibrate(50);
                            BossMiss.remove(k);
                            break;
                        }
                    }
                }
                else if(stage > 10) {
                    for (int k = 0; k < BossMiss.size(); k++) {
                        if (((myX + (25 * px_w) >= BossMiss.get(k).X1) && (myX - (25 * px_w) <= BossMiss.get(k).X1 ) &&
                                (myY + (25 * px_w) >= BossMiss.get(k).Y1+ (24 * px_w)) && (myY - (25 * px_w) <= BossMiss.get(k).Y1+ (24 * px_w))) ||
                                ((myX + (25 * px_w) >= BossMiss.get(k).X2) && (myX - (25 * px_w) <= BossMiss.get(k).X2 ) &&
                                        (myY + (25 * px_w) >= BossMiss.get(k).Y2+ (24 * px_w) ) && (myY - (25 * px_w) <= BossMiss.get(k).Y2+ (24 * px_w))) ||
                                ((myX + (25 * px_w) >= BossMiss.get(k).X3 ) && (myX - (25 * px_w) <= BossMiss.get(k).X3 ) &&
                                        (myY + (25 * px_w) >= BossMiss.get(k).Y3+ (24 * px_w)) && (myY - (25 * px_w) <= BossMiss.get(k).Y3+ (24 * px_w))) ||
                                ((myX + (25 * px_w) >= BossMiss.get(k).X11 ) && (myX - (25 * px_w) <= BossMiss.get(k).X11 ) &&
                                        (myY + (25 * px_w) >= BossMiss.get(k).Y11+ (24 * px_w)) && (myY - (25 * px_w) <= BossMiss.get(k).Y11+ (24 * px_w))) ||
                                ((myX + (25 * px_w) >= BossMiss.get(k).X22 ) && (myX - (25 * px_w) <= BossMiss.get(k).X22 ) &&
                                        (myY + (25 * px_w) >= BossMiss.get(k).Y22+ (24 * px_w)) && (myY - (25 * px_w) <= BossMiss.get(k).Y22+ (24 * px_w))) ||
                                ((myX + (25 * px_w) >= BossMiss.get(k).X33 ) && (myX - (25 * px_w) <= BossMiss.get(k).X33 ) &&
                                        (myY + (25 * px_w) >= BossMiss.get(k).Y33+ (24 * px_w)) && (myY - (25 * px_w) <= BossMiss.get(k).Y33+ (24 * px_w)))){
                            HP -= 50 + (30 * stage);
                            vibe.vibrate(50);
                            BossMiss.remove(k);
                            break;
                        }
                    }
                }
            }
            if(HP < 0){
                sdPool.play(MyExpl, 1, 1, 0, 0, 1);
                vibe.vibrate(1000);
                deathflag = 1;
                Intent outIntent = new Intent(getApplicationContext(), MainActivity.class);
                outIntent.putExtra("score", score);
                setResult(RESULT_OK, outIntent);
                finish();
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
                    else if(EnemyPl.get(j).X >= width){
                        EnemyPl.get(j).flag = -1;
                    }
                    if(EnemyPl.get(j).Y >= height){
                        EnemyPl.remove(j);
                        break;
                    }
                    EnemyPl.get(j).Enemy_moveX();
                }
            }
            if(BossPl.size() != 0){
                if(BossPl.get(0).X < 0){
                    BossPl.get(0).flag = 1;
                }
                else if(BossPl.get(0).X >= width){
                    BossPl.get(0).flag = -1;
                }
                if(stage < 10){
                    BossPl.get(0).X += (BossPl.get(0).flag * stage);
                }
                else{
                    BossPl.get(0).X += (BossPl.get(0).flag * 10);
                }
            }
        }

        //적기 미사일 쏘기
        public void EnemyMiss(){
            //미사일 추가
            if(stage <= 20){
                if(EnemyPl.size() != 0 && (Enemycount % (25 - stage) == (24 - stage))){
                    for(int k = 0; k < EnemyPl.size(); k++){
                        if(EnemyPl.get(k).X <= myX) {
                            EnemMiss.add(new EnemMiss(EnemyPl.get(k).X, EnemyPl.get(k).Y, 1, EnemyPl.get(k).sprite));
                        }
                        else{
                            EnemMiss.add(new EnemMiss(EnemyPl.get(k).X, EnemyPl.get(k).Y, 0, EnemyPl.get(k).sprite));
                        }
                    }
                }
            }
            else if(stage > 20){
                if(EnemyPl.size() != 0 && (Enemycount % 5 == 4)){
                    for(int k = 0; k < EnemyPl.size(); k++){
                        if(EnemyPl.get(k).X <= myX) {
                            EnemMiss.add(new EnemMiss(EnemyPl.get(k).X, EnemyPl.get(k).Y, 1, EnemyPl.get(k).sprite));
                        }
                        else{
                            EnemMiss.add(new EnemMiss(EnemyPl.get(k).X, EnemyPl.get(k).Y, 0, EnemyPl.get(k).sprite));
                        }
                    }
                }
            }
            //미사일 삭제
            if(EnemMiss.size() > 0) {
                for (int i = 0; i < EnemMiss.size(); i++) {
                    EnemMiss.get(i).Miss_move();
                    if (EnemMiss.get(i).Y > height) {
                        EnemMiss.remove(i);
                        break;
                    }
                }
            }
        }

        //아이템 생성 및 움직이기
        public void ItemMove(){
            int n = new Random().nextInt() % 250;
            if(n == 1){
                Item.add(new item());
            }
            if(Item.size() != 0){
                for(int i = 0; i < Item.size(); i++){
                    Item.get(i).Y += 10;
                    if(Item.get(i).Y >= height){
                        Item.remove(i);
                        break;
                    }
                    if((myX + (35 * px_w) >= Item.get(i).X) && (myX - (30 * px_w) <=Item.get(i).X) &&
                            (myY + (35 * px_w) >= Item.get(i).Y) && (myY - (30 * px_w) <= Item.get(i).Y)){
                        sdPool.play(MyHeal, 1, 1, 0, 0, 1);
                        switch(Item.get(i).state){
                            case 0:
                                if(HP < 1000 * stage - 150) {
                                    HP += 150;
                                    Item.remove(i);
                                    break;
                                }
                                else{
                                    HP = 1000 * stage;
                                    Item.remove(i);
                                    break;
                                }
                            case 1:
                                damageup = 2;
                                Item.remove(i);
                                break;
                        }
                    }
                }
            }
        }
    }

    public class MyMiss {
        MyMiss(float x, float y) {
            X = x;
            Y = y;
            if(damageup == 2){
                Image = MyMissPwUp;
            }
            else{
                Image = MyMissile;
            }
        }
        float X, Y;
        Bitmap Image;
        int weight = new Random().nextInt(30) + 40;
        double damage = weight * (stage * 0.5) * damageup;
        public void Miss_move(){
                X += 0;
                Y -= 10;
        }
    }

    public class item{
        item(){
            X = new Random().nextInt(width - 5) + 5;
            Y = 0;
            int a = new Random().nextInt(10);
            if(BossPl.size() != 0){
                if(a < 5){
                    state = 0;
                }
                else{
                    state = 1;
                }
            }
            else{
                if(a < 2){
                    state = 1;
                }
                else{
                    state = 0;
                }
            }
            switch(state){
                case 0:
                    Image = item1;
                    break;
                case 1:
                    Image = item2;
                    break;
            }
        }
        float Y;
        float X;
        int state;
        Bitmap Image;
    }

    public class EnemyPlane{
        EnemyPlane(){
            Resources res = getResources();
            switch(sprite){
                case 1:
                    Image = enem1_1;
                case 2:
                    Image = enem2_1;
                case 3:
                    Image = enem3_1;
            }
        }
        float X = new Random().nextInt(width) - 5;
        float Y = 0;
        int flag = 1;
        int n = new Random().nextInt(10) + 5;
        int sprite = new Random().nextInt(3) + 1;
        Bitmap Image;
        public void Enemy_moveX(){
            X += flag * n;
        }
        public void Enemy_moveY(){
            Y += 100;
        }
    }

    public class BossPlane {
        BossPlane() {
            Resources res = getResources();
            switch (sprite) {
                case 0:
                    Image =  Boss1_1;
                case 1:
                    Image =  Boss2_1;
            }
            HPImage = HP120;
        }
        float X = width / 2;
        float Y = 0;
        int flag = 1;
        double HP = stage * 1500;
        int sprite = stage % 2;
        Bitmap Image;
        Bitmap HPImage;
    }

    public class EnemMiss {
        EnemMiss(float x, float y, int flag, int sprite) {
            Resources res = getResources();
            X = x;
            Y = y;
            Flag = flag;
            switch(sprite){
                case 1:
                    Image = enemmiss1;
                    break;
                case 2:
                    Image = enemmiss2;
                    break;
                case 3:
                    Image = enemmiss3;
                    break;
            }
            if(stage < 20){
                damage = new Random().nextInt(30) + (stage * 15);
            }
            else if(stage >= 20){
                damage = new Random().nextInt(50) + 350;
            }
        }
        float X, Y;
        int Flag;
        int damage;
        Bitmap Image;
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

    public class BossMiss{
        BossMiss(float x, float y) {
            X1 = x;
            X2 = x;
            X3 = x;
            Y1 = y;
            Y2 = y;
            Y3 = y;
            X11 = x;
            X22 = x;
            X33 = x;
            Y11 = y;
            Y22 = y;
            Y33 = y;
            if(stage < 20){
                damage = new Random().nextInt(20) + (stage * 20);
            }
            else if(stage >= 20){
                damage = new Random().nextInt(50) + 500;
            }
        }
        float X1, X2, X3, Y1, Y2, Y3;
        float X11, X22, X33, Y11, Y22, Y33;
        int damage;
        public void Miss_move10(){
            X1 += 4;
            X3 -= 4;
            Y1 += (10 + 2* stage);
            Y2 += (10 + 2*stage);
            Y3 += (10 + 2*stage);
        }
        public void Miss_move20(){
            X1 += 7;
            X2 += 4;
            X3 += 1;
            X11 -= 1;
            X22 -= 4;
            X33 -= 7;
            Y1 += (3 + 2 * stage);
            Y2 += (6 + 2 * stage);
            Y3 += (10 + 2 * stage);
            Y11 += (10 + 2 * stage);
            Y22 += (6 + 2 * stage);
            Y33 += (3 + 2 * stage);
        }
    }

    class MyView extends View {
        public MyView(Context context) {
            super(context);
        }

        public void onDraw(Canvas canvas) { //onDraw 함수는 시스템에 변화가 생겼을 때만 리로드된다
            Paint text = new Paint();
            text.setAntiAlias(true);
            text.setTextSize(50);
            text.setColor(Color.BLACK);
            Typeface t = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL);
            text.setTypeface(t);

            canvas.drawColor(0xFFFF9C5A);
            canvas.drawBitmap(background, 0, BGY, null); //(0, Y)
            canvas.drawBitmap(background, 0, BGY - (800 * px_w), null); //(0, Y+800)
            canvas.drawBitmap(MyPlane, myX - (32 * px_w), myY - (32 * px_w), null);
            canvas.drawBitmap(MyHP, myX - (60 * px_w), myY - (32 * px_w) - 20, null);
            canvas.drawText("HP : " + HP, myX - (60 * px_w), myY - (32 * px_w) - 40, text);
            canvas.drawText("Score : " + score, 0, 50, text);
            canvas.drawText("Stage : " + stage, 550, 50, text);

            if(EnemyPl.size() != 0){
                for(int j = 0; j < EnemyPl.size(); j++){
                    canvas.drawBitmap(EnemyPl.get(j).Image, EnemyPl.get(j).X - (32 * px_w), EnemyPl.get(j).Y - (32 * px_w), null);
                }
            }
            if(EnemMiss.size() != 0){
                for(int k = 0; k < EnemMiss.size(); k++){
                    canvas.drawBitmap(EnemMiss.get(k).Image, EnemMiss.get(k).X - (9 * px_w), EnemMiss.get(k).Y - (9 * px_w), null);
                }
            }
            if(BossPl.size() != 0){
                canvas.drawBitmap(BossPl.get(0).Image, BossPl.get(0).X - (66 * px_w), BossPl.get(0).Y, null);
                canvas.drawBitmap(BossPl.get(0).HPImage, BossPl.get(0).X - (60 * px_w), BossPl.get(0).Y, null);
                if(BossMiss.size() != 0){
                    if(stage <= 10){
                        for(int i = 0; i < BossMiss.size(); i++){
                            canvas.drawBitmap(BossMissile, BossMiss.get(i).X1 - (6 *px_w), BossMiss.get(i).Y1 + (24 * px_w), null);
                            canvas.drawBitmap(BossMissile, BossMiss.get(i).X2 - (6 *px_w), BossMiss.get(i).Y2 + (24 * px_w), null);
                            canvas.drawBitmap(BossMissile, BossMiss.get(i).X3 - (6 *px_w), BossMiss.get(i).Y3 + (24 * px_w), null);
                        }
                    }
                    else if(stage > 10){
                        for(int i = 0; i < BossMiss.size(); i++) {
                            canvas.drawBitmap(BossMissile, BossMiss.get(i).X1 - (6 *px_w), BossMiss.get(i).Y1 + (24 * px_w), null);
                            canvas.drawBitmap(BossMissile, BossMiss.get(i).X2 - (6 *px_w), BossMiss.get(i).Y2 + (24 * px_w), null);
                            canvas.drawBitmap(BossMissile, BossMiss.get(i).X3 - (6 *px_w), BossMiss.get(i).Y3 + (24 * px_w), null);
                            canvas.drawBitmap(BossMissile, BossMiss.get(i).X11 - (6 *px_w), BossMiss.get(i).Y11 + (24 * px_w), null);
                            canvas.drawBitmap(BossMissile, BossMiss.get(i).X22 - (6 *px_w), BossMiss.get(i).Y22 + (24 * px_w), null);
                            canvas.drawBitmap(BossMissile, BossMiss.get(i).X33 - (6 *px_w), BossMiss.get(i).Y33 + (24 * px_w), null);
                        }
                    }
                }
            }
            if (MyMiss.size() != 0) {
                for (int i = 0; i < MyMiss.size(); i++) {
                    canvas.drawBitmap(MyMiss.get(i).Image, MyMiss.get(i).X - (9 * px_w), MyMiss.get(i).Y - (9 * px_w), null);
                }
            }
            if(Item.size() != 0){
                for(int i = 0; i < Item.size(); i++){
                    canvas.drawBitmap(Item.get(i).Image, Item.get(i).X - (10 * px_w), Item.get(i).Y - (10 * px_w), null);
                }
            }
        }

        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if(event.getY() >= height / 2){
                    myY = event.getY();
                }
                sdPool.play(shoot, 1, 1, 0, 0, 1);
                MyMiss.add(new MyMiss(myX, myY));
                myX = event.getX();
                damagecnt++;
                if(damagecnt > 30){
                    damagecnt = 0;
                    damageup = 1;
                }
                invalidate();
                return true;
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                count++;
                if(event.getY() >= height / 2){
                    myY = event.getY();
                }
                myX = event.getX();
                if(count % 20 == 0){
                    MyMiss.add(new MyMiss(event.getX(), event.getY()));
                    sdPool.play(shoot, 1, 1, 0, 0, 1);
                    damagecnt++;
                    if(damagecnt > 30){
                        damagecnt = 0;
                        damageup = 1;
                    }
                    count = 0;
                }
                return true;
            }
            return false;
        }
    }
}
