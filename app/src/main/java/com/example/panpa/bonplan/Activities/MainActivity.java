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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    public NoteAdapter adapter= new NoteAdapter(this);
    public static final int NOTE_CREATE = 101;
    public static final int NOTE_UPDATE = 120;
    public static int Code_Modifier = 100;
    private static final String TAG = "MainActivity";
    //private Notes notes=new Notes();
    public ListView listNote;

    //private ArrayList<Note> arrayList = notes.getNotes();
   // private ArrayAdapter<Note> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        Calendar cal=Calendar.getInstance();
        int dateOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        String monthname=(String)android.text.format.DateFormat.format("MMMM", new Date());
        int year = cal.get(Calendar.YEAR);
        toolbar.setTitle(dateOfMonth + " " + monthname+" "+year);
        setSupportActionBar(toolbar);
        listNote=findViewById(R.id.listNote);
        //generateListContent();
        //adapter =new MyAdapterListView(this,R.layout.list_item,notes.getNotes());
        // Context上下文
        // 子项布局id
        // 数据
        listNote.setAdapter(adapter);//设置适配器
        /*listNote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Note c = notes.get(position);
            }
        });*/
        /*arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);

        // Here, you set the data in your ListView
        listNote.setAdapter(adapter);*/

        Intent intent = getIntent();
        if(intent.getStringExtra("date")!=null){
            String date = intent.getStringExtra("date");
            toolbar.setTitle(date);
        }

        /*if(intent.getParcelableExtra("note")!=null){
            Note c = (Note)intent.getParcelableExtra("note");
            notes.add(c);
            adapter.notifyDataSetChanged();
            //notes.add(c);
            /*for (Note cc :  notes.getNotes()) {
                Log.d(TAG, cc.toString());
            }*/
            /*TextView tv = new TextView(getApplicationContext());
            tv.setText(c.getTitle());
            listNote.addView(tv,0);*/
        //}

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,NoteEditActivity.class);
                intent.putExtra("note", new Note("","","","","","","",""));
                intent.putExtra("pos",-1);
                startActivityForResult(intent,NOTE_CREATE);
                //startActivity(intent);
            }
        });

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Note yo = (Note) adapter.get(position);
        Intent monIntent = new Intent(this,NoteEditActivity.class);//dans ce classe pour démarrer le class ContactActivity
        monIntent.putExtra("note",yo);
        monIntent.putExtra("pos",position);
        monIntent.putExtra("sup",position);
        //il faut ajouter implement seridizable dans le class Contact,sinon,c'est rouge,on peux pas
        //utiliser Contact yo
        //transferer en binaire
        startActivityForResult(monIntent, Code_Modifier);
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode==Code_Modifier){
            if(resultCode ==RESULT_OK){
                Note ha = (Note) data.getSerializableExtra("note");//.getParcelableExtra("note");//récuperer le contact
                //Toast.makeText(this,ha.getNom(),Toast.LENGTH_SHORT).show();
                int position = data.getIntExtra("pos",-1);
                adapter.set(position, ha);//probleme, comment trouver la position
                adapter.notifyDataSetChanged();
            }else if(resultCode == RESULT_CANCELED){

            }
        }
        if(resultCode == 120){
            Note ha = (Note) data.getSerializableExtra("note");//.getParcelableExtra("note");
//                Toast.makeText(this,ha.getNom(),Toast.LENGTH_SHORT).show();
            int sup =data.getIntExtra("sup",-1);
            adapter.remove(sup);
            adapter.notifyDataSetChanged();
        }
        if(resultCode == 101){
            Note ha = (Note) data.getSerializableExtra("note");//.getParcelableExtra("note");

            int position = data.getIntExtra("pos",-1);
            adapter.add(ha);
            adapter.notifyDataSetChanged();

        }

    }

    /*private void generateListContent(){
        for(int i =0;i<55;i++){
            arrayList.add("This is row number " + i);
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private class MyAdapterListView extends ArrayAdapter<Note> {
        private int layout; // 子项布局的ID
        //构造函数
        private MyAdapterListView(Context context,int resource, List<Note> objects){
            super(context,resource,objects);
            layout=resource;
        }
        //重写getView函数
        @Override
        public View getView(final int position, View convertView, ViewGroup parent){
            Note note = getItem(position);
            ViewHolder mainViewHolder = null;
            if(convertView== null){

                LayoutInflater inflater =LayoutInflater.from(getContext());
                //用LayoutInflater来为这个子项加载我们传入的布局
                convertView=inflater.inflate(layout,parent,false);
                //实例化布局中的图片控件和文本控件
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.startTime = convertView.findViewById(R.id.startTimeInList);
                viewHolder.title = convertView.findViewById(R.id.titleInList);
                viewHolder.switchValid = convertView.findViewById(R.id.valideNote);
                viewHolder.switchValid.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(),"Button was clicked for list item "+ position, Toast.LENGTH_SHORT).show();
                    }
                });
                viewHolder.switchPost = convertView.findViewById(R.id.postponeNote);
                viewHolder.switchPost.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(),"Button was clicked for list item "+ position, Toast.LENGTH_SHORT).show();
                    }
                });
                //缓存图片控件和文本控件的实例
                convertView.setTag(viewHolder);
            }else{
                //取出缓存
                mainViewHolder = (ViewHolder) convertView.getTag();
                //mainViewHolder.startTime.setText();
                //得到当前项的Note实例
                //mainViewHolder.title.setText(getItem(position));
                mainViewHolder.title.setText(note.getTitle());
                mainViewHolder.startTime.setText(note.getStartTime());
            }
            //return super.getView(position, convertView,parent);
            return convertView;
        }
    }

    public class ViewHolder{
        public TextView startTime;
        public TextView title;
        public Switch switchValid;
        public Switch switchPost;
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NOTE_CREATE && resultCode == RESULT_OK) {
            Note c = data.getParcelableExtra("note");
            notes.add(c);
            for (Note cc :  notes.getNotes()) {
                Log.d(TAG, cc.toString());
            }
            TextView tv = new TextView(getApplicationContext());
            tv.setText(c.getTitle());
            listNote.addView(tv,0);
        }

    }*/
}
