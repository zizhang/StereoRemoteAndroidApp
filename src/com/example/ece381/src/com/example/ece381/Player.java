package com.example.ece381;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class Player extends MainActivity implements OnSeekBarChangeListener {  
	SeekBar seekBar; // Volume Bar
	ImageButton play2; 
	ImageButton next1;
	ImageButton prev1;
	ImageButton shuffle;
	ImageButton repeat;
	ImageButton settings;
	ImageButton connect;
	ImageButton drum;
	ImageButton mute;
	ImageButton list;
	ImageButton voice;
	ImageButton twit;
	public static TextView currentlyPlaying;
	
	/** Called when the activity is first created. */  
	@Override  
	public void onCreate(Bundle savedInstanceState) {  
	      super.onCreate(savedInstanceState);  
	      setContentView(R.layout.player);  
	      
	      MyApplication app = (MyApplication) getApplication();
	      
	      currentlyPlaying = (TextView) findViewById(R.id.currentlyPlaying);
	      
	      final Handler h = new Handler();
	      final Timer timer = new Timer();
	      
	      /**
	    	 *Timer task for updating the currently playing song
	    	 *on the player GUI.
	  	 */
	      TimerTask timerTask = new TimerTask() {
	    	  @Override
	    	  public void run() {

	    	  h.post(new Runnable() {
		    	  public void run() {
		    		  //Android UI get Updated continually
		    		  MyApplication app = (MyApplication) getApplication();
		    		  if(app.getPlayList().size() !=0 && app.getCurrentSong() != null) {
			    		  currentlyPlaying.setText(app.getCurrentSong().getArtistName() + " - " + app.getCurrentSong().getTitleName());
		    		  }
		    	  }
	    	  });

	    	  }
	      };
	    	  // Timer will schedule with task and periodical time interval.
	    	  timer.schedule(timerTask, 3000, 1000);
	      
	      seekBar = (SeekBar) findViewById(R.id.player_volume);
	      
	      int maxVolume = 10; 
	      Integer currentVolume = app.getVolume();
	      
	      //button functions	      
	      play2 = (ImageButton) findViewById(R.id.play2);
	      play2.setOnTouchListener(playListener);
	      
	      next1 = (ImageButton) findViewById(R.id.next1);
	      next1.setOnTouchListener(nextListener);
	      
	      prev1 = (ImageButton) findViewById(R.id.prev1);
	      prev1.setOnTouchListener(prevListener);
	      
	      repeat = (ImageButton) findViewById(R.id.repeat);
	      repeat.setOnTouchListener(repeatListener);
	      
	      shuffle = (ImageButton) findViewById(R.id.shuffle);
	      shuffle.setOnTouchListener(shuffleListener);
	      
	      settings = (ImageButton) findViewById(R.id.settings);
	      settings.setOnTouchListener(settingsListener);	   
	      
	      connect = (ImageButton) findViewById(R.id.connect);
	      connect.setOnTouchListener(connectListener);	
	      
	      mute = (ImageButton) findViewById(R.id.mute);
	      mute.setOnTouchListener(muteListener);	
	      
	      drum = (ImageButton) findViewById(R.id.drum);
	      drum.setOnTouchListener(drumListener);	
	      
	      list = (ImageButton) findViewById(R.id.list);
	      list.setOnTouchListener(listListener);
	      
	      voice = (ImageButton) findViewById(R.id.voice);
	      voice.setOnTouchListener(voiceListener);
	      
	      twit = (ImageButton) findViewById(R.id.twit);
	      twit.setOnTouchListener(twitListener);
	      
	      // Drawing buttons according to state
	      if(app.getShuffle()) {
	    	  shuffle.setImageResource(R.drawable.shuffle_1);
	      } else if(!app.getShuffle()) {
	    	  shuffle.setImageResource(R.drawable.shuffle_0);
	      }

	      if(app.getPlay()) {
	    	  play2.setImageResource(R.drawable.pause_0);
	      } else if(!app.getPlay()) {
	    	  play2.setImageResource(R.drawable.play_0);
	      }
	  
	      if(app.getRepeat()) {
	    	  repeat.setImageResource(R.drawable.repeat_2);
	      } else if(!app.getRepeat()) {
	    	  repeat.setImageResource(R.drawable.repeat_0);
	      }
	      
	      //seek bar for volume control
	      seekBar.setMax(maxVolume);
	      seekBar.setProgress(currentVolume);
	      seekBar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
			
			public void onStopTrackingTouch(SeekBar seekBar) {
			
				MyApplication app = (MyApplication) getApplication();
				app.setCommand("5");
				sendMessage();
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				MyApplication app = (MyApplication) getApplication();
				app.setVolume(progress);
			}
		});
	      
	      
	    //Gesture implementation for swiping , uncomment for swipes
	     /*
		Gesture activitySwipeDetector = new Gesture(this);
		LinearLayout lowestLayout = (LinearLayout)this.findViewById(R.id.playerView);
		lowestLayout.setOnTouchListener(activitySwipeDetector);
		*/
	}

		// custom shuffle button implementation
	   View.OnTouchListener shuffleListener = new View.OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				MyApplication app = (MyApplication) getApplication();
				// TODO Auto-generated method stub
		        if (event.getAction() == MotionEvent.ACTION_DOWN && !app.getShuffle()) {
		        	shuffle.setImageResource(R.drawable.shuffle_1);
		        	app.setShuffle(true);
		        	app.setCommand("7");
		        	sendMessage();
		        }
		        else if (event.getAction() == MotionEvent.ACTION_DOWN && app.getShuffle()) {
		        	shuffle.setImageResource(R.drawable.shuffle_0);
		        	app.setShuffle(false);
		        	app.setCommand("7");
		        	sendMessage();
		        }
				return false;
			}
	    };
	    
	 // custom play button implementation
	    View.OnTouchListener playListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
	        	MyApplication app = (MyApplication) getApplication();
		        if (event.getAction() == MotionEvent.ACTION_DOWN && !app.getPlay()) {
		        	play2.setImageResource(R.drawable.play_1);

		        }
		        else if (event.getAction()==MotionEvent.ACTION_DOWN && app.getPlay()){
		        	play2.setImageResource(R.drawable.pause_1);
		        }
		        
		        else if (event.getAction()==MotionEvent.ACTION_UP && app.getPlay()){
		        	play2.setImageResource(R.drawable.play_0);
		        	app.setPlay(false);
		        	app.setCommand("2");
		        	sendMessage();
		        }
		        
		        else if (event.getAction()==MotionEvent.ACTION_UP && !app.getPlay()){
		        	play2.setImageResource(R.drawable.pause_0);
		        	app.setCommand("1");
		        	app.setPlay(true);
		        	sendMessage();
		        }
				return false;
			}
		};
		
		// custom next button implementation
	    View.OnTouchListener nextListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				MyApplication app = (MyApplication) getApplication();
				// TODO Auto-generated method stub
		        if (event.getAction() == MotionEvent.ACTION_DOWN) {
		        	next1.setImageResource(R.drawable.next_1);
		        }
		        else	{
		        	next1.setImageResource(R.drawable.next_0);
		        	app.setCommand("3");
		        	sendMessage();
		        }
				return false;
			}
		};
		
		// custom previous button implementation
	    View.OnTouchListener prevListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				MyApplication app = (MyApplication) getApplication();
				
		        if (event.getAction() == MotionEvent.ACTION_DOWN) {
		        	prev1.setImageResource(R.drawable.prev_1);
		        }
		        else	{
		        	prev1.setImageResource(R.drawable.prev_0);
		        	app.setCommand("4");
		        	sendMessage();
		        }
				return false;
			}
		};
		
		// custom repeat button implementation
	    View.OnTouchListener repeatListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				MyApplication app = (MyApplication) getApplication();
		        if (event.getAction() == MotionEvent.ACTION_DOWN && !app.getRepeat()) {
		        	repeat.setImageResource(R.drawable.repeat_2);
		        	app.setRepeat(true);
		        	app.setCommand("A1");
		        }
		        else if (event.getAction()==MotionEvent.ACTION_DOWN && app.getRepeat()){
		        	repeat.setImageResource(R.drawable.repeat_0);
		        	app.setRepeat(false);
		        	app.setCommand("A0");
		        }
		        
		        sendMessage();
				return false;
			}
		};
		
		// custom settings button implementation to go to settings page
	    View.OnTouchListener settingsListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				MyApplication app = (MyApplication) getApplication();
		        if (event.getAction() == MotionEvent.ACTION_DOWN) {
		        	settings.setImageResource(R.drawable.eq_1);
		        }
		        else	{
		        	settings.setImageResource(R.drawable.eq_0);	    		
		        	Intent myIntent0 = new Intent(getBaseContext(), SettingsActivity.class);  
		    		startActivity(myIntent0);		        
		        }
				return false;
			}
		};
		
		// custom mute button implementation
	    View.OnTouchListener muteListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//method stub MUTE COMMAND
	        	MyApplication app = (MyApplication) getApplication();
		        if (event.getAction() == MotionEvent.ACTION_DOWN && !app.getMute()) {
		        	mute.setImageResource(R.drawable.mute_1);

		        }
		        else if (event.getAction()==MotionEvent.ACTION_DOWN && app.getMute()){
		        	mute.setImageResource(R.drawable.mute_3);
		        }
		        
		        else if (event.getAction()==MotionEvent.ACTION_UP && app.getMute()){
		        	mute.setImageResource(R.drawable.mute_0);
		        	app.setMute(false);
		        	app.setCommand("5");
		        	sendMessage();
		        }
		        
		        else if (event.getAction()==MotionEvent.ACTION_UP && !app.getMute()){
		        	mute.setImageResource(R.drawable.mute_2);
		        	app.setMute(true);
		        	app.setCommand("5");
		        	sendMessage();
		        }
				return false;
			}
		};
		
		// custom drums button implementation to go to drums page
	    View.OnTouchListener drumListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				MyApplication app = (MyApplication) getApplication();
		        if (event.getAction() == MotionEvent.ACTION_DOWN) {
		        	drum.setImageResource(R.drawable.drum_1);
		        }
		        else	{
		        	drum.setImageResource(R.drawable.drum_0);
		        	//change to SettingsActivity	    		
		        	Intent myIntent0 = new Intent(getBaseContext(), DrumActivity.class);  
		    		startActivity(myIntent0);		        
		        }
				return false;
			}
		};
		
		// custom connect button implementation to go to connect page
	    View.OnTouchListener connectListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				MyApplication app = (MyApplication) getApplication();
		        if (event.getAction() == MotionEvent.ACTION_DOWN) {
		        	connect.setImageResource(R.drawable.connect_1);
		        }
		        else {
		        	connect.setImageResource(R.drawable.connect_0);
		    		Intent myIntent0 = new Intent(getBaseContext(), MainActivity.class);  
		    		startActivity(myIntent0);
		        }
				return false;
			}
		};
		
		// custom list button implementation to go to Playlists page
	    View.OnTouchListener listListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				MyApplication app = (MyApplication) getApplication();
		        if (event.getAction() == MotionEvent.ACTION_DOWN) {
		        	list.setImageResource(R.drawable.list_1);
		        }
		        else {
		        	list.setImageResource(R.drawable.list_0);
		    		Intent myIntent0 = new Intent(getBaseContext(), PlaylistActivity.class);  
		    		startActivity(myIntent0);
		        }
				return false;
			}
		};
		
		// custom voice button implementation to go to Voice recognition page
	    View.OnTouchListener voiceListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				MyApplication app = (MyApplication) getApplication();
		        if (event.getAction() == MotionEvent.ACTION_DOWN) {
		        	voice.setImageResource(R.drawable.voice_1);
		        }
		        else {
		        	voice.setImageResource(R.drawable.voice_0);
		    		Intent myIntent0 = new Intent(getBaseContext(), VoiceRecognitionActivity.class);  
		    		startActivity(myIntent0);
		        }
				return false;
			}
		};
		
		// custom twitter button implementation to go to twitter page
	    View.OnTouchListener twitListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				MyApplication app = (MyApplication) getApplication();
		        if (event.getAction() == MotionEvent.ACTION_DOWN) {
		        	twit.setImageResource(R.drawable.twit_1);
		        }
		        else {
		        	// twitter page implement
		        	twit.setImageResource(R.drawable.twit_0);
		        	Intent myIntent0 = new Intent(getBaseContext(), TwitterActivity.class);  
		    		startActivity(myIntent0);
		        }
				return false;
			}
		};
	
	/// Implemented methods for SeekBar bar 
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
		Intent myIntent = new Intent(view.getContext(), MainActivity.class);  
		startActivity(myIntent);
	}
	
	public void nextPage(View view) {	
		Intent myIntent = new Intent(view.getContext(), PlaylistActivity.class);  
		startActivity(myIntent);
	}
	
	@Override
	public void onBackPressed() {
		//Do nothing
	}
	
	
	
	// For gestures//
	public void LeftToRight() {
		Intent myIntent0 = new Intent(getBaseContext(), MainActivity.class);  
		startActivity(myIntent0);
	}

	// For gestures//
	public void RightToLeft() {
		Intent myIntent0 = new Intent(getBaseContext(), PlaylistActivity.class);  
		startActivity(myIntent0);
	}
} 