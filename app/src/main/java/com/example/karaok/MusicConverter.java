package com.example.karaok;

import java.io.File;

public class MusicConverter {

    public static void toMp3Converter(File audioInputFile, File audioOutputFile){
        FFmpegAPI.encodeAudio(audioOutputFile, audioInputFile, "mp3 (liblame)");
    }

    public static void toWavConverter(File audioInputFile, File audioOutputFile) {
        FFmpegAPI.encodeAudio(audioOutputFile, audioInputFile, "soxr");
    }

}
