package com.example.prayerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Switch;
import android.widget.CompoundButton;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class setting extends AppCompatActivity {
    DrawerLayout drawerLayout;
   Spinner spinner1, spinner2, spinner3,spinner4;
   Button btnSubmit;
  int calcMethod, asrMethod,timeMethod, silentMethod;
  Calendar c = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        drawerLayout = findViewById(R.id.drawer_layout);
        btnSubmit=findViewById(R.id.btnSubmit);


        //Create Spinner 1 --------------------------------------------------------------------------------------
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

        //Create Spinner 2 --------------------------------------------------------------------------------------

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

        //Create Spinner 3 --------------------------------------------------------------------------------------

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

        //Create Spinner 4 --------------------------------------------------------------------------------------

        spinner4 = (Spinner) findViewById(R.id.spinner4);
        List<String> list4 = new ArrayList<String>();
        list4.add("نظام الرنين");
        list4.add("غير صامت");
        list4.add("15 دقيقة");
        list4.add("30 دقيقة");
        list4.add("45 دقيقة");

        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list4);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4.setAdapter(dataAdapter4);


        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              silentMethod= (int) spinner4.getSelectedItemId();
                Log.i("silent method",silentMethod+"");
                Home.timeformat=silentMethod;

                Long delay;

                switch(silentMethod){
                    //case 0: //turn off Silent
                       // silentOFF();
                       // Toast.makeText(getApplicationContext() , "تم إيقاف الوضع الصامت", Toast.LENGTH_LONG).show();
                       // break;
                    case 2:// 15 minutes 900000
                        delay = (long) 60000;
                        setTime(delay);
                        Toast.makeText(getApplicationContext() , "تم تشغيل الوضع الصامت ل 15 دقيقة", Toast.LENGTH_LONG).show();
                        break;
                    case 3://30 minutes
                        delay = (long) 900000*2;
                        setTime(delay);
                        Toast.makeText(getApplicationContext() , "تم تشغيل الوضع الصامت ل 30 دقيقة", Toast.LENGTH_LONG).show();
                        break;
                    case 4 : //45 minutes
                        delay = (long) 2700000;
                        setTime(delay);
                        Toast.makeText(getApplicationContext() , "تم تشغيل الوضع الصامت ل 45 دقيقة", Toast.LENGTH_LONG).show();
                        break;

                    default :

                }//end switch
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Home.timeformat=1;
            }
        });


        //Switch declerations **************************************DELETE WHEN DONE
        Switch mySwitch = (Switch)findViewById(R.id.on_off_switch);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Context context = getApplicationContext();
                NotificationManager notificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && !notificationManager.isNotificationPolicyAccessGranted()) {

                    Intent intent = new Intent(
                            android.provider.Settings
                                    .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

                    startActivity(intent);
                }
                AudioManager myAudioManager;
                myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

                if (isChecked) {
                    //For Silent
                    myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

                    Toast.makeText(getApplicationContext() , "text", Toast.LENGTH_LONG).show();

                    //do something when unchecked
                }
                else
                    myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                Toast.makeText(getApplicationContext() , "else", Toast.LENGTH_LONG).show();

            }

        });


    }//End on create

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("choice",silentMethod);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        silentMethod=savedInstanceState.getInt("choice");
        spinner4.setSelection(silentMethod);
    }

    //Turn OFF Silent Mode -----------------------------------------------------------------------------------

    public void silentOFF(){
        //Turn of Silent
        Context context = getApplicationContext();
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {

            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

            startActivity(intent);
        }
        AudioManager myAudioManager;
        myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }//End silentOFF

    //Set phone on silent for a specific time --------------------------------------------------------------------

    public void setTime(long time){ //this method would call the broad cast after the desired interval

        System.out.println("in setTime: "+ time);

        //Set Phone on Silent ------------------------------------------------------------------------------------
        Context context = getApplicationContext();
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {

            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

            startActivity(intent);
        }
        AudioManager myAudioManager;
        myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);


        //Set duration to turn off alarm -------------------------------------------------------------------------

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        Log.i("tag","bitch work");
                        silentOFF();
                    }
                }, time);

    }//end setTime


    //Menu related methods
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
}//End class