package ds.wit.dylan.bandfind.Main;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ds.wit.dylan.bandfind.Database.DBManager;
import ds.wit.dylan.bandfind.Model.Band;

public class BandFind extends Application {
    public DBManager dbManager = new DBManager(this);

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("Band Find", "BandFind Started");
        dbManager.open();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        dbManager.close();
    }
}