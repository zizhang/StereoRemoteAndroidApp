package com.example.ece381;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SongList extends MainActivity { 
	List<Song> songs;
	MyApplication app;
	
	/** Called when the activity is first created. */  
	@Override  
	public void onCreate(Bundle savedInstanceState) {  
	      super.onCreate(savedInstanceState);  
	      setContentView(R.layout.song_list);  
	      
	      
	      ListView listView = (ListView) findViewById(R.id.songList);
	      
	      app = (MyApplication) getApplication();
	      
	      songs = (app.getPlayList()).get(app.getPlayListPos()).getPlaylist();
	      
	      
	      ArrayAdapter<Song> adapter = new ArrayAdapter<Song>(this,R.layout.songitems, songs);
	      listView.setAdapter(adapter);
	      
	      //// code to make it clickable
	      listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int pos,
					long arg3) {
				
				
				app.setPos(pos);
				app.setSongSelectedFlag(true);
				Intent myIntent = new Intent(view.getContext(), Player.class);  
				startActivityForResult(myIntent, 0);
			}});
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
	
	@Override
	public void onBackPressed() {
		//Do nothing
	}
	
	public void prevPage(View view) {	
		Intent myIntent = new Intent(view.getContext(), PlaylistActivity.class);  
		startActivityForResult(myIntent, 0);
	}
} 