package com.example.meetyou.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String databaseName = "signup.db";

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("CREATE TABLE allusers(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email TEXT," +
                "password TEXT," +
                "verificated INTEGER DEFAULT 0," +
                "gender TEXT," +
                "find TEXT," +
                "name TEXT," +
                "age INTEGER," +
                "bio TEXT(150)," +
                "height INTEGER," +
                "weight INTEGER," +
                "photo1 BLOB," +
                "photo2 BLOB," +
                "photo3 BLOB," +
                "photo4 BLOB," +
                "photo5 BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int i, int i1) {
        MyDatabase.execSQL("DROP TABLE IF EXISTS allusers");
        onCreate(MyDatabase);
    }

    public long insertData(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("verificated", 0);
        long result = MyDatabase.insert("allusers", null, contentValues);
        return result;
    }

    public long insertBio(long userID, String name, int age, String bio) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("age", age);
        contentValues.put("bio", bio);
        long result = MyDatabase.update("allusers", contentValues, "id=?", new String[]{String.valueOf(userID)});
        return result;
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM allusers WHERE email = ?", new String[]{email});
        return cursor.getCount() > 0;
    }

    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT * FROM allusers WHERE email = ? AND password = ?", new String[]{email, password});
        return cursor.getCount() > 0;
    }
    public long insertPhoto(long userID, int photoNumber, byte[] photoData) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String columnName = "photo" + photoNumber;
        contentValues.put(columnName, photoData);
        long result = MyDatabase.update("allusers", contentValues, "id=?", new String[]{String.valueOf(userID)});
        return result;
    }

    public byte[] getPhoto(long userID, int photoNumber) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        String columnName = "photo" + photoNumber;
        Cursor cursor = MyDatabase.rawQuery("SELECT " + columnName + " FROM allusers WHERE id=?", new String[]{String.valueOf(userID)});
        if (cursor.moveToFirst()) {
            return cursor.getBlob(0);
        }
        return null;
    }

    public boolean checkAllPhotosUploaded(long userID) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery("SELECT photo1, photo2, photo3, photo4, photo5 FROM allusers WHERE id=?", new String[]{String.valueOf(userID)});
        if (cursor.moveToFirst()) {
            byte[] photo1 = cursor.getBlob(0);
            byte[] photo2 = cursor.getBlob(1);
            byte[] photo3 = cursor.getBlob(2);
            byte[] photo4 = cursor.getBlob(3);
            byte[] photo5 = cursor.getBlob(4);
            return photo1 != null && photo2 != null && photo3 != null && photo4 != null && photo5 != null;
        }
        return false;
    }
}
