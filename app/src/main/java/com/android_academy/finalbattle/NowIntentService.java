package com.android_academy.finalbattle;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import com.android_academy.finalbattle.StarWarsUtils.LukeDecision;
import java.io.IOException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

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
      isCompleted = doingNetworkCommunication();
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

  /**
   * Make a network request form STAR_WARS_LOCATION.
   */
  public static boolean doingNetworkCommunication() {
    try {
      URL url = new URL(StarWarsUtils.STAR_WARS_LOCATION);
      HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
      httpsURLConnection.getInputStream();
      Log.d(TAG, "Network call completed.");
      return true;
    } catch (IOException e) {
      Log.e(TAG, "IOException " + e.getMessage());
      return false;
    }
  }

  private void checkIfRebelsReady(Location hanSoloLocation) {
    makeNetworkCall(hanSoloLocation);
  }

  private void makeNetworkCall(Location hanSoloLocation) {
    //another network call
  }
}
