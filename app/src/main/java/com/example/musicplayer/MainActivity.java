package com.example.musicplayer;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView songList;
    ArrayList<String> allSongFile=new ArrayList<>();
    ArrayList<String> allSong=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songList=findViewById(R.id.list_view);
        File root=Environment.getExternalStorageDirectory();


        getSong(root);
        for (String f: allSongFile){
            allSong.add(f.replace(".mp3",""));
        }
        songList.setAdapter(new ArrayAdapter<String>(MainActivity.this,R.layout.list_item,R.id.list_item_id,allSong));

        songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MainActivity.this,PlayerActivity.class).putExtra("songList",allSongFile).putExtra("position",position));
            }
        });

    }

    private void getSong(File root) {
        if (root.isDirectory()){
            File[] child=root.listFiles();
            for (int i=0;i<child.length;i++){
                getSong(child[i]);
            }
        }else {
            if (root.getName().endsWith(".mp3")){
                allSongFile.add(root.getAbsolutePath());
            }
        }
    }
}
