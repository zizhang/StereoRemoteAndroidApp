package com.example.ece381;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import android.app.Application;

public class MyApplication extends Application {
	Socket sock = null;
	private int listPosition;
	private int currentListPosition;
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
	Integer oldVolume;
	private String readBuffer;
	private Playlist tempPlaylist;
	private boolean syncing;
	private Integer balance;
	private boolean syncNewPlaylist;
	private boolean receivedError;
	private boolean syncNewPlaylistError;
	private int treble;
	private int mid;
	private int bass;
	private int fader;
	private boolean shuffle;
	private boolean repeat;
	private boolean mute;
	private boolean play;
	private Song currentSong;
	private boolean lock;
	private boolean confirmReceived;
	
	/**
	 * String for voice commands
	 */
	private String voiceCmd;
	
	public MyApplication() {
		songList = new ArrayList<Song>();
		playlists = new ArrayList<Playlist>(); 
		masterSongList = new ArrayList<Song>();
		currentSong = new Song();
		
		listPosition = 0;
		playListPosition = 0;
		fileName = "";
		currentListPosition = 0;
		
		command = "1";
		newSongSelected = false;
		syncing = false;
		syncNewPlaylist = false;
		currentVolume = 3;
		oldVolume = 0;
		balance = 5;
		receivedError = false;
		confirmReceived = false;
		syncNewPlaylistError = false;
		treble = 5;
		mid = 5;
		bass = 5;
		fader = 5;
		shuffle = false;
		repeat = false;
		mute = false;
		play = false;
		lock = false;
		
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
		//return songList.get(listPosition);
		if(currentListPosition == -1) {
			return null;
		}
		return playlists.get(0).getPlaylist().get(currentListPosition);
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
		
		if(masterSongList.size() == 0) {
			return new ArrayList<Song>();
		}
		
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
	
	public boolean getConfirm() {
		return confirmReceived;
	}
	
	public void setConfirm(boolean confirm) {
		confirmReceived = confirm;
	}
	
	public boolean findPlaylist(String name) {
		for(int i = 0; i < playlists.size(); i++) {
			if(playlists.get(i).getName().equals(name)) {
				return true;
			}
		}
		
		return false;
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
	
	public boolean getReceivedErrorStatus() {
		return receivedError;
	}
	
	public void setReceivedErrorStatus(boolean status) {
		receivedError = status;
	}
	
	public int getTreble() {
		return treble;
	}
	
	public void setTreble(int newTreble) {
		treble = newTreble;
	}
	
	public int getMid() {
		return mid;
	}
	
	public void setMid(int newMid) {
		mid = newMid;
	}
	
	public int getBass() {
		return bass;
	}
	
	public void setBass(int newBass) {
		bass = newBass;
	}
	
	public int getFader() {
		return fader;
	}
	
	public void setFader(int newFader) {
		fader = newFader;
	}
	
	public boolean getShuffle() {
		return shuffle;
	}
	
	public void setShuffle(boolean newShuffle) {
		shuffle = newShuffle;
	}
	
	public boolean getRepeat() {
		return repeat;
	}
	
	public void setRepeat(boolean newRepeat) {
		repeat = newRepeat;
	}
	
	public boolean getMute() {
		return mute;
	}
	
	public void setMute(boolean newMute) {
		if(newMute) {
			oldVolume = currentVolume;
			currentVolume = 0;
		} else {
			currentVolume = oldVolume;
		}
		mute = newMute;
	}
	
	public boolean getPlay() {
		return play;
	}
	
	public void setPlay(boolean newPlay) {
		play = newPlay;
	}
	
	public void setCurrentSong(String filename) {
		List<Song> songs = (playlists.get(0)).getPlaylist();
		for(int i = 0; i < songs.size(); i++) {
			if(songs.get(i).getSongName().equals(filename)) {
				currentListPosition = i;
				return;
			}
		}
		
		currentListPosition = -1;
	}
	
	public boolean getLock() {
		return lock;
	}
	
	public void setLock(boolean newLock) {
		lock = newLock;
	}
	
	public boolean getSyncNewPlaylistError() {
		return syncNewPlaylistError;
	}
	
	public void setSyncNewPlaylistError(boolean errorDetected) {
		syncNewPlaylistError = errorDetected;
	}
}
