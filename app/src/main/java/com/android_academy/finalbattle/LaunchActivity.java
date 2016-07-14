package com.android_academy.finalbattle;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LaunchActivity extends AppCompatActivity implements Handler.Callback{

  private static final int WHAT_LAUNCH_MAIN = 101;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_launch);
    Handler handler = new Handler(getMainLooper(),this);
    handler.sendEmptyMessageDelayed(WHAT_LAUNCH_MAIN,4000);

  }

  @Override public boolean handleMessage(Message msg) {
    Intent intent = new Intent(this, FinalBattleActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
    finish();
    return true;
  }
}
