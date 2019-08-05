package com.example.administrator.anyview;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import com.example.anyview.R;

public class KeepAliveService extends Service{
    NotificationManager mNotificationManager;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification.Builder builder = new Notification.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon))
                .setSmallIcon(R.mipmap.app_icon)
                .setContentTitle("title")
                .setContentText("例子")
                .setSubText("subTitle")
                .setAutoCancel(false)
                .setContentIntent(pendingIntent)
                .setWhen(System.currentTimeMillis());

        /**
         * 为了规范通知栏，在26以后必须创建通知渠道
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){//sdk>=26
            NotificationChannel notificationChannel = new NotificationChannel("com.example.anyview", "AnyView", NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(notificationChannel);
            builder.setChannelId("com.example.anyview");
        }

        startForeground(1, builder.build());//前台通知，通过stopForeground(true)关闭
//        mNotificationManager.notify(1, notification);//普通通知
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("--------", "onBind");
        return null;
    }


    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("--------", "onUnBind");
//        stopForeground(true);
        intent = new Intent("com.example.testBroadCastReceiver");
        sendBroadcast(intent);
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.e("--------", "onDestroy");
//        stopForeground(true);
        Intent intent = new Intent("com.example.testBroadCastReceiver");
        sendBroadcast(intent);
        super.onDestroy();
    }
}
