package com.example.karaok;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SongSelection extends AppCompatActivity implements SongListAdapter.OnItemClickListener{

    private RecyclerView recyclerView;
    private SongListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_selection);

        recyclerView = findViewById(R.id.song_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SongListAdapter(getSongs());
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    private List<Song> getSongs() {
        List<Song> songs = new ArrayList<>();
        songs.add(new Song("Song 1", "Artist 1", "3:45", 4.5f));
        songs.add(new Song("Song 2", "Artist 2", "4:20", 3.5f));
        songs.add(new Song("Song 3", "Artist 3", "2:55", 5f));
        songs.add(new Song("Song 4", "Artist 4", "3:10", 2.5f));
        songs.add(new Song("Song 5", "Artist 5", "4:30", 4f));
        return songs;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, LiveEffectDemo.class);
        startActivity(intent);
    }


}