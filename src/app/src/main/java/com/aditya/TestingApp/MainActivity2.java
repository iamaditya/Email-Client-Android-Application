package com.aditya.TestingApp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    EditText ed1;
    ProgressBar pb;
    EditText ed2;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ed1 = findViewById(R.id.editTextText5);
        ed2 = findViewById(R.id.editTextTextPassword);
        pb = findViewById(R.id.progressBar);
        handler = new Handler();
        pb.setVisibility(View.GONE);
    }

    public void composeMail(View view){
        boolean flag = false;
        String xusername = ed1.getText().toString();
        String xpassword = ed2.getText().toString();


        if(xusername.length() ==0){
            ed1.setError("Please Enter Email");
        }else{
            String emailPattern = "^[A-Za-z0-9._]+@gmail.com$";
            if(xusername.matches(emailPattern)){
                flag = true;
            }else{
                ed1.setError("Please Enter Valid Email");
            }
        }

        if(xpassword.length()<10){
            ed2.setError("Please Enter Valid Password");
        }

        if(flag){
            pb.setVisibility(View.VISIBLE);
            view.setEnabled(false);


            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                    intent.putExtra("xusername",xusername);
                    intent.putExtra("xpassword",xpassword);
                    startActivity(intent);

                    pb.setVisibility(View.GONE);
                    view.setEnabled(true);

                }
            }, 2000);
        }

    }
}