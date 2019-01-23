package com.shang.admin.bookstore;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

public class BookApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
    }
}
