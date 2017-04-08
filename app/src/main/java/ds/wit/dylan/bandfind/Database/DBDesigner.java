package ds.wit.dylan.bandfind.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBDesigner extends SQLiteOpenHelper {
    public static final String TABLE_BAND = "table_band";
    public static final String COLUMN_ID = "bandID";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_BANDFAV = "bandfav";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_PRICE = "price";

    private static final String DATABASE_NAME = "newband.db";
    private static final int DATABASE_VERSION = 4;

    // Database creation sql statement

    private static final String DATABASE_CREATE_TABLE_BAND = "CREATE TABLE " + TABLE_BAND + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT," + COLUMN_GENRE + " TEXT,"
            + COLUMN_BANDFAV + " INTEGER," + COLUMN_LATITUDE + " DOUBLE," + COLUMN_LONGITUDE + " DOUBLE," +
            COLUMN_ADDRESS + " TEXT," + COLUMN_PRICE + " DOUBLE" + ");";


    public DBDesigner(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_TABLE_BAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBDesigner.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BAND);
        onCreate(db);
    }
}