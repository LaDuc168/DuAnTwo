package com.example.app_pro.duantwo.baocao;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.example.app_pro.duantwo.MainActivity;
import com.example.app_pro.duantwo.R;

import me.itangqi.waveloadingview.WaveLoadingView;

public class NotificationHangTon extends AppCompatActivity {
    WaveLoadingView waveLoadingView;
    SeekBar seekBar;
    int progress=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_hang_ton);

        seekBar= (SeekBar) findViewById(R.id.seekBar);
        waveLoadingView= (WaveLoadingView) findViewById(R.id.waveLoading);



        new CountDownTimer(3000, 30) {
            @Override
            public void onTick(long millisUntilFinished) {
                seekBar.setProgress(progress);
                progress++;
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(NotificationHangTon.this, MainActivity.class));
                finish();
            }
        }.start();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                waveLoadingView.setProgressValue(progress);
                if(progress<50){
                    waveLoadingView.setBottomTitle(String.format("%d%%",progress));
                    waveLoadingView.setCenterTitle("");
                    waveLoadingView.setTopTitle("");

                }else if(progress<80){
                    waveLoadingView.setBottomTitle("");
                    waveLoadingView.setCenterTitle(String.format("%d%%",progress));
                    waveLoadingView.setTopTitle("");
                }else {
                    waveLoadingView.setBottomTitle("");
                    waveLoadingView.setCenterTitle("");
                    waveLoadingView.setTopTitle(String.format("%d%%",progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
