package com.shang.admin.bookstore.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.shang.admin.bookstore.Bean.BookSubject;
import com.shang.admin.bookstore.Bean.ISBNBookSubject;
import com.shang.admin.bookstore.Bean.SimpleBookBean;

import java.util.LinkedList;
import java.util.List;

public class BookDB {

    private Context context;
    private static final String TABLE = "books";

    public BookDB(Context context) {
        this.context = context;
    }

    public void insert(BookSubject book) {
        BookDBHelper helper = new BookDBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        for (int i = 0; i < book.getBooks().size(); i++) {
            if ("CNKI数字图书馆个人检索阅读卡(100元面值)".equals(book.getBooks().get(i).getTitle())){
                continue;
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("isbn", book.getBooks().get(i).getIsbn13());
            contentValues.put("title", book.getBooks().get(i).getTitle());
            contentValues.put("subtitle", book.getBooks().get(i).getSubtitle());
            contentValues.put("image", book.getBooks().get(i).getImage());
            contentValues.put("pubdate", book.getBooks().get(i).getPubdate());
            contentValues.put("price", book.getBooks().get(i).getPrice());
            contentValues.put("author", book.getBooks().get(i).getAuthor().toString());
            db.insert(TABLE, null, contentValues);
        }
        Log.d("giant", "数据插入成功");
    }

    public void insert(ISBNBookSubject book) {
        BookDBHelper helper = new BookDBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        if (!helper.tabIsExist(TABLE)) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("isbn", book.getIsbn13());
            contentValues.put("title", book.getTitle());
            contentValues.put("subtitle", book.getSubtitle());
            contentValues.put("image", book.getImage());
            contentValues.put("pubdate", book.getPubdate());
            contentValues.put("price", book.getPrice());
            contentValues.put("author", book.getAuthor().toString());
            db.insert(TABLE, null, contentValues);
            Log.d("giant", "数据插入成功");
        } else {
            Log.d("giant", "数据已经存在，无需重复添加");
        }
    }

    public List<SimpleBookBean> query(String bookname) {
        BookDBHelper helper = new BookDBHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        List<SimpleBookBean> list = new LinkedList<>();
        Cursor cursor = db.query(TABLE, null, "title = ?", new String[]{bookname}, null, null, null);
        while (cursor.moveToNext()) {
            String isbn = cursor.getString(cursor.getColumnIndex("isbn"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String subtitle = cursor.getString(cursor.getColumnIndex("subtitle"));
            String image = cursor.getString(cursor.getColumnIndex("image"));
            String pubdate = cursor.getString(cursor.getColumnIndex("pubdate"));
            String price = cursor.getString(cursor.getColumnIndex("price"));
            String author = cursor.getString(cursor.getColumnIndex("author"));
            SimpleBookBean simpleBookBean = new SimpleBookBean(isbn, title, subtitle, image, pubdate, price, author);
            list.add(simpleBookBean);
        }
        return list;
    }
}
