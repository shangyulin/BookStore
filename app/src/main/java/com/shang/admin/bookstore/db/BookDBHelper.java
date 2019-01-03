package com.shang.admin.bookstore.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BookDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "book.db";

    public static final int DB_VERSION = 1;

    public static final String TABLE_BOOK = "books";

    //创建 students 表的 sql 语句
    private static final String BOOK_CREATE_TABLE_SQL = "create table " + TABLE_BOOK + "("
            + "isbn integer primary key,"
            + "title varchar(100) not null,"
            + "subtitle varchar(500),"
            + "image varchar(200) not null,"
            + "pubdate varchar(20) not null,"
            + "price double not null,"
            + "author varchar(100) not null"
            + ");";


    public BookDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BOOK_CREATE_TABLE_SQL);
        Log.d("giant", "数据库创建成功");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 判断某张表是否存在
     *
     * @param tabName 表名
     * @return
     */
    public boolean tabIsExist(String tabName) {
        boolean result = false;
        if (tabName == null) {
            return false;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();//此this是继承SQLiteOpenHelper类得到的
            String sql = "select count(*) as c from sqlite_master where type ='table' and name =" + tabName.trim();
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return result;
    }
}
