// Copyright 2016 Google, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
////////////////////////////////////////////////////////////////////////////////

package com.android_academy.finalbattle;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.text.TextUtils;
import android.util.Log;
import com.android_academy.finalbattle.StarWarsUtils.LukeDecision;

public class ConnectivityChangeReceiver extends WakefulBroadcastReceiver {

  private static final String TAG = ConnectivityChangeReceiver.class.getSimpleName();

  @Override public void onReceive(Context context, Intent intent) {
    if (intent == null) {
      Log.w(TAG, "onReceive: intent null");
      return;
    }

    String action = intent.getAction();
    if (TextUtils.isEmpty(action) || !ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
      Log.w(TAG, "onReceive: wrong action");
      return;
    }

    Log.d(TAG, "onReceive: Connectivity changed. Retrying network");
    retryNetworkRequest(context);
  }

  private void retryNetworkRequest(Context context) {
    LukeDecision decisionToRetry = StarWarsUtils.getDecisionToRetry(context);
    Log.d(TAG, "retryNetworkRequest: decisionToRetry: " + decisionToRetry.name());
    StarWarsUtils.makeDecision(decisionToRetry, context);
  }
}
