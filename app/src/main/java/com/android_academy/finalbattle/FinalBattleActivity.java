package com.android_academy.finalbattle;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.android_academy.finalbattle.StarWarsUtils.LukeDecision;

public class FinalBattleActivity extends AppCompatActivity implements View.OnClickListener {

  public static final int ONE_MINUTE = 60 * 1000;
  private static final String TAG = FinalBattleActivity.class.getSimpleName();
  private LukeDecisionResultReceiver receiver;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_final_battle);
    findViewById(R.id.kill_button).setOnClickListener(this);
    findViewById(R.id.light_side_button).setOnClickListener(this);

    scheduleHanSoloReport();

    receiver = new LukeDecisionResultReceiver();
    registerReceiver(receiver, new IntentFilter(StarWarsUtils.SHOW_RESULT_ACTION));
  }

  @Override protected void onDestroy() {
    unregisterReceiver(receiver);
    super.onDestroy();
  }

  @Override public void onClick(View v) {
    switch (v.getId()) {
      case R.id.kill_button:
        Log.d(TAG, "onClick: Kill Darth Vader");
        StarWarsUtils.makeDecision(LukeDecision.LUKE_KILL_DARTH_VADER, this);
        break;
      case R.id.light_side_button:
        Log.d(TAG, "onClick: Stay on the light! I love you, father!");
        StarWarsUtils.makeDecision(LukeDecision.STAY_ON_LIGHT_SIDE, this);
        break;
    }
  }

  private void scheduleHanSoloReport() {
    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    Intent intent = new Intent(HanSoloReceiver.ACTION);
    PendingIntent broadcast = PendingIntent.getBroadcast(this, HanSoloReceiver.REQUEST_CODE, intent,
        PendingIntent.FLAG_UPDATE_CURRENT);
    Log.d(TAG, "scheduleHanSoloReport: Alarm scheduled");
    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), ONE_MINUTE,
        broadcast);
  }

  private void showLukeLoveFather() {
    setImageResult(R.drawable.light_side_result);
  }

  private void showLukeDarkSith() {
    setImageResult(R.drawable.dark_luke);
  }

  private void setImageResult(@DrawableRes int drawable) {
    ImageView imageView = (ImageView) findViewById(R.id.result_view);
    imageView.setVisibility(View.VISIBLE);
    findViewById(R.id.star_wars_layout).setVisibility(View.GONE);
    imageView.setImageDrawable(ContextCompat.getDrawable(this, drawable));
  }

  @Override public void onBackPressed() {
    View imageResult = findViewById(R.id.result_view);
    if (imageResult.getVisibility() == View.VISIBLE) {
      imageResult.setVisibility(View.GONE);
      findViewById(R.id.star_wars_layout).setVisibility(View.VISIBLE);
    } else {
      super.onBackPressed();
    }
  }

  private class LukeDecisionResultReceiver extends BroadcastReceiver {

    @Override public void onReceive(Context context, Intent intent) {
      LukeDecision decision =
          (LukeDecision) intent.getSerializableExtra(LukeDecision.class.getSimpleName());
      switch (decision) {
        case LUKE_KILL_DARTH_VADER:
          showLukeDarkSith();
          break;
        case STAY_ON_LIGHT_SIDE:
          showLukeLoveFather();
          break;
      }
    }
  }
}
