package com.example.zc.rat;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.apache.commons.math3.complex.Complex;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private WAVView mWAVView;

    private AudioPlayer audioPlayer;
    private Button mPlay;
    private Button mTrans;
    private FreWAVView freWAVView;
    private WaveFileReader reader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWAVView = findViewById(R.id.wav_view);
        freWAVView = findViewById(R.id.fre_wav_view);
        mPlay = findViewById(R.id.play);
        mTrans = findViewById(R.id.btn1);
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 22);


        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory() + "/AArate/hh.wav";
                reader = new WaveFileReader(path);
                reader.getFrameLevel(0);
                freWAVView.setMax(reader.max);
                if (reader.isSuccess()) {
                    mWAVView.setData(reader);
                    play(path);
                }
            }
        });
        mTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = Environment.getExternalStorageDirectory() + "/AArate/gu.mp3";
                String dstPath = Environment.getExternalStorageDirectory() + "/AArate/gu.wav";

            }
        });
    }

    private int aa = 0;

    private void play(String path) {
        audioPlayer = new AudioPlayer();
        audioPlayer.setTarget(new File(path));
        audioPlayer.setListener(new AudioPlayer.OnSeekDuration() {
            @Override
            public void onSeek(float percent) {
                mWAVView.setPercent(percent);
//                aa++;
//                if (aa % 10 != 0) {
//                    return;
//                }
//                aa = 0;
//
//                int frameIndex = (int) (reader.frameFrequnce.length * percent);
//                Complex[] fr = reader.frameFrequnce[frameIndex];
//                double[] aa = new double[fr.length];
//                for (int i = 0; i < fr.length; i++) {
//                    aa[i] = fr[i].getReal();
//                }
//                freWAVView.setData(aa);
            }
        });
        audioPlayer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (audioPlayer != null) {
            audioPlayer.stop();
        }
    }
}
