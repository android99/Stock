package com.tom.stock;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.tom.stock.backend.myApi.MyApi;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    MyApi api = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (api==null){
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(getString(R.string.endpoint_url_dev))
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                            request.setDisableGZipContent(true);
                        }
                    });
            api = builder.build();
        }
        new Thread(){
            @Override
            public void run() {
                try {
                    String result = api.sayHi("Tom").execute().getData();
                    Log.d(TAG, result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new TestTask().execute("Hank");
    }

    class TestTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            try {
                String result = api.sayHi(params[0]).execute().getData();
                Log.d(TAG, result);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void login(View v){
        String userid = ((EditText)findViewById(R.id.userid)).getText().toString();
        String passwd = ((EditText)findViewById(R.id.passwd)).getText().toString();
        if (userid.equals("jack") && passwd.equals("1234")){
            setResult(RESULT_OK);
            finish();
        }else{

        }
    }
}
