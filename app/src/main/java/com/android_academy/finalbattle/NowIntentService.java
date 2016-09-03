package com.android_academy.finalbattle;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import com.android_academy.finalbattle.StarWarsUtils.LukeDecision;

public class NowIntentService extends IntentService {

  public static final String TAG = NowIntentService.class.getSimpleName();

  public NowIntentService() {
    super(TAG);
  }

  @Override protected void onHandleIntent(Intent intent) {
    Log.d(TAG, "intent received");
    LukeDecision decision =
        (LukeDecision) intent.getSerializableExtra(LukeDecision.class.getSimpleName());
    if (decision != null) {
      Log.d(TAG, "Taking care of Luke decision");
      makeNetworkCall(decision);
    }

    Location HanSoloLocation = intent.getParcelableExtra(LocationManager.KEY_LOCATION_CHANGED);
    if (HanSoloLocation != null) {
      Log.d(TAG, "Checking if Rebels is here and send Han Solo location");
      checkIfRebelsReady(HanSoloLocation);
    }
  }

  private void makeNetworkCall(LukeDecision decision) {
    boolean isCompleted = false;
    if (StarWarsUtils.isNetworkActive(getApplicationContext())) {
      Log.d(TAG, "Network is active");
      isCompleted = StarWarsUtils.doingNetworkCommunication();
    }

    if (!isCompleted) {
      Log.d(TAG, "Network communication failed. Adding task to retry");
      StarWarsUtils.addRetryTask(decision, this);
      return;
    }

    Intent showResult = new Intent(StarWarsUtils.SHOW_RESULT_ACTION);
    showResult.putExtra(LukeDecision.class.getSimpleName(), decision);
    sendBroadcast(showResult);
  }

  private void checkIfRebelsReady(Location hanSoloLocation) {
    makeNetworkCall(hanSoloLocation);
  }

  private void makeNetworkCall(Location hanSoloLocation) {
    //another network call
  }
}
