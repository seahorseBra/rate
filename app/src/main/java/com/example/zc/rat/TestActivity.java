package com.example.zc.rat;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.CharBuffer;
import java.util.List;

public class TestActivity extends AppCompatActivity {

    private View mPlay;
    private AudioPlayer audioPlayer;
    private View red;
    String[] name = {"1.mp3", "2.mp3", "3.mp3", "4.mp3", "5.mp3", "6.mp3", "7.mp3", "8.mp3"};
    String[] arrays = {"1.txt", "2.txt", "3.txt", "4.txt", "5.txt", "6.txt", "7.txt", "8.txt"};
    int index = 0;
    private JSONArray objects;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mPlay = findViewById(R.id.play);
        red = findViewById(R.id.image);
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index++;
                index = index % name.length;
                play();
            }
        });

        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });
    }


    private void play() {
        try {
            InputStream assetFileDescriptor = getResources().getAssets().open(arrays[index]);
            BufferedReader reader = new BufferedReader(new InputStreamReader(assetFileDescriptor));
            String a = reader.readLine();
            if (!TextUtils.isEmpty(a)) {
                objects = JSONArray.parseArray(a);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (audioPlayer == null) {
            audioPlayer = new AudioPlayer();
        }

        if (audioPlayer.getMediaPlayer() != null && audioPlayer.getMediaPlayer().isPlaying()) {
            audioPlayer.getMediaPlayer().reset();
        }
        try {
            audioPlayer.setTarget(getResources().getAssets().openFd(name[index]));
        } catch (IOException e) {
            e.printStackTrace();
        }

        audioPlayer.setListener(new AudioPlayer.OnSeekDuration() {
            @Override
            public void onSeek(float percent, int currentPosition) {
                red.setVisibility(View.INVISIBLE);
                while (!objects.isEmpty()) {
                    float aFloat = objects.getFloatValue(0);
                    float index = aFloat * 1000;
                    float dis = currentPosition - index;
                    if (dis < -50) {
                        break;
                    }
                    objects.remove(0);
                    if (Math.abs(dis) <= 50) {
                        red.setVisibility(View.VISIBLE);
                    }
                    break;
                }


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
