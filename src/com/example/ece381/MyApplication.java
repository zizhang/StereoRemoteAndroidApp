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
	boolean newSongSelected;
	boolean swipeFlag;
	Integer currentVolume;
	private String readBuffer;
	
	/**
	 * String for voice commands
	 */
	private String voiceCmd;
	
	public MyApplication() {
		songList = new ArrayList<Song>();
		playlists = new ArrayList<Playlist>(); 
		
		songList.add(new Song("SONG4.WAV", "Call Me Maybe", "Carly Rae Jepsen"));
		songList.add(new Song("SONG5.WAV", "Unknown Title", "Unknown Artist"));
		songList.add(new Song("SONG1.WAV", "Harlem Shake", "Miami Heat"));
		songList.add(new Song("SONG3.WAV", "NHL Theme", "TSN"));
		songList.add(new Song("SONG2.WAV", "PoH", "Kid Cudi"));
		songList.add(new Song("SONG6.WAV", "Nyan Cat", "Unknown Artist"));
  
		listPosition = 0;
		playListPosition = 0;
		fileName = songList.get(listPosition).getSongName();
		
		command = "1";
		newSongSelected = false;
		
		///
		swipeFlag = true;
		///
		
		currentVolume = 5;
		
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
		playlists.add(playlistB);
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
	

	public void setSwipeFlag(boolean flag) {
		swipeFlag = flag;
	}
	
	public boolean getSwipeFlag() {
		return swipeFlag;
	}
	
	
	
	/**
	 * methods for voiceCmd global class variable
	 **/
	public String getVoiceCmd() {
		return voiceCmd;
	}
	
	public void setVoiceCmd(String cmd) {
		voiceCmd = cmd;
	}
	 
}
