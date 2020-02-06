package com.example.myproj_13_07;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "LibDB";

    public static final String KEY_ID = "_id";
    public static final String TABLE_RACKS = "Racks";
    public static final String TABLE_OBJ = "Obj";

    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_RACKSNUMBER = "RacksNumber";
    public static final String COLUMN_SHELFSNUMBER = "ShelfsNumber";
    public static final String COLUMN_KEEPFORM = "KeepForm";
    public static final String COLUMN_TYPE = "Type";
    public static final String COLUMN_AUTOR = "Autor";
    public static final String COLUMN_AVAILABILITY = "Availability";
    public static final String COLUMN_NOTES = "Notes";

    public static final String[] dataType = {"Книга", "Журнал", "Диск", "Прочее"};
    public static final String[] dataAvailability = {"Дома", "Выдано"};

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Racks (" +
                "_id INTEGER NOT NULL UNIQUE PRIMARY KEY AUTOINCREMENT," +
                "RacksNumber INTEGER NOT NULL UNIQUE," +
                "Shelfs      INTEGER NOT NULL)");

        db.execSQL("CREATE TABLE Obj (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL," +
                "Name         TEXT  NOT NULL," +
                "RacksNumber  INTEGER NOT NULL," +
                "ShelfsNumber INTEGER NOT NULL," +
                "KeepForm     INTEGER NOT NULL," +
                "Type         INTEGER NOT NULL," +
                "Autor        TEXT," +
                "Availability INTEGER," +
                "Notes        TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE " + TABLE_OBJ);
        onCreate(db);
    }
}
