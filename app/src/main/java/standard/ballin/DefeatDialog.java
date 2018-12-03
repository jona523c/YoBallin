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
public class DefeatDialog {
    public void showDialog(final Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.defeat_dialog);

        // Removes the dialog, removes the old activity and starts a new identical one.
        Button restartButton = (Button) dialog.findViewById(R.id.restart_button);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPlayer.playSound(activity, SoundPlayer.BUTTON);
                GameActivity.getGame().restartGame();
                // TODO: Should be done differently at a later stage
                dialog.dismiss();
                //Intent intent = new Intent (activity, GameActivity.class);
                //intent.putExtra("selectedLevel", 1);
                //activity.finish();
                //activity.startActivity(intent);
            }
        });

        // Returns to the main menu
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