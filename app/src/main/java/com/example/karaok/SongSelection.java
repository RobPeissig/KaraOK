package com.example.karaok;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class SongSelection extends AppCompatActivity implements SongListAdapter.OnItemClickListener{

    private RecyclerView recyclerView;
    private SongListAdapter adapter;
    private TextView textView;
    StorageReference storageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_selection);
        textView = findViewById(R.id.list);
        recyclerView = findViewById(R.id.song_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        storageRef = FirebaseStorage.getInstance().getReference("SongTitles");
        getSongs();
        //adapter.setOnItemClickListener(this);
        //recyclerView.setAdapter(adapter);
    }

    private void getSongs() {
        String[] song = new String[10];

        storageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {

                    @Override
                    public void onSuccess(ListResult listResult) {

                        List<Song> songs = new ArrayList<>();
                        SongListAdapter adapter;
                        String forContext [] = new String[20];
                        int i = 0;
                        for (StorageReference item: listResult.getItems()){
                            forContext[i] = item.getName();
                            String[] arrOfStr = item.getName().split(" ");
                            String name = "";
                            String tempTitle = "";
                            for (int j = 0; j< arrOfStr.length; j++){
                                if(j<2){
                                    name += arrOfStr[j] + " ";
                                }
                                else{
                                    tempTitle += arrOfStr[j] + " ";
                                }
                            }
                            String[] title = tempTitle.split("\\.");
                            songs.add(new Song(title[0], name, "", 4.5f));
                            i++;
                        }
                        adapter = new SongListAdapter(songs);
                        adapter.setOnItemClickListener(new SongListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                switchContext(forContext[position]);
                            }
                        });
                        recyclerView.setAdapter(adapter);
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Uh-oh, an error occurred!
                    }
                });

        List<Song> songs = new ArrayList<>();
        songs.add(new Song(song[0], "Artist 1", "3:45", 4.5f));
        songs.add(new Song("Song 2", "Artist 2", "4:20", 3.5f));
        songs.add(new Song("Song 3", "Artist 3", "2:55", 5f));
        songs.add(new Song("Song 4", "Artist 4", "3:10", 2.5f));
        songs.add(new Song("Song 5", "Artist 5", "4:30", 4f));
        //return songs;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, LiveEffectDemo.class);
        startActivity(intent);
    }
    public void switchContext(String songName) {
        Intent intent = new Intent(this, LiveEffectDemo.class);
        intent.putExtra("songName",songName);
        startActivity(intent);
    }
}