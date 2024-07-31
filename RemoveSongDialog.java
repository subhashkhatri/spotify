package com.mansi.spotify.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mansi.spotify.operations.SpotifyOp;

public class RemoveSongDialog extends JDialog {
    private JTextField songNameField;
    private SpotifyOp spotifyOp;

    public RemoveSongDialog(Frame parent, SpotifyOp spotifyOp) {
        super(parent, "Remove Song", true);
        this.spotifyOp = spotifyOp;

        setLayout(new GridLayout(2, 2));

        add(new JLabel("Song Name:"));
        songNameField = new JTextField();
        add(songNameField);

        JButton removeButton = new JButton("Remove");
        add(removeButton);

        JButton cancelButton = new JButton("Cancel");
        add(cancelButton);

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String songName = songNameField.getText();
                spotifyOp.removeSong(songName);
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
}
