package com.example.prayerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

                if (!isEmail(user)){
                    Toast.makeText(Register.this,"صيغة خاطئة للبريد الالكتروني",Toast.LENGTH_SHORT).show();
                } else {
                    if (db.checkExist(user)){
                        Toast.makeText(Register.this,"البريد الالكتروني موجود سابقا",Toast.LENGTH_SHORT).show();
                    }else {
                        if (pwd.length() < 8){
                            Toast.makeText(Register.this,"كلمة السر قصيرة ، يجب ان تكون اطول من 8 أحرف",Toast.LENGTH_SHORT).show();
                        }else {
                            if(pwd.equals(cpwd)){
                                long val = db.addUser(user,pwd);
                                if(val > 0){
                                    Toast.makeText(Register.this,"تم إنشاء الحساب بنجاح",Toast.LENGTH_SHORT).show();
                                    Intent moveToLogin = new Intent(Register.this,Login.class);
                                    startActivity(moveToLogin);
                                }
                                else{
                                    Toast.makeText(Register.this,"خطأ في انشاء الحساب",Toast.LENGTH_SHORT).show();
                                }

                            }
                            else{
                                Toast.makeText(Register.this,"كلمة السر لا تطابق التأكيد",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });
    }

    public boolean isEmail(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}