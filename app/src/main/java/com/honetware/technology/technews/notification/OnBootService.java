package com.honetware.technology.technews.notification;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import utils.Utils;

public class OnBootService extends Service {
    public OnBootService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.startAlarm(getApplicationContext(),true,false);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }
}
