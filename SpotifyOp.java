package com.mansi.spotify.operations;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpotifyOp {
	private Connection conn;

	public SpotifyOp(String dbUrl, String user, String password) throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL JDBC driver
			conn = DriverManager.getConnection(dbUrl, user, password);
			initializeDatabase();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new SQLException("MySQL JDBC driver not found.", e);
		}
	}
	public String getSongPath(String songTitle) {
		String filePath = null;
		String query = "SELECT file_path FROM songs WHERE title = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, songTitle);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				filePath = rs.getString("file_path");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return filePath;
	}
	private void initializeDatabase() throws SQLException {
		String createTableSQL = "CREATE TABLE IF NOT EXISTS songs ("
				+ "id INT PRIMARY KEY AUTO_INCREMENT, "
				+ "title VARCHAR(100) NOT NULL, "
				+ "artist VARCHAR(100) NOT NULL)";
		try (Statement stmt = conn.createStatement()) {
			stmt.execute(createTableSQL);
		}
	}
	private void createTables() throws SQLException {
		Statement stmt = conn.createStatement();
		String createSongsTable = "CREATE TABLE IF NOT EXISTS songs (" +
				"id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"title TEXT NOT NULL," +
				"artist TEXT NOT NULL)";
		stmt.execute(createSongsTable);
	}

	public List<String> getAllSongs() {
		List<String> songs = new ArrayList<>();
		try (Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT title FROM songs")) {
			while (rs.next()) {
				songs.add(rs.getString("title"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return songs;
	}

	public void addSong(String filePath, String title, String artist) throws SQLException {
		String query = "INSERT INTO songs (title, artist, file_path) VALUES (?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setString(1, title);
			pstmt.setString(2, artist);
			pstmt.setString(3, filePath);
			pstmt.executeUpdate();
		}
	}

	public void addAllSongs(List<String> songTitles, List<String> artists) {
		if (songTitles.size() != artists.size()) {
			throw new IllegalArgumentException("The size of song titles and artists lists must be the same.");
		}

		String sql = "INSERT INTO songs (title, artist) VALUES (?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			for (int i = 0; i < songTitles.size(); i++) {
				pstmt.setString(1, songTitles.get(i));
				pstmt.setString(2, artists.get(i));
				pstmt.addBatch();
			}
			pstmt.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void removeSong(String title) {
		String sql = "DELETE FROM songs WHERE title = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, title);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean searchSong(String title) {
		String sql = "SELECT title FROM songs WHERE title = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, title);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean searchSongByName(String title) {
		String query = "SELECT * FROM songs WHERE title = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, title);
			ResultSet rs = stmt.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
