package com.example.myapplication;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.myapplication.MainActivity;


public class Setting extends AppCompatActivity{
    private ImageButton cutSound;
    private ImageView barSound;
    private static Boolean barHide = true;

    private SeekBar seekBarSound;
    private static int volume = 10;

    private ImageButton home;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_setting);
        this.cutSound = findViewById(R.id.cutSound);
        this.barSound = findViewById(R.id.barSound);
        this.seekBarSound = findViewById(R.id.seekBarSound);
        this.home = findViewById(R.id.homeButton);
        seekBarSound.setMax(10);
        seekBarSound.setProgress(volume);

        if (!barHide) {
            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
            barSound.setAnimation(animation);
            barSound.setVisibility(View.VISIBLE);
            barSound.startAnimation(animation);
            seekBarSound.setProgress(0);


        }

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherAcitivity = new Intent(getApplicationContext(), MainActivity2.class);
                startActivity(otherAcitivity);
                finish();
            }
        });

        cutSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer musique = MainActivity.getMusique();
                if (barHide) {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
                    barSound.setAnimation(animation);
                    barSound.setVisibility(View.VISIBLE);
                    barSound.startAnimation(animation);
                    musique.setVolume(0.0f,0.0f);
                    seekBarSound.setProgress(0);

                }
                else{
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                    barSound.setAnimation(animation);
                    barSound.setVisibility(View.GONE);
                    barSound.startAnimation(animation);
                    seekBarSound.setProgress(volume);
                    musique.setVolume((volume/(float) 10),(volume/(float) 10));
                }
            }
        });
        seekBarSound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MediaPlayer musique = MainActivity.getMusique();
                musique.setVolume(progress/(float)10,progress/(float)10);
                if (progress==0){
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
                    barSound.setAnimation(animation);
                    barSound.setVisibility(View.VISIBLE);
                    barSound.startAnimation(animation);
                    barHide = false;
                }
                else {
                    if (!barHide){
                        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                        barSound.setAnimation(animation);
                        barSound.setVisibility(View.GONE);
                    }
                    barHide = true;
                    volume = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}