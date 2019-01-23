package com.shang.admin.bookstore;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.shang.admin.bookstore.Bean.BookSubject;
import com.shang.admin.bookstore.Bean.ISBNBookSubject;
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

public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener, View.OnClickListener {

    public static final String BASE_URL = "https://api.douban.com/v2/book/";
    public static final String ISBN_URL = "https://api.douban.com/v2/book/isbn/:";

    private ViewPager vp;
    private AppBarLayout abl_bar;
    private View tl_expand, tl_collapse;
    private static final int REQUEST_CODE_SCAN = 12;

    private Observer<BookSubject> observer = new Observer<BookSubject>() {
        @Override
        public void onSubscribe(Disposable d) {
        }

        @Override
        public void onNext(final BookSubject t) {
            books = t.getBooks();
            lv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            lv.setAdapter(new MyInnerAdapter());
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


    private RecyclerView lv;
    private List<BookSubject.BooksBean> books;
    private ImageView search;
    private ImageView search2;
    private ImageView scanner;
    private ImageView scanner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndPermission.with(this).permission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}).start();

        lv = findViewById(getResources().getIdentifier("lv", "id", getPackageName()));

        tl_expand = findViewById(R.id.tl_expand);
        tl_collapse = findViewById(R.id.tl_collapse);
        search = findViewById(R.id.iv_search);
        search2 = findViewById(R.id.iv_search2);
        scanner = findViewById(R.id.iv_scanner);
        scanner2 = findViewById(R.id.iv_scanner2);
        vp = findViewById(R.id.vp);
        vp.setAdapter(new MyViewPagerAdapter());

        abl_bar = findViewById(R.id.abl_bar);
        abl_bar.addOnOffsetChangedListener(this);
        search.setOnClickListener(this);
        search2.setOnClickListener(this);
        scanner.setOnClickListener(this);
        scanner2.setOnClickListener(this);


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

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int offset = Math.abs(verticalOffset);
        int total = appBarLayout.getTotalScrollRange();
        if (offset <= total * 0.45) {
            tl_expand.setVisibility(View.VISIBLE);
            tl_collapse.setVisibility(View.GONE);
        } else {
            tl_expand.setVisibility(View.GONE);
            tl_collapse.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_search:
            case R.id.iv_search2:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;
            case R.id.iv_scanner:
            case R.id.iv_scanner2:
                startScanner();
                break;
        }
    }


    class MyInnerAdapter extends RecyclerView.Adapter<MyInnerAdapter.MyHolder> {

        @NonNull
        @Override
        public MyInnerAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = View.inflate(MainActivity.this, R.layout.book_item, null);
            return new MyHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyInnerAdapter.MyHolder myHolder, int position) {
            Glide.with(MainActivity.this).load(books.get(position).getImages().getSmall()).override(100, 100).into(myHolder.icon);
            myHolder.title.setText(books.get(position).getTitle());
            myHolder.author.setText(books.get(position).getAuthor().get(0));
        }

        @Override
        public int getItemCount() {
            return books.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {

            private ImageView icon;
            private TextView title;
            private TextView author;

            public MyHolder(@NonNull View itemView) {
                super(itemView);
                icon = itemView.findViewById(R.id.icon);
                title = itemView.findViewById(R.id.title);
                author = itemView.findViewById(R.id.author);
            }
        }
    }

    // 条形码扫描
    private void startScanner() {
        Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCAN);
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
                // 将扫描的书也保存到数据库
                addIntoDB(isbnBookSubject);
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


    public void addIntoDB(ISBNBookSubject bookBean) {
        BookDB db = new BookDB(MainActivity.this);
        db.insert(bookBean);
    }

    class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            TextView textView = new TextView(MainActivity.this);
            textView.setText("这是第" + position + "页");
            textView.setTextColor(Color.RED);
            textView.setGravity(Gravity.CENTER);
            container.addView(textView);
            return textView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
