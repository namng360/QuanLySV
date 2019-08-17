package vn.mac.gnam.quanlysv.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.mac.gnam.quanlysv.Constant;
import vn.mac.gnam.quanlysv.model.Lop;

public class ClassDAO implements Constant {
    private DatabaseHelper databaseHelper;

    public ClassDAO(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public void insertLop(Lop lop) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        //contentValues.put(COLUMN_CLASS_ID, lop.getId());
        contentValues.put(COLUMN_CLASS_CODE, lop.getMaLop());
        contentValues.put(COLUMN_CLASS_NAME, lop.getLop());

        sqLiteDatabase.insert(TABLE_CLASS, null, contentValues);
        sqLiteDatabase.close();
    }

    public int updateLop(Lop lop) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CLASS_ID,lop.getId());
        contentValues.put(COLUMN_CLASS_CODE,lop.getMaLop());
        contentValues.put(COLUMN_CLASS_NAME,lop.getLop());
        return db.update(TABLE_CLASS,contentValues,COLUMN_CLASS_ID+"=?",new String[]{String.valueOf(lop.getId())});
    }

    public void deleteLop(Lop lop) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_CLASS, COLUMN_CLASS_ID + " = ?",
                new String[]{String.valueOf(lop.getId())});
        db.close();
    }

//    public List<Lop> getAllLop() {
//        // array of columns to fetch
//        String[] columns = {
//                COLUMN_ClASS_ID,
//                COLUMN_CLASS_NAME
//        };
//        // sorting orders
//        String sortOrder =
//                COLUMN_CLASS_NAME + " ASC";
//        List<Lop> lops = new ArrayList<Lop>();
//
//        SQLiteDatabase db = databaseHelper.getReadableDatabase();
//
//        // query the user table
//
//        Cursor cursor = db.query(TABLE_CLASS, //Table to query
//                columns,    //columns to return
//                null,        //columns for the WHERE clause
//                null,        //The values for the WHERE clause
//                null,       //group the rows
//                null,       //filter by row groups
//                sortOrder); //The sort order
//
//
//        // Traversing through all rows and adding to list
//        if (cursor.moveToFirst()) {
//            do {
//                Lop lop = new Lop();
//
//                lop.setMaLop(cursor.getString(cursor.getColumnIndex(COLUMN_ClASS_ID)));
//                lop.setLop(cursor.getString(cursor.getColumnIndex(COLUMN_CLASS_NAME)));
//
//                // Adding user record to list
//                lops.add(lop);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//
//        // return user list
//        return lops;
//    }

    public List<Lop> getAllLop() {
        List<Lop> lops = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_CLASS;

        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Lop lop = new Lop();
                lop.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_CLASS_ID)));
                lop.setMaLop(cursor.getString(cursor.getColumnIndex(COLUMN_CLASS_CODE)));
                lop.setLop(cursor.getString(cursor.getColumnIndex(COLUMN_CLASS_NAME)));
                lops.add(lop);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return lops;
    }

    public boolean checkClass(String lop) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_CLASS_ID
        };
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_CLASS_NAME + " = ?";

        // selection argument
        String[] selectionArgs = {lop};

        // query user table with condition

        Cursor cursor = db.query(TABLE_CLASS, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public Lop getLopByID(String id) {
        Lop lop = null;
        // xin quyen ghi!!!
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        // Tao cau lenh query voi Cursor
        Cursor cursor = sqLiteDatabase.query
                (TABLE_CLASS, new String[]{COLUMN_CLASS_ID, COLUMN_CLASS_NAME, COLUMN_CLASS_CODE},
                        COLUMN_CLASS_ID + "=?", new String[]{id},
                        null, null, null);

        // kiem tra xem cursor !=null va co chua gia tri
        if (cursor != null && cursor.moveToFirst()) {
            lop.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_CLASS_ID)));
            lop.setMaLop(cursor.getString(cursor.getColumnIndex(COLUMN_CLASS_CODE)));
            lop.setLop(cursor.getString(cursor.getColumnIndex(COLUMN_CLASS_NAME)));

        }

        cursor.close();
        return lop;

    }
}
