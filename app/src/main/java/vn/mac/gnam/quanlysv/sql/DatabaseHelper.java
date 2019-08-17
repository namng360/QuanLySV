package vn.mac.gnam.quanlysv.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import vn.mac.gnam.quanlysv.Constant;


public class DatabaseHelper extends SQLiteOpenHelper implements Constant {



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_TABLE_CLASS);
        db.execSQL(CREATE_SV_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_CLASS_TABLE);
        db.execSQL(DROP_SV_TABLE);

        // Create tables again
        onCreate(db);

    }


}
