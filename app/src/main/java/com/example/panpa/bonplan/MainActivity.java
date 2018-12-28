package com.example.panpa.bonplan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        Calendar cal=Calendar.getInstance();
        int date = cal.get(Calendar.DAY_OF_MONTH);
        String monthname=(String)android.text.format.DateFormat.format("MMMM", new Date());
        int year = cal.get(Calendar.YEAR);
        toolbar.setTitle(date + " " + monthname+" "+year);
        setSupportActionBar(toolbar);
        //toolbar.setNavigationIcon(R.mipmap.calendarlogo);
        //Menu menu = findViewById(R..menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.logoCalendar){
                    Intent intent =new Intent(MainActivity.this,CalendarActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
