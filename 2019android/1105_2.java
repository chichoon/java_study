package com.example.a1105;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int mMainValue = 0;
    int mBackValue = 0;
    TextView mMainText;
    TextView mBackText;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainText = (TextView)findViewById(R.id.mainvalue);
        mBackText = (TextView)findViewById(R.id.backvalue);
        BackThread thread = new BackThread();
        thread.setDaemon(true);
        thread.start();
    }
    public void mOnClick(View v) {
        mMainValue++;
        mMainText.setText("MainValue : " + mMainValue);
    }
    class BackThread extends Thread {
        public void run() {
            while (true) {
                mBackValue++;
                mHandler.sendEmptyMessage(0);
                try { Thread.sleep(1000); } catch (InterruptedException e) {;}
            }
        }
    }
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) { //run 함수에서 empty message (what = 0) 을 수신받으면
            //동작하는 함수
            int second = mBackValue % 60;
            int minute = mBackValue / 60;
            int hour = mBackValue / 3600;
            if (msg.what == 0) { mBackText.setText("Time : " + hour + " : " + minute + " : " + second); }
        }
    };
}
