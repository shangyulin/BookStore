package com.shang.admin.bookstore.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.shang.admin.bookstore.Bean.BookSubject;
import com.shang.admin.bookstore.Bean.SimpleBookBean;

import java.util.LinkedList;
import java.util.List;

public class BookDB {

    private Context context;
    private final SQLiteDatabase db;
    private static final String TABLE = "books";
    private final BookDBHelper helper;

    public BookDB(Context context) {
        this.context = context;
        helper = new BookDBHelper(context);
        db = helper.getWritableDatabase();
    }

    public void insert(BookSubject book) {
        if (!helper.tabIsExist(TABLE)){
            for (int i = 0; i < book.getBooks().size(); i++) {
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
        }else{
            Log.d("giant", "数据已经存在，无需重复添加");
        }
    }

    public List<SimpleBookBean> query() {
        List<SimpleBookBean> list = new LinkedList<>();
        Cursor cursor = db.query(TABLE, null, null, null, null, null, null);
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
