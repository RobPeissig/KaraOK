package com.example.karaok;

import static com.arthenica.ffmpegkit.Packages.getPackageName;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.google.common.truth.Truth;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)

public class FFmpegAPITest {

    @Test
    public void mp3towavTest() {

        String outputExtension = FFmpegAPI.getDefaultAudioOutputFileExtension("soxr");
        Truth.assertThat(outputExtension).isEqualTo("wav"); // check expected ext

        File testInputAudio = new File(InstrumentationRegistry.getInstrumentation().getTargetContext().getFilesDir(), "test_audio.mp3");
        if (testInputAudio.exists()) {
            testInputAudio.delete();
        }

        // copy res/raw to app files dir
        try {
            InputStream inputStream = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources().openRawResource(R.raw.test_audio);
            FileOutputStream fileOutputStream = new FileOutputStream(testInputAudio);

            byte buf[]=new byte[1024];
            int len;
            while((len=inputStream.read(buf))>0) {
                fileOutputStream.write(buf,0,len);
            }

            fileOutputStream.close();
            inputStream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        // check if copy succeed
        Truth.assertThat(testInputAudio.exists()).isTrue();

        File testOutputAudio = new File(InstrumentationRegistry.getInstrumentation().getTargetContext().getFilesDir(), "test_audio.wav");
        if (testOutputAudio.exists()) {
            testOutputAudio.delete();
        }

        FFmpegAPI.encodeAudio(testOutputAudio, testInputAudio, "soxr");
//        // wait for ffmpeg to complete, since it is async, we sleep 10 sec here
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        // verify expect output file exist
        Truth.assertThat(testOutputAudio.exists()).isTrue();

    }



}