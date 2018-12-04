package standard.ballin;

import android.app.*;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

/**
 * Dialog class for the victory.
 * @author Jonas Madsen
 */
public class FinishDialog {
    /**
     * Called to create and show the dialog
     * @param activity activity calling the dialog
     * @param bundle
     */
    public void showDialog(final Activity activity, Bundle bundle){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
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
        LevelsActivity.updateStarsForLevel(bundle);

        Button nextButton = (Button) dialog.findViewById(R.id.resume_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPlayer.playSound(activity, SoundPlayer.BUTTON);
                dialog.dismiss();
                activity.finish();
            }
        });

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
