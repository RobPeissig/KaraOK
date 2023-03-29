package com.example.karaok;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AudioRecorder {
    private MediaRecorder mediaRecorder;
    private String outputFilePath;

    public void startRecording() {
        outputFilePath = generateOutputFilePath();

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setAudioSamplingRate(44100);
        mediaRecorder.setAudioChannels(1);
        mediaRecorder.setAudioEncodingBitRate(96000);
        mediaRecorder.setOutputFile(outputFilePath);

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            Log.d("AudioRecorder", "Started recording");
        } catch (IOException e) {
            Log.e("AudioRecorder", "Failed to prepare MediaRecorder", e);
        }
    }

    public void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            Log.d("AudioRecorder", "Stopped recording");
        }
    }

    private String generateOutputFilePath() {
        File outputDir = new File(Environment.getExternalStorageDirectory(), "Recordings");
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        return new File(outputDir, "Recording_" + timeStamp + ".m4a").getAbsolutePath();
    }
    public void release() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }
}
