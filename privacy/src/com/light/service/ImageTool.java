package com.light.service;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImageTool extends JPanel {
    public ImageTool(URL url) {
        ImageIcon icon = new ImageIcon(url);
        JLabel label = new JLabel(icon, JLabel.CENTER);
        add(label);

        System.out.println("Image width: " + icon.getIconWidth());
        System.out.println("Image height: " + icon.getIconHeight());
    }

    public static void main(String[] args) throws MalformedURLException {
        
    }
}