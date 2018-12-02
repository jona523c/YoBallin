package standard.ballin;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

public class SoundPlayer {
    public static final int BUTTON = R.raw.button;
    public static final int DEFEAT = R.raw.defeat;
    public static final int VICTORY = R.raw.victory;

    private static SoundPool soundPool;
    private static HashMap<Integer, Integer> soundMap;

    private static float volume = (float) 1.0;
    private static boolean playState = true;

    public static void initSounds(Context context) {
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 100);
        soundMap = new HashMap(3);

        soundMap.put(BUTTON, soundPool.load(context, BUTTON, 1));
        soundMap.put(DEFEAT, soundPool.load(context, DEFEAT, 2));
        soundMap.put(VICTORY, soundPool.load(context, VICTORY, 3));
    }

    public static void playSound(Context context, int soundNumber) {
        if (soundPool == null) {
            initSounds(context);
        }
        if (playState) {
            soundPool.play(soundMap.get(soundNumber), volume, volume, 1, 0, 1f);
        }
    }

    public static void setVolume(float number) {
        volume = number;
    }

    public static float getVolume() {
        return volume;
    }

    public static void turnOn() {
        playState = true;
    }

    public static void turnOff() {
        playState = false;
    }

    public static boolean getPlayState() {
        return playState;
    }
}
