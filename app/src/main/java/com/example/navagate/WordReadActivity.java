package com.example.navagate;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WordReadActivity extends AppCompatActivity {
    float textSize;
    TextView textView;
    TextView textViewForSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_read);
        textView=findViewById(R.id.ImportanceOfStudy);
        textViewForSize=findViewById(R.id.SizeOfWord);
        textSize=textView.getTextSize();
        textViewForSize.setText(":"+textSize);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        textSize=textView.getTextSize();
//        textViewForSize.setText((int) textSize);
//    }

    public void big(View view) {
        textSize+=5;
        textView.setTextSize(textSize);
        textViewForSize.setText(":"+textSize);
    }
    public void small(View view) {
        textSize-=5;
        textView.setTextSize(textSize);
        textViewForSize.setText(":"+textSize);
    }
}