package com.tom.stock;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.tom.stock.backend.registration.Registration;

import java.io.IOException;

/**
 * Created by tom on 2016/7/18.
 */
public class RegistrationIntentService extends IntentService {
    public RegistrationIntentService(){
        super("RegistrationIntentService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID instanceID = InstanceID.getInstance(this);
        try {
            String token = instanceID.getToken(getString(R.string.gcm_sender_id),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.d("MyInstanceID", token);
            sendRegistrationToServer(token);
            //subscribe
            GcmPubSub sub = GcmPubSub.getInstance(this);
            sub.subscribe(token, "/topics/global", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRegistrationToServer(final String token) {
        final Registration api =
                new Registration.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                .setRootUrl("https://regal-habitat-137623.appspot.com/_ah/api")
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                        request.setDisableGZipContent(true);
                    }
                }).build();
        new Thread(){
            @Override
            public void run() {
                try {
                    api.register(token).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
