package standard.ballin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class PauseDialog {
    public void showDialog(final Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.pause_dialog);

        Button resumeButton = (Button) dialog.findViewById(R.id.resume_button);

        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameActivity.getGame().startGame();
                dialog.dismiss();
            }
        });

        final Button restartButton = (Button) dialog.findViewById(R.id.restart_button);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestartDialog restartDialog = new RestartDialog();
                restartDialog.showDialog(activity);
            }
        });

        Button settingsButton = (Button) dialog.findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (activity, SettingsActivity.class);
                activity.startActivity(intent);
            }
        });

        Button quitButton = (Button) dialog.findViewById(R.id.quit_button);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent (activity, MainActivity.class);
                activity.startActivity(intent);
            }
        });

        dialog.show();
    }
}