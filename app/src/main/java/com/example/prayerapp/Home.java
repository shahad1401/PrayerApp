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

    public static int timeformat , calcMethod, asrMethod;
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

//prayersTime(calcMethod,asrMethod,timeformat);

        drawerLayout = findViewById(R.id.drawer_layout);
    } // end on create

    public void prayersTime(int timeformat, int calcmethod, int asrMethod) {
        PrayTime prayers = new PrayTime();

        prayers.setTimeFormat(1); // 12 (1)
        prayers.setCalcMethod(4); //um alqora 4
        //Log.i("calc method",prayers.getCalcMethod()+"");
        prayers.setAsrJuristic(0); // Shafii (standard) (0)
       // Log.i("AsrJuristic method",prayers.getAsrJuristic()+"");
        prayers.setAdjustHighLats(0); //none
        //Log.i("AdjustHighLats method",prayers.getAdjustHighLats()+"");
        int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
        prayers.tune(offsets);

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
       double timezone=prayers.getBaseTimeZone();
        //Log.i("timezone",timezone+"");
       // Log.i("lat,lang",lat+", "+lang+"");
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

        createNotification(prayerNames,prayerTimes);

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
                        prayersTime(calcMethod,asrMethod,timeformat);
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

    public void createNotification(ArrayList<String> names , ArrayList<String> times){

        //fajr notification
        Log.i("notify" , "Notify");
        Log.i("size" , ""+times.size());
        for (int i=0 ; i< 7 ; i++){

            if(i!=1 && i!=4){

                Log.i(times.get(i) , names.get(i));

                String hour = times.get(i).substring(0,2);
                String min = times.get(i).substring(3,5);

                int ihour = parseInt(hour);
                int imin = parseInt(min);
                Log.i(hour , "hour");

                c.setTimeInMillis(System.currentTimeMillis());
                c.set(Calendar.HOUR_OF_DAY, ihour);
                c.set(Calendar.MINUTE, imin); // prayerTimeNotify
                // to update time use java.awt.event

                long delay = c.getTimeInMillis();

                Intent intent = new Intent(this, Notification.class);
                intent.putExtra("name", names.get(i));

                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, random.nextInt(9999 - 1000) + 1000,  intent, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP  , delay , pendingIntent) ;
                }
            }
        }

    }


}