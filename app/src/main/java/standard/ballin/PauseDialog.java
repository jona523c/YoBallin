package standard.ballin;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;


/**
 * Dialog class for the pause menu.
 * @author Jonas Madsen
 */
public class PauseDialog {
    /**
     * Called to create and show the dialog
     * @param activity activity calling the dialog
     */
    public void showDialog(final Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.pause_dialog);

        // Unpauses game and dismisses dialog
        Button resumeButton = (Button) dialog.findViewById(R.id.resume_button);
        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPlayer.playSound(activity, SoundPlayer.BUTTON);
                GameActivity.getGame().startGame();
                dialog.dismiss();
            }
        });

        // Creates a restart confirmation dialog
        Button restartButton = (Button) dialog.findViewById(R.id.restart_button);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPlayer.playSound(activity, SoundPlayer.BUTTON);
                RestartDialog restartDialog = new RestartDialog();
                restartDialog.showDialog(activity, dialog);
            }
        });

        // Starts a new settings activity
        Button settingsButton = (Button) dialog.findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPlayer.playSound(activity, SoundPlayer.BUTTON);
                Intent intent = new Intent (activity, SettingsActivity.class);
                activity.startActivity(intent);
            }
        });

        // Dismisses the GameActivity and dialog
        Button quitButton = (Button) dialog.findViewById(R.id.quit_button);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPlayer.playSound(activity, SoundPlayer.BUTTON);
                dialog.dismiss();
                activity.finish();
            }
        });

        dialog.show();
    }
}