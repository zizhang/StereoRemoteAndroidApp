package com.example.ece381;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.ViewSwitcher;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class Player extends MainActivity implements OnSeekBarChangeListener {  
	SeekBar seekBar; // Volume Bar
	TextView txt1; //txt2
	private ViewSwitcher viewSwitcher;
	
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
	      
	      seekBar = (SeekBar) findViewById(R.id.balancer);
	      seekBar.setMax(10);
	      seekBar.setProgress(5);
	      seekBar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
			
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				//txt1.setText("Stopped");
				MyApplication app = (MyApplication) getApplication();
				app.setCommand("6");
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
		Intent myIntent = new Intent(view.getContext(), MainActivity.class);  
		startActivity(myIntent);
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
		case R.id.sync_button:
			app.setCommand("sync");
			sendMessage();
			setContentView(R.layout.loadingscreen);
            new LoadViewTask().execute();
			return;
		}
		
		sendMessage();
	}
	
	//To use the AsyncTask, it must be subclassed
    private class LoadViewTask extends AsyncTask<Void, Integer, Void>
    {
    	//A TextView object and a ProgressBar object
    	private TextView tv_progress;
    	private ProgressBar pb_progressBar;

    	//Before running code in the separate thread
		@Override
		protected void onPreExecute()
		{
			//Initialize the ViewSwitcher object
	        viewSwitcher = new ViewSwitcher(Player.this);
	        /* Initialize the loading screen with data from the 'loadingscreen.xml' layout xml file.
	         * Add the initialized View to the viewSwitcher.*/
			viewSwitcher.addView(ViewSwitcher.inflate(Player.this, R.layout.loadingscreen, null));

			//Initialize the TextView and ProgressBar instances - IMPORTANT: call findViewById() from viewSwitcher.
			pb_progressBar = (ProgressBar) viewSwitcher.findViewById(R.id.progressBar1);
			//Sets the maximum value of the progress bar to 100
			pb_progressBar.setMax(100);

			//Set ViewSwitcher instance as the current View.
			setContentView(viewSwitcher);
		}

		//The code to be executed in a background thread.
		@Override
		protected Void doInBackground(Void... params)
		{
			/* This is just a code that delays the thread execution 4 times,
			 * during 850 milliseconds and updates the current progress. This
			 * is where the code that is going to be executed on a background
			 * thread must be placed.
			 */
			
			//Get the current thread's token
			synchronized (this)
			{
				MyApplication app = (MyApplication) getApplication();
				while(app.getSyncStatus());
				/*
				//Initialize an integer (that will act as a counter) to zero
				int counter = 0;
				//While the counter is smaller than four
				while(counter <= 4)
				{
					//Wait 850 milliseconds
					this.wait(850);
					//Increment the counter
					counter++;
					//Set the current progress.
					//This value is going to be passed to the onProgressUpdate() method.
					publishProgress(counter*25);
				}
				*/
			}
			return null;
		}

		/*
		//Update the TextView and the progress at progress bar
		@Override
		protected void onProgressUpdate(Integer... values)
		{
			
		}
		*/

		//After executing the code in the thread
		@Override
		protected void onPostExecute(Void result)
		{
			/* Initialize the application's main interface from the 'main.xml' layout xml file.
	         * Add the initialized View to the viewSwitcher.*/
			viewSwitcher.addView(ViewSwitcher.inflate(Player.this, R.layout.player, null));
			//Switch the Views
			viewSwitcher.showNext();
		}
    }

    //Override the default back key behavior
    @Override
    public void onBackPressed()
    {
    	//Emulate the progressDialog.setCancelable(false) behavior
    	//If the first view is being shown
    	if(viewSwitcher.getDisplayedChild() == 0)
    	{
    		super.onBackPressed();
    		return;
    	}
    	else
    	{
    		//Finishes the current Activity
    		super.onBackPressed();
    	}
    }
} 