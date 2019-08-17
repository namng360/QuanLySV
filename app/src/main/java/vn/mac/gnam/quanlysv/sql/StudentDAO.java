package vn.mac.gnam.quanlysv.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.mac.gnam.quanlysv.Constant;
import vn.mac.gnam.quanlysv.model.Lop;
import vn.mac.gnam.quanlysv.model.SinhVien;

public class StudentDAO implements Constant {
    private DatabaseHelper databaseHelper;
    private ClassDAO classDAO;

    public StudentDAO(DatabaseHelper databaseHelper) {

        this.databaseHelper = databaseHelper;
        this.classDAO = new ClassDAO(this.databaseHelper);
    }

    public void addSv(SinhVien sinhVien) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(COLUMN_SV_ID, sinhVien.getId());
        values.put(COLUMN_SV_NAME, sinhVien.getTen());
        values.put(COLUMN_SV_CLASS, sinhVien.getLop());
        values.put(COLUMN_SV_ADDRESS, sinhVien.getDiaChi());

        // Inserting Row
        db.insert(TABLE_STUDENT, null, values);
        db.close();
    }

    public int updateSv(SinhVien sinhVien) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SV_ID, sinhVien.getId());
        values.put(COLUMN_SV_NAME, sinhVien.getTen());
        values.put(COLUMN_SV_CLASS, sinhVien.getLop());
        values.put(COLUMN_SV_ADDRESS, sinhVien.getDiaChi());
        return db.update(TABLE_STUDENT,values,COLUMN_SV_ID+"=?",new String[]{String.valueOf(sinhVien.getId())});
    }

    public void deleteSv(SinhVien sinhVien) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_STUDENT, COLUMN_SV_ID + " = ?",
                new String[]{String.valueOf(sinhVien.getId())});
        db.close();
    }

    public List<SinhVien> getAllSv() {
        List<SinhVien> sinhViens = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_STUDENT;

        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                SinhVien sinhVien = new SinhVien();
                sinhVien.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_SV_ID)));
                sinhVien.setTen(cursor.getString(cursor.getColumnIndex(COLUMN_SV_NAME)));
                sinhVien.setLop(cursor.getString(cursor.getColumnIndex(COLUMN_SV_CLASS)));
                sinhVien.setTenLop(GetClassNameById(cursor.getString(cursor.getColumnIndex(COLUMN_SV_CLASS))));
                sinhVien.setDiaChi(cursor.getString(cursor.getColumnIndex(COLUMN_SV_ADDRESS)));
                sinhViens.add(sinhVien);
            } while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return sinhViens;
    }

    String GetClassNameById(String lopId) {
        List<Lop> classes = this.classDAO.getAllLop();
        for (Lop lo : classes) {
            int lop_Id = lo.getId();
            if (lopId.equals(String.valueOf(lop_Id))){
                return lo.getLop();
            }
        }
        return "";
    }

    public SinhVien getSvByID(String id) {
        SinhVien sinhVien = null;
        // xin quyen ghi!!!
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        // Tao cau lenh query voi Cursor
        Cursor cursor = sqLiteDatabase.query
                (TABLE_STUDENT, new String[]{COLUMN_SV_ID, COLUMN_SV_NAME, COLUMN_SV_CLASS, COLUMN_SV_ADDRESS},
                        COLUMN_SV_ID + "=?", new String[]{id},
                        null, null, null);

        // kiem tra xem cursor !=null va co chua gia tri
        if (cursor != null && cursor.moveToFirst()) {
            sinhVien.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_SV_ID)));
            sinhVien.setTen(cursor.getString(cursor.getColumnIndex(COLUMN_SV_NAME)));
            sinhVien.setLop(cursor.getString(cursor.getColumnIndex(COLUMN_SV_CLASS)));
            sinhVien.setDiaChi(cursor.getString(cursor.getColumnIndex(COLUMN_SV_ADDRESS)));

        }

        cursor.close();
        return sinhVien;

    }
}
