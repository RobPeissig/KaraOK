package com.example.karaok;

import static com.example.karaok.MainActivity.TAG;

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

        FFmpegKit.executeAsync(ffmpegCommand, new FFmpegSessionCompleteCallback() {

            @Override
            public void apply(final FFmpegSession session) {
                final SessionState state = session.getState();
                final ReturnCode returnCode = session.getReturnCode();

                if (ReturnCode.isSuccess(returnCode)) {
                    android.util.Log.d(TAG, "Encode completed successfully.");
                } else {
                    android.util.Log.d(TAG, String.format("Encode failed with state %s and rc %s", state, returnCode));
                }
            }
        });
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

}
