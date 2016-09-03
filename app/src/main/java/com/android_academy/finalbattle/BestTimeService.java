package com.android_academy.finalbattle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.android_academy.finalbattle.StarWarsUtils.LukeDecision;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

public class BestTimeService extends GcmTaskService {
  public static final String LUKE_DECISION = "LUKE_DECISION";
  public static final String HAN_SOLO_LOCATION = "HAN_SOLO_LOCATION";
  private static final String TAG = BestTimeService.class.getSimpleName();

  @Override public int onRunTask(TaskParams taskParams) {
    switch (taskParams.getTag()) {
      case LUKE_DECISION:
        Log.d(TAG, "onRunTask: LUKE_DECISION");
        Bundle extras = taskParams.getExtras();
        LukeDecision decision =
            (LukeDecision) extras.getSerializable(LukeDecision.class.getSimpleName());
        StarWarsUtils.makeDecision(decision, this);
        return GcmNetworkManager.RESULT_SUCCESS;
      case HAN_SOLO_LOCATION:
        Log.d(TAG, "onRunTask: HAN_SOLO_LOCATION");
        Intent intent = new Intent(HanSoloReceiver.ACTION);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        return GcmNetworkManager.RESULT_SUCCESS;
      default:
        return GcmNetworkManager.RESULT_FAILURE;
    }
  }
}
