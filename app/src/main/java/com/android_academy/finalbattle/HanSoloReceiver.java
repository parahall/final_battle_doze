package com.android_academy.finalbattle;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class HanSoloReceiver extends WakefulBroadcastReceiver {
  public static final int REQUEST_CODE = 101;
  public static final int LOCATION_REQUEST_CODE = 102;
  public static final String ACTION = "com.android_academy.final_battle.han_solo_perform_gps";
  private static final String TAG = HanSoloReceiver.class.getSimpleName();

  @Override public void onReceive(Context context, Intent intent) {
    LocationManager locationService =
        (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

    Intent hanSoloLocationIntent = new Intent(context, NowIntentService.class);
    PendingIntent pendingIntent =
        PendingIntent.getService(context, LOCATION_REQUEST_CODE, hanSoloLocationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT);

    if (checkPermission(context)) return;

    Log.d(TAG, "onReceive: Requesting single location");
    Criteria criteria = new Criteria();
    criteria.setAccuracy(Criteria.ACCURACY_FINE);
    String provider = locationService.getBestProvider(criteria, true);
    locationService.requestSingleUpdate(provider, pendingIntent);
  }

  private boolean checkPermission(Context context) {
    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      Log.e(TAG, "checkPermission: No permission for location!",
          new WTFexception("WTF WHERE IS MY PERMISSIONS!"));
      // TODO: Consider calling
      //    ActivityCompat#requestPermissions
      // here to request the missing permissions, and then overriding
      //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
      //                                          int[] grantResults)
      // to handle the case where the user grants the permission. See the documentation
      // for ActivityCompat#requestPermissions for more details.
      return true;
    }
    return false;
  }
}
