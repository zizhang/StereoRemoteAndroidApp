package com.example.ece381;

import java.util.ArrayList;
import java.util.List;

public class Playlist {
	private String name;
	private List<Song> songList;
	
	public Playlist(String tName) {
		name = tName;
		songList = new ArrayList<Song>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String newName) {
		name = newName;
	}
	
	public List<Song> getPlaylist() {
		return songList;
	}
	
	public void addSong(Song newSong) {
		songList.add(newSong);
	}
	
	@Override
	public String toString() {
		return name;
	}
}
