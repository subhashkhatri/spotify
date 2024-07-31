package com.mansi.spotify.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.sound.sampled.*;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import com.mansi.spotify.gui.LoginDialog;
import com.mansi.spotify.gui.SignupDialog;
import com.mansi.spotify.operations.SpotifyOp;
import com.mansi.spotify.operations.UserOp;

public class SpotifyGUI extends JFrame {
    private SpotifyOp spotifyOp;
    private UserOp userOp = new UserOp();
    private JTextArea displayArea;

    // Buttons
    private JButton signupButton;
    private JButton loginButton;
    private JButton allSongsButton;
    private JButton addSongButton;
    private JButton removeSongButton;
    private JButton searchSongButton;
    private JButton playSongButton;

    public SpotifyGUI() {
        try {
            spotifyOp = new SpotifyOp("jdbc:mysql://127.0.0.1:3306/spotify", "root", "Chandan@123");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setTitle("Spotify Clone");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Load and add Spotify image from local drive
        JLabel imageLabel = new JLabel();
        try {
            File imageFile = new File("C:\\Users\\PMLS\\Downloads\\spotify.png"); // Adjust the path as needed
            if (imageFile.exists()) {
                ImageIcon imageIcon = new ImageIcon(imageFile.getAbsolutePath());
                imageLabel.setIcon(imageIcon);
                imageLabel.setHorizontalAlignment(JLabel.CENTER);
                mainPanel.add(imageLabel, BorderLayout.WEST);
            } else {
                System.out.println("Image not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        signupButton = new JButton("Signup");
        loginButton = new JButton("Login");
        allSongsButton = new JButton("All Songs");
        addSongButton = new JButton("Add Song");
        removeSongButton = new JButton("Remove Song");
        searchSongButton = new JButton("Search Song");
        playSongButton = new JButton("Play Song");

        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSignupDialog();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showLoginDialog();
            }
        });

        allSongsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayAllSongs();
            }
        });

        addSongButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    addSong();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        removeSongButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeSong();
            }
        });

        searchSongButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchSong();
            }
        });

        playSongButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playSong();
            }
        });

        buttonPanel.add(signupButton);
        buttonPanel.add(loginButton);
        buttonPanel.add(allSongsButton);
        buttonPanel.add(addSongButton);
        buttonPanel.add(removeSongButton);
        buttonPanel.add(searchSongButton);
        buttonPanel.add(playSongButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        // Initially disable song operation buttons
        disableSongFunctions();
    }

    private void showSignupDialog() {
        SignupDialog signupDialog = new SignupDialog(this, userOp);
        signupDialog.setVisible(true);
    }

    private void showLoginDialog() {
        LoginDialog loginDialog = new LoginDialog(this, userOp);
        loginDialog.setVisible(true);
        if (loginDialog.isLoginSuccessful()) {
            enableSongFunctions();
        }
    }

    private void enableSongFunctions() {
        displayArea.setText("Login successful! You can now add, remove, search, and play songs.");
        allSongsButton.setEnabled(true);
        addSongButton.setEnabled(true);
        removeSongButton.setEnabled(true);
        searchSongButton.setEnabled(true);
        playSongButton.setEnabled(true);
    }

    private void disableSongFunctions() {
        allSongsButton.setEnabled(false);
        addSongButton.setEnabled(false);
        removeSongButton.setEnabled(false);
        searchSongButton.setEnabled(false);
        playSongButton.setEnabled(false);
    }

    private void displayAllSongs() {
        StringBuilder sb = new StringBuilder();
        for (String song : spotifyOp.getAllSongs()) {
            sb.append(song).append("\n");
        }
        displayArea.setText(sb.toString());
    }

    private void addSong() throws SQLException {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String songTitle = JOptionPane.showInputDialog(this, "Enter song title:");
            String songArtist = JOptionPane.showInputDialog(this, "Enter song artist:");
            spotifyOp.addSong(selectedFile.getAbsolutePath(), songTitle, songArtist);
            displayAllSongs();
        }
    }

    private void removeSong() {
        String songTitle = JOptionPane.showInputDialog(this, "Enter song title to remove:");
        spotifyOp.removeSong(songTitle);
        displayAllSongs();
    }

    private void searchSong() {
        String songTitle = JOptionPane.showInputDialog(this, "Enter song title to search:");
        boolean found = spotifyOp.searchSong(songTitle);
        displayArea.setText(found ? "Song found: " + songTitle : "Song not found.");
    }

    private void playSong() {
        String songTitle = JOptionPane.showInputDialog(this, "Enter song title to play:");
        String songPath = spotifyOp.getSongPath(songTitle); // Method to get the path of the song
        if (songPath != null) {
            File audioFile = new File(songPath);
            try {
                if (songPath.toLowerCase().endsWith(".mp3")) {
                    // Play MP3 using JLayer
                    FileInputStream fileInputStream = new FileInputStream(audioFile);
                    Player player = new Player(fileInputStream);
                    new Thread(() -> {
                        try {
                            player.play();
                            displayArea.setText("Playing song: " + songTitle);
                        } catch (JavaLayerException e) {
                            e.printStackTrace();
                            displayArea.setText("Error playing song: " + songTitle);
                        }
                    }).start();
                } else {
                    // Play WAV or other supported formats
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                    Clip audioClip = AudioSystem.getClip();
                    audioClip.open(audioStream);
                    audioClip.start();
                    displayArea.setText("Playing song: " + songTitle);
                }
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
                displayArea.setText("Error playing song: " + songTitle);
            } catch (JavaLayerException e) {
                throw new RuntimeException(e);
            }
        } else {
            displayArea.setText("Song not found: " + songTitle);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SpotifyGUI spotifyGUI = new SpotifyGUI();
            spotifyGUI.setVisible(true);
        });
    }
}
