package standard.ballin;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

class SoundPlayer {
    static final int BUTTON = R.raw.button;
    static final int DEFEAT = R.raw.defeat;
    static final int VICTORY = R.raw.victory;

    private static SoundPool soundPool;
    private static HashMap<Integer, Integer> soundMap;

    private static float volume = (float) 1.0;
    private static boolean playState = true;

    /**
     *  Initializes the sounds in the SoundPool.
     * @param context context its called from
     */
    static void initSounds(Context context) {
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 100);
        soundMap = new HashMap<Integer, Integer>(3);

        soundMap.put(BUTTON, soundPool.load(context, BUTTON, 1));
        soundMap.put(DEFEAT, soundPool.load(context, DEFEAT, 2));
        soundMap.put(VICTORY, soundPool.load(context, VICTORY, 3));
    }

    /**
     *  If sounds aren't initialized, will initialize them. Plays the specified sound.
     * @param context  context its called from
     * @param soundNumber  the song to be played, is specified by enums
     */
    static void playSound(Context context, int soundNumber) {
        if (soundPool == null) {
            initSounds(context);
        }
        if (playState) {
            soundPool.play(soundMap.get(soundNumber), volume, volume, 1, 0, 1f);
        }
    }

    /**
     *  Sets the volume of the SoundPool
     * @param number the number to set the volume to
     */
    static void setVolume(float number) {
        volume = number;
    }

    /**
     *  Returns the current volume of the SoundPool
     * @return current volume of MediaPlayer
     */
    static float getVolume() {
        return volume;
    }

    /**
     *  Turns on the SoundPool
     */
    static void turnOn() {
        playState = true;
    }

    /**
     * Turns off the SoundPool
     */
    static void turnOff() {
        playState = false;
    }

    /**
     *  Returns if the SoundPool is on or off
     * @return SoundPool's playState
     */
    static boolean getPlayState() {
        return playState;
    }

    /**
     *  Releases the SoundPool
     */
    static void release() {
        soundPool.release();
    }
}
