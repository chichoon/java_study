package com.example.a1105;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Context;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.view.View;


public class MainActivity extends AppCompatActivity {
    public MyView vw; //**
    float mX1, mY1; //공의 좌표
    float mX2, mY2;
    float mX3, mY3;
    float mX4, mY4;
    Handler mHandler;
    Handler mHandler2;
    int dir1 = 1;
    int dir2 = 1;
    int dir3 = 1;
    int dir4 = 1;
    int colorR = 1;
    int colorG = 1;
    int colorB = 1;
    int mColor1 = 0xFFFF0101;
    int mColor2 = Color.RED;
    int mColor3 = Color.BLACK;
    int mColor4 = Color.GREEN;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vw = new MyView(this);
        setContentView(vw);
        /*
        mHandler2 = (Handler)handleMessage(msg){
            while(mColor1 <= 0xFFFFFF01){
                mColor1 += 0x00000500;
            }
            while(mColor1 >= 0xFF01FF01){
                mColor1 -= 0x00050000;
            }
            while(mColor1 <= 0xFF01FFFF){
                mColor1 += 0x00000005;
            }
            while(mColor1 >= 0xFF0101FF){
                mColor1 -= 0x00000500;
            }
            while(mColor1 <= 0xFFFF01FF){
                mColor1 += 0x00050000;
            }
            while(mColor1 >= 0xFFFF0101){
                mColor1 -= 0x00000005;
            }
            vw.invalidate();
            mHandler.sendEmptyMessageDelayed(0, 10);
        }
        */
        mHandler = new Handler(){
            public void handleMessage(Message msg){
                if(mColor1 >= 0xFFFFFF01){
                    colorR = -1;
                }
                if(mColor1 >= 0xFFFFFFFF){
                    colorG = -1;
                }
                else if (mColor1 < 0xFF000000){
                    colorB = 1;
                }
                mColor1 += colorR * 0x00050000;
                mColor1 += colorG * 0x00000500;
                mColor1 += colorB * 0x00000005;
                mX1 += dir1 * 20;
                mX2 += dir2 * 10;
                mX3 += dir3 * 5;
                mX4 += dir4 * 1;
                if(mX1 >= 800){
                    dir1 = -1;
                }
                else if (mX1 < 0){
                    dir1 = 1;
                }

                if(mX2 >=  800){
                    dir2 = -1;
                }
                else if (mX2 < 0){
                    dir2 = 1;
                }

                if(mX3 >= 800){
                    dir3 = -1;
                }
                else if (mX3 < 0){
                    dir3 = 1;
                }

                if(mX4 >= 800){
                    dir4 = -1;
                }
                else if (mX4 < 0){
                    dir4 = 1;
                }

                vw.invalidate();
                mHandler.sendEmptyMessageDelayed(0, 10);
            }
        };
        mHandler.sendEmptyMessage(0);
    }

    class MyView extends View {
        public MyView(Context context) {
            super(context);
            mX1 = 500;
            mY1 = 300;
            mX2 = 500;
            mY2 = 600;
            mX3 = 500;
            mY3 = 900;
            mX4 = 500;
            mY4 = 1200;
        }

        public void onDraw(Canvas canvas) {

            Paint Pnt1 = new Paint();
            Pnt1.setColor(mColor1);
            Pnt1.setAntiAlias(true);

            Paint Pnt2 = new Paint();
            Pnt2.setColor(mColor2);
            Pnt2.setAntiAlias(true);

            Paint Pnt3 = new Paint();
            Pnt3.setColor(mColor3);
            Pnt3.setAntiAlias(true);

            Paint Pnt4 = new Paint();
            Pnt4.setColor(mColor4);
            Pnt4.setAntiAlias(true);

            canvas.drawColor(Color.WHITE);
            canvas.drawCircle(mX1, mY1, 50, Pnt1);
            canvas.drawCircle(mX2, mY2, 16, Pnt2);
            canvas.drawCircle(mX3, mY3, 16, Pnt3);
            canvas.drawCircle(mX4, mY4, 16, Pnt4);
        }
    }
}
