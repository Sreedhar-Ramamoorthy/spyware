package org.rifluxyss.javadev.harddrivescanner.commons.gui.custom;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class SpyJSpacer extends JPanel{	
	public SpyJSpacer(int x, int y, int width, int height){
		setBorder(BorderFactory.createLineBorder(Color.WHITE,0));
		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
		setBounds(x,y,height,width);
		setLayout(null);
	}
}
