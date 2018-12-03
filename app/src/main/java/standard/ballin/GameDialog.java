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
    private int oneStar, twoStar, threeStar;

    public GameDialog(int oneStar, int twoStar, int threeStar) {
        this.oneStar = oneStar;
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

        TextView oneStarText = (TextView) dialog.findViewById(R.id.oneStar);
        oneStarText.setText(oneStar+":00");

        TextView twoStarText = (TextView) dialog.findViewById(R.id.twoStar);
        twoStarText.setText(twoStar+":00");

        TextView threeStarText = (TextView) dialog.findViewById(R.id.threeStar);
        threeStarText.setText(threeStar+":00");

        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                GameActivity.getGame().startGame();
            }
        });
    }
}