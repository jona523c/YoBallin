package standard.ballin;

import android.app.*;
import android.content.Intent;
import android.view.*;
import android.widget.*;

public class FinishDialog {
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
                Intent intent = new Intent (activity, LevelsActivity.class);
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
