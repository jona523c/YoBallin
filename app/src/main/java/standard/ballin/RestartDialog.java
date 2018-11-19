package standard.ballin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Dialog class for the restart confirmation.
 * @author Jonas Madsen
 */
public class RestartDialog {
    /**
     * Called to create and show the dialog
     * @param activity activity calling the dialog
     * @param oldDialog the dialog that prompted this dialog
     */
    public void showDialog(final Activity activity, final Dialog oldDialog) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.restart_dialog);

        // Removes the dialog, removes the old activity and starts a new identical one.
        Button yesButton = (Button) dialog.findViewById(R.id.yes_button);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameActivity.getGame().restartGame();
                // TODO: Should be done differently at a later stage
                oldDialog.dismiss();
                dialog.dismiss();
            }
        });

        // Dismiss the dialog
        Button noButton = (Button) dialog.findViewById(R.id.no_button);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
