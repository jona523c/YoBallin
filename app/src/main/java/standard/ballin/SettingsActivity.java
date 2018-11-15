package standard.ballin;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {
    Switch musicSwitch;
    SeekBar musicBar;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        musicSwitch = (Switch) findViewById(R.id.switch2);
        musicSwitch.setChecked(MusicPlay.isPlaying());
        musicSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MusicPlay.isPlaying()) {
                    MusicPlay.pauseAudio();
                    musicSwitch.setChecked(MusicPlay.isPlaying());
                } else {
                    MusicPlay.resumeAudio();
                    musicSwitch.setChecked(MusicPlay.isPlaying());
                }
            }
        });

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

    public void backButton(View view) {
        finish();
    }
}
