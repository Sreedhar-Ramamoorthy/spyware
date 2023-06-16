package org.rifluxyss.javadev.harddrivescanner.commons.gui.cellrenderers;

import java.awt.Color;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.apache.commons.io.FileUtils;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJGlobals;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJLogger;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJUtilities;

public class IMUserAccountCellRenderer implements ListCellRenderer {
	
	static int clickCount = 0;
	
	JList _chatWithJlist;
	
	JEditorPane _historyPane;
	
	public IMUserAccountCellRenderer(JList chatWithJlist, JEditorPane historyPane){
		_chatWithJlist = chatWithJlist;
		_historyPane = historyPane;
	}
	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index,
	      boolean isSelected, boolean cellHasFocus) {
	    Font theFont = null;
	    Color theForeground = null;
	    Icon IM_ICON = null;
	    String userAccount = null;
	    String IM = null;
	    
	    JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
	        isSelected, cellHasFocus);
	    
	    if (value instanceof Object[]) {
	      Object values[] = (Object[]) value;
	      theFont = SpyJGlobals.TitleFontPlain12;
	      theForeground = Color.BLACK;
	      IM_ICON = (Icon) values[0];
	      userAccount = (String) values[1];
	      IM = (String) values[2];
	    } else {
	      theFont = list.getFont();
	      theForeground = list.getForeground();
	      userAccount = "";
	    }
	    if (!isSelected) {
	      renderer.setForeground(theForeground);
	    }
	    if (cellHasFocus) {
	    	clickCount++;
	    	
	    	if(clickCount == 2){
	    		
	    		_historyPane.setText("");	    		
	    		
	    		if(IM.equals("AIM")){
	    			
	    			File userAccountPath = SpyJUtilities.AIMUserAccounts.get(index);
	    			
	    			File chatWithUsersIMLogs = new File(userAccountPath, "IM Logs");
	    			
	    			if(chatWithUsersIMLogs != null){
	    				
	    				String[] logExtension = {"html"};
	    				
	    				Collection chatWithUsersList = FileUtils.listFiles(chatWithUsersIMLogs, logExtension, false);
	    				
	    				if(chatWithUsersList != null){
	    					
	    					SpyJLogger.debug(chatWithUsersList.size());
	    					
		    				Object[][] chatWithUsersData = new Object[chatWithUsersList.size()][3];
		    				int count = 0;
		    				for(Iterator<File> iterator = chatWithUsersList.iterator(); iterator.hasNext();){
		    					
		    					File logFile = iterator.next();
		    					String UserName = logFile.getName().replaceAll(".html", "");
		    					chatWithUsersData[count][0] = new String(UserName);
		    					chatWithUsersData[count][1] = logFile;
		    					chatWithUsersData[count][2] = new String("AIM");
		    					count++;
		    				}
		    				_chatWithJlist.setListData(chatWithUsersData);
		    				_chatWithJlist.paintAll(_chatWithJlist.getGraphics());
	    				}
	    			}	    			
	    		}else if(IM.equals("PIDGIN")){
	    			int posHelper = index - SpyJUtilities.AIMUserAccounts.size();
	    			File userAccountPath = SpyJUtilities.PidginUserAccounts.get(posHelper);
	    			String[] chatWithUsersList = userAccountPath.list();
	    			if(chatWithUsersList != null){
	    				Object[][] chatWithUsersData = new Object[chatWithUsersList.length][3];
	    				for(int count=0; chatWithUsersList.length>count; count++){
	    					//SpyJLogger.debug(chatWithUsersList[count]);
	    					chatWithUsersData[count][0] = new String(chatWithUsersList[count]);
	    					chatWithUsersData[count][1] = new File(userAccountPath, chatWithUsersList[count]);
	    					chatWithUsersData[count][2] = new String("PIDGIN");
	    				}
	    				_chatWithJlist.setListData(chatWithUsersData);
	    				_chatWithJlist.paintAll(_chatWithJlist.getGraphics());
	    				
	    			}
	    		}
	    		clickCount = 0;
	    	}
	    	
	    }
	    
	   if (IM_ICON != null) {
		   renderer.setIcon(IM_ICON);
	   }
	    
	   renderer.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3));
	   renderer.setText(userAccount);
	   renderer.setFont(theFont);
	   renderer.setPreferredSize(new Dimension(200,25));
	   renderer.setMinimumSize(new Dimension(200,25));
	   return renderer;
	  }
	}
