package standard.ballin;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.*;
import android.os.IBinder;


/**
 * Class for creating a global MediaPlayer to be turned on and off in different activities
 * @author Jonas Madsen
 */
public class MusicPlay extends Service {
    private static MediaPlayer mediaPlayer;
    private static boolean isAudioOn = false;
    private static boolean isTurnedOff = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        isAudioOn = true;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.start();
        isAudioOn = true;
        return Service.START_STICKY;
    }

    public static void turnOn() {
        isTurnedOff = false;
    }

    public static void turnOff() {
        isTurnedOff = true;
        pauseAudio();
    }

    /**
     * Pauses the audio
     */
    public static void pauseAudio() {
        isAudioOn = false;
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }


    /**
     * Resumes the audio
     */
    public static void resumeAudio() {
        if (!isTurnedOff) {
            isAudioOn = true;
            if (mediaPlayer != null) {
                mediaPlayer.start();
            }
        }
    }

    public static boolean isTurnedOff() {
        return isTurnedOff;
    }


    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}
