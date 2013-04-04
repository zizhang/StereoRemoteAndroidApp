package com.example.ece381;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class PlaylistActivity extends MainActivity {
	List<Playlist> playlists;
	
	
	/** Called when the activity is first created. */  
	@Override  
	public void onCreate(Bundle savedInstanceState) {  
	      super.onCreate(savedInstanceState);  
	      setContentView(R.layout.play_list);  
	      
	      ListView listView = (ListView) findViewById(R.id.playList);
	      
	      MyApplication app = (MyApplication) getApplication();
	      
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
<<<<<<< HEAD
			Gesture activitySwipeDetector = new Gesture(this);
			LinearLayout lowestLayout = (LinearLayout)this.findViewById(R.id.playListView);
			lowestLayout.setOnTouchListener(activitySwipeDetector);
			
=======
		Gesture activitySwipeDetector = new Gesture(this);
		LinearLayout lowestLayout = (LinearLayout)this.findViewById(R.id.playListView);
		lowestLayout.setOnTouchListener(activitySwipeDetector);
>>>>>>> 0e6d17f75b994796b147814dcea5cad96416389f
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
	
	
	public void prevPage(View view) {	
		Intent myIntent = new Intent(view.getContext(), Player.class);  
		startActivityForResult(myIntent, 0);
	}
	
	public void nextPage(View view) {	
		Intent myIntent = new Intent(view.getContext(), CreatePlaylistActivity.class);  
		startActivityForResult(myIntent, 0);
	}
}
