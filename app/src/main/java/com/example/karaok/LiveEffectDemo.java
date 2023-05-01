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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
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

import org.w3c.dom.Text;

import java.io.File;
import java.lang.annotation.Native;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;

import com.ttv.spleeter.SpleeterSDK;


public class LiveEffectDemo extends Activity
        implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String TAG = MainActivity.class.getName();
    private static final int AUDIO_EFFECT_REQUEST = 0;
    private static final int OBOE_API_AAUDIO = 0;
    private static final int OBOE_API_OPENSL_ES=1;
    public TextView ld1;
    public TextView ld2;
    public ImageView album;
    public TextView timeTextView;
    private Button toggleEffectButton;
    private AudioDeviceSpinner recordingDeviceSpinner;
    private AudioDeviceSpinner playbackDeviceSpinner;
    private boolean isPlaying = false;
    private int apiSelection = OBOE_API_AAUDIO;
    private boolean mAAudioRecommended = true;
    private static final int RECORD_REQUEST_CODE = 101;
    private AudioRecorder audioRecorder;
    private boolean isRecording = false;
    private MediaPlayer player;
    private SeekBar seekBar;
    private final Handler mHandler = new Handler();
    private int mDuration;
    private VolumeMixer volumeMixer;
    private boolean curPlaying;
    private Button recordButton;
    private String songName;
    private File mLastFile;
    private int mProcessing = 0;
    private AudioTrack mAudioTrack = null;
    private int mBufferSize = 0;
    private int songMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liveeffect_demo);
        Bundle bundle = getIntent().getExtras();
        songName= bundle.getString("songName");
        songMode = bundle.getInt("songMode");
        audioRecorder = new AudioRecorder();
        player = new MediaPlayer();
        volumeMixer = new VolumeMixer(player);
        ld1 = findViewById(R.id.ld1);
        ld2 = findViewById(R.id.ld2);
        toggleEffectButton = findViewById(R.id.button_toggle_effect);
        recordButton = findViewById(R.id.record_button);

        seekBar = findViewById(R.id.seekBar);

        mHandler.postDelayed(updateSeekBarRunnable, 1000);
        timeTextView = findViewById(R.id.timeTextView);

        if (songMode == 1) {
            initSpleeterInstrumentalPlay();
        }

        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        // Get a reference to the ImageView
        ImageView albumCoverImageView = findViewById(R.id.album_cover_image_view);
        String[] albums = songName.split("\\.");
        String albumFile = albums[0] + ".png";
        TextView songNameTextView = findViewById(R.id.song_name_text_view);
        songNameTextView.setText("Now playing: " + albums[0]);
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference jpgRef = storageRef.child("SongCover/" + albumFile);
        final long ONE_MEGABYTE = 2000 * 1024;
        // Set the default album cover image
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

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRecording) {
                    stopRecording();
                } else {
                    startRecording();
                }
            }
        });
        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                long time = (millisUntilFinished / 1000) + 1;
                toggleEffectButton.setText("" + time);
            }

            public void onFinish() {
                toggleEffectButton.setClickable(true);
                toggleEffectButton.performClick();
                toggleEffectButton.setVisibility(View.INVISIBLE);
            }
        }.start();
        toggleEffectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleEffect();                     //Need to change to NativeInterface
            }
        });
        toggleEffectButton.setText(getString(R.string.start_effect));
        recordingDeviceSpinner = findViewById(R.id.recording_devices_spinner);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            recordingDeviceSpinner.setDirectionType(AudioManager.GET_DEVICES_INPUTS);
            recordingDeviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //LiveEffectEngine.setRecordingDeviceId(getRecordingDeviceId());
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
        // set up the seek bar listener
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    // update the MediaPlayer position
                    int newPosition = (int) ((progress / 100.0) * mDuration);
                    player.seekTo(newPosition);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // no action needed
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // no action needed
            }
        });
        apiSelection = OBOE_API_AAUDIO;
        setSpinnersEnabled(true);
        //LiveEffectEngine.setDefaultStreamValues(this);        //TODO:Changed
    }


    @Override
    protected void onStart() {
        super.onStart();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }
    @Override
    protected void onResume() {
        super.onResume();
        //NativeInterface.INSTANCE.createAudioEngine();
        NativeInterface.INSTANCE.enable(true);
        //LiveEffectEngine.create();
        //mAAudioRecommended = LiveEffectEngine.isAAudioRecommended();
        setSpinnersEnabled(true);
        //LiveEffectEngine.setAPI(apiSelection);
        stopEffect();
    }
    @Override
    protected void onPause() {
        stopEffect();
        //NativeInterface.INSTANCE.destroyAudioEngine();
        NativeInterface.INSTANCE.enable(false);
        //LiveEffectEngine.delete();
        super.onPause();
    }
    public void toggleEffect() {
        //LiveEffectEngine.create();
        //mAAudioRecommended = LiveEffectEngine.isAAudioRecommended();
        setSpinnersEnabled(true);
        if (isPlaying) {
            stopEffect();
        } else {
            //LiveEffectEngine.setAPI(apiSelection);
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
        volumeMixer.setLiveEffectVolume(2.0f);

        boolean success = LiveEffectEngine.setEffectOn(false);   //TODO:Changed
        success = true;
        if (success) {
            //OLD: mp.start();
            startRecording();
            //toggleEffectButton.setText(R.string.stop_effect);
            isPlaying = true;
            setSpinnersEnabled(false);
            lyricsDisplay();
        } else {
            ld1.setText(R.string.status_open_failed);
            isPlaying = false;
        }
    }
    private void stopEffect() {
        Log.d(TAG, "Playing, attempting to stop");
        //LiveEffectEngine.setEffectOn(false);
        resetStatusView();
        if(curPlaying){
            player.pause();
            curPlaying = true;
        }
        isPlaying = false;
        setSpinnersEnabled(true);
        stopRecording();
    }

    private void startRecording() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_REQUEST_CODE);
        } else {
            audioRecorder.startRecording();
            isRecording = true;
            recordButton.setText(R.string.stop_recording);
        }
    }

    private void stopRecording() {
        audioRecorder.stopRecording();
        isRecording = false;
        recordButton.setText(R.string.start_recording);
    }
    private void setSpinnersEnabled(boolean isEnabled){
        recordingDeviceSpinner.setEnabled(isEnabled);
        playbackDeviceSpinner.setEnabled(isEnabled);
    }

    private int getRecordingDeviceId(){
        //always default
        return 0;
    }

    private int getPlaybackDeviceId(){
        //always default
        return 0;
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
            toggleEffect();                                         //TODO:changed
        }
    }
    public void lyricsDisplay(){
        String[] lyrics = songName.split("\\.");
        String lyricFile = lyrics[0] + ".lrc";
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference lrcRef = storageRef.child("SongLyrics/" + lyricFile);
        final long ONE_MEGABYTE = 2229 * 3150;
        lrcRef.getBytes(ONE_MEGABYTE*100).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Log.d(TAG, "Success");
                String str = new String(bytes, StandardCharsets.UTF_8);
                long prevTime = 0;
                Scanner scanner = new Scanner(str);
                curPlaying = startSong();
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
                ld2.setText(" ");
                scanner.close();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Failed");
            }
        });
    }
    public boolean startSong(){
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference mpRef = storageRef.child("SongTitles/" + songName);

        mpRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>(){

            @Override
            public void onSuccess(Uri downloadUrl){
                try {
                    volumeMixer.setMusicVolume(0.5f);
                    player.setDataSource(downloadUrl.toString());
                    player.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mDuration = player.getDuration();
                            seekBar.setMax(1000);
                            mp.start();
                            if (songMode == 1) {
                                mp.setVolume(0, 0);
                            }
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
    // define the runnable to update the seek bar
    private final Runnable updateSeekBarRunnable = new Runnable() {
        @Override
        public void run() {
            if (player != null && player.isPlaying()) {
                System.out.println("Duration: " + mDuration);
                int currentPosition = player.getCurrentPosition();
                int progress = (int) (((double) currentPosition / mDuration) * 1000);
                System.out.println("Progress: " + progress);
                seekBar.setProgress(progress, true);
                // Update current time TextView
                int currentSeconds = currentPosition / 1000;
                int currentMinutes = currentSeconds / 60;
                currentSeconds = currentSeconds % 60;
                String currentTime = String.format(Locale.getDefault(), "%d:%02d", currentMinutes, currentSeconds);

                // Update total time TextView
                int totalSeconds = mDuration / 1000;
                int totalMinutes = totalSeconds / 60;
                totalSeconds = totalSeconds % 60;
                String totalTime = String.format(Locale.getDefault(), "%d:%02d", totalMinutes, totalSeconds);

                String timeText = currentTime + " / " + totalTime;
                timeTextView.setText(timeText);
                if(currentMinutes == totalMinutes && currentSeconds+1 >= totalSeconds){
                    endScreen();
                }
            }
            mHandler.postDelayed(this, 1000);
        }
    };

    public long getMilli(String time) {
        long milliTime = 0;
        milliTime += Long.parseLong(time.substring(0,2)) * 60000;
        milliTime += Long.parseLong(time.substring(3,5)) * 1000;
        milliTime += Long.parseLong(time.substring(6,8));
        return milliTime;
    }
    public void endScreen(){
        Intent intent = new Intent(this, EndScreen.class);
        startActivity(intent);

    }

    // hacky way to play instrumental: mute MediaPlayer and then play from Spleeter
    @SuppressWarnings("deprecation")
    public void initSpleeterInstrumentalPlay() {
        player.setVolume(0, 0);

        int frameRate = 44100;
        int minBufferSize =
                AudioTrack.getMinBufferSize(
                        frameRate, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT);
        mBufferSize = (3 * (minBufferSize / 2)) & ~3;
        Log.e("TestEngine", "Audio minBufferSize = " + minBufferSize + " " + mBufferSize);
        mAudioTrack =
                new AudioTrack(
                        AudioManager.STREAM_MUSIC,
                        frameRate,
                        AudioFormat.CHANNEL_OUT_STEREO,
                        AudioFormat.ENCODING_PCM_16BIT,
                        mBufferSize,
                        AudioTrack.MODE_STREAM);

        int ret1 = SpleeterSDK.createInstance(this).create();
        Log.e("TestEngine", String.format("init instance ret:%d", ret1));

        File f = new File(getFilesDir(), "out");
        if (!f.exists()) {
            f.mkdirs();
        }

        File file1 = new File(getFilesDir(), "out");
        String[] myFiles;

        myFiles = file1.list();
        for (int i=0; i<myFiles.length; i++) {
            File myFile = new File(file1, myFiles[i]);
            myFile.delete();
        }

        File downloadOutputAudio = new File(getFilesDir(), "temp_audio.wav");

        new Thread(new Runnable() {
            @Override
            public void run() {
                int ret = SpleeterSDK.getInstance().process(downloadOutputAudio.getPath(), downloadOutputAudio.getParent() + "/out");
                mProcessing = 0;
                Log.e("TestEngine", String.format("start instance ret:%d", ret));
                Log.e("TestEngine", String.format("input:%s, output%s", downloadOutputAudio.getPath(), downloadOutputAudio.getParent() + "/out"));
            }
        }).start();
        new ProgressTask().execute("", "", "");
    }



    @SuppressWarnings("deprecation")
    public class ProgressTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            Log.e("TestEngine", "onPreExecute");
            super.onPreExecute();
            mAudioTrack.play();
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.e("TestEngine", "start background task");
            try {
                int playSize = 0;
                int offset = 0;
                while(true) {
                    short[] playbuffer = new short[8192];
                    float[] stemRatio = new float[5];
                    stemRatio[0] = 0;
                    stemRatio[1] = 1;
                    stemRatio[2] = 1;
                    stemRatio[3] = 1;
                    stemRatio[4] = 1;


                    int ret = SpleeterSDK.getInstance().playbuffer(playbuffer, offset, stemRatio);
                    if(ret == 0) {
                        break;
                    } else if(ret < 0) {
                        Thread.sleep(30);
                    } else {
                        if(playSize == 0) {
                            playSize = SpleeterSDK.getInstance().playsize();
                        }
                        Log.e("TestEngine", String.format("return code: %d", ret));

                        offset += ret;
                        mAudioTrack.write(playbuffer, 0, playbuffer.length);
                        Log.e("TestEngine", "write " + playbuffer.length);

                        int progress = SpleeterSDK.getInstance().progress();

                        Log.e("TestEngine", "write " + playbuffer.length);

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mAudioTrack.stop();

            float[] stemRatio = new float[5];
            stemRatio[0] = 0;
            stemRatio[1] = 1;
            stemRatio[2] = 1;
            stemRatio[3] = 1;
            stemRatio[4] = 1;

//            SpleeterSDK.getInstance().saveAllStem("/mnt/sdcard/split");
//            SpleeterSDK.getInstance().saveOne("/mnt/sdcard/one.wav", stemRatio);

        }
    }
}
