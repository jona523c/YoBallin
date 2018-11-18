package standard.ballin;

import android.content.Context;
import android.media.*;


/**
 * Class for creating a global MediaPlayer to be turned on and off in different activities
 * @author Jonas Madsen
 */
public class MusicPlay {
    public static MediaPlayer mediaPlayer;
    public static boolean isAudioOn = false;

    /**
     * Creates a MediaPlayer for audio and starts playing it
     */
    public static void playAudio(Context c, int id) {
        mediaPlayer = MediaPlayer.create(c, id);
        boolean audioNotPlaying = !mediaPlayer.isPlaying();
        if (audioNotPlaying) {
            isAudioOn = true;
            mediaPlayer.start();
        }
    }

    /**
     * Pauses the audio
     */
    public static void pauseAudio() {
        isAudioOn = false;
        mediaPlayer.pause();
    }


    /**
     * Resumes the audio
     */
    public static void resumeAudio() {
        isAudioOn = true;
        mediaPlayer.start();
    }

    /**
     * Returns if audio is playing or not
     */
    public static boolean isPlaying() {
        return isAudioOn;
    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
