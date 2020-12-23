package com.example.navagate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class VedioPlayActivity extends AppCompatActivity {
    int KeyTimes=0;
    ImageView imageView;
    Button nextVideo_btn;
    Button play_btn;
    VideoView videoView;
    MediaController mediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_play);
        imageView=findViewById(R.id.Scale_ImageView);
        videoView=findViewById(R.id.videoView);
        play_btn=findViewById(R.id.BeginVideo);
        nextVideo_btn=findViewById(R.id.nextVideo);
        mediaController =new MediaController(this);

    }

    public void CENTER_CROP(View view) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    public void CENTER_INSIDE(View view) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    }

    public void FIT_CENTER(View view) {
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }

    public void FIT_END(View view) {
        imageView.setScaleType(ImageView.ScaleType.FIT_END);
    }

    public void NextViewPiKa(View view) {
        KeyTimes+=1;
        if (KeyTimes%2==1){
            imageView.setImageResource(R.drawable.pikawo);
        }else {
            imageView.setImageResource(R.drawable.pika);
        }
    }

    public void Begin(View view) {
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.man;  //本地
        videoView.setVideoURI(Uri.parse(uri));
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();
    }

//    public void nextVideo(View view) {
//        String uri ="android.resource://" + getPackageName() + "/" + R.raw.tang;
//        videoView.setVideoURI(Uri.parse(uri));
//        videoView.setMediaController(mediaController);
//        mediaController.setMediaPlayer(videoView);
//        videoView.requestFocus();
//        videoView.start();
//    }

    public void pauseVideo(View view) {
        videoView.stopPlayback();
    }
    public void TomainAc(View view) {
        this.startActivity(new Intent(this,MainActivity.class));
    }
}