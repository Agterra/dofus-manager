package fr.company.agterra.dofusmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Henri on 29/08/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Database.db";


    private String SQL_CREATE_ENTRIES (String TABLE_NAME) {
        String AllEffect = "";

        for (EffectType type : EffectType.values())
            AllEffect += "," + type.name() + " SMALLINT()";

        String Result = "CREATE TABLE " + TABLE_NAME + " (ID" + " SMALLINT()" + AllEffect + ")";

        return Result;
    }


    private String SQL_DELETE_ENTRIES (String TABLE_NAME) {
        return ("DROP TABLE IF EXISTS " + TABLE_NAME);
    }


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase db) {
        for (ItemType type : ItemType.values())
            db.execSQL(SQL_CREATE_ENTRIES(type.name()));
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (ItemType type : ItemType.values())
            db.execSQL(SQL_DELETE_ENTRIES(type.name()));

        onCreate(db);
    }


    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}