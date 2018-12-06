package standard.ballin;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Dialog class for the defeat.
 * @author Jonas Madsen
 */
class DefeatDialog {
    void showDialog(final Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.defeat_dialog);

        // Removes the dialog, removes the old activity and starts a new identical one.
        Button restartButton = dialog.findViewById(R.id.restart_button);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPlayer.playSound(activity, SoundPlayer.BUTTON);
                GameActivity.getGame().restartGame();
                dialog.dismiss();
            }
        });

        // Returns to the main menu
        Button quitButton = dialog.findViewById(R.id.quit_button);
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