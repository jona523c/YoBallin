package standard.ballin;

import android.content.Context;
import android.media.*;


public class MusicPlay {
    public static MediaPlayer mediaPlayer;
    //private static SoundPool soundPool;
    public static boolean isPlayingAudio = false;

    // Creates a new mediaPlayer to play audio and starts it.
    public static void playAudio(Context c, int id) {
        mediaPlayer = MediaPlayer.create(c, id);
        //soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        //soundPool = new SoundPool.Builder()
        //        .setAudioAttributes(new AudioAttributes.Builder().setLegacyStreamType(AudioManager.STREAM_MUSIC).build())
        //        .build();

        boolean audioNotPlaying = !mediaPlayer.isPlaying();
        if (audioNotPlaying) {
            isPlayingAudio = true;
            mediaPlayer.start();
        }
    }

    // Pauses the audio
    public static void pauseAudio() {
        isPlayingAudio = false;
        mediaPlayer.pause();
    }

    // Resumes the audio
    public static void resumeAudio() {
        isPlayingAudio = true;
        mediaPlayer.start();
    }

    // Returns whether music is playing or not
    public static boolean isPlaying() {
        return isPlayingAudio;
    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
