package com.example.panpa.bonplan.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.panpa.bonplan.Plan.Note;
import com.example.panpa.bonplan.Plan.NoteAdapter;
import com.example.panpa.bonplan.R;

public class NoteEditActivity extends AppCompatActivity {
    private NoteAdapter adapter = new NoteAdapter(this);
    private static final String TAG = "NoteEditActivity";
    public TextView startTextView;
    public TextView endTextView;
    public TextView freqTextView;
    public TextView recallTextView;
    public EditText titleText;
    public EditText placeText;
    public Switch wholeDay;
    public EditText descriptionText;
    public Button imgButton;
    public Button cameraButton;
    public Button audioButton;
    public Button paintButton;
    private static final int REQUEST_CODE_PICK_IMAGE=3;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 7;
    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        Note note = (Note)getIntent().getSerializableExtra("note")/*.getParcelableExtra("note")*/;
        Toolbar toolbar = findViewById(R.id.toolbarEdit);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);//设置toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 给左上角图标的左边加上一个返回的图标。
        titleText = findViewById(R.id.title);
        placeText = findViewById(R.id.place);
        wholeDay = findViewById(R.id.wholeDay);
        descriptionText= findViewById(R.id.description);
        imgButton = findViewById(R.id.image);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(NoteEditActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(NoteEditActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE2);

                }else {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");//相片类型
                    //setResult(REQUEST_CODE_PICK_IMAGE);
                    startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
                }

                /*Intent intent = new Intent(NoteEditActivity.this,GalleryActivity.class);
                Note note = getEditNote();
                intent.putExtra("note",note);
                startActivity(intent);*/
            }
        });
        cameraButton = findViewById(R.id.camera);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteEditActivity.this,CameraActivity.class);
                Note note = getEditNote();
                intent.putExtra("note",note);
                startActivity(intent);
                //startActivityForResult(intent,PHOTO_CREATE);
                //setResult(PHOTO_CREATE,intent);
            }
        });
        audioButton = findViewById(R.id.audio);
        audioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NoteEditActivity.this,RecordActivity.class);
                Note note = getEditNote();
                intent.putExtra("note",note);
                startActivity(intent);
            }
        });
        paintButton=findViewById(R.id.paint);
        paintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(NoteEditActivity.this, CanvasActivity.class);
                Note note = getEditNote();
                intent.putExtra("note",note);
                startActivity(intent);
            }
        });
        titleText.setText(note.getTitle());
        placeText.setText(note.getPlace());
        descriptionText.setText(note.getDescrip());

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.logoCheck){
                    doValid();
                }
                return true;
            }
        });
        startTextView= findViewById(R.id.startTextView);
        startTextView.setText(note.getStartTime());
        startTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogTime dialog = new DialogTime();
                dialog.show(getFragmentManager(),"DialogTime");
            }
        });
        endTextView= findViewById(R.id.endButton);
        endTextView.setText(note.getStartTime());
        endTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogTime dialog = new DialogTime();
                dialog.show(getFragmentManager(),"DialogTime");
            }
        });

        freqTextView = findViewById(R.id.frequancyButton);
        freqTextView.setText(note.getStartTime());
        freqTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFreq dialog = new DialogFreq();
                dialog.show(getFragmentManager(),"DialogFreq");
            }
        });
        recallTextView= findViewById(R.id.recallButton);
        recallTextView.setText(note.getStartTime());
        recallTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogRecall dialog = new DialogRecall();
                dialog.show(getFragmentManager(),"DialogRecall");
            }
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    public Note getEditNote(){
        String title = this.titleText.getText().toString();
        String place = this.placeText.getText().toString();
        String startTime = this.startTextView.getText().toString();
        String endTime = this.endTextView.getText().toString();
        String frequency = this.freqTextView.getText().toString();
        String recallTime = this.recallTextView.getText().toString();
        String descrip = this.descriptionText.getText().toString();
        String path = "";
        return new Note(title,place,startTime,endTime,frequency,recallTime,descrip,path);
    }

    public void doValid(){
        /*String title = this.titleText.getText().toString();
        String place = this.placeText.getText().toString();
        String startTime = this.startTextView.getText().toString();
        String endTime = this.endTextView.getText().toString();
        String frequency = this.freqTextView.getText().toString();
        String recallTime = this.recallTextView.getText().toString();
        String descrip = this.descriptionText.getText().toString();*/
        //switch (wholeDay.)
        //Intent intent =new Intent(NoteEditActivity.this,MainActivity.class);
        //Intent intent = getIntent();
        Note note =getEditNote();
        Intent data = new Intent();
        int position = getIntent().getIntExtra("pos",-1);
        if(position!=-1){
            //Note note = new Note(title,place,startTime,endTime,frequency,recallTime,descrip);
            data.putExtra("pos",position);
            data.putExtra("note",note);
            setResult(RESULT_OK,data);
            finish();;
        }
        else{
            //Note note = new Note(title,place,startTime,endTime,frequency,recallTime,descrip);
            //data.putExtra("pos",position);
            data.putExtra("note",note);
            setResult(101,data);
            finish();
        }
        //Note note = new Note(title,place, startTime, endTime,frequency, recallTime, descrip);
        //intent.putExtra("note", note);
        //setResult(RESULT_OK, intent);
        //finish();
        //startActivity(intent);
        //startActivityForResult(intent,RESULT_OK);
    }

    public void setTime(View v){
        //getLayoutInflater().inflate(R.layout.dialog_choice_time, v,false);
        Log.d("NoteEditActivity","onClick : opening dialog.");
    }

    public void onActivityResult(int req, int res, Intent data) {
        switch (req) {
            case REQUEST_CODE_PICK_IMAGE:
                //if (res == RESULT_OK) {
                try {
                    Toast.makeText(this,"entrer",Toast.LENGTH_SHORT).show();
                    uri = data.getData();
                    //Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                    Intent intent = new Intent(NoteEditActivity.this,GalleryActivity.class);
                    intent.putExtra("note",getEditNote());
                    intent.putExtra("uri",uri.toString());
                    startActivity(intent);
                    //imageView.setImageBitmap(bit);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("tag",e.getMessage());
                    Toast.makeText(this,"程序崩溃",Toast.LENGTH_SHORT).show();
                }
                //}
                //else{
                //Log.i("liang", "失败");
                //}

                break;

            default:
                break;
        }
    }
}
