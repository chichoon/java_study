package com.example.a1001;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public MyView vw;
    public Button bt_up, bt_left, bt_center, bt_right, bt_down, bt_show;
    public EditText edit_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vw = findViewById(R.id.MyViewWidget);
        vw.setFocusable(true);
        vw.setFocusableInTouchMode(true);
        vw.draw_flag = -1;
        bt_up = findViewById(R.id.UpButton);
        bt_left = findViewById(R.id.LeftButton);
        bt_center = findViewById(R.id.CenterButton);
        bt_right = findViewById(R.id.RightButton);
        bt_down = findViewById(R.id.DownButton);
        bt_show = findViewById(R.id.ShowButton);
        edit_text = findViewById(R.id.EditTextWidget);
        bt_up.setTextColor(Color.BLUE);
        bt_left.setTextColor(Color.GREEN);
        bt_center.setTextColor(Color.BLACK);
        bt_right.setTextColor(Color.YELLOW);
        bt_down.setTextColor(Color.RED);
        bt_show.setTextColor(Color.WHITE);
        bt_show.setBackgroundColor(Color.BLACK);
        bt_up.setOnClickListener(this);
        bt_left.setOnClickListener(this);
        bt_center.setOnClickListener(this);
        bt_right.setOnClickListener(this);
        bt_down.setOnClickListener(this);
        bt_show.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.UpButton:
                if (vw.mY > 25) vw.mY -= 5;
                vw.invalidate();
                break;
            case R.id.LeftButton:
                if (vw.mX > 25) vw.mX -= 5;
                vw.invalidate();
                break;
            case R.id.CenterButton:
                if (vw.mColor == Color.BLUE) {
                    vw.mColor = Color.RED;
                } else {
                    vw.mColor = Color.BLUE;
                }
                ;
                vw.mX = 250;
                vw.mY = 250;
                vw.invalidate();
                break;
            case R.id.RightButton:
                if (vw.mX < 500 - 35) vw.mX += 5;
                vw.invalidate();
                break;
            case R.id.DownButton:
                if (vw.mY < 500 - 25) vw.mY += 5;
                vw.invalidate();
                break;
            case R.id.ShowButton:
                vw.mytext = edit_text.getText();
                vw.draw_flag = vw.draw_flag * (-1);
                vw.invalidate();
                break;
        }
    }
}

    class MyView extends View {
        float mX, mY;
        int mColor;
        Paint Pnt_Green, Pnt_Black;
        int draw_flag = 0;
        Editable mytext;

        public MyView(Context context, AttributeSet attrs) {
            super(context, attrs);
            mX = 250;
            mY = 250;
            mColor = Color.BLUE;
            Pnt_Green = new Paint();
            Pnt_Green.setColor(Color.GREEN);
            Pnt_Green.setAntiAlias(true);
            Pnt_Black = new Paint();
            Pnt_Black.setColor(Color.BLACK);
            Pnt_Black.setAntiAlias(true);
            Pnt_Black.setTextSize(30);
        }

        public void onDraw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);
            Paint Pnt = new Paint();
            if (draw_flag == 1) {
                canvas.drawRect(50, 20, 100, 70, Pnt_Green);
                canvas.drawText(mytext.toString(), 50, 100, Pnt_Black);
            }
            Pnt.setColor(mColor);
            Pnt.setAntiAlias(true);
            canvas.drawCircle(mX, mY, 16, Pnt);
        }
    }