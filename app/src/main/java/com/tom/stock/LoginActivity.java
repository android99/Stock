package com.tom.stock;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
