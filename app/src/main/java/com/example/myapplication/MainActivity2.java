package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity2 extends AppCompatActivity {
    private ImageButton play;
    private ImageButton setting;
    private ImageView fallingCircle1;
    private ImageView fallingCircle2;
    private ImageView fallingCircle3;
    private ImageView fallingCircle4;
    private ImageView fallingCircle5;
    private ImageView ground;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        this.play = findViewById(R.id.play);
        this.setting = findViewById(R.id.setting);
        this.fallingCircle1 = findViewById(R.id.fallingcircle1);
        this.fallingCircle2 = findViewById(R.id.fallingcircle2);
        this.fallingCircle3 = findViewById(R.id.fallingcircle3);
        this.fallingCircle4 = findViewById(R.id.fallingcircle4);
        this.fallingCircle5 = findViewById(R.id.fallingcircle5);
        this.ground=findViewById(R.id.ground);
        thread fallingCircle1 = new thread(ground,this.fallingCircle1);
        thread fallingCircle2 = new thread(ground,this.fallingCircle2);
        thread fallingCircle3 = new thread(ground,this.fallingCircle3);
        thread fallingCircle4 = new thread(ground,this.fallingCircle4);
        thread fallingCircle5 = new thread(ground,this.fallingCircle5);
        fallingCircle1.start();
        fallingCircle2.start();
        fallingCircle3.start();
        fallingCircle4.start();
        fallingCircle5.start();
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherAcitivity = new Intent(getApplicationContext(), playActivity.class);
                startActivity(otherAcitivity);
                finish();
            }
        });
        setting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent otherAcitivity = new Intent(getApplicationContext(), Setting.class);
                startActivity(otherAcitivity);
                finish();
            }
        });

    }

}