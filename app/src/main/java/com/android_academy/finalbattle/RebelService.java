package com.android_academy.finalbattle;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class RebelService extends Service implements Handler.Callback {
  private static final String TAG = RebelService.class.getSimpleName();
  private static final int WHAT_MAKE_NETWORK_REQUEST = 101;
  private static final int DELAY_MILLIS = 5 * 1000;
  private Handler handler;
  private HandlerThread handlerThread;
  private PowerManager.WakeLock wl;

  public RebelService() {
  }

  @Override public IBinder onBind(Intent intent) {
    // TODO: Return the communication channel to the service.
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override public void onCreate() {
    Log.d(TAG, "onCreate");
    handlerThread = new HandlerThread("RebelServiceHandlerThread");
    handlerThread.start();
    Looper looper = handlerThread.getLooper();
    handler = new Handler(looper, this);
    handler.sendEmptyMessage(WHAT_MAKE_NETWORK_REQUEST);
    PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
    wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, TAG);
    wl.acquire();

    Notification notification =
        new NotificationCompat.Builder(this).setContentTitle("Truiton Music Player")
            .setTicker("Rebel Services")
            .setContentText("To protect and save the galaxy")
            .setSmallIcon(R.mipmap.ic_launcher)
            .build();
    startForeground(101, notification);
  }

  @Override public boolean handleMessage(Message msg) {
    Log.d(TAG, "handleMessage: Received message. Will try to communicate. Is network active? "
        + StarWarsUtils.isNetworkActive(this));
    Log.d(TAG, "Network call completed? " + StarWarsUtils.doingNetworkCommunication());
    handler.sendEmptyMessageDelayed(WHAT_MAKE_NETWORK_REQUEST, DELAY_MILLIS);
    return true;
  }

  @Override public void onDestroy() {
    wl.release();
    handler.removeCallbacksAndMessages(null);
    handlerThread.quit();
    handler = null;
    handlerThread = null;
  }
}
