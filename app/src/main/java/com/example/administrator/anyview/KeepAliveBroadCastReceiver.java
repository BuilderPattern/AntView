package com.example.administrator.anyview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class KeepAliveBroadCastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        if ("com.example.testBroadCastReceiver".equals(intent.getAction())){
            intent = new Intent(context, KeepAliveService.class);
            context.startService(intent);
        }else if ("alarm_action".equals(intent.getAction())){

        }
    }
}
