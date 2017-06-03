package com.example.rishabhcha.videorecording;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.opengl.GLES20;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.VideoView;

import com.example.rishabhcha.videorecording.gles.EglCore;
import com.example.rishabhcha.videorecording.gles.WindowSurface;

import java.io.File;
import java.io.IOException;

public class PlayMovieSurfaceActivity extends AppCompatActivity {

    boolean isPlaying = true;
    VideoView videoView;
    int stopPosition;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_movie_surface);

        videoView = (VideoView) findViewById(R.id.videoView);

        //MediaController mediaController = new MediaController(this);
        //mediaController.setAnchorView(videoView);

        Uri uri= Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/test.mp4");

        //videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        //videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                mediaPlayer = mp;
                mediaPlayer.start();

            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                mediaPlayer.start();

            }
        });

    }

    /*@Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        View decorView = getWindow().getDecorView();
        if (hasFocus){
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }*/

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP){

            if (isPlaying){

                mediaPlayer.pause();
                isPlaying=false;

            }else {

                mediaPlayer.start();
                isPlaying=true;

            }

        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mediaPlayer.isPlaying()){

            mediaPlayer.pause();

        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("Play----------onStop","MediaPlayer releasing");

        mediaPlayer.release();
        mediaPlayer = null;

        Log.d("Play----------onStop","released");

    }

    @Override
    public void onBackPressed() {
        Intent recordIntent = new Intent(PlayMovieSurfaceActivity.this, CameraCaptureActivity.class);
        recordIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(recordIntent);

        super.onBackPressed();
    }
}
