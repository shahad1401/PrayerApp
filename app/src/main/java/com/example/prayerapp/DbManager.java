package com.example.prayerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbManager extends SQLiteOpenHelper{
    private static final String dbName = "prayertimes.db";
    private static final String tName = "users";

    private static final String col1 = "id";
    private static final String col2 = "email";
    private static final String col3 = "password";

    public DbManager(@Nullable Context context) //, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version
    {
        super(context, dbName,  null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table users (id integer primary key AUTOINCREMENT, email text , password text)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + tName);
        onCreate(db);
    }

    public long addUser( String email , String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("email",email); //"em@gmail.com"
        cv.put("password",pass); //1234
        long res = db.insert("users", null , cv);
        db.close();
        return res;
    }

    public boolean checkUser(String username, String password){
        String[] columns = { col1 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = col2 + "=?" + " and " + col3 + "=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(tName,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return  true;
        else
            return  false;
    }
}
