package standard.ballin;

import android.content.Context;
import android.media.*;

public class MusicPlay {
    public static MediaPlayer mediaPlayer;
    private static SoundPool soundPool;
    public static boolean isPlayingAudio=false;

    public static void playAudio(Context c, int id) {
        mediaPlayer = MediaPlayer.create(c,id);
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
        if(!mediaPlayer.isPlaying())
        {
            isPlayingAudio = true;
            mediaPlayer.start();
        }
    }

    public static void stopAudio() {
        isPlayingAudio = false;
        mediaPlayer.stop();
    }

    public static boolean isPlaying() {
        return isPlayingAudio;
    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
