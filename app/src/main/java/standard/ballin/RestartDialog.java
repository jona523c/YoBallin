package standard.ballin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class RestartDialog {
    public void showDialog(final Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.restart_dialog);

        Button yesButton = (Button) dialog.findViewById(R.id.yes_button);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Restart
            }
        });

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
