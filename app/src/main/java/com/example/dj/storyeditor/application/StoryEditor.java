package com.example.dj.storyeditor.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by DJ on 31-08-2017.
 */

public class StoryEditor extends Application {

    private static StoryEditor sSingleton;

    public static StoryEditor singleton() {
        return sSingleton;
    }

    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        sSingleton = this;
        context = getApplicationContext();
    }

    public Context getContext() {
        return context;
    }
}
