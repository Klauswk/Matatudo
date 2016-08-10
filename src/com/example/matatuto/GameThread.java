package com.example.matatuto;

import android.graphics.Canvas;

public class GameThread extends Thread{
	private Game View;
	private boolean running = false;
	static final int FPS = 10;


	public GameThread(Game view)
	{
		this.View = view;
	}


	public boolean isRunning() {
		return running;
	}


	public void setRunning(boolean running) {
		this.running = running;
	}

	@Override
	public void run(){
		long fps = 1000/FPS;
		long startTime;
		long sleepTime;
		while(running)
		{
			Canvas canvas = null;
			startTime = System.currentTimeMillis();
			try 
			{
				canvas = View.getHolder().lockCanvas();
				synchronized(View.getHolder())
				{
					View.onDraw(canvas);
				}
			} 
			finally{
				if(canvas != null)
				{
					View.getHolder().unlockCanvasAndPost(canvas);
				}
			}
			sleepTime = fps - (System.currentTimeMillis() - startTime);
			try {
				if(sleepTime>0)
				{
					sleep(sleepTime);
				}
				else
				{
					sleep(10);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
