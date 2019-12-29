package com.example.zc.rat;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private WAVView mWAVView;

    private AudioPlayer audioPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWAVView = findViewById(R.id.wav_view);
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 22);

        mWAVView.post(new Runnable() {


            @Override
            public void run() {
                String path = Environment.getExternalStorageDirectory() + "/AArate/aabb.wav";
//                play(path);
//
                WaveFileReader reader = new WaveFileReader(path);
                if (reader.isSuccess()) {
                    int[] data = reader.getData()[0];
                    mWAVView.setData(data);
                    play(path);
                }
            }
        });


    }

    private void play(String path) {
        audioPlayer = new AudioPlayer();
        audioPlayer.setTarget(new File(path));
                    audioPlayer.setListener(new AudioPlayer.OnSeekDuration() {
                        @Override
                        public void onSeek(float percent) {
                            mWAVView.setPercent(percent);
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
