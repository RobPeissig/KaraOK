package com.example.karaok;

import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.arthenica.ffmpegkit.FFmpegKit;
import com.arthenica.ffmpegkit.ReturnCode;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AudioRecorder {
    Handler handler = new Handler(Looper.getMainLooper());

    private MediaRecorder mediaRecorder;
    private String outputFilePath;
    public String currentTimestamp;
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

    public void stopRecording(){
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            Log.d("AudioRecorder", "Stopped recording");
        }
        File outputDir = new File(Environment.getExternalStorageDirectory(), "Recordings");
        File musicDir = new File(Environment.getExternalStorageDirectory(), "Download");
        File m4aFile = new File(outputDir,  "Recording_" + currentTimestamp + ".mp3");
        File wavFile = new File(outputDir,  "Recording_" + currentTimestamp + ".wav");
        File songFile = new File(musicDir, SongSelection.getFullSongName() + ".wav");
        File mixedFile = new File(outputDir, "Karaoke_" + currentTimestamp + ".wav");
        convertMp3ToWav(m4aFile);
        Handler handler = new Handler(Looper.getMainLooper());
        Runnable mixWavFilesRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    double volumeFactor = 5;
                    mixWavFiles(wavFile, songFile, mixedFile, volumeFactor);
                } catch (IOException e) {
                    Log.e("AudioRecorder", "Error mixing audio files", e);
                }
            }
        };
        Runnable deleteWavRecording = new Runnable() {
            @Override
            public void run() {
                wavFile.delete();
            }
        };
        handler.postDelayed(deleteWavRecording, 5000);
        handler.postDelayed(mixWavFilesRunnable, 2000);
    }

    private String generateOutputFilePath() {
        File outputDir = new File(Environment.getExternalStorageDirectory(), "Recordings");
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }
        currentTimestamp  = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        return new File(outputDir, "Recording_" + currentTimestamp + ".mp3").getAbsolutePath();
    }
    public void release() {
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }
    private void convertMp3ToWav(File mp3File) {
        File outputDir = new File(Environment.getExternalStorageDirectory(), "Recordings");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        File wavFile = new File(outputDir, "Recording_" + currentTimestamp + ".wav");

        String ffmpegCmd = String.format("-i \"%s\" \"%s\"", mp3File.getAbsolutePath(), wavFile.getAbsolutePath());

        FFmpegKit.executeAsync(ffmpegCmd, session -> {
            ReturnCode returnCode = session.getReturnCode();
            if (ReturnCode.isSuccess(returnCode)) {
                mp3File.delete();
                Log.d("FFmpeg", "MP3 to WAV conversion succeeded.");
            } else if (ReturnCode.isCancel(returnCode)) {
                Log.d("FFmpeg", "MP3 to WAV conversion cancelled.");
            } else {
                Log.d("FFmpeg", "MP3 to WAV conversion failed.");
            }
        });
    }

    public String getCurrentTimestamp() {
        return currentTimestamp;
    }
    public static Runnable mixWavFiles(File fileName1, File fileName2, File outputFileName, double volumeFactor) throws IOException {
        String ffmpegCmd = String.format("-i \"%s\" -i \"%s\" -filter_complex \"[0:a]volume=%f[a0];[a0][1:a]amix=inputs=2:duration=longest[a]\" -map \"[a]\" \"%s\"",
                fileName1.getAbsolutePath(), fileName2.getAbsolutePath(), volumeFactor, outputFileName.getAbsolutePath());

        FFmpegKit.executeAsync(ffmpegCmd, session -> {
            ReturnCode returnCode = session.getReturnCode();
            if (ReturnCode.isSuccess(returnCode)) {
                Log.d("FFmpeg", "Mixing WAV files succeeded.");
            } else if (ReturnCode.isCancel(returnCode)) {
                Log.d("FFmpeg", "Mixing WAV files cancelled.");
            } else {
                Log.d("FFmpeg", "Mixing WAV files failed.");
            }
        });
        return null;
    }
}
