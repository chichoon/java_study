package com.example.a1008;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int cpu, me;
    Random random = new Random();
    int wincount, losecount, trycount;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button rock = findViewById(R.id.rock);
        Button scissor = findViewById(R.id.scissor);
        Button paper = findViewById(R.id.paper);
        Button reset = findViewById(R.id.reset);
        final ImageView cpu_view = findViewById(R.id.cpu_view);
        final ImageView me_view = findViewById(R.id.me_view);
        final TextView N_WIN = findViewById(R.id.nwin);
        final TextView N_LOSE = findViewById(R.id.nlose);
        final TextView N_TRY = findViewById(R.id.ntry);
        final TextView WIN_LOSE = findViewById(R.id.winlose);


        final RSP rsp = new RSP();
        rock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                me = 0; //me = rock
                cpu = random.nextInt(3);
                rsp.fight(me, cpu);
                N_LOSE.setText(String.valueOf(losecount));
                N_WIN.setText(String.valueOf(wincount));
                N_TRY.setText(String.valueOf(trycount));
                WIN_LOSE.setText(str);

                me_view.setImageResource(R.drawable.rock);

                if(cpu == 0){
                    cpu_view.setImageResource(R.drawable.rock);
                }
                else if(cpu == 1){
                    cpu_view.setImageResource(R.drawable.scissor);
                }
                else{
                    cpu_view.setImageResource(R.drawable.paper);
                }
            }
        });
        scissor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                me = 1; //me = scissor
                cpu = random.nextInt(3);
                rsp.fight(me, cpu);

                N_LOSE.setText(String.valueOf(losecount));
                N_WIN.setText(String.valueOf(wincount));
                N_TRY.setText(String.valueOf(trycount));
                WIN_LOSE.setText(str);

                me_view.setImageResource(R.drawable.scissor);

                if(cpu == 0){
                    cpu_view.setImageResource(R.drawable.rock);
                }
                else if(cpu == 1){
                    cpu_view.setImageResource(R.drawable.scissor);
                }
                else{
                    cpu_view.setImageResource(R.drawable.paper);
                }
            }
        });
        paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                me = 2; //me = paper
                cpu = random.nextInt(3);
                rsp.fight(me, cpu);

                N_LOSE.setText(String.valueOf(losecount));
                N_WIN.setText(String.valueOf(wincount));
                N_TRY.setText(String.valueOf(trycount));
                WIN_LOSE.setText(str);

                me_view.setImageResource(R.drawable.paper);

                if(cpu == 0){
                    cpu_view.setImageResource(R.drawable.rock);
                }
                else if(cpu == 1){
                    cpu_view.setImageResource(R.drawable.scissor);
                }
                else{
                    cpu_view.setImageResource(R.drawable.paper);
                }
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trycount = 0;
                wincount = 0;
                losecount = 0;
                N_LOSE.setText(String.valueOf(losecount));
                N_WIN.setText(String.valueOf(wincount));
                N_TRY.setText(String.valueOf(trycount));
                Toast.makeText(MainActivity.this,"값을 리셋합니다", Toast.LENGTH_SHORT).show();
            }
        });

    }
    class RSP{
        public void fight(int me, int cpu){
            if(me == 0){
                switch(cpu){
                    case 0:
                        trycount++;
                        str = "무승부!";
                        break;
                    case 1:
                        wincount++;
                        trycount++;
                        str = "승리!";
                        break;
                    case 2:
                        losecount++;
                        trycount++;
                        str = "패배!";
                        break;
                }
            }
            else if (me == 1){
                switch(cpu){
                    case 0:
                        losecount++;
                        trycount++;
                        str = "패배!";
                        break;
                    case 1:
                        trycount++;
                        str = "무승부!";
                        break;
                    case 2:
                        wincount++;
                        trycount++;
                        str = "승리!";
                        break;
                }
            }
            else{
                switch(cpu){
                    case 0:
                        wincount++;
                        trycount++;
                        str = "승리!";
                        break;
                    case 1:
                        losecount++;
                        trycount++;
                        str = "패배!";
                        break;
                    case 2:
                        trycount++;
                        str = "무승부!";
                        break;
                }
            }
        }
    }
}
