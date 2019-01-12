package com.example.panpa.bonplan.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.panpa.bonplan.R;

import java.util.Calendar;
import java.util.Date;

public class NoteEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarEdit);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);//设置toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 给左上角图标的左边加上一个返回的图标 。
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent= new Intent(NoteEditActivity.this, MainActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
