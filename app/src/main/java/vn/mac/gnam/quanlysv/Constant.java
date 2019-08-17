package vn.mac.gnam.quanlysv;

public interface Constant {
    // Database Version
     int DATABASE_VERSION = 1;

    // Database Name
     String DATABASE_NAME = "UserManager.db";

    // User table name
    String TABLE_USER = "user";

        // User Table Columns names
        String COLUMN_USER_ID = "user_id";
        String COLUMN_USER_NAME = "user_name";
        String COLUMN_USER_EMAIL = "user_email";
        String COLUMN_USER_PASSWORD = "user_password";

        // create table sql query
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

        // drop table sql query
        String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    // Class table name
    String TABLE_CLASS = "Lop";

    String COLUMN_CLASS_ID = "class_id";
    String COLUMN_CLASS_CODE = "class_code";
    String COLUMN_CLASS_NAME = "class_name";

    String CREATE_TABLE_CLASS = "CREATE TABLE " + TABLE_CLASS + "(" +
            "" + COLUMN_CLASS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            "" + COLUMN_CLASS_CODE + " TEXT," +
            "" + COLUMN_CLASS_NAME + " TEXT" + ")";

    // drop table sql query
    String DROP_CLASS_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    // Student table name
    String TABLE_STUDENT = "student";

        String COLUMN_SV_ID = "student_id";
        String COLUMN_SV_ADDRESS = "student_address";
        String COLUMN_SV_NAME = "student_name";
        String COLUMN_SV_CLASS = "student_class";

        // create table sql query
        String CREATE_SV_TABLE = "CREATE TABLE " + TABLE_STUDENT + "(" +
                "" + COLUMN_SV_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                "" + COLUMN_SV_ADDRESS + " TEXT," + COLUMN_SV_NAME + " TEXT," +
                "" + COLUMN_SV_CLASS + " TEXT" +")";

        // drop table sql query
        String DROP_SV_TABLE = "DROP TABLE IF EXISTS " + TABLE_STUDENT;
}
