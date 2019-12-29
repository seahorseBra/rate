package com.example.zc.rat;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 22);
        Helper helper = new Helper();
        helper.decodeMusicFile(this, null,
                Environment.getExternalStorageDirectory() + "/aa.pcm", 0, -1, new Helper.DecodeOperateInterface() {
                    @Override
                    public void onDecodeResult(byte[] pcm) {
                        Log.d(TAG, "onDecodeResult: "+pcm);
                    }
                });
    }
}
