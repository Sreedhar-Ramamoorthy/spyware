package org.rifluxyss.javadev.harddrivescanner.commons.gui.cellrenderers;

import java.awt.Color;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.rifluxyss.javadev.harddrivescanner.commons.SpyJGlobals;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.helper.HTMLLoader;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.helper.HTMLParser;

public class IMChatWithCellRenderer implements ListCellRenderer {
	
	static int clickCount = 0;
	
	JEditorPane _targetPane;
	
	public IMChatWithCellRenderer(JEditorPane targetPane){
		_targetPane = targetPane;
	}
	
	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index,
	      boolean isSelected, boolean cellHasFocus) {
	    Font theFont = null;
	    Color theForeground = null;
	    Icon theIcon = null;
	    String logFile = null;
	    String IM = null;
	    File logPath = null;
	    
	    JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
	        isSelected, cellHasFocus);

	    if (value instanceof Object[]) {
	      Object values[] = (Object[]) value;
	      theFont = SpyJGlobals.TitleFontPlain12;
	      theForeground = Color.BLACK;
	      //theIcon = (Icon) values[0];
	      logFile = (String) values[0];
	      logPath = (File) values[1];
	      IM = (String) values[2];
	    } else {
	      theFont = list.getFont();
	      theForeground = list.getForeground();
	      logFile = "";
	    }
	    if (!isSelected) {
	      renderer.setForeground(theForeground);
	    }
	    if (cellHasFocus) {
	    	clickCount++;
	    	if(clickCount == 2){
	    		//SpyJLogger.debug(index);
	    		_targetPane.setText("");
	    		
	    		if(IM.equals("AIM")){
	    			try {
						new HTMLParser(IM, logPath, _targetPane);
					} catch (Exception e) {
						e.printStackTrace();
					}
	    		}else if(IM.equals("PIDGIN")){
	    			new HTMLLoader(logPath, _targetPane);
	    		}
	    		clickCount = 0;
	    	}
	    	
	    }
	    
	   if (theIcon != null) {
		   renderer.setIcon(theIcon);
	   }
	    
	  renderer.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
	  renderer.setText(logFile);
	  renderer.setFont(theFont);
	  renderer.setPreferredSize(new Dimension(200,25));
	   renderer.setMinimumSize(new Dimension(200,25));
	   return renderer;
	  }
	}
