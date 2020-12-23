package com.example.navagate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ToBaidu(View view) {
        this.startActivity(new Intent(this,BaiduActivity.class));
    }

    public void ToImageView(View view) {
        this.startActivity(new Intent(this, ImageViewActivity.class));
    }

    public void ToVedioPlay(View view) {
        this.startActivity(new Intent(this, VedioPlayActivity.class));
    }

    public void ToWordRead(View view) {
        this.startActivity(new Intent(this,WordReadActivity.class));
    }
}