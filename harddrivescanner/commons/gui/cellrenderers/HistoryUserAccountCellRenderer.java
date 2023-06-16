package org.rifluxyss.javadev.harddrivescanner.commons.gui.cellrenderers;

import java.awt.Color;


import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;

import org.rifluxyss.javadev.harddrivescanner.commons.SpyJGlobals;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJLogger;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJUtilities;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.tablemodels.HistoryTableModel;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.custom.SpyJHistoryTable;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.datparser.*;
import org.rifluxyss.javadev.harddrivescanner.gui.BrowserHistoryPanel;


public class HistoryUserAccountCellRenderer implements ListCellRenderer {
	
	static int clickCount = 0;
	BrowserHistoryPanel _targetJPanel;
	
	public HistoryUserAccountCellRenderer(BrowserHistoryPanel targetJPanel){
		_targetJPanel = targetJPanel;
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
	    		
	    		if(IM.equals("MOZILLA")){
	    			
	    			File sqlitePath = SpyJUtilities.MozillaHistoryAccounts.get(index);
	    			
	    			if(sqlitePath.exists()){
	    				
	    				String dbName = "places_" + index +".sqlite";
	    				
	    				ArrayList<String> HistoryUrl = new ArrayList<String>();
	    				ArrayList<Date> LastVisitedDate = new ArrayList<Date>();
	    				
	    				try {
							Class.forName("org.sqlite.JDBC");			
		    				Connection conn = DriverManager
		    						.getConnection("jdbc:sqlite:"+dbName);
		    				Statement stat = conn.createStatement();		    				
		    				
		    				ResultSet count_rs = stat.executeQuery("select url,last_visit_date from moz_places where url like 'http://%' or  url like 'https://%' or  url like 'file://%' or url like 'ftp:%';");
		    				
		    				int rs_count = 0;
		    				
		    				
		    				while (count_rs.next()) {
		    					if(count_rs.getLong("last_visit_date") != 0){
		    						long yourmilliseconds = Long.parseLong(count_rs.getString("last_visit_date"))/1000; 
		    						Date resultdate = new Date(yourmilliseconds);
		    						HistoryUrl.add(count_rs.getString("url"));
		    						LastVisitedDate.add(resultdate);
		    						rs_count++;
		    					}		    					
		    				}
		    				count_rs.close();
		    				stat.close();
		    				
	    				} catch (Exception e) {
							SpyJLogger.error(e.getMessage(), e);							
						}finally{
							_targetJPanel.remove(_targetJPanel.getComponentCount() - 1);		    				
		    				JScrollPane historyListPane = new JScrollPane(new SpyJHistoryTable(new HistoryTableModel(HistoryUrl, LastVisitedDate)));
		    				historyListPane.setBounds(225, 40, 645, 250);
		    				_targetJPanel.add(historyListPane);
		    				_targetJPanel.paintAll(_targetJPanel.getGraphics());		    				
						}
	    			}	    			
	    		}else if(IM.equals("IE")){    			
	    			
	    			int getIEHistoryAccountPos = index - SpyJUtilities.MozillaHistoryAccounts.size();
	    			
	    			File historyPath = SpyJUtilities.IEHistoryAccounts.get(getIEHistoryAccountPos);
	    			
	    			ArrayList<String> HistoryUrl = new ArrayList<String>();
    				ArrayList<Date> LastVisitedDate = new ArrayList<Date>();
	    			
	    			if(historyPath.exists()){
	    				
	    				Iterator itr = SpyJUtilities.IEHistoryDatFiles.iterator();
	    				
	    				while (itr.hasNext()) {	    					
	    					String datFile = itr.next().toString();
	    					//SpyJLogger.debug("datFile => " + datFile);	
	    					//SpyJLogger.debug("historyPath => " + historyPath);
	    					if(datFile.contains(historyPath.getAbsolutePath())){		    					    					
		    					ParseResult parseResult;
		    					try {
		    						parseResult = IndexDatParser.Parse(datFile);
		    						if (parseResult.Status == 0) {
		    							for(Iterator urlEntry = parseResult.Entries.UrlEntries.iterator(); urlEntry.hasNext();){
		    								UrlEntry urlObj = (UrlEntry) urlEntry.next();
		    								String url = urlObj.getUrl();
		    								if(url.contains("http://") || url.contains("file:///") || url.contains("https://") || url.contains("ftp://")){
		    									//SpyJLogger.debug(url);
		    									DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		    									if(url.contains("http://")){
		    										HistoryUrl.add(url.substring(url.indexOf("http://"),url.length()));
		    										int YEAR = Integer.parseInt(url.substring(1,5));
		    										int MONTH = Integer.parseInt(url.substring(5,7));
		    										int DATE = Integer.parseInt(url.substring(7,9));
		    										
		    										String strDate = ""+DATE + "/"+MONTH + "/"+YEAR;
		    										
		    										LastVisitedDate.add(formatter.parse(strDate));
		    									}else if(url.contains("file:///")){
		    										HistoryUrl.add(url.substring(url.indexOf("file:///"),url.length()));
		    										int YEAR = Integer.parseInt(url.substring(1,5));
		    										int MONTH = Integer.parseInt(url.substring(5,7));
		    										int DATE = Integer.parseInt(url.substring(7,9));
		    										
		    										String strDate = ""+DATE + "/"+MONTH + "/"+YEAR;
		    										LastVisitedDate.add(formatter.parse(strDate));
		    									}else if(url.contains("https://")){
		    										int YEAR = Integer.parseInt(url.substring(1,5));
		    										int MONTH = Integer.parseInt(url.substring(5,7));
		    										int DATE = Integer.parseInt(url.substring(7,9));
		    										
		    										String strDate = ""+DATE + "/"+MONTH + "/"+YEAR;
		    										HistoryUrl.add(url.substring(url.indexOf("https://"),url.length()));
		    										LastVisitedDate.add(formatter.parse(strDate));
		    									}else if(url.contains("ftp://")){
		    										HistoryUrl.add(url.substring(url.indexOf("ftp://"),url.length()));
		    										int YEAR = Integer.parseInt(url.substring(1,5));
		    										int MONTH = Integer.parseInt(url.substring(5,7));
		    										int DATE = Integer.parseInt(url.substring(7,9));
		    										
		    										String strDate = ""+DATE + "/"+MONTH + "/"+YEAR;
		    										LastVisitedDate.add(formatter.parse(strDate));
		    									}
		    								}
		    							}
		    							//LastVisitedDate = parseResult.Entries.RedirectEntries;
		    							//readRedirectUrlEntryList(_redrEntries);
		    							//print(_urlEntries.toString());
		    						}
		    					} catch (Exception e) {
		    						SpyJLogger.error(e.getMessage(), e);
		    					}
	    					}
	    				}
	    				
	    				_targetJPanel.remove(_targetJPanel.getComponentCount() - 1);		    				
	    				JScrollPane historyListPane = new JScrollPane(new SpyJHistoryTable(new HistoryTableModel(HistoryUrl, LastVisitedDate)));
	    				historyListPane.setBounds(225, 40, 645, 250);
	    				_targetJPanel.add(historyListPane);
	    				_targetJPanel.paintAll(_targetJPanel.getGraphics());
	    					
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
