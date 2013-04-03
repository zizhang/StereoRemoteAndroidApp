package com.example.ece381;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import android.app.Application;

public class MyApplication extends Application {
	Socket sock = null;
	private int listPosition;
	private String fileName;
	private List<Song> songList;
	private String command;
	private Playlist playlistA;
	private Playlist playlistB;
	private Playlist allSongs;
	private List<Playlist> playlists;
	private int playListPosition;
	private List<Song> masterSongList;
	boolean newSongSelected;
	Integer currentVolume;
	private String readBuffer;
	private Playlist tempPlaylist;
	private boolean syncing;
	private Integer balance;
	private boolean syncNewPlaylist;
	
	public MyApplication() {
		songList = new ArrayList<Song>();
		playlists = new ArrayList<Playlist>(); 
		masterSongList = new ArrayList<Song>();
		
		listPosition = 0;
		playListPosition = 0;
		fileName = "";
		//fileName = songList.get(listPosition).getSongName();
		
		command = "1";
		newSongSelected = false;
		syncing = false;
		syncNewPlaylist = false;
		currentVolume = 3;
		balance = 5;
		
		/*
		songList.add(new Song("SONG4.WAV", "Call Me Maybe", "Carly Rae Jepsen"));
		songList.add(new Song("SONG5.WAV", "Unknown Title", "Unknown Artist"));
		songList.add(new Song("SONG1.WAV", "Harlem Shake", "Miami Heat"));
		songList.add(new Song("SONG3.WAV", "NHL Theme", "TSN"));
		songList.add(new Song("SONG2.WAV", "PoH", "Kid Cudi"));
		songList.add(new Song("SONG6.WAV", "Nyan Cat", "Unknown Artist"));
		
		playlistA = new Playlist("DEMO1.LST");
		playlistB = new Playlist("DEMO2.LST");
		allSongs = new Playlist("LIST.LST");
		
		allSongs.addSong(songList.get(0));
		allSongs.addSong(songList.get(1));
		allSongs.addSong(songList.get(2));
		allSongs.addSong(songList.get(3));
		allSongs.addSong(songList.get(4));
		allSongs.addSong(songList.get(5));
		
		playlistA.addSong(songList.get(5));
		playlistA.addSong(songList.get(0));
		playlistA.addSong(songList.get(1));
		
		playlistB.addSong(songList.get(3));
		playlistB.addSong(songList.get(2));
		playlistB.addSong(songList.get(4));
		
		playlists.add(allSongs);
		playlists.add(playlistA);
		playlists.add(playlistB); */
	}
	
	public Integer getVolume() {
		return currentVolume;
	}
	
	public void setVolume(Integer vol) {
		currentVolume = vol;
	}
	
	public int getPlayListPos() {
		return playListPosition;
	}
	
	public void setPlayListPos(int pos) {
		playListPosition = pos;
	}
	
	public int getPos() {
		return listPosition;
	}
	
	public void setPos(int pos) {
		listPosition = pos;
		fileName = (playlists.get(playListPosition)).getPlaylist().get(listPosition).getSongName();
	}
	
	public int findSong(String songFileName) {
		int pos;
		List<Song> songs = (playlists.get(playListPosition)).getPlaylist();
		for(pos = 0; pos < songs.size(); pos++) {
			if(songs.get(pos).getSongName().equals(songFileName)) {
				return pos;
			}
		}
		
		return -1;
	}
	
	public Song getCurrentSong() {
		return songList.get(listPosition);
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String filename) {
		fileName = filename;
	}
	
	public List<Song> getSongList() {
		return songList;
	}
	
	public List<Song> getMasterSongList() {
		masterSongList = playlists.get(0).getPlaylist();
		return masterSongList;
	}
	
	public List<Playlist> getPlayList() {
		return playlists;
	}
	
	public String getCommand() {
		return command;
	}
	
	public void setCommand(String cmd) {
		command = cmd;
	}
	
	public void setSongSelectedFlag(boolean flag) {
		newSongSelected = flag;
	}
	
	public boolean getSongSelectedFlag() {
		return newSongSelected;
	}
	
	public void addPlaylist(Playlist newPlaylist) {
		playlists.add(newPlaylist);
	}
	
	public void setTempPlaylist(String playlistName) {
		if(playlistName == null) {
			tempPlaylist = null;
		} else {
			tempPlaylist = new Playlist(playlistName);
		}
	}
	
	public void addNewPlaylist(Playlist newPlaylist) {
		playlists.add(newPlaylist);
	}
	
	public void addTempPlaylist() {
		playlists.add(tempPlaylist);
	}
	
	public void addTempSong(Song newSong) {
		tempPlaylist.addSong(newSong);
	}
	
	public boolean getSyncStatus() {
		return syncing;
	}
	
	public void setSyncStatus(boolean status) {
		syncing = status;
	}
	
	public void clearPlaylists() {
		playlists = new ArrayList<Playlist>(); 
	}
	
	public Integer getBalance() {
		return balance;
	}
	
	public void setBalance(Integer newBalance) {
		balance = newBalance;
	}
	
	public boolean getSyncNewPlaylistStatus() {
		return syncNewPlaylist;
	}
	
	public void setSyncNewPlaylistStatus(boolean status) {
		syncNewPlaylist = status;
	}
	
	public boolean findPlaylist(String name) {
		for(int i = 0; i < playlists.size(); i++) {
			if(playlists.get(i).getName().equals(name)) {
				return false;
			}
		}
		
		return true;
	}
}
