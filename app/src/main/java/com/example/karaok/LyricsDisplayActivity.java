package com.example.karaok;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class LyricsDisplayActivity extends LiveEffectDemo{
    public static void runLyrics(TextView ld1, TextView ld2) {
                String lrcName = "i See Fire by ed Sheeran.lrc";
                String mpName = "Ed Sheeran I See Fire Lyrics.mp3";
                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                StorageReference lrcRef = storageRef.child(lrcName);
                StorageReference mpRef = storageRef.child(mpName);
                final long ONE_MEGABYTE = 2229 * 3150;
                lrcRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Log.d(TAG, "Success");
                        String str = new String(bytes, StandardCharsets.UTF_8);
                        long prevTime = 0;
                        Scanner scanner = new Scanner(str);
                        try {
                            MediaPlayer player = new MediaPlayer();
                            player.setDataSource("https://firebasestorage.googleapis.com/v0/b/karaok-a4389.appspot.com/o/Ed%20Sheeran%20I%20See%20Fire%20Lyrics.mp3?alt=media&token=0f4bb7e1-c98d-4f6f-b358-7fcda6073531");
                            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    mp.start();
                                }
                            });
                            player.prepare();

                        } catch (Exception e) {
                            // TODO: handle exception
                            System.out.print("FAILED");
                        }
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine();
                            // process the line
                            if (!line.startsWith("[0")) {
                                continue;
                            }
                            String time = line.substring(1,9);
                            long milliTime = 1;//REPLACE:: getMilli(time);
                            String lyrics = line.substring(10);
                            Log.d(TAG, String.valueOf(milliTime));
                            Log.d(TAG, lyrics);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ld1.setText(lyrics);
                                }
                            }, milliTime);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ld2.setText(lyrics);
                                }
                            }, prevTime);
                            prevTime = milliTime;
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

    /*public static long getMilli(String time) {
        long milliTime = 0;
        milliTime += Long.parseLong(time.substring(0,2)) * 60000;
        milliTime += Long.parseLong(time.substring(3,5)) * 1000;
        milliTime += Long.parseLong(time.substring(6,8));
        return milliTime;
    }*/
}
