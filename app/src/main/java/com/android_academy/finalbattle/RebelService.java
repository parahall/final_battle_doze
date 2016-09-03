package com.android_academy.finalbattle;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class RebelService extends Service implements Handler.Callback {
  private static final String TAG = RebelService.class.getSimpleName();
  private static final int WHAT_MAKE_NETWORK_REQUEST = 101;
  private static final int DELAY_MILLIS = 30 * 1000;
  private Handler handler;
  private HandlerThread handlerThread;

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
  }

  @Override public boolean handleMessage(Message msg) {
    Log.d(TAG, "handleMessage: Received message. Will try to communicate. Is network active? "
        + StarWarsUtils.isNetworkActive(this));
    Log.d(TAG, "Network call completed? " + StarWarsUtils.doingNetworkCommunication());
    handler.sendEmptyMessageDelayed(WHAT_MAKE_NETWORK_REQUEST, DELAY_MILLIS);
    return true;
  }

  @Override public void onDestroy() {
    handler.removeCallbacksAndMessages(null);
    handlerThread.quit();
    handler = null;
    handlerThread = null;
  }
}
