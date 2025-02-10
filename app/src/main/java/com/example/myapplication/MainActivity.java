package com.example.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {
    private static MediaPlayer musique;

    private static int volume = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        this.musique = MediaPlayer.create(getApplicationContext(),R.raw.musique_fond);
        this.musique.setVolume(1,1);
        this.musique.setLooping(true);
        this.musique.start();
        Intent otherAcitivity = new Intent(getApplicationContext(), MainActivity2.class);
        startActivity(otherAcitivity);
        finish();
    }

    public static MediaPlayer getMusique (){
        return musique;
    }

}