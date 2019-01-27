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
import com.example.panpa.bonplan.R;
import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {
    private String date;
    private TextView dateChoosed;
    private TextView calendarText;
    private Button choose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Toolbar toolbar = findViewById(R.id.toolbarCalendar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);//mettre toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // logo pour retourner

        CalendarView calendar = findViewById(R.id.calendarView);
        dateChoosed = findViewById(R.id.dateText);
        calendarText = findViewById(R.id.chooseDateText);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar cal=Calendar.getInstance();
                date = dayOfMonth + "/" + (month+1) + "/" + year;
                dateChoosed.setText(date);
                calendarText.setText("You choose : ");
            }
        });
        choose = findViewById(R.id.chooseDateButton);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //envoyer la date qu'on a choisit à MainActivity
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
