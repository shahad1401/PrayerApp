package com.example.prayerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

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

    public void logout(View view){
        Home.logout(this);
    }

    protected void onPause() {
        super.onPause();
        Home.closeDrawer(drawerLayout);
    }
}