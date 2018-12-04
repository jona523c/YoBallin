package standard.ballin;

import android.app.*;
import android.content.DialogInterface;
import android.view.*;
import android.widget.*;

/**
 * Dialog class for the victory.
 * @author Jonas Madsen
 */
public class GameDialog {
    private int twoStar, threeStar;

    public GameDialog(int twoStar, int threeStar) {
        this.twoStar = twoStar;
        this.threeStar = threeStar;
    }

    /**
     * Called to create and show the dialog
     * @param activity activity calling the dialog
     */
    public void showDialog(final Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.game_dialog);

        TextView twoStarText = (TextView) dialog.findViewById(R.id.twoStar);
        twoStarText.setText("2: "+twoStar+":00");

        TextView threeStarText = (TextView) dialog.findViewById(R.id.threeStar);
        threeStarText.setText("3: "+threeStar+":00");

        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                GameActivity.getGame().startGame();
            }
        });
    }
}