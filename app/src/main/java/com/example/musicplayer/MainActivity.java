package com.example.musicplayer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int RQ_CODE = 101;
    ListView songList;
    ArrayList<String> allSongPath=new ArrayList<>();
    ArrayList<String> allSongName=new ArrayList<>();
    File root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songList=findViewById(R.id.list_view);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                        root =Environment.getExternalStorageDirectory();
                        getSong(root);
                        songList.setAdapter(new ArrayAdapter<String>(MainActivity.this,R.layout.list_item,R.id.list_item_id,allSongName));
            }else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},RQ_CODE);
            }
        }else {
                    root =Environment.getExternalStorageDirectory();
                    getSong(root);
                    songList.setAdapter(new ArrayAdapter<String>(MainActivity.this,R.layout.list_item,R.id.list_item_id,allSongName));
        }

        songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MainActivity.this,PlayerActivity.class).putExtra("songList",allSongPath).putExtra("position",position));
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
            root =Environment.getExternalStorageDirectory();
            getSong(root);
            songList.setAdapter(new ArrayAdapter<String>(MainActivity.this,R.layout.list_item,R.id.list_item_id,allSongName));
        }
    }

    private void getSong(File root) {
        if (root.isDirectory()){
            File[] child=root.listFiles();
            for (int i=0;i<child.length;i++){
                getSong(child[i]);
            }
        }else {
            if (root.getName().endsWith(".mp3")){
                allSongPath.add(root.getAbsolutePath());
                allSongName.add(root.getName());
            }
        }
    }
}
