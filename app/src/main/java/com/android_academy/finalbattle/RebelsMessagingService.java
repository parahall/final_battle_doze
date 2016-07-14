package com.android_academy.finalbattle;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class RebelsMessagingService extends FirebaseMessagingService {
  @Override public void onMessageReceived(RemoteMessage remoteMessage) {
    Intent intent = new Intent(HanSoloReceiver.ACTION);
    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
  }
}
