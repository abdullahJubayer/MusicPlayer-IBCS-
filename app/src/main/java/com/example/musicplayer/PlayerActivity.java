package com.example.musicplayer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener {
    TextView txtFw,txtBc,txt_st_sp;
    ImageView imageView;
    SeekBar seekBar;
    MediaPlayer mediaPlayer;
    ArrayList<File> song;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

         song= (ArrayList<File>) getIntent().getParcelableArrayListExtra("songList");

        txtFw=findViewById(R.id.for_word);
        txtBc=findViewById(R.id.back_word);
        txt_st_sp=findViewById(R.id.start_stop);
        imageView=findViewById(R.id.image_view);
        seekBar=findViewById(R.id.seek_bar);

        txtFw.setOnClickListener(this);
        txtBc.setOnClickListener(this);
        txt_st_sp.setOnClickListener(this);


        if (path.isEmpty() || path==null){
            Toast.makeText(PlayerActivity.this,"File Not Found",Toast.LENGTH_SHORT).show();
        }else {
            mediaPlayer=MediaPlayer.create(PlayerActivity.this, Uri.parse(path));
            seekBar.setMax(mediaPlayer.getDuration());
        }


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                    txt_st_sp.setText(">|");
                    seekBar.setProgress(0);
            }
        });

    }

    public void setSeekBarProgress(){
        Thread start_seekbar_thread=new Thread(){
            @Override
            public void run() {
                while (mediaPlayer.getCurrentPosition() < mediaPlayer.getDuration()){
                    try {
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };

        start_seekbar_thread.start();
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.for_word){
            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+5000);
        }
        if (v.getId()==R.id.back_word){
            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-5000);
        }
        if (v.getId()==R.id.start_stop){
            if (mediaPlayer.isPlaying()){
                txt_st_sp.setText(">|");
                mediaPlayer.pause();
            }
            else {
                txt_st_sp.setText("||");
                mediaPlayer.start();
                setSeekBarProgress();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
    }


}
