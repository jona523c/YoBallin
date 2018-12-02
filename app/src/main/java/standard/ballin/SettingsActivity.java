package standard.ballin;

import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;

/**
 * Activity class for Settings menu
 * @author Jonas Madsen
 */
public class SettingsActivity extends AppCompatActivity {
    ImageView backButton;
    Switch musicSwitch, soundSwitch;
    SeekBar musicBar, soundBar;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        // Sound toggle on and off.
        soundSwitch = (Switch) findViewById(R.id.soundSwitch);
        soundSwitch.setChecked(SoundPlayer.getPlayState());
        soundSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SoundPlayer.getPlayState()) {
                    SoundPlayer.turnOff();
                } else {
                    SoundPlayer.turnOn();
                }
                soundSwitch.setChecked(SoundPlayer.getPlayState());
            }
        });

        // Music toggle on and off.
        musicSwitch = (Switch) findViewById(R.id.musicSwitch);
        musicSwitch.setChecked(MusicPlay.getPlayState());
        musicSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MusicPlay.getPlayState()) {
                    MusicPlay.turnOff();
                    MusicPlay.pauseAudio();
                } else {
                    MusicPlay.turnOn();
                    MusicPlay.resumeAudio();
                }
                musicSwitch.setChecked(MusicPlay.getPlayState());
            }
        });

        backButton = (ImageView) findViewById(R.id.backView);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // SeekBar that controls volume level (currently for the whole phone)
        musicBar = (SeekBar) findViewById(R.id.musicBar);
        musicBar.setMax(10);
        musicBar.setProgress(convertFromFloatToInt(MusicPlay.getVolume()));
        Log.d("MusicPlay", "musicBar progress has been set to "+convertFromFloatToInt(MusicPlay.getVolume()));
        musicBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
            {
                Log.d("MusicPlay", "Volume has been set to "+convertFromIntToFloat(progress));
                MusicPlay.setVolume(convertFromIntToFloat(progress));
            }
        });

        soundBar = (SeekBar) findViewById(R.id.soundBar);
        soundBar.setMax(10);
        soundBar.setProgress(convertFromFloatToInt(SoundPlayer.getVolume()));
        Log.d("SoundPlayer", "soundBar progress has been set to "+convertFromFloatToInt(SoundPlayer.getVolume()));
        soundBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
            {
                Log.d("SoundPlayer", "Volume has been set to "+convertFromIntToFloat(progress));
                SoundPlayer.setVolume(convertFromIntToFloat(progress));
            }
        });
    }

    @Override
    protected void onResume() {
        if (MusicPlay.getPlayState()) {
            MusicPlay.resumeAudio();
        }
        super.onResume();
    }

    @Override
    protected void onPause () {
        super.onPause();
    }

    private float convertFromIntToFloat(int intVal) {
        float floatVal = .1f * intVal;
        return floatVal;
    }

    private int convertFromFloatToInt(float floatVal) {
        int intVal = (int) (floatVal/0.1);
        return intVal;
    }
}
