package com.example.karaok;

import android.media.MediaPlayer;

public class VolumeMixer {

    private MediaPlayer mediaPlayer;

    private float musicVolume = 1.0f;
    private float liveEffectVolume = 1.0f;

    public VolumeMixer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public void setMusicVolume(float volume) {
        musicVolume = volume;
        mediaPlayer.setVolume(musicVolume, musicVolume);
    }

    public void setLiveEffectVolume(float volume) {
        liveEffectVolume = volume;
        LiveEffectEngine.setGain(liveEffectVolume);
    }

    public float getLiveEffectVolume() {
        return liveEffectVolume;
    }

    public float getMusicVolume() {
        return musicVolume;
    }
}
