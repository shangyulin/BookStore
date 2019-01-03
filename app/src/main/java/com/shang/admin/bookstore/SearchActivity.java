package com.shang.admin.bookstore;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shang.admin.bookstore.Bean.SimpleBookBean;
import com.shang.admin.bookstore.db.BookDB;

import java.util.List;

public class SearchActivity extends Activity {

    private EditText et;
    private ImageView icon;
    private List<SimpleBookBean> result;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
        et = findViewById(getResources().getIdentifier("et", "id", getPackageName()));
        icon = findViewById(getResources().getIdentifier("search", "id", getPackageName()));
        lv = findViewById(getResources().getIdentifier("lv", "id", getPackageName()));
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookDB db = new BookDB(SearchActivity.this);
                result = db.query(et.getText().toString());
                Log.d("giant", result.get(0).getTitle());
                lv.setAdapter(new SearchAdapter());
            }
        });
    }

    static class ViewHolder {
        ImageView icon;
        TextView title;
        TextView author;
    }

    class SearchAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return result.size();
        }

        @Override
        public Object getItem(int position) {
            return result.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;
            if (convertView == null) {
                view = View.inflate(SearchActivity.this, R.layout.book_item, null);
                holder = new ViewHolder();
                holder.icon = view.findViewById(R.id.icon);
                holder.title = view.findViewById(R.id.title);
                holder.author = view.findViewById(R.id.author);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            Glide.with(SearchActivity.this).load(result.get(position).getImage()).override(100, 100).into(holder.icon);
            holder.title.setText(result.get(position).getTitle());
            holder.author.setText(result.get(position).getAuthor());
            return view;
        }
    }
}
