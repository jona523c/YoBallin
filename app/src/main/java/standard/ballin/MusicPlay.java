package standard.ballin;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.*;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;


/**
 * Class for creating a global MediaPlayer to be turned on and off in different activities
 * @author Jonas Madsen
 */
public class MusicPlay extends Service implements MediaPlayer.OnPreparedListener {
    private static MediaPlayer mediaPlayer;
    //private static boolean isPlaying = true;
    private static boolean playState = true;
    private static float volume = (float) 1.0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Called when the service is started. Prepares the MediaPlayer asynchronous.
     * @param intent intent recieved with the service start
     * @param flags flags recieved with the service start
     * @param startId startID recieved with the service start
     * @return a enum int if it was successful or not
     */
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer = new MediaPlayer();
        String filename = "android.resource://" + this.getPackageName() + "/raw/music";
        try {
            mediaPlayer.setDataSource(this, Uri.parse(filename));
        } catch (IOException e) {
            Log.e("MusicPlay", "Couldn't find the music for MusicPlay", e);
        }
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.prepareAsync();
        return Service.START_STICKY;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
    }

    /**
     *  Turns on the MediaPlayer
     */
    public static void turnOn() {
        playState = true;
    }

    /**
     * Turns off the MediaPlayer and pauses it.
     */
    public static void turnOff() {
        playState = false;
        pauseAudio();
    }

    /**
     * Pauses the audio
     */
    public static void pauseAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    /**
     * Resumes the audio
     */
    public static void resumeAudio() {
        if (playState) {
            if (mediaPlayer != null) {
                mediaPlayer.start();
            }
        }
    }

    /**
     *  Returns if the MediaPlayer is on or off
     * @return MediaPlayer's playState
     */
    public static boolean getPlayState() {
        return playState;
    }

    /**
     *  Sets the volume of the MediaPlayer
     * @param number the number to set the volume to
     */
    public static void setVolume(float number) {
        volume = number;
        mediaPlayer.setVolume(number, number);
    }

    /**
     *  Returns the current volume of the MediaPlayer
     * @return current volume of MediaPlayer
     */
    public static float getVolume() {
        return volume;
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}
