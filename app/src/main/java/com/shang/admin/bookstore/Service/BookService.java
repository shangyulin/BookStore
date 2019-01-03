package com.shang.admin.bookstore.Service;

import com.shang.admin.bookstore.Bean.BookSubject;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookService {

    //获取豆瓣图书列表
    /**
     * BASE_URL = https://api.douban.com/v2/book
     * FINAL_URL = https://api.douban.com/v2/book/search?q=0&start=0&count=10
     */
    @GET("search")
    Observable<BookSubject> search(@Query("q") int q, @Query("start") int start, @Query("count") int count);
}