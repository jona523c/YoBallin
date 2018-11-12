package standard.ballin;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.media.MediaDrm;
import android.widget.FrameLayout;

// This is the game class.
public class Game extends FrameLayout implements SensorEventListener {
    public Game(Context context) {
        super(context);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
