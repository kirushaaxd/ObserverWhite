package com.example.observerwhite;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class AudioService extends Service{
    static MediaPlayer player;
    static boolean isRunning = false;
    static boolean changeChapter = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void create(Context context){
        player = MediaPlayer.create(context, R.raw.observer_white_1); //select music file
        player.setLooping(true); //set looping
        player.start();
    }

    public static void create2(Context context){
        player.release();
        player = MediaPlayer.create(context, R.raw.observer_white_2); //select music file
        player.setLooping(true); //set looping
        player.start();
    }


    public void onCreate() {
        player = MediaPlayer.create(this, R.raw.observer_white_1); //select music file
        player.setLooping(true); //set looping
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return Service.START_NOT_STICKY;
    }

    public void onStopCommand(Intent intent, int flags){
        player.stop();
    }

    @Override
    public void onDestroy ()
    {
        super.onDestroy();
        if(player != null)
        {
            try{
                player.stop();
                player.release();
            }
            finally {
                player = null;
            }
        }
    }

}
