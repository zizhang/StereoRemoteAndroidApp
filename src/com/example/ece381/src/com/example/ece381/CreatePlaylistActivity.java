package com.example.ece381;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ViewSwitcher;

public class CreatePlaylistActivity extends MainActivity { 
	
	List<Song> songs;
	List<Song> master;
	Playlist newPlaylist;
	MyApplication app;  // Global variable class object
	ArrayAdapter<Song> adapter;
	private ViewSwitcher viewSwitcher;
	
	/** Called when the activity is first created. */  
	@Override  
	public void onCreate(Bundle savedInstanceState) {  
	      super.onCreate(savedInstanceState);  
	      setContentView(R.layout.create_playlist);  
	      newPlaylist = new Playlist();
	      
	      // hide keyboard
	      
	      ListView listView = (ListView) findViewById(R.id.masterList);
	      
	      app = (MyApplication) getApplication();
	      
	      master = app.getMasterSongList();
	      songs = new ArrayList<Song>();
	      
	      for(int i = 0; i < master.size(); i++) {
	    	  songs.add(master.get(i));
	    	  Log.d("copy", ""+i);
	      }
	      
	      adapter = new ArrayAdapter<Song>(this,R.layout.mastersongitems, songs);
	      listView.setAdapter(adapter);
	      
	      //// code for makeing it clickable
	      listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int pos,
					long arg3) {
				try {
					app.setPos(pos);
					
					// Add song to new playlist and remove song from listView
					newPlaylist.addSong(songs.get(pos));
					songs.remove(pos);
	
					adapter.notifyDataSetChanged();
				} catch(Exception e) {
					Log.d("Exception Caught", e.toString());
				}
			}});
	}  
	
	/**
     * For creating a new playlist
     * **/
	public void createPlaylist(View view) {
		
		EditText et = (EditText) findViewById(R.id.newPlaylistName);
		String newPlaylistName = et.getText().toString();
		
		if(newPlaylistName.equals("") || newPlaylist == null || newPlaylist.getPlaylist().size() == 0) {
			Intent myIntent = new Intent(view.getContext(), Playlist.class);  
			startActivityForResult(myIntent, 0);
			return;
		} else {
			//Add new playlist
			newPlaylist.setName(newPlaylistName.toUpperCase() + ".LST");
			app.addNewPlaylist(newPlaylist);
			
			app.setCommand("syncnewp");
			syncNewPlaylist(newPlaylist);
			
			newPlaylist = null;
			
			setContentView(R.layout.loadingscreen);
	        new LoadViewTask().execute();
		}
	}
	
	/**
     * For going back to the previous page
     * **/
	public void prevPage(View view) {	
		Intent myIntent = new Intent(view.getContext(), Player.class);  
		startActivityForResult(myIntent, 0);
	}
	
	/**
     * Private class for loading task asynchonously
     * To use the AsyncTask, it must be subclassed
     **/
    private class LoadViewTask extends AsyncTask<Void, Integer, Void>
    {
    	//A TextView object and a ProgressBar object
    	private ProgressBar pb_progressBar;

    	//Before running code in the separate thread
		@Override
		protected void onPreExecute()
		{
			
	        viewSwitcher = new ViewSwitcher(CreatePlaylistActivity.this);
	        
			viewSwitcher.addView(ViewSwitcher.inflate(CreatePlaylistActivity.this, R.layout.loadingscreen, null));

			pb_progressBar = (ProgressBar) viewSwitcher.findViewById(R.id.progressBar1);
			
			pb_progressBar.setMax(100);

			//Set ViewSwitcher instance as the current View.
			setContentView(viewSwitcher);
		}

		//The code to be executed in a background thread.
		@Override
		protected Void doInBackground(Void... params)
		{
			/**
		   	 * This is just a code that delays the thread execution 4 times,
			 * during 850 milliseconds and updates the current progress. This
			 * is where the code that is going to be executed on a background
			 * thread must be placed.
			 */
			
			//Get the current thread's token
			synchronized (this)
			{
				MyApplication app = (MyApplication) getApplication();
				
			}
			return null;
		}


		//After executing the code in the thread
		@Override
		protected void onPostExecute(Void result)
		{
			/* Initialize the apps main interface then
	         * Add the initialized View to the viewSwitcher.*/
			
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