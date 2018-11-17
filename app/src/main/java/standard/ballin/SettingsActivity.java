package standard.ballin;

import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.Switch;

/**
 * Activity class for Settings menu
 * @author Jonas Madsen
 */
public class SettingsActivity extends AppCompatActivity {
    Switch musicSwitch;
    SeekBar musicBar;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        // Music toggle on and off.
        musicSwitch = (Switch) findViewById(R.id.switch2);
        musicSwitch.setChecked(MusicPlay.isPlaying());
        musicSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MusicPlay.isPlaying()) {
                    MusicPlay.pauseAudio();
                } else {
                    MusicPlay.resumeAudio();
                }
                musicSwitch.setChecked(MusicPlay.isPlaying());
            }
        });

        // SeekBar that controls volume level (currently for the whole phone)
        musicBar = (SeekBar) findViewById(R.id.musicBar);
        musicBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        musicBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        musicBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onProgressChanged(SeekBar arg0, int progress, boolean arg2)
            {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                        progress, 0);
            }
        });
    }

    /**
     * Called when user clicks the back-arrow, returns to last screen
     * @param view
     */
    public void backButton(View view) {
        finish();
    }
}
