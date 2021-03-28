package com.example.prayerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Athkar extends AppCompatActivity {

    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athkar);
        drawerLayout = findViewById(R.id.drawer_layout);
    }

    public void clickMenu(View view){
        Home.openDrawer(drawerLayout);
    }

    public void clickLogo(View view){
        Home.closeDrawer(drawerLayout);
    }

    public void clickHome(View view){
        Home.redirectActivity(this,Home.class);
    }

    public void clickDashboard(View view){
        Home.redirectActivity(this,Tasbeh.class);
    }

    public void clickAboutUs(View view){
        recreate();
    }
    public void clickSetting(View view){
        Home.redirectActivity(this, setting.class );
    }

    public void clickLogOut(View view){
        logout(this);
    }
    public void logout(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("تسجيل الخروج");
        builder.setMessage("هل تريد تسجيل الخروج؟");

        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finishAffinity();
                Intent intent = new Intent(Athkar.this, Login.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    public void clickMorining(View view){
        Home.redirectActivity(this,morningAthkar.class);
    }
    public void clickNight(View view){
        Home.redirectActivity(this,nightAthkar.class);
    }
    protected void onPause() {
        super.onPause();
        Home.closeDrawer(drawerLayout);
    }
}