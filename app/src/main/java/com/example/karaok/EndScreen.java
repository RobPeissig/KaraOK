package com.example.karaok;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class EndScreen extends AppCompatActivity {
    private Button returnB;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private Handler seekBarHandler = new Handler();
    private Runnable updateSeekBar = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null) {
                int currentPosition = mediaPlayer.getCurrentPosition() / 1000;
                seekBar.setProgress(currentPosition);
            }
            seekBarHandler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_screen);

        // Get the mixed file path from the intent extras
        Bundle bundle = getIntent().getExtras();
        String albumFile = bundle.getString("albumFile");
        String currentTimeStamp = bundle.getString("currentTimeStamp");

        // Set up the play button listener
        Button recordTextView = findViewById(R.id.record_textview);
        recordTextView.setEnabled(false);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                File outputDir = new File(Environment.getExternalStorageDirectory(), "Recordings");
                File mixedFile = new File(outputDir, "Karaoke_" + currentTimeStamp + ".wav");
                FileInputStream inputStream = null;
                try {
                    Log.d("boi","Setting data source to " + mixedFile.getAbsolutePath());
                    inputStream = new FileInputStream(mixedFile);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(inputStream.getFD());
                    mediaPlayer.prepare();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                recordTextView.setEnabled(true);
            }
        }, 3000); // Delay in milliseconds


        // Find the seek bar view in the layout and set its max value to the duration of the recording
        seekBar = findViewById(R.id.seek_bar);
        seekBar.setProgress(0);

        // Set up the seek bar listener to update the position of the MediaPlayer when the user drags the seek bar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Remove the updateSeekBar Runnable when the user starts dragging the seek bar
                seekBarHandler.removeCallbacks(updateSeekBar);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Start the updateSeekBar Runnable again when the user stops dragging the seek bar
                seekBarHandler.postDelayed(updateSeekBar, 1000);
            }
        });

        // Set up the album cover image view
        ImageView albumCoverImageView = findViewById(R.id.record_icon);
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference jpgRef = storageRef.child("SongCover/" + albumFile);
        final long ONE_MEGABYTE = 2000 * 1024;
        jpgRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                albumCoverImageView.setImageBitmap(bmp);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        // Set up the return button listener
        returnB = findViewById(R.id.returnBut);
        returnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                returnHome();
            }
        });

        recordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.start();
                seekBar.setMax(mediaPlayer.getDuration() / 1000);
                seekBarHandler.postDelayed(updateSeekBar, 1000);
            }
        });
    }

    public void returnHome(){
        Intent intent = new Intent(this, SongSelection.class);
        startActivity(intent);
    }
}
