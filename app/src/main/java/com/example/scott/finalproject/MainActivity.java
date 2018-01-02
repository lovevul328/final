package com.example.scott.finalproject;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ImageView fan;
    private Button low, middom, high;
    private Switch switch1;
    private TextView showtext;
    private SeekBar seekBar;
    private float i;
    private int seekvalue;
    ClockThread1 thread1 = new ClockThread1();
    ClockThread2 thread2 = new ClockThread2();
    ClockThread3 thread3 = new ClockThread3();
    ClockThread4 thread4 = new ClockThread4();
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fan=(ImageView) findViewById(R.id.fan);
        low=(Button)findViewById(R.id.low);
        middom=(Button)findViewById(R.id.middom);
        high=(Button)findViewById(R.id.high);
        switch1=(Switch)findViewById(R.id.switch1);
        showtext=(TextView)findViewById(R.id.showtext);
        seekBar=(SeekBar)findViewById(R.id.seekBar);

        low.setOnClickListener(LowListener);
        middom.setOnClickListener(MiddomListener);
        high.setOnClickListener(HighListener);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(switch1.isChecked()==true){
                    low.setClickable(true);
                    middom.setClickable(true);
                    high.setClickable(true);
                    showtext.setText("電風扇已開");
                }
                else if(switch1.isChecked()==false){
                    handler.removeCallbacks(thread1);
                    handler.removeCallbacks(thread2);
                    handler.removeCallbacks(thread3);
                    handler.removeCallbacks(thread4);
                    low.setClickable(false);
                    middom.setClickable(false);
                    high.setClickable(false);
                    showtext.setText("電風扇已關");
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(switch1.isChecked()==true) {
                    seekvalue = seekBar.getProgress();
                    if(seekvalue==0){
                        showtext.setText("電風扇已關");
                        switch1.setChecked(false);
                    }
                    else if (seekvalue > 0 && seekvalue <= 5) {
                        showtext.setText("強度： 弱");
                    } else if (seekvalue >= 6 && seekvalue <= 10) {
                        showtext.setText("強度： 中");
                    } else if (seekvalue >= 11 && seekvalue <= 15) {
                        showtext.setText("強度： 強");
                    }
                    handler.removeCallbacks(thread1);
                    handler.removeCallbacks(thread2);
                    handler.removeCallbacks(thread3);
                    handler.removeCallbacks(thread4);
                    handler.post(thread4);
                }
            }
        });

    }

    public class ClockThread1 extends Thread{
        public void run(){
            i=fan.getRotation();
            i=i+4;
            fan.setRotation(i);
            handler.postDelayed(thread1,1/100);
        }
    }

    public class ClockThread2 extends Thread{
        public void run(){
            i=fan.getRotation();
            i=i+7;
            fan.setRotation(i);
            handler.postDelayed(thread2,1/100);
        }
    }

    public class ClockThread3 extends Thread{
        public void run(){
            i=fan.getRotation();
            i=i+15;
            fan.setRotation(i);
            handler.postDelayed(thread3,1/100);
        }
    }

    public class ClockThread4 extends Thread{
        public void run(){
            i=fan.getRotation();
            i=i+seekvalue;
            fan.setRotation(i);
            handler.postDelayed(thread4,1/100);
        }
    }


    private Button.OnClickListener LowListener=new Button.OnClickListener() {
        public void onClick(View v) {
            handler.removeCallbacks(thread1);
            handler.removeCallbacks(thread2);
            handler.removeCallbacks(thread3);
            handler.removeCallbacks(thread4);
            handler.post(thread1);
            showtext.setText("強度： 弱");
            seekBar.setProgress(4);
        }
    };

    private Button.OnClickListener MiddomListener=new Button.OnClickListener() {
        public void onClick(View v) {
            handler.removeCallbacks(thread1);
            handler.removeCallbacks(thread2);
            handler.removeCallbacks(thread3);
            handler.removeCallbacks(thread4);
            handler.post(thread2);
            showtext.setText("強度： 中");
            seekBar.setProgress(7);
        }
    };

    private Button.OnClickListener HighListener=new Button.OnClickListener() {
        public void onClick(View v) {
            handler.removeCallbacks(thread1);
            handler.removeCallbacks(thread2);
            handler.removeCallbacks(thread3);
            handler.removeCallbacks(thread4);
            handler.post(thread3);
            showtext.setText("強度： 強");
            seekBar.setProgress(15);
        }
    };
}
