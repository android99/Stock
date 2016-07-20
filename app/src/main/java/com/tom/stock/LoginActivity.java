package com.tom.stock;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.tom.stock.backend.myApi.MyApi;
import com.tom.stock.backend.myApi.model.LoginValidation;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    MyApi api = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (api==null){
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/regApi")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                            request.setDisableGZipContent(true);
                        }
                    });
            api = builder.build();
        }
//        new TestTask().execute("Tom");
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);

    }

    class TestTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                result = api.sayHi(params[0]).execute().getData();
                Log.d("LoginActivity", result);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
    }

    public void login(View v){
        String userid = ((EditText)findViewById(R.id.userid)).getText().toString();
        String passwd = ((EditText)findViewById(R.id.passwd)).getText().toString();
        new LoginTask().execute(userid, passwd);
        /*if (userid.equals("jack") && passwd.equals("1234")){
                        setResult(RESULT_OK);
                        finish();
                    }else{
                }*/
    }

    class LoginTask extends AsyncTask<String, Void, LoginValidation> {
        @Override
        protected LoginValidation doInBackground(String... params) {
            LoginValidation result = null;
            try {
                result = api.login(params[0], params[1]).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(LoginValidation loginValidation) {
            Log.d("LoginActivity", loginValidation.getMessage());
            if (loginValidation.getResultCode()==1){
                setResult(RESULT_OK);
                finish();
            }
        }
    }
}