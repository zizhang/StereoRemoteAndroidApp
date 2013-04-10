package com.example.ece381;

import java.util.ArrayList;
import java.util.List;


/**
 *  Playlist class for the Playlist object
 **/
public class Playlist {
	private String name;
	private List<Song> songList;
	
	// constructor
	public Playlist() {
		name = "";
		songList = new ArrayList<Song>();
	}
	
	//constructor
	public Playlist(String tName) {
		name = tName;
		songList = new ArrayList<Song>();
	}
	
	// getting name
	public String getName() {
		return name;
	}
	
	// setting name
	public void setName(String newName) {
		name = newName;
	}
	
	// getting playlist
	public List<Song> getPlaylist() {
		return songList;
	}
	
	// adding a song
	public void addSong(Song newSong) {
		songList.add(newSong);
	}
	
	@Override
	public String toString() {
		return name;
	}
}
