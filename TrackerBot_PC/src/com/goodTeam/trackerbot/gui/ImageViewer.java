package com.goodTeam.trackerbot.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImageViewer extends JPanel {
	 private Image backgroundImage;
	 
	 public ImageViewer(){

	 }

	 public void setImage(BufferedImage image){
		 backgroundImage = image;
		 repaint();
	 }

	 public void paintComponent(Graphics g) {
	  super.paintComponent(g);
	  
	  g.drawImage(backgroundImage, 0, 0, null);
	 }
}
