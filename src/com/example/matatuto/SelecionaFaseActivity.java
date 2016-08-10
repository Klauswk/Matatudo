package com.example.matatuto;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SelecionaFaseActivity extends Activity{
	HorizontalScrollView hsv;
	ImageView a,b,c,d,e;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		hsv = (HorizontalScrollView) findViewById(R.id.hsv);
		final LinearLayout layout = (LinearLayout) findViewById(R.id.meulayout);
		final Bundle bundle = new Bundle();
		ImageView fases[] = new ImageView[5];
		
		for (int i = 0; i < fases.length; i++) {
			fases[i] = new ImageView(this);
		}
		for (int i = 0; i < fases.length; i++) {
			fases[i] = new ImageView(this);

			fases[i].setAdjustViewBounds(true);
			fases[i].setMaxHeight(100);
			fases[i].setMaxWidth(100);
		}
		
		fases[0].setImageResource(R.drawable.mapa);
		fases[1].setImageResource(R.drawable.mapa2);
		fases[2].setImageResource(R.drawable.mapa3);
		fases[3].setImageResource(R.drawable.mapa4);
		

		layout.addView(Adicionarview(fases[0],"Fase 1"));
		layout.addView(Adicionarview(fases[1],"Fase 2"));
		layout.addView(Adicionarview(fases[2],"Fase 3"));
		layout.addView(Adicionarview(fases[3],"Fase 4"));
		
		fases[0].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				bundle.putInt("Fase", 1);
				bundle.putInt("condicao", 1000);
				Intent ChamaJogo = new Intent(getBaseContext(),GameActivity.class);
				ChamaJogo.putExtras(bundle);
				startActivity(ChamaJogo);

			}
		});

		fases[1].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				bundle.putInt("Fase", 2);
				bundle.putInt("condicao", 2000);
				Intent ChamaJogo = new Intent(getBaseContext(),GameActivity.class);
				ChamaJogo.putExtras(bundle);
				startActivity(ChamaJogo);

			}
		});
		
		fases[2].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				bundle.putInt("Fase", 3);
				bundle.putInt("condicao", 3000);
				Intent ChamaJogo = new Intent(getBaseContext(),GameActivity.class);
				ChamaJogo.putExtras(bundle);
				startActivity(ChamaJogo);

			}
		});
		
		fases[3].setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				bundle.putInt("Fase", 4);
				bundle.putInt("condicao", 4000);
				Intent ChamaJogo = new Intent(getBaseContext(),GameActivity.class);
				ChamaJogo.putExtras(bundle);
				startActivity(ChamaJogo);

			}
		});
	}

View Adicionarview(View view, String text){

	LinearLayout layout = new LinearLayout(getApplicationContext());
	layout.setLayoutParams(new LayoutParams(250, 250));
	layout.setOrientation(LinearLayout.VERTICAL);
	TextView textv = new TextView(this);
	textv.setText(text);
	textv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
	layout.addView(view);
	layout.addView(textv);
	return layout;
}

}
