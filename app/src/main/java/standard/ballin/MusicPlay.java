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

    @Override
    public void onCreate() {
        super.onCreate();
    }

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
        //isPlaying = true;
        return Service.START_STICKY;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
    }

    public static void turnOn() {
        playState = true;
    }

    public static void turnOff() {
        playState = false;
        pauseAudio();
    }

    /**
     * Pauses the audio
     */
    public static void pauseAudio() {
        //isPlaying = false;
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    /**
     * Resumes the audio
     */
    public static void resumeAudio() {
        if (playState) {
            //isPlaying = true;
            if (mediaPlayer != null) {
                mediaPlayer.start();
            }
        }
    }

    public static boolean getPlayState() {
        return playState;
    }

    public static void setVolume(float number) {
        volume = number;
        mediaPlayer.setVolume(number, number);
    }

    public static float getVolume() {
        return volume;
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}
