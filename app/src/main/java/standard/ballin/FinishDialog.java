package standard.ballin;

import android.app.*;
import android.content.Intent;
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
     */
    public void showDialog(final Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.finish_dialog);
        
        Button nextButton = (Button) dialog.findViewById(R.id.resume_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                activity.finish();
            }
        });

        Button quitButton = (Button) dialog.findViewById(R.id.quit_button);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                activity.finish();
            }
        });

        dialog.show();
    }
}
