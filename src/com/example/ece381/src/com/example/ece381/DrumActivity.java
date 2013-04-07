package com.example.ece381;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

public class DrumActivity extends MainActivity {
	ImageButton drum1;
	ImageButton drum2;
	ImageButton drum3;
	ImageButton drum4;
	ImageButton drum5;
	ImageButton drum6;
	ImageButton drum7;
	ImageButton drum8;
	ImageButton homeFdrums;
	
	
	/** Called when the activity is first created. */  
	@Override  
	public void onCreate(Bundle savedInstanceState) {  
	      super.onCreate(savedInstanceState);  
	      setContentView(R.layout.drums);  
	      
	      
	      drum1 = (ImageButton) findViewById(R.id.drum1);
	      drum1.setOnTouchListener(drum1Listener);
	      
	      drum2 = (ImageButton) findViewById(R.id.drum2);
	      drum2.setOnTouchListener(drum2Listener);
	      
	      drum3 = (ImageButton) findViewById(R.id.drum3);
	      drum3.setOnTouchListener(drum3Listener);
	      
	      drum4 = (ImageButton) findViewById(R.id.drum4);
	      drum4.setOnTouchListener(drum4Listener);
	      
	      drum5 = (ImageButton) findViewById(R.id.drum5);
	      drum5.setOnTouchListener(drum5Listener);
	      
	      drum6 = (ImageButton) findViewById(R.id.drum6);
	      drum6.setOnTouchListener(drum6Listener);
	      
	      drum7 = (ImageButton) findViewById(R.id.drum7);
	      drum7.setOnTouchListener(drum7Listener);
	      
	      drum8 = (ImageButton) findViewById(R.id.drum8);
	      drum8.setOnTouchListener(drum8Listener);
     
	      homeFdrums = (ImageButton) findViewById(R.id.homeFdrums);
	      homeFdrums.setOnTouchListener(homeListener);
	      
	      
	    
}
	
    View.OnTouchListener homeListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
	        if (event.getAction() == MotionEvent.ACTION_DOWN) {
	        	homeFdrums.setImageResource(R.drawable.home_1);
	        }
	        else if (event.getAction()==MotionEvent.ACTION_UP){
	        	homeFdrums.setImageResource(R.drawable.home_0);
	        	Intent myIntent0 = new Intent(getBaseContext(),Player.class);
	        	startActivity(myIntent0);
	        }
			return false;
		}
	};

    View.OnTouchListener drum1Listener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
	    	MyApplication app = (MyApplication) getApplication();
	    	if (!app.getLock()) {
				// TODO Auto-generated method stub
		        if (event.getAction() == MotionEvent.ACTION_DOWN) {
		        	drum1.setImageResource(R.drawable.drum1_1);
		        	app.setLock(true);
		        	app.setCommand("C1");
		        	sendMessage();
		        	app.setLock(false);
		        }
		        else if (event.getAction()==MotionEvent.ACTION_UP){
		        	drum1.setImageResource(R.drawable.drum1_0);
		        }
	    	}
	    	return false;
		}
	};
	
    View.OnTouchListener drum2Listener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
	    	MyApplication app = (MyApplication) getApplication();
	    	if (!app.getLock()) {
				// TODO Auto-generated method stub
		        if (event.getAction() == MotionEvent.ACTION_DOWN) {
		        	drum2.setImageResource(R.drawable.drum1_1);
		        	app.setLock(true);
		        	app.setCommand("C2");
		        	sendMessage();
		        	app.setLock(false);
		        }
		        else if (event.getAction()==MotionEvent.ACTION_UP){
		        	drum2.setImageResource(R.drawable.drum1_0);
		        }
	    	}
	    	return false;
		}
	};
	
    View.OnTouchListener drum3Listener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
	    	MyApplication app = (MyApplication) getApplication();
	    	if (!app.getLock()) {
				// TODO Auto-generated method stub
		        if (event.getAction() == MotionEvent.ACTION_DOWN) {
		        	drum3.setImageResource(R.drawable.drum1_1);
		        	app.setLock(true);
		        	app.setCommand("C3");
		        	sendMessage();
		        	app.setLock(false);
		        }
		        else if (event.getAction()==MotionEvent.ACTION_UP){
		        	drum3.setImageResource(R.drawable.drum1_0);
		        }
	    	}
	    	return false;
		}
	};
	
    View.OnTouchListener drum4Listener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
	    	MyApplication app = (MyApplication) getApplication();
	    	if (!app.getLock()) {
				// TODO Auto-generated method stub
		        if (event.getAction() == MotionEvent.ACTION_DOWN) {
		        	drum4.setImageResource(R.drawable.drum1_1);
		        	app.setLock(true);
		        	app.setCommand("C4");
		        	sendMessage();
		        	app.setLock(false);
		        }
		        else if (event.getAction()==MotionEvent.ACTION_UP){
		        	drum4.setImageResource(R.drawable.drum1_0);
		        }
	    	}
	    	return false;
		}
	};
	
    View.OnTouchListener drum5Listener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
	    	MyApplication app = (MyApplication) getApplication();
	    	if (!app.getLock()) {
				// TODO Auto-generated method stub
		        if (event.getAction() == MotionEvent.ACTION_DOWN) {
		        	drum5.setImageResource(R.drawable.drum2_1);
		        	app.setLock(true);
		        	app.setCommand("C5");
		        	sendMessage();
		        	app.setLock(false);
		        }
		        else if (event.getAction()==MotionEvent.ACTION_UP){
		        	drum5.setImageResource(R.drawable.drum2_0);
		        }
	    	}
	    	return false;
		}
	};
	
    View.OnTouchListener drum6Listener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
	    	MyApplication app = (MyApplication) getApplication();
	    	if (!app.getLock()) {
				// TODO Auto-generated method stub
		        if (event.getAction() == MotionEvent.ACTION_DOWN) {
		        	drum6.setImageResource(R.drawable.drum2_1);
		        	app.setLock(true);
		        	app.setCommand("C6");
		        	sendMessage();
		        	app.setLock(false);
		        }
		        else if (event.getAction()==MotionEvent.ACTION_UP){
		        	drum6.setImageResource(R.drawable.drum2_0);
		        }
	    	}
	    	return false;
		}
	};
	
    View.OnTouchListener drum7Listener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
	    	MyApplication app = (MyApplication) getApplication();
	    	if (!app.getLock()) {
				// TODO Auto-generated method stub
		        if (event.getAction() == MotionEvent.ACTION_DOWN) {
		        	drum7.setImageResource(R.drawable.drum2_1);
		        	app.setLock(true);
		        	app.setCommand("C7");
		        	sendMessage();
		        	app.setLock(false);
		        }
		        else if (event.getAction()==MotionEvent.ACTION_UP){
		        	drum7.setImageResource(R.drawable.drum2_0);
		        }
	    	}
	    	return false;
		}
	};
	
    View.OnTouchListener drum8Listener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
	    	MyApplication app = (MyApplication) getApplication();
	    	if (!app.getLock()) {
				// TODO Auto-generated method stub
		        if (event.getAction() == MotionEvent.ACTION_DOWN) {
		        	drum8.setImageResource(R.drawable.drum2_1);
		        	app.setLock(true);
		        	app.setCommand("C8");
		        	sendMessage();
		        	app.setLock(false);
		        }
		        else if (event.getAction()==MotionEvent.ACTION_UP){
		        	drum8.setImageResource(R.drawable.drum2_0);
		        }
	    	}
	    	return false;
		}
	};
	
	@Override
	public void onBackPressed() {
		// Do nothing
	}
	
}