package com.example.karaok;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SongViewHolder extends RecyclerView.ViewHolder {

    private TextView songNameTextView;
    private TextView songArtistTextView;
    private TextView songDurationTextView;
    private RatingBar songRatingBar;

    public SongViewHolder(@NonNull View itemView) {
        super(itemView);
        songNameTextView = itemView.findViewById(R.id.song_name);
        songArtistTextView = itemView.findViewById(R.id.song_artist);
        songDurationTextView = itemView.findViewById(R.id.song_duration);
        songRatingBar = itemView.findViewById(R.id.song_rating);
    }

    public void bind(Song song) {
        songNameTextView.setText(song.getName());
        songArtistTextView.setText(song.getArtist());
        songDurationTextView.setText(song.getDuration());
        songRatingBar.setRating(song.getRating());
    }

    public TextView getSongNameTextView() {
        return songNameTextView;
    }

    public TextView getSongArtistTextView() {
        return songArtistTextView;
    }

    public TextView getSongDurationTextView() {
        return songDurationTextView;
    }

    public RatingBar getSongRatingBar() {
        return songRatingBar;
    }
}

