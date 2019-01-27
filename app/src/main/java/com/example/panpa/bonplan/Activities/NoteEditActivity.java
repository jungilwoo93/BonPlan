package com.example.panpa.bonplan.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.panpa.bonplan.Plan.Note;
import com.example.panpa.bonplan.R;

public class NoteEditActivity extends AppCompatActivity {
    private static final int NOTE_CREATE = 101;
    public TextView startTextView;
    public TextView endTextView;
    public TextView freqTextView;
    public TextView recallTextView;
    public EditText titleText;
    public EditText placeText;
    public CheckBox wholeDay;
    public EditText descriptionText;
    public Button imgButton;
    public Button cameraButton;
    public Button audioButton;
    public Button paintButton;
    public Button playButton;
    private static final int REQUEST_CODE_PICK_IMAGE=3;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE2 = 7;
    private Uri uri;
    public LinearLayout layoutPlayAudio;
    public ImageView imageView;
    private Note note;
    private MediaPlayer mAudioPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        note = (Note)getIntent().getSerializableExtra("note")/*.getParcelableExtra("note")*/;
        Toolbar toolbar = findViewById(R.id.toolbarEdit);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.logoCheck){
                    doValid();
                }
                return true;
            }
        });
        titleText = findViewById(R.id.title);
        placeText = findViewById(R.id.place);
        descriptionText= findViewById(R.id.description);
        imgButton = findViewById(R.id.image);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
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
        startTextView= findViewById(R.id.startTextView);
        startTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogTime dialog = new DialogTime();
                dialog.setType(0);
                dialog.show(getFragmentManager(),"DialogTime");
            }
        });
        endTextView= findViewById(R.id.endButton);
        endTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogTime dialog = new DialogTime();
                dialog.setType(1);
                dialog.show(getFragmentManager(),"DialogTime");
            }
        });

        freqTextView = findViewById(R.id.frequancyButton);
        freqTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFreq dialog = new DialogFreq();
                dialog.show(getFragmentManager(),"DialogFreq");
            }
        });
        recallTextView= findViewById(R.id.recallButton);
        recallTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogRecall dialog = new DialogRecall();
                dialog.show(getFragmentManager(),"DialogRecall");
            }
        });
        wholeDay = findViewById(R.id.wholeDay);
        wholeDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    startTextView.setVisibility(View.GONE);
                    endTextView.setVisibility(View.INVISIBLE);
                    freqTextView.setVisibility(View.INVISIBLE);
                    recallTextView.setVisibility(View.INVISIBLE);
                }else{
                    startTextView.setVisibility(View.VISIBLE);
                    endTextView.setVisibility(View.VISIBLE);
                    freqTextView.setVisibility(View.VISIBLE);
                    recallTextView.setVisibility(View.VISIBLE);
                }


            }
        });

        if(note!=null){
            titleText.setText(note.getTitle());
            placeText.setText(note.getPlace());
            descriptionText.setText(note.getDescrip());
            startTextView.setHint(note.getStartTime());
            endTextView.setHint(note.getStartTime());
            freqTextView.setHint(note.getFrequency());
            recallTextView.setHint(note.getRecallTime());
            if(note.getPathImg()!=""){
                imageView = findViewById(R.id.image_note);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageURI(Uri.parse(note.getPathImg()));
            }
            if(note.getPathAudio()!=""){
                layoutPlayAudio =findViewById(R.id.layoutPlayAudio);
                layoutPlayAudio.setVisibility(View.VISIBLE);
                playButton=findViewById(R.id.playAudio);
                playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(NoteEditActivity.this,RecordActivity.class);
                        intent.putExtra("note",note);
                        startActivity(intent);
                    }
                });
            }
        }
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
        String startTime = this.startTextView.getHint().toString();
        String endTime = this.endTextView.getHint().toString();
        String frequency = this.freqTextView.getHint().toString();
        String recallTime = this.recallTextView.getHint().toString();
        String descrip = this.descriptionText.getText().toString();
        String path = note.getPathImg();
        return new Note(title,place,startTime,endTime,frequency,recallTime,descrip,path);
    }

    public void doValid(){
        Note note =getEditNote();
        Intent data = new Intent();
        int position = getIntent().getIntExtra("pos",-1);
        if(position!=-1){
            data.putExtra("pos",position);
            data.putExtra("note",note);
            setResult(RESULT_OK,data);
            finish();;
        }
        else{
            data.putExtra("note",note);
            setResult(NOTE_CREATE,data);
            finish();
        }
    }

    public void changeImage(View v){
        openGallery();
    }

    public void openGallery(){
        //ouvrir le gallerie et choisir une image et l'envoyer
        if (ContextCompat.checkSelfPermission(NoteEditActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(NoteEditActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE2);

        }else {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
        }
    }

    public void onActivityResult(int req, int res, Intent data) {
        switch (req) {
            case REQUEST_CODE_PICK_IMAGE:
                //req quand on choisit une photo dans le gallerie
                try {
                    uri = data.getData();
                    //dernière activity retourne cet uri
                    Intent intent = new Intent(NoteEditActivity.this,GalleryActivity.class);
                    intent.putExtra("note",getEditNote());
                    intent.putExtra("uri",uri.toString());
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}
