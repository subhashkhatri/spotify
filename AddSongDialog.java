package com.mansi.spotify.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import com.mansi.spotify.operations.SpotifyOp;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.decoder.JavaLayerException;
public class AddSongDialog extends JDialog {
    private JTextField titleField;
    private JTextField artistField;
    private JButton addButton;
    private SpotifyOp spotifyOp;
    private JFileChooser fileChooser;
    private File selectedFile;

    public AddSongDialog(Frame parent, SpotifyOp spotifyOp) {
        super(parent, "Add Song", true);
        this.spotifyOp = spotifyOp;

        setLayout(new GridLayout(4, 2));

        add(new JLabel("Song Title:"));
        titleField = new JTextField();
        add(titleField);

        add(new JLabel("Song Artist:"));
        artistField = new JTextField();
        add(artistField);

        add(new JLabel("Song File:"));
        JButton chooseFileButton = new JButton("Choose File");
        add(chooseFileButton);

        fileChooser = new JFileChooser();
        chooseFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnValue = fileChooser.showOpenDialog(AddSongDialog.this);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                }
            }
        });

        addButton = new JButton("Add Song");
        add(addButton);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addSong();
            }
        });

        pack();
        setLocationRelativeTo(parent);
    }

    private void addSong() {
        String songTitle = titleField.getText();
        String songArtist = artistField.getText();

        if (songTitle.isEmpty() || songArtist.isEmpty() || selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Please enter all details and select a file.");
            return;
        }

        // Add the song using the SpotifyOp instance
        try {
            spotifyOp.addSong(selectedFile.getAbsolutePath(), songTitle, songArtist);
            JOptionPane.showMessageDialog(this, "Song added successfully.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding song: " + e.getMessage());
            e.printStackTrace();
        }

        dispose();
    }
}
