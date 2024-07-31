package com.mansi.spotify.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mansi.spotify.operations.SpotifyOp;

public class SearchSongDialog extends JDialog {
    private JTextField searchField;
    private SpotifyOp spotifyOp;

    public SearchSongDialog(Frame parent, SpotifyOp spotifyOp) {
        super(parent, "Search Song", true);
        this.spotifyOp = spotifyOp;

        setLayout(new GridLayout(3, 2));

        add(new JLabel("Search by Song Name:"));
        searchField = new JTextField();
        add(searchField);

        JButton searchButton = new JButton("Search");
        add(searchButton);

        JButton cancelButton = new JButton("Cancel");
        add(cancelButton);

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String songName = searchField.getText();
                JOptionPane.showMessageDialog(parent, spotifyOp.searchSongByName(songName), "Search Result", JOptionPane.INFORMATION_MESSAGE);
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
