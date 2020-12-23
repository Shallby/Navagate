package com.example.navagate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.app.ActivityCompat;

        import android.Manifest;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.media.AudioAttributes;
        import android.media.AudioManager;
        import android.media.MediaPlayer;
        import android.net.Uri;
        import android.os.Build;
        import android.os.Bundle;
        import android.view.SurfaceHolder;
        import android.view.SurfaceView;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

        import java.io.IOException;

public class ImageViewActivity extends AppCompatActivity {
    int KeyTimes=0;
    ImageView imageView;
    private int img[]={R.id.image1,R.id.image2,R.id.image3,R.id.image4};
    Button nextVideo_btn;
    Button play_btn;
    VideoView videoView;
    MediaController mediaController;

    ImageView imageViewForShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        imageView=findViewById(R.id.Scale_ImageView);
        play_btn=findViewById(R.id.BeginVideo);
        imageViewForShow=findViewById(R.id.imageForShow);

    }

    @Override
    protected void onStart() {
        super.onStart();
        for (int i = 0; i <img.length ; i++) {
            ImageView iv=this.findViewById(img[i]);
            //这下面的一堆简而言之就是重新赋图片 当点击了图片之后 上面的那个框会发生改变
            if (iv!=null){
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageViewForShow.setImageDrawable(iv.getDrawable());
                    }
                });
            }

        }
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


}