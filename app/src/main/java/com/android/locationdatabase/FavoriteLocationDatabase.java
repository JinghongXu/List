package com.android.locationdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JinghongXu on 5/28/16.
 */
public class FavoriteLocationDatabase extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "CSE110 project.db";
    public static final String TABLE_NAME = "LOCATIONLIST";
    public static final int DATABASE_VERSION = 1;
    public static final String UID = "_id";
    public static final String NAME = "Name";
    public static final String LATITUDE = "Latitude";
    public static final String LONGITUDE = "Longitude";
    public static final String RINGTONE = "RingTone";
    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+NAME+" VARCHAR(255), "+LATITUDE+" VARCHAR(255), "+ LONGITUDE +" VARCHAR(255), "+RINGTONE+" VARCHAR(255));";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS" +TABLE_NAME;
    private List<FavoriteLocation> favoriteLocationList;
    private Context context;

    public FavoriteLocationDatabase(Context context)
    {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        favoriteLocationList = new ArrayList<>();

    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {

        try
        {

            db.execSQL(CREATE_TABLE);

        }

        catch (SQLException e)
        {

            Toast.makeText(context, "Cannot create location database!" + e, Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

        try
        {

            db.execSQL(DROP_TABLE);
            onCreate(db);

        }

        catch (SQLException e)
        {

            Toast.makeText(context, "Cannot create location database!" + e, Toast.LENGTH_LONG).show();

        }

    }

    public void addLocation(String str, Double mLatitude, Double mLongitude){

        FavoriteLocation location = new FavoriteLocation(str, mLatitude, mLongitude);

        boolean contain = false;
        for(int i = 0; i < favoriteLocationList.size(); i++){

            FavoriteLocation l = favoriteLocationList.get(i);
            if(l.equals(location)){
                contain = true;
                break;
            }
        }

        if(!contain)
        {
            favoriteLocationList.add(location);

            ContentValues query = new ContentValues();
            query.put(NAME, str);
            query.put(LATITUDE, mLatitude);
            query.put(LONGITUDE, mLongitude);
            query.put(RINGTONE,"default");

            SQLiteDatabase db = getWritableDatabase();
            long rowAdd = db.insert(TABLE_NAME, null, query);
            db.close();

        }
        else
        {
            Toast.makeText(context, "Location already exists!", Toast.LENGTH_LONG).show();
        }
    }

    public List<FavoriteLocation> getFavoriteLocations()
    {

        return favoriteLocationList;

    }

    public String passedLocation(Location point)
    {

        for (FavoriteLocation location : favoriteLocationList)
        {

            Location check = new Location("Check");
            check.setLatitude(location.getLatitude());
            check.setLongitude(location.getLongitude());

            if (point.distanceTo(check) < 160)
            {

                if (!location.isVisiting())
                {

                    location.setVisit(true);

                    return location.getName();

                }

            }

            else
            {

                location.setVisit(false);

                return null;

            }

        }

        return null;

    }

    public void loadDataBase()
    {

        SQLiteDatabase db = getWritableDatabase();

        /* Iterate through database queries to get column info */
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if(cursor.moveToFirst())
        {

            while(!cursor.isAfterLast())
            {

                String name = cursor.getString(1);
                Double latitude = cursor.getDouble(2);
                Double longitude = cursor.getDouble(3);
                String ringTone = cursor.getString(4);

                cursor.moveToNext();

                FavoriteLocation location = new FavoriteLocation(name, latitude, longitude, ringTone);
                favoriteLocationList.add(location);

            }

        }

    }


    public void editLocation(String str1, Double mLatitude, Double mLongitude, String str2){

        FavoriteLocation location = new FavoriteLocation(str1, mLatitude, mLongitude);
        int index = -1;

        for(int i = 0; i < favoriteLocationList.size(); i++){

            FavoriteLocation l = favoriteLocationList.get(i);
            if(l.equals(location)){
                index = i;
                break;
            }
        }

        if ( index != -1 )
        {
            favoriteLocationList.get(index).RingTone = str2;

            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(RINGTONE,str2);
            String whereClause = NAME+" =? AND "+LATITUDE+" =? AND "+LONGITUDE+" =?";
            String[] whereArgs = new String[]{str1, String.valueOf(mLatitude), String.valueOf(mLongitude)};
            db.update(TABLE_NAME, contentValues, whereClause, whereArgs);
            db.close();
        }
        else
        {
            Toast.makeText(context, "Cannot update location info!", Toast.LENGTH_LONG).show();
        }

    }


    public void delete(){

        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.close();

    }


}
