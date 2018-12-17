package com.example.panpa.bonplan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

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
    }
}
