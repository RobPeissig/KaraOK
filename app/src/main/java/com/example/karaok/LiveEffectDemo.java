/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.karaok;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.oboe.samples.audio_device.AudioDeviceListEntry;
import com.google.oboe.samples.audio_device.AudioDeviceSpinner;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class LiveEffectDemo extends Activity
        implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String TAG = MainActivity.class.getName();
    private static final int AUDIO_EFFECT_REQUEST = 0;
    private static final int OBOE_API_AAUDIO = 0;
    private static final int OBOE_API_OPENSL_ES=1;

    public TextView ld1;
    public TextView ld2;
    private Button toggleEffectButton;
    private AudioDeviceSpinner recordingDeviceSpinner;
    private AudioDeviceSpinner playbackDeviceSpinner;
    private boolean isPlaying = false;

    private int apiSelection = OBOE_API_AAUDIO;
    private boolean mAAudioRecommended = true;
    //Addition of Media Player here

    private static final int RECORD_REQUEST_CODE = 101;
    private AudioRecorder audioRecorder;
    private boolean isRecording = false;
    private MediaPlayer player;
    private boolean curPlaying;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liveeffect_demo);
//        mp = MediaPlayer.create(this,R.raw.audio);
        audioRecorder = new AudioRecorder();
//        statusText = findViewById(R.id.status_view_text);
        //OLD:: mp = MediaPlayer.create(this,R.raw.audio);
        ld1 = findViewById(R.id.ld1);
        ld2 = findViewById(R.id.ld2);
        toggleEffectButton = findViewById(R.id.button_toggle_effect);
        toggleEffectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleEffect();
            }
        });
        toggleEffectButton.setText(getString(R.string.start_effect));

        recordingDeviceSpinner = findViewById(R.id.recording_devices_spinner);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            recordingDeviceSpinner.setDirectionType(AudioManager.GET_DEVICES_INPUTS);
            recordingDeviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    LiveEffectEngine.setRecordingDeviceId(getRecordingDeviceId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    // Do nothing
                }
            });
        }

        playbackDeviceSpinner = findViewById(R.id.playback_devices_spinner);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            playbackDeviceSpinner.setDirectionType(AudioManager.GET_DEVICES_OUTPUTS);
            playbackDeviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    LiveEffectEngine.setPlaybackDeviceId(getPlaybackDeviceId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    // Do nothing
                }
            });
        }
        ((RadioGroup)findViewById(R.id.apiSelectionGroup)).check(R.id.aaudioButton);
        findViewById(R.id.aaudioButton).setOnClickListener(new RadioButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (((RadioButton)v).isChecked()) {
                    apiSelection = OBOE_API_AAUDIO;
                    setSpinnersEnabled(true);
                }
            }
        });
        findViewById(R.id.slesButton).setOnClickListener(new RadioButton.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (((RadioButton)v).isChecked()) {
                    apiSelection = OBOE_API_OPENSL_ES;
                    setSpinnersEnabled(false);
                }
            }
        });

        LiveEffectEngine.setDefaultStreamValues(this);
    }

    private void EnableAudioApiUI(boolean enable) {
        if(apiSelection == OBOE_API_AAUDIO && !mAAudioRecommended)
        {
            apiSelection = OBOE_API_OPENSL_ES;
        }
        findViewById(R.id.slesButton).setEnabled(enable);
        if(!mAAudioRecommended) {
            findViewById(R.id.aaudioButton).setEnabled(false);
        } else {
            findViewById(R.id.aaudioButton).setEnabled(enable);
        }

        ((RadioGroup)findViewById(R.id.apiSelectionGroup))
          .check(apiSelection == OBOE_API_AAUDIO ? R.id.aaudioButton : R.id.slesButton);
        setSpinnersEnabled(enable);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LiveEffectEngine.create();
        mAAudioRecommended = LiveEffectEngine.isAAudioRecommended();
        EnableAudioApiUI(true);
        LiveEffectEngine.setAPI(apiSelection);
        stopEffect();
    }
    @Override
    protected void onPause() {
        stopEffect();
        LiveEffectEngine.delete();
        super.onPause();
    }

    public void toggleEffect() {
        if (isPlaying) {
            stopEffect();
        } else {
            LiveEffectEngine.setAPI(apiSelection);
            curPlaying = false;
            startEffect();
        }
    }

    private void startEffect() {
        Log.d(TAG, "Attempting to start");

        if (!isRecordPermissionGranted()){
            requestRecordPermission();
            return;
        }

        boolean success = LiveEffectEngine.setEffectOn(true);
        if (success) {
            //OLD: mp.start();
            startRecording();
            toggleEffectButton.setText(R.string.stop_effect);
            isPlaying = true;
            EnableAudioApiUI(false);
            lyricsDisplay();
        } else {
            ld1.setText(R.string.status_open_failed);
            isPlaying = false;
        }
    }

    private void stopEffect() {
        Log.d(TAG, "Playing, attempting to stop");
        LiveEffectEngine.setEffectOn(false);
        resetStatusView();
        toggleEffectButton.setText(R.string.start_effect);
        if(curPlaying){
            player.pause();
            curPlaying = true;
        }
        isPlaying = false;
        EnableAudioApiUI(true);

        //player.pause();
        //OLD:: mp.pause();
        stopRecording();
    }

    private void startRecording() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_REQUEST_CODE);
        } else {
            audioRecorder.startRecording();
            isRecording = true;
//            recordButton.setText(R.string.stop_recording);
        }
    }

    private void stopRecording() {
        audioRecorder.stopRecording();
        isRecording = false;
//        recordButton.setText(R.string.start_recording);
    }

    private void setSpinnersEnabled(boolean isEnabled){
        if (((RadioButton)findViewById(R.id.slesButton)).isChecked())
        {
            isEnabled = false;
            playbackDeviceSpinner.setSelection(0);
            recordingDeviceSpinner.setSelection(0);
        }
        recordingDeviceSpinner.setEnabled(isEnabled);
        playbackDeviceSpinner.setEnabled(isEnabled);
    }

    private int getRecordingDeviceId(){
        return ((AudioDeviceListEntry)recordingDeviceSpinner.getSelectedItem()).getId();
    }

    private int getPlaybackDeviceId(){
        return ((AudioDeviceListEntry)playbackDeviceSpinner.getSelectedItem()).getId();
    }

    private boolean isRecordPermissionGranted() {
        return (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) ==
                PackageManager.PERMISSION_GRANTED);
    }

    private void requestRecordPermission(){
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                AUDIO_EFFECT_REQUEST);
    }

    private void resetStatusView() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == RECORD_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startRecording();
            }
        }
        if (AUDIO_EFFECT_REQUEST != requestCode) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 1 ||
                grantResults[0] != PackageManager.PERMISSION_GRANTED) {

            // User denied the permission, without this we cannot record audio
            // Show a toast and update the status accordingly

            Toast.makeText(getApplicationContext(),
                    getString(R.string.need_record_audio_permission),
                    Toast.LENGTH_SHORT)
                    .show();
        } else {
            // Permission was granted, start live effect
            toggleEffect();
        }
    }

    public void lyricsDisplay(){
        String lrcName = "i See Fire by ed Sheeran.lrc";
        String mpName = "Ed Sheeran I See Fire Lyrics.mp3";
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference lrcRef = storageRef.child(lrcName);
        final long ONE_MEGABYTE = 2229 * 3150;
        lrcRef.getBytes(ONE_MEGABYTE*100).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Log.d(TAG, "Success");
                String str = new String(bytes, StandardCharsets.UTF_8);
                long prevTime = 0;
                Scanner scanner = new Scanner(str);
                curPlaying = startSong(mpName);
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
    public boolean startSong(String mp3Name){
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference mpRef = storageRef.child(mp3Name);

        mpRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){

            @Override
            public void onSuccess(Uri downloadUrl){
                    player = new MediaPlayer();
                try {
                    player.setDataSource(downloadUrl.toString());
                    player.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                        }
                    });
                    player.prepare();


                } catch (Exception e) {
                    Log.d(TAG,"SongStartFailed");

                }

            }
        });
        return true;
    }

    public long getMilli(String time) {
        long milliTime = 0;
        milliTime += Long.parseLong(time.substring(0,2)) * 60000;
        milliTime += Long.parseLong(time.substring(3,5)) * 1000;
        milliTime += Long.parseLong(time.substring(6,8));
        return milliTime;
    }



}
