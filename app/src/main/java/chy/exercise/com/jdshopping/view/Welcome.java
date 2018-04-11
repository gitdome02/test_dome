package chy.exercise.com.jdshopping.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import chy.exercise.com.jdshopping.Baes.BaseActivity;
import chy.exercise.com.jdshopping.R;

public class Welcome extends AppCompatActivity {
    private TextView timers;
    private int i = 10;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (i == 0) {
                Intent intent = new Intent(Welcome.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                timers.setText(i + "秒后跳转");
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        SharedPreferences pref = this.getSharedPreferences("myActivityName", MODE_PRIVATE);
        //取得相应的值，如果没有该值， 说明还未写入，用true作为默认值
        boolean isFirstIn = pref.getBoolean("isFirstIn", false);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isFirstIn", true);
        if (isFirstIn == true) {
            startActivity(new Intent(this, MainActivity.class));
            finish();


            timers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Welcome.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            new Thread() {
                @Override
                public void run() {
                    super.run();
                    while (true) {
                        try {
                            sleep(1000);
                            i--;
                            handler.sendEmptyMessage(i);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();


        }
        editor.commit();
    }
}