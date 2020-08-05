package com.example.a1105;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
        mMainText = (TextView)findViewById(R.id.mainvalue); //xml에서 mainvalue 텍스트뷰 찾기
        mBackText = (TextView)findViewById(R.id.backvalue); //xml에서 backvalue 텍스트뷰 찾기
        BackThread thread = new BackThread(); //클래스 BackThread 새롭게 생성
        thread.setDaemon(true); //해당 Thread 실행 대기시키기
        thread.start(); //해당 Thread 실행
        //start되는 순간 thread 클래스 내의 run() 을 구동하며,
        //run 내의 while(true)에 의해 while문 내의 line이 무한히 루프한다
        //Main문과는 별개로 병렬적으로 동시에 수행됨
    }
    public void mOnClick(View v) { //버튼을 클릭할 때마다 수행되는 함수
        //xml파일 내에서, 버튼이 onclick을 불러올 때 해당 함수를 불러오기 때문에
        //android:onClick="mOnClick"
        //실제로 버튼을 누르면 함수 내의 라인이 수행된다
        mMainValue++; //mainvalue 변수를 하나씩 증가시킨다
        mMainText.setText("MainValue : " + mMainValue); //Mainvalue를 갱신시킨다
        mBackText.setText("BackValue : " + mBackValue); //Backvalue를 갱신시킨다
    }
    class BackThread extends Thread { //thread 클래스를 상속받는 Backthread 클래스
        public void run() { //메인문에서 thread.start되면 수행시작하는 함수
            while (true) { //Thread start 후에 무한히 반복한다
                mBackValue++; //Backvalue가 1초에 한 번씩 증가한다
                mBackText.setText("BackValue : " + mBackValue); //1초에 한번 씩 text도 변경된다
                //이때 thread에서  메인 GUI를 직접 건드리는 것이므로
                //조금 더 복잡한 구조에서는 오류가 날 수 있음
                //thread는 GUI를 건드릴 수 없다 (핸들러는 가능)
                try { Thread.sleep(1000); } catch (InterruptedException e) {;} //1초 대기
                //try & catch는 오류가 나는 것을 대비하여 추가하는 것
            }
        }
    }
}