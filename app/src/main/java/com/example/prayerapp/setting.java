package com.example.prayerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Switch;
import android.widget.CompoundButton;
import java.util.ArrayList;
import java.util.List;

public class setting extends AppCompatActivity {
    DrawerLayout drawerLayout;
   Spinner spinner1, spinner2, spinner3;
   Button btnSubmit;
  public static int calcMethod, asrMethod,timeMethod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        drawerLayout = findViewById(R.id.drawer_layout);
        btnSubmit=findViewById(R.id.btnSubmit);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        List<String> list = new ArrayList<String>();
        list.add("اثنى عشري");
        list.add("جامعه العلوم الإسلامية بكراتشي");
        list.add("الإتحاد الإسلامي بأمريكا الشمالية");
        list.add("رابطة العالم الإسلامي");
        list.add("أم القرى");
        list.add("الهيئه المصرية االعامة للمساحه");
        list.add("معهد الجيوفيزياء بجامعة طهران");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);


//Switch declerations
        Switch mySwitch = (Switch)findViewById(R.id.on_off_switch);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AudioManager am;
                am= (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
                if (isChecked) {
                    am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    //do something when unchecked
                }
                else
                    am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);

            }

        });



        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calcMethod= (int) spinner1.getSelectedItemId();
                Log.i("calc method",calcMethod+"");
                Home.calcMethod=calcMethod;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Home.calcMethod=4;
            }
        });

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list2 = new ArrayList<String>();
        list2.add("شافعي");
        list2.add("حنفي");

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list2);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                asrMethod= (int) spinner1.getSelectedItemId();
                Log.i("asr method",asrMethod+"");
                Home.asrMethod=asrMethod;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Home.asrMethod=0;
            }
        });

         spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list3 = new ArrayList<String>();
        list3.add("24 ساعة");
        list3.add("12 ساعة");


        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list3);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(dataAdapter3);
 spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
     @Override
     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         timeMethod= (int) spinner1.getSelectedItemId();
         Log.i("time method",timeMethod+"");
         Home.timeformat=timeMethod;

     }

     @Override
     public void onNothingSelected(AdapterView<?> parent) {
         Home.timeformat=1;
     }
 });

    }


    public void clickMenu(View view){
        Home.openDrawer(drawerLayout);
    }

    public void clickLogo(View view){
        Home.closeDrawer(drawerLayout);
    }

    public void clickHome(View view){
        Home.redirectActivity(this, Home.class);
    }

    public void clickDashboard(View view){
        Home.redirectActivity(this , Tasbeh.class);
    }

    public void clickAboutUs(View view){
        Home.redirectActivity(this, Athkar.class);
    }

    public void logout(View view){
        Home.logout(this);
    }

    public void clickSetting(View view){ recreate(); }

    protected void onPause() {
        super.onPause();
        Home.closeDrawer(drawerLayout);
    }

    public void ChangeMethod(View view) {
        Home.redirectActivity(this, Home.class);
    }
}