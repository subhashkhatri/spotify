package com.mansi.spotify.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.mansi.spotify.entity.User;
import com.mansi.spotify.operations.UserOp;

public class SignupDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private UserOp userOp;

    public SignupDialog(Frame parent, UserOp userOp) {
        super(parent, "Signup", true);
        this.userOp = userOp;

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Username:"));
        usernameField = new JTextField(15);
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(15);
        panel.add(passwordField);

        panel.add(new JLabel("Email:"));
        emailField = new JTextField(15);
        panel.add(emailField);

        JButton signupButton = new JButton("Signup");
        signupButton.setPreferredSize(new Dimension(100, 30));
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                signup();
            }
        });
        panel.add(signupButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100, 30));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(cancelButton);

        getContentPane().add(panel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(parent);
    }

    private void signup() {
        User user = new User();
        user.setUsername(usernameField.getText());
        user.setPassword(new String(passwordField.getPassword()));
        user.setEmail(emailField.getText());

        if (userOp.signup(user)) {
            JOptionPane.showMessageDialog(this, "Signup successful!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Username already exists.");
        }
    }
}
