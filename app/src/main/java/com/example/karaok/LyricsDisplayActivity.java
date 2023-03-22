package com.example.karaok;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class LyricsDisplayActivity extends AppCompatActivity {
    private static final String TAG = "LyricsActivity";
    private Handler mainHandler = new Handler();
    Button button_display_lyrics;
    TextView lyricsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics_display);
        lyricsTextView = findViewById(R.id.lyricsTextView);
        button_display_lyrics = findViewById(R.id.DisplayLyrics);

        button_display_lyrics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lrcName = "i See Fire by ed Sheeran.lrc";

                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                StorageReference lrcRef = storageRef.child(lrcName);
                final long ONE_MEGABYTE = 2229 * 3150;
                lrcRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Log.d(TAG, "Success");
                        String str = new String(bytes, StandardCharsets.UTF_8);
                        //long prevTime = 0;
                        Scanner scanner = new Scanner(str);
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            // process the line
                            if (!line.startsWith("[0")) {
                                continue;
                            }
                            String time = line.substring(1,9);
                            long milliTime = getMilli(time);
                            String lyrics = line.substring(10);
                            Log.d(TAG, String.valueOf(milliTime));
                            Log.d(TAG, lyrics);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    lyricsTextView.setText(lyrics);
                                }
                            }, milliTime);
                            //prevTime = milliTime;
                        }
                        scanner.close();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Failed");
                    }
                });
            }
        });
    }

    public long getMilli(String time) {
        long milliTime = 0;
        milliTime += Long.parseLong(time.substring(0,2)) * 60000;
        milliTime += Long.parseLong(time.substring(3,5)) * 1000;
        milliTime += Long.parseLong(time.substring(6,8));
        return milliTime;
    }
}
