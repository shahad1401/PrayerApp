package com.example.prayerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {


    EditText email;
    EditText pass;
    Button btn;
    TextView rLink;
    DbManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DbManager(this);
        email = (EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.pass);
        btn = (Button)findViewById(R.id.btn);
        rLink = (TextView)findViewById(R.id.rLink);
        rLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerIntent = new Intent(Login.this, Register.class);
                startActivity(registerIntent);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = email.getText().toString().trim();
                String pwd = pass.getText().toString().trim();
                Boolean res = db.checkUser(user, pwd);
                if(res == true)
                {
                    Intent HomePage = new Intent(Login.this,Home.class);
                    startActivity(HomePage);
                }
                else
                {
                    Toast.makeText(Login.this,"Error in email or password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}