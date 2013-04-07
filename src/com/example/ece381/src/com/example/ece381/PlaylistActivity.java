package com.example.ece381;

import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ViewSwitcher;

public class PlaylistActivity extends MainActivity {
	List<Playlist> playlists;
  	ImageButton add;
	ImageButton sync;	
	ImageButton homeFplaylist;
	private ViewSwitcher viewSwitcher;
	
	/** Called when the activity is first created. */  
	@Override  
	public void onCreate(Bundle savedInstanceState) {  
		  super.onCreate(savedInstanceState);  
		  setContentView(R.layout.play_list);  
		  
		  ListView listView = (ListView) findViewById(R.id.playList);
		  
		  MyApplication app = (MyApplication) getApplication();
		add = (ImageButton) findViewById(R.id.add);
		add.setOnTouchListener(addListener);
			
		sync = (ImageButton) findViewById(R.id.sync);
		sync.setOnTouchListener(syncListener);	
		
		homeFplaylist = (ImageButton) findViewById(R.id.homeFplaylist);
		homeFplaylist.setOnTouchListener(homeListener);	
		
		  playlists = app.getPlayList();
	      
	      
	      ArrayAdapter<Playlist> adapter = new ArrayAdapter<Playlist>(this,R.layout.playlistitems, playlists);
	      listView.setAdapter(adapter);
	      
	      //// code to make it clickable
	      listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				//ListIterator<Song> x = songs.listIterator();
				MyApplication app = (MyApplication) getApplication();
				app.setPlayListPos(pos);
				app.setCommand("6");
				Intent myIntent = new Intent(view.getContext(), SongList.class);  
				startActivityForResult(myIntent, 0);
			}});
	      
	    //Gesture implementation for swiping 
	    /*
		Gesture activitySwipeDetector = new Gesture(this);
		LinearLayout lowestLayout = (LinearLayout)this.findViewById(R.id.playListView);
		lowestLayout.setOnTouchListener(activitySwipeDetector);
		*/
		
		//setContentView(R.layout.loadingscreen);
        //new LoadViewTask().execute();
	}  
	
    View.OnTouchListener addListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
	        if (event.getAction() == MotionEvent.ACTION_DOWN) {
	        	add.setImageResource(R.drawable.add_1);
	        }
	        else {
	        	add.setImageResource(R.drawable.add_0);
	        	Intent myIntent0 = new Intent(getBaseContext(), CreatePlaylistActivity.class);  
	    		startActivity(myIntent0);
	        }
			return false;
		}
	};
	
    View.OnTouchListener homeListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
	        if (event.getAction() == MotionEvent.ACTION_DOWN) {
	        	homeFplaylist.setImageResource(R.drawable.home_1);
	        }
	        else if (event.getAction()==MotionEvent.ACTION_UP){
	        	homeFplaylist.setImageResource(R.drawable.home_0);
	        	Intent myIntent0 = new Intent(getBaseContext(),Player.class);
	        	startActivity(myIntent0);
	        }
			return false;
		}
	};
	
    View.OnTouchListener syncListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			MyApplication app = (MyApplication) getApplication();
	        if (event.getAction() == MotionEvent.ACTION_DOWN) {
	        	sync.setImageResource(R.drawable.sync_1);
	        }
	        else if (event.getAction() == MotionEvent.ACTION_UP){
	        	sync.setImageResource(R.drawable.sync_0);
	        	app.setCommand("sync");
				sendMessage();
				setContentView(R.layout.loadingscreen);
	            new LoadViewTask().execute();
	        }
            
			return false;
		}
	};	
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
	
	
	public void prevPage(View view) {	
		Intent myIntent = new Intent(view.getContext(), Player.class);  
		startActivityForResult(myIntent, 0);
	}
	
	public void nextPage(View view) {	
		Intent myIntent = new Intent(view.getContext(), CreatePlaylistActivity.class);  
		startActivityForResult(myIntent, 0);
	}
	
	//To use the AsyncTask, it must be subclassed
    private class LoadViewTask extends AsyncTask<Void, Integer, Void>
    {
    	//A TextView object and a ProgressBar object
    	private ProgressBar pb_progressBar;

    	//Before running code in the separate thread
		@Override
		protected void onPreExecute()
		{
			//Initialize the ViewSwitcher object
	        viewSwitcher = new ViewSwitcher(PlaylistActivity.this);
	        /* Initialize the loading screen with data from the 'loadingscreen.xml' layout xml file.
	         * Add the initialized View to the viewSwitcher.*/
			viewSwitcher.addView(ViewSwitcher.inflate(PlaylistActivity.this, R.layout.loadingscreen, null));

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
				
				//Initialize an integer (that will act as a counter) to zero
				//int counter = 0;
				//While the counter is smaller than four
				/*while(counter <= 4)
				{
					//Wait 850 milliseconds
					try {
						this.wait(850);
					} catch(Exception ex) {
						return null;
					}
					
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
			//viewSwitcher.addView(ViewSwitcher.inflate(PlaylistActivity.this, R.layout.play_list, null));
			//Switch the Views
			//viewSwitcher.showNext();
			
			Intent myIntent = new Intent(getBaseContext(), PlaylistActivity.class);  
			startActivity(myIntent);
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
