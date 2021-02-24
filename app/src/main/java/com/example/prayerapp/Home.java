package com.example.prayerapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

public class Home extends AppCompatActivity {
    //initialize variable
    Button btLocation;
    double lat, lang;
    String country, locality, address;
    TextView textView1,textView2,textView3,fajr,duhr, asr, maghrb,isha;
    FusedLocationProviderClient fusedLocationProviderClient;
    DrawerLayout drawerLayout;
String[] prayer;
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

prayersTime();
        drawerLayout = findViewById(R.id.drawer_layout);
    }

    private void prayersTime() {
        PrayTime prayers = new PrayTime();

        prayers.setTimeFormat(1); // 12
        prayers.setCalcMethod(4); //um alqora
        Log.i("calc method",prayers.getCalcMethod()+"");
        prayers.setAsrJuristic(0); // Shafii (standard)
        Log.i("AsrJuristic method",prayers.getAsrJuristic()+"");
        prayers.setAdjustHighLats(0); //none
        Log.i("AdjustHighLats method",prayers.getAdjustHighLats()+"");
        int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
        prayers.tune(offsets);

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
       double timezone=prayers.getBaseTimeZone();
        Log.i("timezone",timezone+"");
        Log.i("lat,lang",lat+", "+lang+"");
        ArrayList<String> prayerTimes = prayers.getPrayerTimes(cal, lat, lang, timezone);
        ArrayList<String> prayerNames = prayers.getTimeNames();

        for (int i = 0; i < prayerTimes.size(); i++) {
            System.out.println(prayerNames.get(i) + " - " + prayerTimes.get(i));
           // prayer[i]= prayerTimes.get(i);
        }
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
                        Log.i("Latitude",lat+"");

                         //Set Longitude on TextView
                        lang= addresses.get(0).getLongitude();
                        Log.i("Longitude",lang+"");

                        // Set CountryName on TextView
                        country= addresses.get(0).getCountryName();
                        Log.i("CountryName",country);
                        textView1.setText("Country: "+ country);

                        //Set Locality on TextView
                        locality= addresses.get(0).getLocality();
                        Log.i("Locality",locality);
                        textView2.setText("Locality: "+ locality);

                        //Set AddressLine on TextView
                        address=addresses.get(0).getAddressLine(0);
                        Log.i("AddressLine",address);
                        textView3.setText("Address: "+ address);
                        prayersTime();
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

    public static void logout(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("r u sure u want to log out?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                activity.finishAffinity();
                System.exit(0);
            }
        });

        builder.setNegativeButton("NOOOH", new DialogInterface.OnClickListener() {
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
}