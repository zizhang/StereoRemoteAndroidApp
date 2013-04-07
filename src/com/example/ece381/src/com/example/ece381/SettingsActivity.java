package com.example.ece381;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class SettingsActivity extends MainActivity {
	ImageButton home;
	SeekBar trebleBar;
	SeekBar midBar;
	SeekBar bassBar;
	SeekBar fadeBar;
	SeekBar balanceBar;
	
	/** Called when the activity is first created. */  
	@Override  
	public void onCreate(Bundle savedInstanceState) { 
		MyApplication app = (MyApplication) getApplication();
		super.onCreate(savedInstanceState);  
		setContentView(R.layout.settings);  
      
		home = (ImageButton) findViewById(R.id.home);
		home.setOnTouchListener(homeListener);
      
		trebleBar = (SeekBar) findViewById(R.id.treble);
		midBar = (SeekBar) findViewById(R.id.mid);
		bassBar = (SeekBar) findViewById(R.id.bass);
		fadeBar = (SeekBar) findViewById(R.id.fade);
		balanceBar = (SeekBar) findViewById(R.id.balance);
		
		Integer currentTreble = app.getTreble();
		Integer currentMid = app.getMid();
		Integer currentBass = app.getBass();
		
		Integer currentFade = app.getFader();
		
		Integer currentBalance = app.getBalance();
      
		
		/**
		 * TREBLE BAR
		 */
		trebleBar.setMax(10);
		trebleBar.setProgress(currentTreble);
		
		trebleBar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
		
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				//Treble command
				MyApplication app = (MyApplication) getApplication();
				String cmd = "B" + app.getTreble() + " " + app.getMid() + " " + app.getBass();
				app.setCommand(cmd);
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
				app.setTreble(progress);
			}
		});
		
		/**
		 * MID BAR
		 */
		midBar.setMax(10);
		midBar.setProgress(currentMid);
		
		midBar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
		
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				//Treble command
				MyApplication app = (MyApplication) getApplication();
				String cmd = "B" + app.getTreble() + " " + app.getMid() + " " + app.getBass();
				app.setCommand(cmd);
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
				app.setMid(progress);
			}
		});
		
		/**
		 * BASS BAR
		 */
		bassBar.setMax(10);
		bassBar.setProgress(currentBass);
		
		bassBar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
		
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				//Treble command
				MyApplication app = (MyApplication) getApplication();
				String cmd = "B" + app.getTreble() + " " + app.getMid() + " " + app.getBass();
				app.setCommand(cmd);
				sendMessage();
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
							//Cool feature! makes the text bar change with the volume level
							//txt1.setTextSize(progress);	
				MyApplication app = (MyApplication) getApplication();
				app.setBass(progress);
			}
		});
		
		/**
		 * fade BAR
		 */
		fadeBar.setMax(10);
		fadeBar.setProgress(currentFade);
		fadeBar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
		
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				//Treble command
				MyApplication app = (MyApplication) getApplication();
				app.setCommand("D" + app.getFader());
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
				app.setFader(progress);
			}
		});
		
		/**
		 * BALANCE BAR
		 */
		balanceBar.setMax(10);
		balanceBar.setProgress(currentBalance);
		balanceBar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
		
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				//Treble command
				MyApplication app = (MyApplication) getApplication();
				app.setCommand("6" + app.getBalance());
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
				app.setBalance(progress);
			}
		});
	    
	}
	
    View.OnTouchListener homeListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
	        if (event.getAction() == MotionEvent.ACTION_DOWN) {
	        	home.setImageResource(R.drawable.home_1);
	        }
	        else if (event.getAction()==MotionEvent.ACTION_UP){
	        	home.setImageResource(R.drawable.home_0);
	        	Intent myIntent0 = new Intent(getBaseContext(),Player.class);
	        	startActivity(myIntent0);
	        }
			return false;
		}
	};
	
	@Override
	public void onBackPressed() {
		//Do nothing
	}
}