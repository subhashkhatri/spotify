package com.mansi.spotify.entity;

public class Spotify {
	private String songName;
	private String singerName;
	private String movieName;
	private String actressName;
	private String actorName;
	private String filePath; // Path to the audio file

	// Getters and setters

	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public String getSingerName() {
		return singerName;
	}

	public void setSingerName(String singerName) {
		this.singerName = singerName;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public String getActressName() {
		return actressName;
	}

	public void setActressName(String actressName) {
		this.actressName = actressName;
	}

	public String getActorName() {
		return actorName;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return "Spotify \n song Name= " + songName + ", \n singer Name= " + singerName + ",\n movie Name= " + movieName
				+ ",\n actress Name= " + actressName + ",\n actor Name= " + actorName;
	}
}
