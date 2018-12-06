package standard.ballin;

import android.app.*;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

/**
 * Dialog class for the victory.
 * @author Jonas Madsen
 */
class FinishDialog {
    /**
     * Called to create and show the dialog
     * @param activity activity calling the dialog
     * @param bundle bundle containing the stars earned in the level, key "stars"
     */
    void showDialog(final Activity activity, Bundle bundle, final Bundle bundle2){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);

        // Determines the layout of the finish dialog based on the stars earned
        int stars = bundle.getInt("stars");
        switch (stars) {
            case 1: dialog.setContentView(R.layout.finish_one_dialog);
                    break;
            case 2: dialog.setContentView(R.layout.finish_two_dialog);
                    break;
            case 3: dialog.setContentView(R.layout.finish_three_dialog);
                    break;
        }
        Log.d("FinishDialog", "Stars in Finishdialog was set to: "+stars);

        Button nextButton = dialog.findViewById(R.id.resume_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPlayer.playSound(activity, SoundPlayer.BUTTON);
                dialog.dismiss();

                Intent intent = new Intent(activity, GameActivity.class);
                intent.putExtra("selectedLevel", bundle2.getInt("level"));
                activity.startActivity(intent);
                activity.finish();
            }
        });

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
