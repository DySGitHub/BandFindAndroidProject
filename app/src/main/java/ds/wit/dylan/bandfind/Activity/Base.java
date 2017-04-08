package ds.wit.dylan.bandfind.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import ds.wit.dylan.bandfind.R;
import ds.wit.dylan.bandfind.Main.BandFind;

public class Base extends AppCompatActivity {
    public static BandFind app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (BandFind) getApplication();
    }

    protected void goToActivity(Activity current, Class<? extends Activity> activityClass, Bundle bundle) {
        Intent newActivity = new Intent(current, activityClass);
        if (bundle != null) newActivity.putExtras(bundle);
        current.startActivity(newActivity);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    public void menuHome(MenuItem home) {
        goToActivity(this, Home.class, null);
    }
}
