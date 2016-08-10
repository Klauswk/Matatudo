package com.example.matatuto;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity {	/*FastRenderView renderView;            	public void onCreate(Bundle savedInstanceState) {		super.onCreate(savedInstanceState);		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,				WindowManager.LayoutParams.FLAG_FULLSCREEN);		renderView = new FastRenderView(this);		setContentView(renderView); 	}      	protected void onResume() {		super.onResume();		renderView.resume();	}	protected void onPause() {		super.onPause();         		renderView.pause();	}    
	
	protected void onDestroy() {
		super.onDestroy(); 
	}    
	
	@Override
	public void onBackPressed() {
	    super.onPause();
	    renderView.MostraPause();
	}	class FastRenderView extends SurfaceView implements Runnable {
		private int Pontos=0;
		private int Tempo = 30;
		private float currentTime = 0;
		private float frameTime = 0;
		private float accumulator = 0.0f;
		private int ticks = 0;
		MediaPlayer mediaplayer;		Thread renderThread = null;		Context context;		SurfaceHolder holder;		volatile boolean running = false;
		private boolean isPaused = false;
		private boolean flagFimJogo = false;
		private List<Sprite> sprites;
		private Bitmap bmp;
		public String auxilio;
		public Handler mHandler;
		final Dialog dialog;
		Button btSair, btReiniciar;
		SoundPool soundpool;
		int explosionId = -1;		public FastRenderView(Context context) {			super(context);
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			soundpool = new SoundPool(20,AudioManager.STREAM_MUSIC,0);
			mediaplayer = new MediaPlayer();
			
			try {
				AssetManager am = getAssets();
				AssetFileDescriptor descriptor = am.openFd("musicas/batida.ogg");
				explosionId = soundpool.load(descriptor, 1);
				descriptor = am.openFd("musicas/zelda.mp3");
				mediaplayer.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset(),descriptor.getLength());
				mediaplayer.prepare();
				mediaplayer.setLooping(true);
				} 
			
			catch (Exception e) {
					Log.i("Game", "Audio nao pode ser carregado!");
				}
			
			dialog = new Dialog(context);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.fimjogo);
			dialog.setCanceledOnTouchOutside(false);
			btSair = (Button) dialog.findViewById(R.id.btSairJogo);
			btReiniciar = (Button) dialog.findViewById(R.id.btReiniciarJogo);			this.context = context;			holder = getHolder(); 
			sprites = new ArrayList<Sprite>();
			auxilio = "Nada a declarar";
			holder.addCallback(new SurfaceHolder.Callback() {

				@Override
				public void surfaceDestroyed(SurfaceHolder holder) {

				}

				@Override
				public void surfaceCreated(SurfaceHolder holder) {

				}

				@Override
				public void surfaceChanged(SurfaceHolder holder, int format, int width,
						int height) {

				}
			});
			AdicionarInimigos();
		}
		
		public void destroy() {
			
			
		}

		private void AdicionarInimigos() {
			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.personagem);
			for(int i = 0; i < 3; i++)
			{
				sprites.add(new Sprite(this,bmp));
			}	
		}
		public void resume() {          			running = true;			renderThread = new Thread(this);			renderThread.start();
			if(isPaused)
			{
				MostraPause();
			}
			if(mediaplayer != null)
			{
				mediaplayer.start();
			}		}		public void pause() {			running = false; 
			isPaused = true;
			
			if(mediaplayer != null)
			{
				mediaplayer.pause();
				if(isFinishing())
				{
					mediaplayer.stop();
					mediaplayer.release();
				}
			}
						while(true) {				try {					renderThread.join();					break;				} catch (InterruptedException e) {					// retry				}			}
			renderThread = null;        		}		public void run() {	
			Looper.prepare();			while(running) {  				if(!holder.getSurface().isValid())					continue;
				Canvas canvas = holder.lockCanvas();            				drawSurface(canvas);                                           				holder.unlockCanvasAndPost(canvas);

				float newTime = System.nanoTime() / 1000000000.0f;
				Time.deltaTime = frameTime = newTime - currentTime;

				if(frameTime > Time.maxDeltaTime)
					frameTime = Time.maxDeltaTime;

				currentTime = newTime;

				accumulator += frameTime;

				while(accumulator > Time.fixedDeltaTime)
				{
					accumulator -= Time.fixedDeltaTime;
					ticks++;

					if(ticks > 10)
					{
						if(isPaused)
						{

						}
						else
						{
							Tempo--;
							ticks=0;
						}
					}		
				}
				if(Tempo==0)
				{
					runOnUiThread(new Runnable() { 
						@Override 
						public void run() { 
							if(!flagFimJogo)
							{
								dialog.show();
								flagFimJogo = true;
								isPaused = true;
							}
							else
							{
								btSair.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										dialog.cancel();
										Intent main = new Intent(context,MainActivity.class);
										context.startActivity(main);
										System.exit(0);
										
									}
								});

								btReiniciar.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										isPaused = false;
										flagFimJogo = false;
										Pontos = 0;
										Tempo = 5;
										AdicionarInimigos();		
										dialog.cancel();
									}
								});
							}

						} 
					}); 
				}

				++Time.frameCount;			}		
			Looper.loop();
		}       		private void drawSurface(Canvas canvas) {			canvas.drawRGB(255, 0, 0);
			for(int i = 0; i < sprites.size(); i++)
			{
				sprites.get(i).onDraw(canvas,isPaused);
			}
			Paint paint = new Paint(); 
			paint.setColor(Color.BLACK); 
			paint.setTextSize(20); 
			canvas.drawText(String.valueOf(Pontos), 10, 25, paint); 
			canvas.drawText(String.valueOf(Tempo), 300, 25, paint); 
			canvas.drawText(auxilio, 400, 25, paint);

		}
		
		@Override
		public boolean onTouchEvent(MotionEvent event)
		{
			for(int i = 0; i < sprites.size() ; i++)
			{
				Sprite sprite = sprites.get(i);
				if(sprite.Colisao(event.getX(),event.getY()))
				{
					if(explosionId != -1)
					{
						soundpool.play(explosionId, 1, 1, 1, 0, 1);
					}
					
					if(sprite.DiminuiVida())
					{
						sprites.remove(i);
						Tempo=Tempo + 5;
						Pontos= Pontos +100;
					}
					auxilio = "inimigo está com isso de vida" + sprite.vida;
				}
			}
			Handlers(sprites.size());
			return super.onTouchEvent(event);
		}

		private void Handlers(int size) {

			if(size==0)
			{
				AdicionarInimigos();
			}
			else if(Tempo <= 0)
			{
				FimdeJogo();
			}

		}
		private void MostraPause()
		{
			isPaused = true;
			final Dialog dialog = new Dialog(context);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.popup);
			dialog.setCanceledOnTouchOutside(false);
			ImageView imageview = (ImageView) dialog.findViewById(R.id.imageView1);
			dialog.show();

			imageview.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					dialog.cancel();
					isPaused = false;
				}
			});

		}
		private void FimdeJogo()
		{
			final Dialog dialog = new Dialog(context);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setContentView(R.layout.fimjogo);
			dialog.setCanceledOnTouchOutside(false);
			Button btSair, btReiniciar;
			isPaused = true;
			btSair = (Button) dialog.findViewById(R.id.btSairJogo);
			btReiniciar = (Button) dialog.findViewById(R.id.btReiniciarJogo);
			dialog.show();
			btSair.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent main = new Intent(context,MainActivity.class);
					context.startActivity(main);
					
					System.exit(0);
				}
			});

			btReiniciar.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					isPaused = false;
					Pontos = 0;
					Tempo = 5;
					AdicionarInimigos();		
					dialog.cancel();
				}
			});

		} 

	}*/}   
