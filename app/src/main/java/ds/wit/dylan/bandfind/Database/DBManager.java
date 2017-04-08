package ds.wit.dylan.bandfind.Database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import ds.wit.dylan.bandfind.Model.Band;

import static ds.wit.dylan.bandfind.Database.DBDesigner.COLUMN_ID;

public class DBManager {
    private SQLiteDatabase db;
    private ds.wit.dylan.bandfind.Database.DBDesigner dbHelper;

    public DBManager(Context context) {
        dbHelper = new ds.wit.dylan.bandfind.Database.DBDesigner(context);
    }

    public void open() throws SQLException {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public void insert(Band b) {
        ContentValues values = new ContentValues();
        values.put(DBDesigner.COLUMN_NAME, b.name);
        values.put(DBDesigner.COLUMN_GENRE, b.genre);
        values.put(DBDesigner.COLUMN_BANDFAV, (b.bandfav == true) ? 1 : 0);
        values.put(DBDesigner.COLUMN_LATITUDE, b.latitude);
        values.put(DBDesigner.COLUMN_LONGITUDE, b.longitude);
        values.put(DBDesigner.COLUMN_ADDRESS, b.address);
        values.put(DBDesigner.COLUMN_PRICE, b.price);
        long lId = db.insert(DBDesigner.TABLE_BAND, null, values);

    }

    public void delete(int id) {
        Log.v("DB", "Band" + id + "Deleted");
        db.delete(ds.wit.dylan.bandfind.Database.DBDesigner.TABLE_BAND, COLUMN_ID + "=" + id, null);
    }

    public void update(Band b) {
        ContentValues values = new ContentValues();
        values.put(DBDesigner.COLUMN_NAME, b.name);
        values.put(DBDesigner.COLUMN_GENRE, b.genre);
        values.put(DBDesigner.COLUMN_BANDFAV, (b.bandfav == true) ? 1 : 0);
        values.put(DBDesigner.COLUMN_LATITUDE, b.latitude);
        values.put(DBDesigner.COLUMN_LONGITUDE, b.longitude);
        values.put(DBDesigner.COLUMN_ADDRESS, b.address);
        values.put(DBDesigner.COLUMN_PRICE, b.price);

        long insertId = db.update(DBDesigner.TABLE_BAND, values, COLUMN_ID + " = " + b.bandId, null);
        Log.v("DB", "" + COLUMN_ID + b.name + b.genre + b.bandfav + b.latitude + b.longitude + b.address + b.price);

    }

    public List<Band> getAll() {
        List<Band> bands = new ArrayList<Band>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBDesigner.TABLE_BAND, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Band pojo = toBand(cursor);
            bands.add(pojo);
            cursor.moveToNext();
        }
        cursor.close();
        return bands;
    }


    public List<Band> getFav() {
        List<Band> bands = new ArrayList<Band>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBDesigner.TABLE_BAND + " WHERE " + DBDesigner.COLUMN_BANDFAV + "=1", null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Band pojo = toBand(cursor);
            bands.add(pojo);
            cursor.moveToNext();
        }
        cursor.close();
        return bands;
    }


    public Band get(int ID) {
        Band pojo = null;
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBDesigner.TABLE_BAND + " WHERE " + COLUMN_ID + "=" + ID, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Band temp = toBand(cursor);
            pojo = temp;
            cursor.moveToNext();
        }
        cursor.close();
        return pojo;
    }


    private Band toBand(Cursor cursor) {
        Band pojo = new Band();
        pojo.bandId = cursor.getInt(0);
        pojo.name = cursor.getString(1);
        pojo.genre = cursor.getString(2);
        pojo.bandfav = (cursor.getInt(3) == 1) ? true : false;
        pojo.latitude = cursor.getDouble(4);
        pojo.longitude = cursor.getDouble(5);
        pojo.address = cursor.getString(6);
        pojo.price = cursor.getDouble(7);
        return pojo;
    }

    public void LoadList() {
        Band b1 = new Band("Daft Punk", "Electronic", false, 53.347695, -6.228422, "3Arena Dublin Ireland", 55.00);
        Band b2 = new Band("Foo Fighters", "Rock", true, 53.709704, -6.561443, "Slane Castle Meath Ireland", 40.00);
        Band b3 = new Band("Adele", "Pop", false, 52.259688, -7.106797, "Theatre Royal Waterford Ireland", 60.00);
        Band b4 = new Band("Eminem", "Rap", true, 51.503238, 0.003133, "O2 Arena London United Kingdom", 50.00);

        insert(b1);
        insert(b2);
        insert(b3);
        insert(b4);
    }

}
