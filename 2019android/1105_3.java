package com.example.a1105;

        import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.view.View;
        import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int value=0;
    TextView mText;
    Handler mHandler;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mText=(TextView)findViewById(R.id.text);
        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                value++;
                int second = value % 60;
                int minute = value / 60;
                int hour = value / 3600;
                mText.setText("Time : " + hour + " : " + minute + " : " + second);
                //시간으로 바꿔보고 싶어서
                mHandler.sendEmptyMessageDelayed(5,1000);
                //1초 (1000밀리초) 에 한 번씩 메시지 (what = 0) 을 보냄
            }
        };
        mHandler.sendEmptyMessage(5);
        //시작을 위한 첫 메시지
        //해당 메시지가 보내진 후 Oncreate는 종료되며, HandleMessage만이 무한히 수행됨
    }
}
