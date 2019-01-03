package com.shang.admin.bookstore;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.shang.admin.bookstore.Bean.BookSubject;
import com.shang.admin.bookstore.Bean.ISBNBookSubject;
import com.shang.admin.bookstore.CacheUtils.LocalCacheUtils;
import com.shang.admin.bookstore.Service.BookService;
import com.shang.admin.bookstore.db.BookDB;
import com.yanzhenjie.permission.AndPermission;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static final String BASE_URL = "https://api.douban.com/v2/book/";
    public static final String ISBN_URL = "https://api.douban.com/v2/book/isbn/:";
    private static final int REQUEST_CODE_SCAN = 12;

    private Observer<BookSubject> observer = new Observer<BookSubject>() {
        @Override
        public void onSubscribe(Disposable d) {
        }

        @Override
        public void onNext(final BookSubject t) {
            books = t.getBooks();
            pb.setVisibility(View.GONE);
            lv.setAdapter(new BookAdapter());
            // 开启线程池，写入缓存
            ThreadPoolManager.getInstance().executed(new Runnable() {
                @Override
                public void run() {
                    // 缓存到本地
                    // LocalCacheUtils.saveObject(t);
                    // 保存到数据库
                    BookDB db = new BookDB(MainActivity.this);
                    db.insert(t);
                }
            });
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onComplete() {
        }
    };


    private ListView lv;
    private List<BookSubject.BooksBean> books;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndPermission.with(this).permission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}).start();

        lv = findViewById(getResources().getIdentifier("lv", "id", getPackageName()));
        pb = findViewById(getResources().getIdentifier("pb", "id", getPackageName()));
        pb.setVisibility(View.VISIBLE);

        // 网络获取
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        BookService movieService = retrofit.create(BookService.class);
        movieService.search(0, 0, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }


    static class ViewHolder {
        ImageView icon;
        TextView title;
        TextView author;
    }


    class BookAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return books.size();
        }

        @Override
        public Object getItem(int position) {
            return books.get(position);
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
                view = View.inflate(MainActivity.this, R.layout.book_item, null);
                holder = new ViewHolder();
                holder.icon = view.findViewById(R.id.icon);
                holder.title = view.findViewById(R.id.title);
                holder.author = view.findViewById(R.id.author);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            Glide.with(MainActivity.this).load(books.get(position).getImages().getSmall()).override(100, 100).into(holder.icon);
            holder.title.setText(books.get(position).getTitle());
            holder.author.setText(books.get(position).getAuthor().get(0));
            return view;
        }
    }


    @SuppressLint("NewApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(
                R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }


    /**
     * 处理actionBar菜单条目的点击事件
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            // TODO

        } else if (item.getItemId() == R.id.action_scanner) {
            startScanner();
        }
        return super.onOptionsItemSelected(item);
    }

    // 条形码扫描
    private void startScanner() {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String isbn = data.getStringExtra(Constant.CODED_CONTENT);
                Log.d("giant", isbn);
                String final_url = ISBN_URL + isbn;
                getISBNBookInfo(final_url);
            }
        }
    }

    private void getISBNBookInfo(String final_url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().get().url(final_url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.d("giant", result);
                Gson gson = new Gson();
                final ISBNBookSubject isbnBookSubject = gson.fromJson(result, ISBNBookSubject.class);
                if (isbnBookSubject != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, ISBNDetailActivity.class);
                            intent.putExtra("book", isbnBookSubject);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }
}
