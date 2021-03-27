package com.example.prayerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static java.lang.Integer.parseInt;


public class Home extends AppCompatActivity {

    public static int timeformat=0 , calcMethod=0, asrMethod=0 , highlats=1 , offset=0;
    //initialize variable
    Button btLocation;
    static double lat;
    static double lang;
    String country, locality, address;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    static TextView fajr;
    static TextView duhr;
    static TextView asr;
    static TextView maghrb;
    static TextView isha;
    FusedLocationProviderClient fusedLocationProviderClient;
    DrawerLayout drawerLayout;
String[] prayer;
    public static final String CHANNEL_1_ID = "channel1";
    NotificationManager manager;
    static ArrayList<String> prayerTimesNotify = new ArrayList<String>();
    static ArrayList<String> prayerNamesNotify = new ArrayList<String>();
     Random random = new Random();
    private Calendar c = Calendar.getInstance();
//     int m = random.nextInt(9999 - 1000) + 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//assign variable
        btLocation = findViewById(R.id.bt_location);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        fajr = findViewById(R.id.fajr);
        duhr = findViewById(R.id.duhr);
        asr = findViewById(R.id.asr);
        duhr = findViewById(R.id.duhr);
        maghrb = findViewById(R.id.maghrb);
        isha = findViewById(R.id.isha);
        //initialize fusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //__________________Did this my self so the location will be updated oninit ___________________________________
        //check permission
        if (ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //when permission granted
            getLocation();
        } else {
            //when permission denied
            ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//check permission
                if (ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //when permission granted
                    getLocation();
                } else {
                    //when permission denied
                    ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
        drawerLayout = findViewById(R.id.drawer_layout);

       boolean isExist= getIntent().getBooleanExtra("setting",false);
       if(isExist){
           timeformat= getIntent().getIntExtra("timeformat",0);
           calcMethod = getIntent().getIntExtra("calcemethod",0);
           asrMethod = getIntent().getIntExtra("asrMethod",0);
           highlats=getIntent().getIntExtra("highlats",0);
           offset= getIntent().getIntExtra("offset",0);
           System.out.println("timeformat" + timeformat);
       }

        getLocation();
        createNotification();

    } // end on create

    public void prayersTime(int calcmethod, int asrMethod, int timeformat , int highlats , int offset) {
        PrayTime prayers = new PrayTime();

        Log.i("Prayers :","Prayers");

        prayers.setTimeFormat(timeformat); // 12 (1)
        prayers.setCalcMethod(calcmethod); //um alqora 4
        Log.i("calc method",prayers.getCalcMethod()+"");
        prayers.setAsrJuristic(asrMethod); // Shafii (standard) (0)
        Log.i("AsrJuristic method",prayers.getAsrJuristic()+"");
        prayers.setAdjustHighLats(highlats); //none
        Log.i("AdjustHighLats method",prayers.getAdjustHighLats()+"");
        int[] offsets = {offset, offset, offset, offset, offset, offset, offset}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
        prayers.tune(offsets);

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
       double timezone=prayers.getBaseTimeZone();

        ArrayList<String> prayerTimes = prayers.getPrayerTimes(cal, lat, lang, timezone);
        ArrayList<String> prayerNames = prayers.getTimeNames();

        for (int i = 0; i < prayerTimes.size(); i++) {
            System.out.println(prayerNames.get(i) + " - " + prayerTimes.get(i));
            // prayer[i]= prayerTimes.get(i);
        }

// time for notification fajr
//        prayerTimesNotify.add(prayerTimes.get(0));
//        prayerNamesNotify.add(prayerNames.get(0));
//        // duhr
//        prayerTimesNotify.add(prayerTimes.get(2));
//        prayerNamesNotify.add(prayerNames.get(2));
//        //asr
//        prayerTimesNotify.add(prayerTimes.get(3));
//        prayerNamesNotify.add(prayerNames.get(3));
//        //magrb
//        prayerTimesNotify.add(prayerTimes.get(5));
//        prayerNamesNotify.add(prayerNames.get(5));
//        //isha
//        prayerTimesNotify.add(prayerTimes.get(6));
//        prayerNamesNotify.add(prayerNames.get(6));

        //setting the values on the screen
        fajr.setText(prayerNames.get(0)+": "+ prayerTimes.get(0));
        duhr.setText(prayerNames.get(2)+": "+ prayerTimes.get(2));
        asr.setText(prayerNames.get(3)+": "+ prayerTimes.get(3));
        maghrb.setText(prayerNames.get(5)+": "+ prayerTimes.get(5));
        isha.setText(prayerNames.get(6)+": "+ prayerTimes.get(6));
        //Log.i("duhr",prayerNames.get(0)+": "+ prayerTimes.get(0));
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                System.out.println("im in ouuut");

                Toast.makeText(getApplicationContext(),"im in complete" , Toast.LENGTH_LONG);
                //initialize location
                Location location = task.getResult();
                if (location != null) {
                    try {
                        //initialize geoCoder
                        Geocoder geocoder = new Geocoder(Home.this, Locale.getDefault());
                        //initialize address list
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1);


                        //Set Latitude on TextView
                        lat= addresses.get(0).getLatitude();
                        //Log.i("Latitude",lat+"");

                         //Set Longitude on TextView
                        lang= addresses.get(0).getLongitude();
                        //Log.i("Longitude",lang+"");

                        // Set CountryName on TextView
                        country= addresses.get(0).getCountryName();
                        //Log.i("CountryName",country);
                        textView1.setText("Country: "+ country);

                        //Set Locality on TextView
                        locality= addresses.get(0).getLocality();
                        //Log.i("Locality",locality);
                        textView2.setText("Locality: "+ locality);

                        //Set AddressLine on TextView
                        address=addresses.get(0).getAddressLine(0);
                        //Log.i("AddressLine",address);
                        textView3.setText("Address: "+ address);
                        prayersTime(calcMethod,asrMethod,timeformat,highlats,offset);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void clickMenu(View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void clickLogo(View view){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {

        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void clickHome(View view){
        recreate();
    }

    public void clickDashboard(View view){
        redirectActivity(this , Tasbeh.class);
    }

    public void clickAboutUs(View view){
        redirectActivity(this , Athkar.class );
    }

    public void clickLogOut(View view){
        logout(this);
    }

    public void clickSetting(View view){
        redirectActivity(this , setting.class );
    }

    public static void logout(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("تسجيل الخروج");
        builder.setMessage("هل تريد تسجيل الخروج؟");

        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finishAffinity();
                System.exit(0);
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


    public static void redirectActivity(Activity activity , Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }

    public void createNotification(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 6);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Intent intent1 = new Intent(Home.this, Notification.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(Home.this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) Home.this.getSystemService(Home.this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }


}