package com.example.ece381;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class Player extends MainActivity implements OnSeekBarChangeListener {  
	SeekBar seekBar; // Volume Bar
	TextView txt1; //txt2
	
	/** Called when the activity is first created. */  
	@Override  
	public void onCreate(Bundle savedInstanceState) {  
	      super.onCreate(savedInstanceState);  
	      setContentView(R.layout.player);  
	      
	      MyApplication app = (MyApplication) getApplication();
	      
	      seekBar = (SeekBar) findViewById(R.id.player_volume);
	      txt1 = (TextView) findViewById(R.id.volume_level);
	      int maxVolume = 10; //10
	      Integer currentVolume = app.getVolume();
	      txt1.setText(currentVolume.toString());
	      seekBar.setMax(maxVolume);
	      seekBar.setProgress(app.getVolume());
	      seekBar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
			
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				//txt1.setText("Stopped");
				MyApplication app = (MyApplication) getApplication();
				app.setCommand("5");
				sendMessage();
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				//txt1.setText("Started");
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
							//Cool feature! makes the text bar change with the volume level
							//txt1.setTextSize(progress);	
				MyApplication app = (MyApplication) getApplication();
				app.setVolume(progress);
				txt1.setText(app.getVolume().toString());
			}
		});
	}  
	
	private boolean pauseHasRun = false;
	//On pause for android activity lifecycle
	@Override
	public void onPause(){
		super.onPause();
		if (!pauseHasRun) {
	        pauseHasRun = true;
	        return;
		}
	}
	
	private boolean resumeHasRun = false;
	//On resume for android activity lifecycle
	@Override
	public void onResume(){
		super.onResume();
		if (!resumeHasRun) {
	        resumeHasRun = true;
	        return;
		}
	}
	
	/// Methods for SeekBar(Volume) bar need otherwise results in error

	@Override
	public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void prevPage(View view) {	
		onBackPressed();
	}
	
	public void nextPage(View view) {	
		Intent myIntent = new Intent(view.getContext(), PlaylistActivity.class);  
		startActivity(myIntent);
	}
	
	// Method for when any of the buttons are clicked
	public void onClick(View view){
		MyApplication app = (MyApplication) getApplication();
		switch(view.getId()){
		case R.id.play:
			app.setCommand("1");
			break;
		case R.id.pause:
			app.setCommand("2");
			break;
		case R.id.next:
			app.setCommand("3");
			break;
		case R.id.previous:
			app.setCommand("4");
			break;
		case R.id.shuffle_button:
			app.setCommand("7");
			break;
		}
		
		sendMessage();
	}
} 