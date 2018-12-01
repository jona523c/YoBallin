package standard.ballin;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

public class SoundEffectPLayer {
    public static final int BUTTON = R.raw.button;
    public static final int DEFEAT = 2;
    public static final int VICTORY = 3;

    private static SoundPool soundPool;
    private static HashMap<Integer, Integer> soundMap;

    public static void initSounds(Context context) {
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 100);
        soundMap = new HashMap(3);

        soundMap.put(BUTTON, soundPool.load(context, BUTTON, 1));
     //   soundMap.put(DEFEAT, soundPool.load(context, DEFEAT, 2));
     //   soundMap.put(VICTORY, soundPool.load(context, VICTORY, 3));
    }

    public static void playSound(Context context, int soundNumber) {
        if (soundPool == null) {
            initSounds(context);
        }
        float volume = (float) 1.0;
        soundPool.play(soundMap.get(soundNumber), volume, volume, 1, 0, 1f);
    }
}
