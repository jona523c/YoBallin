package standard.ballin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class LevelsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_levels);
    }

    /** **/
    public void backButton(View view) {
        finish();
    }

    /** **/
    public void levelButton(View view) {
        Intent intent = new Intent (this, GameActivity.class);
        startActivity(intent);
    }
}
