package com.example.zc.rat;

import android.media.MediaPlayer;
import android.media.TimedText;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

public class AudioPlayer {


    private int duration;
    private boolean isPlaying = false;

    private File targetFile;
    private MediaPlayer mMediaPlayer = null;
    private Consumer<AudioPlayer> mOnCompletionListener;

    public AudioPlayer() {

    }

    public void setTarget(File file) {
        this.targetFile = file;
    }

    public void setOnCompletionListener(Consumer<AudioPlayer> consumer) {
        this.mOnCompletionListener = consumer;
    }

    public boolean start() {
        if (targetFile == null) {
            return false;
        }

        mMediaPlayer = new MediaPlayer();

        try {
            mMediaPlayer.setDataSource(targetFile.getAbsolutePath());
            mMediaPlayer.prepare();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    AudioPlayer.this.onCompletion(mp);
                }
            });
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    startSeek();
                }
            });
            mMediaPlayer.setOnTimedTextListener(new MediaPlayer.OnTimedTextListener() {
                @Override
                public void onTimedText(MediaPlayer mp, TimedText text) {
                    String text1 = text.getText();
                }
            });
            mMediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    return false;
                }
            });


            this.duration = mMediaPlayer.getDuration();
        } catch (IOException e) {
            e.printStackTrace();

            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

        boolean result = (mMediaPlayer != null);
        this.isPlaying = result;

        return result;
    }

    public void stop() {
        this.isPlaying = false;
        this.duration = 0;

        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public void resume() {
        if (mMediaPlayer != null) {
            this.isPlaying = true;

            mMediaPlayer.start();
        }
    }

    public void pause() {
        this.isPlaying = false;

        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
        }
    }

    public boolean isRunning() {
        return (mMediaPlayer != null);
    }

    public boolean isPlaying() {
        return this.isPlaying;
    }

    public int getDuration() {
        if (mMediaPlayer == null) {
            return this.duration;
        }

        return mMediaPlayer.getDuration();
    }

    public int getCurrentPosition() {
        if (mMediaPlayer == null) {
            return 0;
        }

        return mMediaPlayer.getCurrentPosition();
    }

    public MediaPlayer getMediaPlayer() {
        return this.mMediaPlayer;
    }

    public void onCompletion(MediaPlayer mp) {
        this.stop();

        if (mOnCompletionListener != null) {
            mOnCompletionListener.accept(this);
        }
    }

    private void startSeek() {
        if (listener == null) {
            return;
        }
        new Thread() {
            @Override
            public void run() {
                while (mMediaPlayer.isPlaying()) {
                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (mMediaPlayer == null || !mMediaPlayer.isPlaying()) {
                        break;
                    }
                    int currentPosition = mMediaPlayer.getCurrentPosition();

                    //发送数据给activity
                    Message message = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putFloat("percent", currentPosition * 1f / duration);
                    message.setData(bundle);
                    handler.sendMessage(message);
                }
            }
        }.start();
    }

    Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Bundle data = msg.getData();
            if (data != null) {
                float percent = data.getFloat("percent");
                if (listener != null) {
                    listener.onSeek(percent);
                }
            }
            return false;
        }
    });
    OnSeekDuration listener;

    public void setListener(OnSeekDuration listener) {
        this.listener = listener;
    }

    public interface OnSeekDuration {
        void onSeek(float percent);
    }

}
