package com.example.matatuto;

import java.util.ArrayList;
import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Button;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Game extends SurfaceView {
	private Bitmap bmp;
	private SurfaceHolder holder;
	private GameThread gamethread;
	private List<Sprite> sprites;
	private int Pontos=0;
	
	public Game(Context context) {
		super(context);
		holder = getHolder();
		gamethread = new GameThread(this);
		sprites = new ArrayList<Sprite>();
		holder.addCallback(new SurfaceHolder.Callback() {
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				gamethread.setRunning(true);
				gamethread.start();
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				
			}
		});
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.personagem);
		for(int i = 0; i < 20; i++)
		{
			sprites.add(new Sprite(this,bmp));
		}
	}
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.GREEN);
		for(int i = 0; i < sprites.size(); i++)
		{
			sprites.get(i).onDraw(canvas);
		}
		Paint paint = new Paint(); 
		paint.setColor(Color.BLACK); 
		paint.setTextSize(20); 
		canvas.drawText(String.valueOf(Pontos), 10, 25, paint); 
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		for(int i = 0; i < sprites.size() ; i++)
		{
			Sprite sprite = sprites.get(i);
			if(sprite.Colisao(event.getX(),event.getY()))
			{
				sprites.remove(i);
				Pontos= Pontos +100;
			}
		}
		return super.onTouchEvent(event);
	}
}
