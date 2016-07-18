package com.tom.stock;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by tom on 2016/7/18.
 */
public class MyGcmListenerService extends GcmListenerService {
    @Override
    public void onMessageReceived(String s, Bundle bundle) {
        super.onMessageReceived(s, bundle);
        Log.d("MyGcmListenerService", "From "+s);
        Log.d("MyGcmListenerService", "Message "+
                bundle.getString("message"));
    }
}
