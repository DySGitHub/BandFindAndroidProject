package ds.wit.dylan.bandfind.Activity;

import ds.wit.dylan.bandfind.R;
import ds.wit.dylan.bandfind.Activity.Home;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;

public class SScreen extends Activity {

    boolean backPress;
    private static int SPLASH_TIME = 3000;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sscreen);

        new Handler().postDelayed(new Runnable() {
            @Override

                public void run() {
                    startActivity(new Intent(SScreen.this, Home.class));
                    finish();
                }
            }, SPLASH_TIME);

        }


    @Override
    public void onBackPressed() {
        backPress = true;
        super.onBackPressed();
    }
}
