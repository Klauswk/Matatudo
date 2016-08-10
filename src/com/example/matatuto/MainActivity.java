package com.example.matatuto;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class MainActivity extends Activity {
	FrameLayout frame;
	Button bt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		bt = new Button(this);
		frame  = new FrameLayout(this);  
		final Game game = new Game(this);
		super.onCreate(savedInstanceState);
		bt.setText("Parar");
		bt.setTextSize(8);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		 RelativeLayout.LayoutParams b1 = new LayoutParams(  
				                  100,  
		                   50);  
		 RelativeLayout.LayoutParams params = new LayoutParams(  
				                      RelativeLayout.LayoutParams.FILL_PARENT,  
				                      RelativeLayout.LayoutParams.FILL_PARENT);  
		 bt.setLayoutParams(b1);
		
		frame.addView(game);
		frame.addView(bt);
		setContentView(frame);
		
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
