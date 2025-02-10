package com.example.myapplication;

import android.animation.Animator;
import android.os.Build;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.util.Random;

public class thread extends Thread{
    ImageView ground;
    ImageView fallingCircle;

    public thread(ImageView ground,ImageView fallingCircle){
        this.ground=ground;
        this.fallingCircle=fallingCircle;
    }

    public void run(){
        placement();
    }
    private void animateFall(){
        Random numberRandom = new Random();
        float newY = ground.getY()-10;
        int duration = numberRandom.nextInt(3000)+6000;
        int delay = numberRandom.nextInt(3000);
        ViewPropertyAnimator animator = fallingCircle.animate().
                translationY(newY).
                setInterpolator(new AccelerateInterpolator()).
                setDuration(duration).
                setStartDelay(delay);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                moveFallingCircle();
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {

            }
        });
        animator.start();
    }

    private void moveFallingCircle(){
        Random numberRandom = new Random();
        float scale = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            scale = numberRandom.nextFloat(1)+0.2f;
        }

        int newY= numberRandom.nextInt(500)-550;
        int newX = numberRandom.nextInt(1000)-500;
        ViewPropertyAnimator animator = fallingCircle.animate().
                translationY(newY).
                translationX(newX).
                scaleX(scale).
                scaleY(scale).
                setInterpolator(new AccelerateInterpolator()).
                setDuration(1);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                animateFall();
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {

            }
        });
    }

    private void placement(){
        Random numberRandom = new Random();
        int newY= numberRandom.nextInt((500))-500;
        int newX = numberRandom.nextInt(1000)-500;

        ViewPropertyAnimator animator = fallingCircle.animate().
                translationY(newY).
                translationX(newX).
                setInterpolator(new AccelerateInterpolator()).
                setDuration(1);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                waitPlacement();
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {

            }
        });
    }

    private void waitPlacement(){
        Random numberRandom = new Random();
        float scale = 1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            scale = numberRandom.nextFloat(1)+0.2f;
        }
        int delay=0;
        fallingCircle.setImageAlpha(175);
        if (fallingCircle.getId()==R.id.fallingcircle2){
            delay+=2000;
            fallingCircle.setImageAlpha(155);
        }
        if (fallingCircle.getId()==R.id.fallingcircle3){
            delay+=4000;
            fallingCircle.setImageAlpha(115);
        }
        if (fallingCircle.getId()==R.id.fallingcircle4){
            delay+=6000;
            fallingCircle.setImageAlpha(135);
        }
        if (fallingCircle.getId()==R.id.fallingcircle5){
            delay+=8000;
            fallingCircle.setImageAlpha(95);
        }

        ViewPropertyAnimator animator = fallingCircle.animate().
                translationY(fallingCircle.getY()).
                setInterpolator(new AccelerateInterpolator()).
                setDuration(1).
                scaleY(scale).
                scaleX(scale).
                setStartDelay(delay);
        animator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                animateFall();
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {

            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {

            }
        });
    }
}
