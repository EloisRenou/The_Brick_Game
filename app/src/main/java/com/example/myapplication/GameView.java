package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GameView extends View {
    private float rectX;
    private float rectY;
    private final float rectWidth = 300;
    private final float rectHeight = 50;
    private final Paint rectPaint;
    private final Paint obstaclePaint;
    private float ballX, ballY;
    private float ballRadius = 50;
    private float ballSpeedX, ballSpeedY;
    private final Paint ballPaint;
    private boolean isDragging = false;
    private final int screenWidth, screenHeight;
    private Bitmap background;
    private List<int[]> hideRect;
    private List<int[]> showedRect;
    private float obstacleWidth;
    private float obstacleHeight;
    private int nbObstacleHorizontaly=6;
    private int nbObstacleVerticaly = 13;
    public GameView(Context context, int screenWidth, int screenHeight) {
        super(context);
        background = BitmapFactory.decodeResource(getResources(),R.drawable.backgroundjgp);
        background = Bitmap.createScaledBitmap(background,screenWidth,screenHeight,true);
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.rectY=screenHeight-400;

        // Rectangle
        rectX = screenWidth / 2f - rectWidth / 2;
        rectPaint = new Paint();
        rectPaint.setColor(0xFF74E2FA);


        // Balle
        ballX = screenWidth / 2f;
        ballY = 3* screenHeight / 4f;
        Random random = new Random();
        ballSpeedX = (random.nextFloat() * 15) + 15;
        ballSpeedY = (random.nextFloat() * 15) + 15;
        ballPaint = new Paint();
        ballPaint.setColor(0xFFCD33A2);

        //Obstacle
        obstaclePaint = new Paint();
        obstaclePaint.setStyle(Paint.Style.STROKE);
        obstaclePaint.setColor(0xFF74E2FA);
        obstaclePaint.setStrokeWidth(10);

        obstacleWidth = (screenWidth-20)/(float) nbObstacleHorizontaly;
        obstacleHeight = ((float) screenHeight/2+100)/ nbObstacleVerticaly;
        hideRect = new LinkedList<>();
        showedRect = new LinkedList<>();

        for (int i =0; i<nbObstacleHorizontaly;i++){
            for (int j=0; j<nbObstacleVerticaly; j++){
                int [] array= new int[2];
                array[0]=i;
                array[1]=j;
                hideRect.add(array);
            }
        }
        postInvalidateDelayed(8);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background,0,0,null);
        canvas.drawRect(rectX, rectY, rectX + rectWidth, rectY + rectHeight, rectPaint);
        canvas.drawCircle(ballX, ballY, ballRadius, ballPaint);

        for (int[] tab: showedRect){
            canvas.drawRect(tab[0]*obstacleWidth+10,tab[1]*obstacleHeight,(tab[0]+1)*obstacleWidth+10,(tab[1]+1)*obstacleHeight,obstaclePaint);
        }

        updateBall();
        postInvalidateDelayed(16); // ca ca indique à l'ecran de se redessiner dans 16 milliseconde et donc ca appel on draw
    }

    private void updateBall() {

        ballX += ballSpeedX;
        ballY += ballSpeedY;

        if (ballX - ballRadius <= 0 || ballX + ballRadius >= screenWidth) {
            ballSpeedX *= -1; //gauche droite
        }
        if (ballY - ballRadius <= 0) { //haut
            ballSpeedY *= -1;
        }
        if (ballY + ballRadius >= rectY && ballY + ballRadius <= rectY + rectHeight &&
                ballX >= rectX && ballX <= rectX + rectWidth) { //rectangle
            ballSpeedY *= -1;
            ballY = rectY - ballRadius;
        }
        for (int[] tab : showedRect){
            System.out.println(tab[0]+" " +tab[1] );
            if ((ballX + ballRadius > tab[0]*obstacleWidth) && (ballX - ballRadius < (tab[0]+1)*obstacleWidth )&&
                    ballY +ballRadius> tab[1]*obstacleHeight && ballY-ballRadius< (1+tab[1])*obstacleHeight) {
                if (ballX > tab[0]*obstacleWidth && ballX < (tab[0]+1)*obstacleWidth) {
                    ballSpeedY *= -1;
                    hideRect.add(tab);
                    showedRect.remove(tab);
                    // Inverser la direction horizontale
                }
                // Rebonds sur le côté supérieur ou inférieur (vertical)
                if (ballY > tab[1]*obstacleHeight && ballY< (1+tab[1])*obstacleHeight) {
                    ballSpeedX  *= -1;
                    if (!hideRect.contains(tab)) {
                        hideRect.add(tab);
                    }
                    showedRect.remove(tab);
                }
                break;
            }
        }
        if (ballY + ballRadius >= screenHeight) { //game over
            Intent otherAcitivity = new Intent(getContext(), LostActivity.class);
            getContext().startActivity(otherAcitivity);
        }
        updateRect(ballX,ballY);
    }

    private void updateRect(float ballX, float ballY){
        Random random = new Random();
        int numberRandom = random.nextInt(25); //random.nextInt(1);
        if (numberRandom==1){
            while (!hideRect.isEmpty()){
                int[] randomRectangle = hideRect.get(random.nextInt(hideRect.size()));
                if (!obstacleOnBall(ballX,ballY,randomRectangle[0],randomRectangle[1])) {
                    hideRect.remove(randomRectangle);
                    showedRect.add(randomRectangle);
                    break;
                }

            }
        }
    }

    private boolean obstacleOnBall(float ballX, float ballY, int i, int j){
        return (ballX > i*obstacleWidth - 3* ballRadius ) && (ballX < (i+1)*obstacleWidth + 3* ballRadius  )&&
                (ballY +3*ballRadius> j*obstacleHeight) && (ballY-3* ballRadius< (1+j)*obstacleHeight);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (event.getX() >= rectX && event.getX() <= rectX + rectWidth &&
                        event.getY() >= rectY && event.getY() <= rectY + rectHeight) {
                    isDragging = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isDragging && event.getX()+rectWidth/2<screenWidth && event.getX()>rectWidth/2) {
                    rectX = event.getX() - rectWidth / 2;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                isDragging = false;
                break;
        }
        return true;
    }
}
