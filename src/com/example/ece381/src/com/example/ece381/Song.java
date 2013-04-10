package com.example.ece381;

/**
 *  Song class for the Song objects
 **/
public class Song {

	private String fileName;
	private String title;
	private String artist;
	
	//constructor
	public Song() {
		
		fileName = new String();
		title = new String();
		artist = new String();
		
	}
	
	// constructor
	public Song(String fileName, String title, String artist){
		this.fileName = fileName;
		this.title = title;
		this.artist = artist;
	}
	
	//setter
	public void setSongName( String fn){
		this.fileName = fn;
	}
	
	//setter
	public void setArtistName( String a){
		this.artist = a;
	}
	
	//setter
	public void setTitleName( String t){
		this.title  = t;
	}
	
	// getter
	public String getSongName(){
		return this.fileName;
	}
	
	// getter
	public String getArtistName(){
		return this.artist;
	}
	
	// getter
	public String getTitleName(){
		return this.title;
	}

	// to concatonate 
	public String conCat(String fn, String t, String a) {
		String fullName = new String();
		fullName = "fileName" + "title" + "artist";
		return fullName;
		
	}
	
	@Override
	public String toString() {
		return this.fileName + " : " + this.title + " by " + this.artist;
	}
	

}
