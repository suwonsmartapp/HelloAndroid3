package com.jsoh.myfirstandroidapp;

import android.app.Application;

import com.facebook.stetho.Stetho;

import io.smooch.core.Smooch;

/**
 * Created by junsuk on 16. 3. 10..
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
        Smooch.init(this, "dqnceuy4luaazx99atempn7nx");
    }
}
