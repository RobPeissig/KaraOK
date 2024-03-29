package com.example.karaok;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arthenica.ffmpegkit.FFmpegKit;
import com.arthenica.ffmpegkit.ReturnCode;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SongSelection extends AppCompatActivity implements SongListAdapter.OnItemClickListener{

    private RecyclerView recyclerView;
    private SongListAdapter adapter;
    private TextView textView;
    StorageReference storageRef;

    public static String fullSongName;
    private ImageView fxlabButton;
    private SearchView searchView;

    private Context context;

    FirebaseAuth auth;
    FirebaseUser user;
    private Button logout;
    Handler handler = new Handler(Looper.getMainLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_selection);
        context = this;

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        logout = findViewById(R.id.Logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutB();
            }
        });
        if (user == null) {
            openMainActivity();
            finish();
        }

        textView = findViewById(R.id.list);
        recyclerView = findViewById(R.id.song_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        storageRef = FirebaseStorage.getInstance().getReference("SongTitles");
        fxlabButton = findViewById(R.id.fxlab);
        getSongs("");

        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String song) {
                getSongs(song);
                return false;
            }
        });

        fxlabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFxlab();
            }
        });
    }
    public void logoutB(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void openFxlab(){
        Intent intent = new Intent(this, FXMainActivity.class);
        startActivity(intent);
    }

    private void getSongs(String song) {
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
                            Boolean titleStart = false;
                            for (int j = 0; j< arrOfStr.length; j++){
                                if(arrOfStr[j].equals("-")){
                                    titleStart = true;
                                }
                                else if(titleStart == false){
                                    name += arrOfStr[j] + " ";
                                }
                                else{
                                    tempTitle += arrOfStr[j] + " ";
                                }
                            }
                            String[] title = tempTitle.split("\\.");
                            int sizeSong = song.length();
                            if(sizeSong == 0 || (song.toLowerCase()).equals(title[0].substring(0,sizeSong).toLowerCase()) ) {
                                songs.add(new Song(title[0], name, "", 4.5f));
                            }
                            i++;
                        }
                        int list = songs.size();
                        if (list == 0){
                            songs.add(new Song("Song Not Found", "","",0));
                            recyclerView.setAdapter(new SongListAdapter(songs));
                        }
                        else {
                            adapter = new SongListAdapter(songs);
                            adapter.setOnItemClickListener(new SongListAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    if (list > 0) {
                                        Song song = songs.get(position);
                                        showPreviewDialog(song, forContext[position]);
                                        fullSongName = song.getArtist() + "- " + song.getName();
                                        Log.d("Song", fullSongName);
                                        downloadWavFromFirebase(fullSongName);
                                    }
                                }
                            });

                            recyclerView.setAdapter(adapter);
                        }
                    }

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Uh-oh, an error occurred!
                    }
                });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, LiveEffectDemo.class);
        startActivity(intent);
    }

    public void switchContext(String songName, int songMode) {
            Intent intent = new Intent(this, LiveEffectDemo.class);
            intent.putExtra("songName",songName);
            intent.putExtra("songMode", songMode);
            startActivity(intent);

    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showPreviewDialog(Song song, String context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_preview, null);
        builder.setView(view);

        // Set the title of the dialog to the song name
        TextView titleTextView = view.findViewById(R.id.dialog_preview_title);
        titleTextView.setText(song.getName());

        // Set the artist, duration and rating of the song
        TextView artistTextView = view.findViewById(R.id.dialog_preview_artist);
        artistTextView.setText(song.getArtist());

        TextView durationTextView = view.findViewById(R.id.dialog_preview_duration);
        durationTextView.setText(song.getDuration());

        RatingBar ratingBar = view.findViewById(R.id.dialog_preview_rating);
        ratingBar.setRating(song.getRating());

        RadioGroup radioGroup = view.findViewById(R.id.dialog_preview_radioGroup);

        final int[] mode_selection = {0}; // 0:original mode; 1:instrumental mode
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = view.findViewById(i);
                mode_selection[0] = (radioButton.getId() == R.id.radioButton_inst) ? 1 : 0;
            }
        });


        // Set the positive button to switch to the song activity
        builder.setPositiveButton(R.string.select, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String ext;
                if (mode_selection[0] == 1) {
                    ext = ".wav";
                }
                else {
                    ext = ".mp3";
                }
                switchContext(song.getArtist()+ "- " + song.getName() + ext, mode_selection[0]);
            }
        });

        // Set the negative button to cancel the dialog
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        builder.create().show();
    }
    private void downloadWavFromFirebase(String songName) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference songRef = storageRef.child("SongInstrumental/" + songName + ".wav");
        Log.d("Download", songName);
        File musicDirectory = new File(Environment.getExternalStorageDirectory(), "Download");
        if (!musicDirectory.exists()) {
            musicDirectory.mkdirs();
        }

        File wavFile = new File(musicDirectory, songName + "_unsampled" + ".wav");
        File wavFileSampled = new File(musicDirectory, songName + ".wav");
        if (wavFile.exists()) {
            Log.d("downloadWavFromFirebase", "File already exists, skipping download.");
            return;
        }
        songRef.getFile(wavFile).addOnSuccessListener(taskSnapshot -> {
            // Local file has been created
            resampleAudioFile(wavFile, wavFileSampled, 44100);
            Runnable deleteWavRecording = new Runnable() {
                @Override
                public void run() {
                    wavFile.delete();
                }
            };
            handler.postDelayed(deleteWavRecording, 20000);
        }).addOnFailureListener(exception -> {
            // Handle any errors
        });
    }

    private void resampleAudioFile(File inputFile, File outputFile, int targetSampleRate) {
        String ffmpegCmd = String.format("-i \"%s\" -ar %d -ac 2 -sample_fmt s16 \"%s\"", inputFile.getAbsolutePath(), targetSampleRate, outputFile.getAbsolutePath());

        FFmpegKit.executeAsync(ffmpegCmd, session -> {
            ReturnCode returnCode = session.getReturnCode();
            if (ReturnCode.isSuccess(returnCode)) {
                inputFile.delete();
                Log.d("FFmpeg", "Resampling succeeded.");
            } else if (ReturnCode.isCancel(returnCode)) {
                Log.d("FFmpeg", "Resampling cancelled.");
            } else {
                Log.d("FFmpeg", "Resampling failed.");
            }
        });
    }


    public static String getFullSongName() {
        return fullSongName;
    }
    private void requestStoragePermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }
}