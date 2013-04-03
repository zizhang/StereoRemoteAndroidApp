package com.example.ece381;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListView;

public class SongList extends MainActivity { 
	List<Song> songs;
	MyApplication app;
	EditText inputSearch;
	
	/** Called when the activity is first created. */  
	@Override  
	public void onCreate(Bundle savedInstanceState) {  
	      super.onCreate(savedInstanceState);  
	      setContentView(R.layout.song_list);  
	      
	      //sendMessage();
	      
	      ListView listView = (ListView) findViewById(R.id.songList);
	      inputSearch = (EditText) findViewById(R.id.inputSearch);
	      
	      app = (MyApplication) getApplication();
	      
	      songs = (app.getPlayList()).get(app.getPlayListPos()).getPlaylist();
	        
	      ArrayAdapter<Song> adapter = new ArrayAdapter<Song>(this,R.layout.songitems, songs);
	      listView.setAdapter(adapter);
	      
	      //// code to make it clickable
	      listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				//ListIterator<Song> x = songs.listIterator();
				
				app.setPos(pos);
				app.setSongSelectedFlag(true);
				Intent myIntent = new Intent(view.getContext(), Player.class);  
				startActivityForResult(myIntent, 0);
			}});
	      
	    //Gesture implementation for swiping 
			Gesture activitySwipeDetector = new Gesture(this);
			LinearLayout lowestLayout = (LinearLayout)this.findViewById(R.id.songListView);
			lowestLayout.setOnTouchListener(activitySwipeDetector);
			
			
			///////****************
			///////TO-DO FIX!!!!!!
			inputSearch.addTextChangedListener(new TextWatcher() {
				
				
			    @Override
			    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
			        // When user changed the Text
			         // SongList.this.songs.getFilter().filter(cs);
			    	
			    }
			    
			 
			    @Override
			    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			            int arg3) {
			        // TODO Auto-generated method stub
			 
			    }
			 
			    @Override
			    public void afterTextChanged(Editable arg0) {
			        // TODO Auto-generated method stub
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
	
	
	public void prevPage(View view) {	
		Intent myIntent = new Intent(view.getContext(), PlaylistActivity.class);  
		startActivityForResult(myIntent, 0);
	}
} 