package com.example.panpa.bonplan.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.panpa.bonplan.Plan.Note;
import com.example.panpa.bonplan.Plan.NoteAdapter;
import com.example.panpa.bonplan.Plan.Notes;
import com.example.panpa.bonplan.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity{
    private NoteAdapter adapter;
    private static final int NOTE_CREATE = 101;
    private static final int NOTE_UPDATE = 120;
    private static int Code_Modifier = 100;
    private ListView listNote;
    private Map<Calendar,NoteAdapter> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mettre layout
        setContentView(R.layout.activity_main);
        // afficher la date sur Toolbar
        Calendar cal=Calendar.getInstance();
        int dateOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        String monthname=(String)android.text.format.DateFormat.format("MMMM", new Date());
        int year = cal.get(Calendar.YEAR);
        map.put(getSchemeCalendar(year,cal.get(Calendar.MONTH)+1,dateOfMonth),adapter);
        //mettre toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(dateOfMonth + " " + monthname+" "+year);
        setSupportActionBar(toolbar);
        // un boutton sur toolbar pour entrer dans le calendar
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
        //initialiser adapter pour listView
        adapter = new NoteAdapter(this);
        listNote=findViewById(R.id.listNote);
        listNote.setAdapter(adapter);
        //si on click item de la listView, il va envoyer cet item à la
        listNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Note note = (Note) adapter.get(position);
                Intent monIntent = new Intent(MainActivity.this,NoteEditActivity.class);//dans cette classe pour démarrer le class NoteEditActivity
                monIntent.putExtra("note",note);
                monIntent.putExtra("pos",position);
                monIntent.putExtra("sup",position);
                //transferer en binaire
                startActivityForResult(monIntent, Code_Modifier);
            }
        });

        Intent intent = getIntent();
        if(intent.getStringExtra("date")!=null){
            String date = intent.getStringExtra("date");
            Date d = splitStringToDate(date);
            SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
            String month_name = month_date.format(d);
            date = d.getDate() + " " + month_name + " " + d.getYear();
            toolbar.setTitle(date);
            Calendar calendar = getSchemeCalendar(d.getYear(),d.getMonth(),d.getDay());
            if(!map.containsKey(calendar)){
                map.put(calendar,adapter);
            }else{
                adapter=map.get(calendar);
            }
        }
        //button flotte pour créer une nouvelle note
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,NoteEditActivity.class);
                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int month =cal.get(Calendar.MONTH)+1;
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int minu = cal.get(Calendar.MINUTE);
                if(minu<30&&minu>=0){
                    minu=30;
                }else{
                    hour+=1;
                    minu=00;
                }
                String startTime=day + "/" + month + " " + hour + ":" + minu;
                String endTime = day + "/" + month + " " + (hour+1) + ":" + minu;
                intent.putExtra("note", new Note("","",startTime,endTime,"Never","10 mins before","",""));
                intent.putExtra("pos",-1);
                startActivityForResult(intent,NOTE_CREATE);
            }
        });
    }

    public Calendar getSchemeCalendar(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,day);
        return calendar;
    }

    public Date splitStringToDate(String date){
        String[] separated = date.split(" ");
        String day = separated[0];
        String month = separated[1];
        String year = separated[2];
        Date d = new Date(Integer.parseInt(year),Integer.parseInt(month)-1,Integer.parseInt(day));
        return d;
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode==Code_Modifier){
            // on clickItem pour modifier la note, NoteEditActivity envoie le resultat ici.
            //et mis à jour le listView
            if(resultCode ==RESULT_OK){
                Note note = (Note) data.getSerializableExtra("note");//récuperer la note
                int position = data.getIntExtra("pos",-1);
                adapter.set(position, note);
                adapter.notifyDataSetChanged();
            }else if(resultCode == RESULT_CANCELED){

            }
        }
        if(resultCode == 120){
            // supprimer la note sur listView
            Note note = (Note) data.getSerializableExtra("note");
            int sup =data.getIntExtra("sup",-1);
            adapter.remove(sup);
            adapter.notifyDataSetChanged();
        }
        if(resultCode == 101){
            //ajouter la note sur listView
            Note note = (Note) data.getSerializableExtra("note");
            int position = data.getIntExtra("pos",-1);
            adapter.add(note);
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //mettre le layout menu sur toolbar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
