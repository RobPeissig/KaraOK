package com.example.karaok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EndScreen extends AppCompatActivity {
    private Button returnB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);
        returnB = findViewById(R.id.returnBut);
        returnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnHome();
            }
        });
    }
    public void returnHome(){
        Intent intent = new Intent(this, SongSelection.class);
        startActivity(intent);
    }
}