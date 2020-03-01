package com.pixelhub.contactssqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbAdapter {

    public static int dbversion =2;
    public static String dbname = "ContactsDB";
    public static String dbTable = "contacts";

    public static class DatabaseHelper extends SQLiteOpenHelper{


        public DatabaseHelper(@Nullable Context context) {
            super(context, dbname, null, dbversion);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE "+dbTable+" (_id INTEGER PRIMARY KEY autoincrement,name, number, email, address, UNIQUE(number))");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+dbTable);
            onCreate(db);
        }
    }

    //establsh connection with SQLiteDataBase
    private final Context c;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase sqlDb;

    public DbAdapter(Context context) {
        this.c = context;
    }
    public DbAdapter open() throws SQLException {
        dbHelper = new DatabaseHelper(c);
        sqlDb = dbHelper.getWritableDatabase();
        return this;
    }

    //insert data
    public void insert(String text2,String text3,String text4,String text5) {
        if(!isExist(text3)) {
            sqlDb.execSQL("INSERT INTO contacts (name,number,email,address) VALUES('"+text2+"','"+text3+"','"+text4+"','"+text5+"')");
        }
    }
    //check entry already in database or not
    public boolean isExist(String num){
        String query = "SELECT number FROM contacts WHERE number='"+num+"' LIMIT 1";
        Cursor row = sqlDb.rawQuery(query, null);
        return row.moveToFirst();
    }

    //edit data
    public void update(int id, String text2, String text3, String text4, String text5) {
        sqlDb.execSQL("UPDATE "+dbTable+" SET name='"+text2+"', number='"+text3+"', email='"+text4+"', address='"+text5+"',   WHERE _id=" + id);
    }

    //delete data
    public void delete(int id) {
        sqlDb.execSQL("DELETE FROM "+dbTable+" WHERE _id="+id);
    }

    //fetch data
    public Cursor fetchAllData() {
        String query = "SELECT * FROM "+dbTable;
        Cursor row = sqlDb.rawQuery(query, null);
        if (row != null) {
            row.moveToFirst();
        }
        return row;
    }

    //fetch data by filter
//    public Cursor fetchdatabyfilter(String inputText,String filtercolumn) throws SQLException {
//        Cursor row = null;
//        String query = "SELECT * FROM "+dbTable;
//        if (inputText == null  ||  inputText.length () == 0)  {
//              row = sqlDb.rawQuery(query, null);
//        }else {
//            //query = "SELECT * FROM "+dbTable+" WHERE "+filtercolumn+" like '%"+inputText+"%'";
//            query = "SELECT * FROM "+dbTable+" WHERE "+filtercolumn+" like '"+inputText+"%'";
//            row = sqlDb.rawQuery(query, null);
//        }
//        if (row != null) {
//            row.moveToFirst();
//        }
//        return row;
//    }

    public Cursor fetchdatabyfilter(String inputText,String filtercolumn) throws SQLException {
        Cursor row = null;
        if (inputText != null  ||  inputText.length () < 0)  {
            //query = "SELECT * FROM "+dbTable+" WHERE "+filtercolumn+" like '%"+inputText+"%'";
            String query = "SELECT * FROM "+dbTable+" WHERE "+filtercolumn+" like '"+inputText+"%'";
            row = sqlDb.rawQuery(query, null);
        }
        if (row != null) {
            row.moveToFirst();
        }
        return row;
    }
}