package com.example.panpa.bonplan.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.panpa.bonplan.Plan.Note;
import com.example.panpa.bonplan.R;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class RecordActivity extends AppCompatActivity {
    private Button btn_control;
    private boolean isRecord = false;
    private boolean isPlay = false;
    private MediaRecorder mr = null;
    private ProgressBar progressBar;
    private Button btn_play;
    private Button btn_cancel;
    private Button btn_validate;
    private Timer mPlayingTimer;
    private Timer mRecordingtimer;
    private MediaPlayer mAudioPlayer;
    private TextView text_timer;
    private int seconds;
    private int MAXTIME = 50000; //50secondes
    private String filePath;
    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        note = (Note)getIntent().getSerializableExtra("note");
        filePath = note.getPathAudio();
        btn_control = findViewById(R.id.btn_control);
        if(filePath!=""){
            btn_control.setVisibility(View.INVISIBLE); //juste pour écouter l'audio
        }else{
            btn_control.setVisibility(View.VISIBLE); //pour faire l'audio
        }
        progressBar = findViewById(R.id.progressBar);
        btn_play = findViewById(R.id.button_play);
        btn_cancel=findViewById(R.id.btn_cancel);
        text_timer = findViewById(R.id.text_timer);
        btn_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isRecord){
                    if (ActivityCompat.checkSelfPermission(RecordActivity.this, Manifest.permission.RECORD_AUDIO)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(RecordActivity.this, new String[] { Manifest.permission.RECORD_AUDIO },
                                10);
                    } else {
                        startRecord();
                    }
                    btn_control.setText("Stop Record");
                    isRecord = true;
                }else{
                    stopRecord();
                    btn_control.setText("Re record");
                    isRecord = false;
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecordActivity.this, NoteEditActivity.class);
                intent.putExtra("note",note);
                startActivity(intent);
            }
        });
        btn_play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!isPlay){
                    if (ActivityCompat.checkSelfPermission(RecordActivity.this, Manifest.permission.RECORD_AUDIO)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(RecordActivity.this, new String[] { Manifest.permission.RECORD_AUDIO },
                                10);
                    } else {
                        startPlay();
                    }
                    btn_play.setText("Stop play");
                    isPlay = true;
                }else{
                    stopPlay();
                    btn_play.setText("Play");
                    isPlay = false;
                }
            }
        });
        btn_validate=findViewById(R.id.btn_validate);
        btn_validate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecordActivity.this,NoteEditActivity.class);
                note.setPathAudio(filePath);
                intent.putExtra("note",note);
                startActivity(intent);
            }
        });

    }

    private void createRecordFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String soundFileName = "Record"+timeStamp;
        File storageDir = new File(Environment.getExternalStorageDirectory(),"sounds");
        if(!storageDir.exists()){
            storageDir.mkdirs();
        }
        File sound = new File(storageDir,soundFileName+".amr");
        if(!sound.exists()){
            sound.createNewFile();
        }
        mr = new MediaRecorder();
        mr.setAudioSource(MediaRecorder.AudioSource.MIC);  //source de l'autdio
        mr.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);   //format pour sortie
        mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);   //format pour encoder
        mr.setOutputFile(sound.getAbsolutePath());
        try {
            mr.prepare();
            mr.start(); //démarrer l'enregistrement
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.filePath = sound.getAbsolutePath();
    }

    //commencer à enregistrer l'audio
    private void startRecord(){
        if(mr == null){
            try {
                progressBar.setMax(MAXTIME);
                progressBar.setProgress(0);
                if (ContextCompat.checkSelfPermission(RecordActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED
                        &&ContextCompat.checkSelfPermission(RecordActivity.this,
                        Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(RecordActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            112);
                    ActivityCompat.requestPermissions(RecordActivity.this,
                            new String[]{Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS},
                            113);

                }else {
                    createRecordFile();
                }
                progressforRecording();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //terminier l'enregistrement, et libérer les ressources
    private void stopRecord(){
        if(mr != null){
            mr.stop();
            mr.release();
            mr = null;
        }
    }



    private void progressforRecording() {
        seconds = 0;
        mRecordingtimer = new Timer();
        mRecordingtimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (progressBar.getProgress() < MAXTIME) {
                                progressBar.setProgress(progressBar.getProgress() + 1000);
                                seconds++;
                                text_timer.setText(seconds > 9 ? "00:" + seconds : "00:0" + seconds);
                            } else {
                                startRecord();
                            }
                        }
                    });
                }
            }
        }, 1000, 1000);
    }

    private void startPlay() {
        mAudioPlayer = new MediaPlayer();
        try {
            mAudioPlayer.setDataSource(this, Uri.parse(filePath));
            mAudioPlayer.prepare();
            updatePlayingProgressBar();
            mAudioPlayer.start();
            progressforPlaying();

        } catch (IOException e) {
            e.printStackTrace();
        }
        mAudioPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                startPlay();
            }
        });
    }

    private void updatePlayingProgressBar() {

        long maxTime = mAudioPlayer.getDuration();
        int second = (int) maxTime / 1000;
        progressBar.setMax(second * 1000);
        progressBar.setProgress(0);
        text_timer.setText(second > 9 ? "00:" + second : "00:0" + second);
    }

    private void progressforPlaying() {
        seconds = 0;
        mPlayingTimer = new Timer();
        mPlayingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isPlay) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (progressBar.getProgress() < mAudioPlayer.getDuration()) {
                                progressBar.setProgress(progressBar.getProgress() + 1000);
                                seconds++;
                                text_timer.setText(seconds > 9 ? "00:" + seconds : "00:0" + seconds);
                            } else {
                                startPlay();
                            }
                        }
                    });
                }
            }
        }, 1000, 1000);
    }

    private void stopPlay() {
        mAudioPlayer.stop();
        mAudioPlayer.release();
        mPlayingTimer.cancel();
        mAudioPlayer = null;
    }
}
