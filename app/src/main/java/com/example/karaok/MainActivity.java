package com.example.karaok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //test commit
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openLyricsActivity();
    }

    public void openLyricsActivity() {
        Intent intent = new Intent(this, LyricsDisplayActivity.class);
        startActivity(intent);
    }
}