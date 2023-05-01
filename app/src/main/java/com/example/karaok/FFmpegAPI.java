package com.example.karaok;

import static com.example.karaok.MainActivity.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arthenica.ffmpegkit.FFmpegKit;
import com.arthenica.ffmpegkit.FFmpegKitConfig;
import com.arthenica.ffmpegkit.FFmpegSession;
import com.arthenica.ffmpegkit.FFmpegSessionCompleteCallback;
import com.arthenica.ffmpegkit.LogCallback;
import com.arthenica.ffmpegkit.ReturnCode;
import com.arthenica.ffmpegkit.SessionState;
import java.io.File;


public class FFmpegAPI {

    public static void encodeAudio(File audioOutputFile, File audioInputFile,String audioCodec) {
        if (audioOutputFile.exists()) {
            audioOutputFile.delete();
        }

        android.util.Log.d(TAG, String.format("Testing AUDIO encoding with '%s' codec.", audioCodec));

        final String ffmpegCommand = generateAudioEncodeScript(audioCodec, audioInputFile, audioOutputFile);

        android.util.Log.d(TAG, String.format("FFmpeg process started with arguments: '%s'.", ffmpegCommand));

        FFmpegSession session = FFmpegKit.execute(ffmpegCommand);
        if (ReturnCode.isSuccess(session.getReturnCode())) {

            // SUCCESS

        } else if (ReturnCode.isCancel(session.getReturnCode())) {

            // CANCEL

        } else {

            // FAILURE
            Log.d(TAG, String.format("Command failed with state %s and rc %s.%s", session.getState(), session.getReturnCode(), session.getFailStackTrace()));

        }

    }

    public static String generateAudioEncodeScript(String audioCodec, File InputFile, File OutputFile) {

        String audioSampleFile = InputFile.getAbsolutePath();
        String audioOutputFile = OutputFile.getAbsolutePath();

        switch (audioCodec) {
            case "mp2 (twolame)":
                return String.format("-hide_banner -y -i %s -c:a mp2 -b:a 192k %s", audioSampleFile, audioOutputFile);
            case "mp3 (liblame)":
                return String.format("-hide_banner -y -i %s -c:a libmp3lame -qscale:a 2 %s", audioSampleFile, audioOutputFile);
            case "mp3 (libshine)":
                return String.format("-hide_banner -y -i %s -c:a libshine -qscale:a 2 %s", audioSampleFile, audioOutputFile);
            case "vorbis":
                return String.format("-hide_banner -y -i %s -c:a libvorbis -b:a 64k %s", audioSampleFile, audioOutputFile);
            case "opus":
                return String.format("-hide_banner -y -i %s -c:a libopus -b:a 64k -vbr on -compression_level 10 %s", audioSampleFile, audioOutputFile);
            case "amr-nb":
                return String.format("-hide_banner -y -i %s -ar 8000 -ab 12.2k -c:a libopencore_amrnb %s", audioSampleFile, audioOutputFile);
            case "amr-wb":
                return String.format("-hide_banner -y -i %s -ar 8000 -ab 12.2k -c:a libvo_amrwbenc -strict experimental %s", audioSampleFile, audioOutputFile);
            case "ilbc":
                return String.format("-hide_banner -y -i %s -c:a ilbc -ar 8000 -b:a 15200 %s", audioSampleFile, audioOutputFile);
            case "speex":
                return String.format("-hide_banner -y -i %s -c:a libspeex -ar 16000 %s", audioSampleFile, audioOutputFile);
            case "wavpack":
                return String.format("-hide_banner -y -i %s -c:a wavpack -b:a 64k %s", audioSampleFile, audioOutputFile);
            default:

                // soxr - mp3 to wav with 16bit depth, 2 channels, sample rate 44100
                return String.format("-hide_banner -y -i %s -af aresample=resampler=soxr -ac 2 -ar 44100 %s", audioSampleFile, audioOutputFile);
        }
    }

    public static String getDefaultAudioOutputFileExtension(String audioCodec) {

        String extension;
        switch (audioCodec) {
            case "mp2 (twolame)":
                extension = "mpg";
                break;
            case "mp3 (liblame)":
            case "mp3 (libshine)":
                extension = "mp3";
                break;
            case "vorbis":
                extension = "ogg";
                break;
            case "opus":
                extension = "opus";
                break;
            case "amr-nb":
            case "amr-wb":
                extension = "amr";
                break;
            case "ilbc":
                extension = "lbc";
                break;
            case "speex":
                extension = "spx";
                break;
            case "wavpack":
                extension = "wv";
                break;
            default:

                // soxr
                extension = "wav";
                break;
        }
        return extension;
    }

}
