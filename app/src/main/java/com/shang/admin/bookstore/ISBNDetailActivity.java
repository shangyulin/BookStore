package com.shang.admin.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ISBNDetailActivity extends AppCompatActivity {

    private ISBNBookSubject book;
    private ImageView icon;
    private TextView title;
    private TextView author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isbndetail);
        Intent intent = getIntent();
        book = (ISBNBookSubject) intent.getSerializableExtra("book");
        icon = findViewById(getResources().getIdentifier("icon", "id", getPackageName()));
        title = findViewById(getResources().getIdentifier("title", "id", getPackageName()));
        author = findViewById(getResources().getIdentifier("author", "id", getPackageName()));
        if (book != null){
            Glide.with(this).load(book.getImages().getSmall()).into(icon);
            title.setText(book.getTitle());
            author.setText(book.getAuthor().get(0));
        }
    }
}
