package com.example.administrator.anyview;

import android.app.Application;
import android.os.Environment;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Beta.autoInit = true;
        Beta.autoCheckUpgrade = true;
        Beta.initDelay = 1 * 1000;
        Beta.storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        Bugly.init(getApplicationContext(), "0e44f55670", true);
    }
}
