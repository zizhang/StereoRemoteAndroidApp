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
	      //txt1 = (TextView) findViewById(R.id.volume_level);
	      int maxVolume = 10; //10
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
	      
	      seekBar.setMax(maxVolume);
	      seekBar.setProgress(currentVolume);
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
			}
		});
	      
	      
	    //Gesture implementation for swiping 
	     /*
		Gesture activitySwipeDetector = new Gesture(this);
		LinearLayout lowestLayout = (LinearLayout)this.findViewById(R.id.playerView);
		lowestLayout.setOnTouchListener(activitySwipeDetector);
		*/
	}

		
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

	    View.OnTouchListener nextListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				MyApplication app = (MyApplication) getApplication();
				// TODO Auto-generated method stub
		        if (event.getAction() == MotionEvent.ACTION_DOWN) {
		        	next1.setImageResource(R.drawable.next_1);
//		        	try {
//		        		getipAddress();
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
		        }
		        else	{
		        	next1.setImageResource(R.drawable.next_0);
		        	app.setCommand("3");
		        	sendMessage();
		        }
				return false;
			}
		};
		
	    View.OnTouchListener prevListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				MyApplication app = (MyApplication) getApplication();
				// TODO Auto-generated method stub
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
		
	    View.OnTouchListener repeatListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				MyApplication app = (MyApplication) getApplication();
				// TODO Auto-generated method stub
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
		
	    View.OnTouchListener settingsListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				MyApplication app = (MyApplication) getApplication();
				// TODO Auto-generated method stub
		        if (event.getAction() == MotionEvent.ACTION_DOWN) {
		        	settings.setImageResource(R.drawable.eq_1);
		        }
		        else	{
		        	settings.setImageResource(R.drawable.eq_0);
		        	//TODO change to playlist page		    		
		        	Intent myIntent0 = new Intent(getBaseContext(), SettingsActivity.class);  
		    		startActivity(myIntent0);		        
		        }
				return false;
			}
		};
		
	    View.OnTouchListener muteListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub MUTE COMMAND
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
		
	    View.OnTouchListener drumListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				MyApplication app = (MyApplication) getApplication();
				// TODO Auto-generated method stub
		        if (event.getAction() == MotionEvent.ACTION_DOWN) {
		        	drum.setImageResource(R.drawable.drum_1);
		        }
		        else	{
		        	drum.setImageResource(R.drawable.drum_0);
		        	//TODO change to SettingsActivity	    		
		        	Intent myIntent0 = new Intent(getBaseContext(), DrumActivity.class);  
		    		startActivity(myIntent0);		        
		        }
				return false;
			}
		};
		
	    View.OnTouchListener connectListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				MyApplication app = (MyApplication) getApplication();
				// TODO Auto-generated method stub
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
		
	    View.OnTouchListener listListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				MyApplication app = (MyApplication) getApplication();
				// TODO Auto-generated method stub
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
		
	    View.OnTouchListener voiceListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				MyApplication app = (MyApplication) getApplication();
				// TODO Auto-generated method stub
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
		
	    View.OnTouchListener twitListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				MyApplication app = (MyApplication) getApplication();
				// TODO Auto-generated method stub
		        if (event.getAction() == MotionEvent.ACTION_DOWN) {
		        	twit.setImageResource(R.drawable.twit_1);
		        }
		        else {
		        	//TODO twitter page implement
		        	twit.setImageResource(R.drawable.twit_0);
		        	Intent myIntent0 = new Intent(getBaseContext(), TwitterActivity.class);  
		    		startActivity(myIntent0);
		        }
				return false;
			}
		};
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
		Intent myIntent = new Intent(view.getContext(), MainActivity.class);  
		startActivity(myIntent);
	}
	
	public void nextPage(View view) {	
		Intent myIntent = new Intent(view.getContext(), PlaylistActivity.class);  
		startActivity(myIntent);
	}
	
	/*
	// Method for when any of the buttons are clicked
	public void onClick(View view){
		MyApplication app = (MyApplication) getApplication();
		switch(view.getId()){
		case R.id.play2:
			break;
		case R.id.next1:
			break;
		case R.id.prev1:
			break;
		case R.id.shuffle:
			break;
		case R.id.repeat:
			break;
		case R.id.mute:
			break;
		case R.id.sync_button:
			app.setCommand("sync");
			sendMessage();
			setContentView(R.layout.loadingscreen);
            new LoadViewTask().execute();
			return;
		}
		
//		sendMessage();
	}
	*/
	
	@Override
	public void onBackPressed() {
		//Do nothing
	}
	
	
	
	///**********
	public void LeftToRight() {
		Intent myIntent0 = new Intent(getBaseContext(), MainActivity.class);  
		startActivity(myIntent0);
	}


	public void RightToLeft() {
		Intent myIntent0 = new Intent(getBaseContext(), PlaylistActivity.class);  
		startActivity(myIntent0);
	}
} 