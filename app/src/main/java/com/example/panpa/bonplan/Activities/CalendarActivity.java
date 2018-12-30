package com.example.panpa.bonplan.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.panpa.bonplan.R;

public class CalendarActivity extends AppCompatActivity {
    private String date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        CalendarView calendar = findViewById(R.id.calendarView);
        final TextView dateChoosed = findViewById(R.id.dateText);
        final TextView calendarText = findViewById(R.id.chooseDateText);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = dayOfMonth + "/" + (month+1) + "/" + year;
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
}
