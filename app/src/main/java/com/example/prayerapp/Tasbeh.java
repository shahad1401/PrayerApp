package com.example.prayerapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class Tasbeh extends AppCompatActivity {

    DrawerLayout drawerLayout;
     long  COUNT = 0 ;
     TextView textView;
     TextSwitcher textSwitcher;
     String[] Tesbeeh = {"سبحان الله","الحمد لله","الله وأكبر","لا إله إلا الله وحده لا شريك له "};
     int currentIndex = 0 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasbeh);
        drawerLayout = findViewById(R.id.drawer_layout);
        textView = findViewById(R.id.tasbeehCount);
        textSwitcher = findViewById(R.id.tasbeehText);
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView text = new TextView(getApplicationContext());
                text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                text.setTextSize(35);
                return text;
            }
        });

ChangeDuya();
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
        recreate();
    }

    public void clickAboutUs(View view){
        Home.redirectActivity(this, Athkar.class);
    }

    public void logout(View view){
        Home.logout(this);
    }

    public void clickSetting(View view){
        Home.redirectActivity(this, setting.class );
    }

    protected void onPause() {
        super.onPause();
        Home.closeDrawer(drawerLayout);
    }

    protected void ChangeDuya(){
        if (currentIndex>Tesbeeh.length-1){
            currentIndex = 0;
//            textSwitcher.setText(Tesbeeh[currentIndex]);
        }
        textSwitcher.setText(Tesbeeh[currentIndex]);
        currentIndex ++;
    }
    public void Reset(View view) {
        //Toast.makeText(this, "Reset", Toast.LENGTH_SHORT).show();
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
      //  builder.setTitle("");
        builder.setMessage("هل تريد إعادة تعيين عداد التسبيح؟");
        builder.setPositiveButton("نعم ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                COUNT = 0 ;
                textView.setText(""+COUNT);
            }
        });
        builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();

    }

    public void ChangeDuya(View view) {
        //Toast.makeText(this, "ChangeDuya", Toast.LENGTH_SHORT).show();
       ChangeDuya();
    }

    public void Count(View view) {
        //Toast.makeText(this, "Count", Toast.LENGTH_SHORT).show();
        COUNT++;
        textView.setText(""+COUNT);
    }
}
