package com.example.zc.rat;

import android.Manifest;
import android.content.Intent;
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

        Intent intent = new Intent(this,TestActivity.class);
        startActivity(intent);


//        mPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String path = Environment.getExternalStorageDirectory() + "/AArate/hh.wav";
//                reader = new WaveFileReader(path);
//                reader.getFrameLevel(0);
//                freWAVView.setMax(reader.max);
//                if (reader.isSuccess()) {
//                    mWAVView.setData(reader);
//                    play(path);
//                }
//            }
//        });
//        mTrans.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String path = Environment.getExternalStorageDirectory() + "/AArate/gu.mp3";
//                String dstPath = Environment.getExternalStorageDirectory() + "/AArate/gu.wav";
//
//            }
//        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (audioPlayer != null) {
//            audioPlayer.stop();
//        }
    }
}
