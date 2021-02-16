package com.example.prayerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText email,pass,confpass;
    Button btn;
    DbManager db;
    TextView link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DbManager(this);
        link = (TextView)findViewById(R.id.link);

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(Register.this, Login.class);
                startActivity(loginIntent);
            }
        });

        email=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.pass);
        confpass = (EditText)findViewById(R.id.confpass);

        btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = email.getText().toString().trim();
                String pwd = pass.getText().toString().trim();
                String cpwd = confpass.getText().toString().trim();

                if(pwd.equals(cpwd)){
                    long val = db.addUser(user,pwd);
                    if(val > 0){
                        Toast.makeText(Register.this,"You have registered",Toast.LENGTH_SHORT).show();
                        Intent moveToLogin = new Intent(Register.this,Login.class);
                        startActivity(moveToLogin);
                    }
                    else{
                        Toast.makeText(Register.this,"Registeration Error",Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(Register.this,"Password is not matching",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}