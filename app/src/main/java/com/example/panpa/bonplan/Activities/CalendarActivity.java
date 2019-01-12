package com.example.panpa.bonplan.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.panpa.bonplan.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {
    private String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarCalendar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);//设置toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 给左上角图标的左边加上一个返回的图标 。

        CalendarView calendar = findViewById(R.id.calendarView);
        final TextView dateChoosed = findViewById(R.id.dateText);
        final TextView calendarText = findViewById(R.id.chooseDateText);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar cal=Calendar.getInstance();
                Date d = new Date(year,month,dayOfMonth);
                SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
                String month_name = month_date.format(d);
                date = dayOfMonth + " " + month_name + " " + year;
                dateChoosed.setText(date);
                calendarText.setText("You choose : ");
            }
        });
        Button choose = findViewById(R.id.chooseDateButton);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CalendarActivity.this, MainActivity.class);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent= new Intent(CalendarActivity.this, MainActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
