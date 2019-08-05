package com.jhhscm.platform.tool;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

public class BroadcastUtils {

    public static final String LOGIN_INVALID_ACTION = "greatness.oyo.login_invalid_action";

    public static final String UMENG_MESSAGE_REGISTRATIONID = "greatness.oyo.umeng_message_registrationid";

    public static void registerLoginInvalidBroadcast(Context context,
                                                     BroadcastReceiver receiver) {
        registerReceiver(context, receiver, LOGIN_INVALID_ACTION);
    }

    public static void sendLoginInvalidBroadCast(Context context) {
        sendLocalBroadCast(context, LOGIN_INVALID_ACTION);
    }

    public static void sendLoginInvalidBroadCast(Context context, String extraMsg) {
        sendLocalBroadCast(context, LOGIN_INVALID_ACTION, extraMsg);
    }

    public static void sendLocalBroadCast(Context context, String action) {
        Intent intent = new Intent(action);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager
                .getInstance(context);
        localBroadcastManager.sendBroadcast(intent);
    }

    public static void registerPushAgentBroadcast(Context context,
                                                  BroadcastReceiver receiver) {
        registerReceiver(context, receiver, UMENG_MESSAGE_REGISTRATIONID);
    }

    public static void sendPushAgentBroadCast(Context context, String extraMsg) {
        sendLocalBroadCast(context, UMENG_MESSAGE_REGISTRATIONID, extraMsg);
    }

    public static void sendLocalBroadCast(Context context, String action, String extraMsg) {
        Intent intent = new Intent(action);
        intent.putExtra("EXTRA_MSG", extraMsg);
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager
                .getInstance(context);
        localBroadcastManager.sendBroadcast(intent);
    }

    public static void registerReceiver(Context context,
                                        BroadcastReceiver receiver, String... actions) {
        IntentFilter intentFilter = new IntentFilter();
        for (String action : actions) {
            intentFilter.addAction(action);
        }
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager
                .getInstance(context);
        localBroadcastManager.registerReceiver(receiver, intentFilter);
    }

    public static void unregisterReceiver(Context context,
                                          BroadcastReceiver receiver) {
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager
                .getInstance(context);
        localBroadcastManager.unregisterReceiver(receiver);
    }
}
