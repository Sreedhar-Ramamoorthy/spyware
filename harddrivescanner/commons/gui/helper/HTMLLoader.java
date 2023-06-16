package org.rifluxyss.javadev.harddrivescanner.commons.gui.helper;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JEditorPane;

import org.apache.commons.io.FileUtils;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJLogger;

public class HTMLLoader {

	String contentToDisplay = "";
	
	File _logPath;
	
	JEditorPane _targetPane;
	
	public HTMLLoader(File logPath, JEditorPane targetPane){
		
		_logPath = logPath;
		_targetPane = targetPane;
		
		
		
		Collection logFiles = FileUtils.listFiles(logPath, new String[]{"html"}, false);
		
		SpyJLogger.debug(logFiles.size());
		
		for(Iterator<File> logFile = logFiles.iterator(); logFile.hasNext();){	
			
			FileInputStream fstream;
			try {
				
				fstream = new FileInputStream(logFile.next().getAbsolutePath());			
			    // Get the object of DataInputStream
			    DataInputStream in = new DataInputStream(fstream);
			    BufferedReader br = new BufferedReader(new InputStreamReader(in));
			    String strLine;
			    //Read File Line By Line
			    while ((strLine = br.readLine()) != null)   {
			      // Print the content on the console
			    	contentToDisplay += strLine;			    	
			    }
			    
			    //	Close the input stream
			    in.close();
		    } catch (Exception e) {
				e.printStackTrace();
			}
		}
		contentToDisplay = contentToDisplay.replaceAll("<head>.*?</head>","");
		contentToDisplay = contentToDisplay.replaceAll("<html>","");
		contentToDisplay = contentToDisplay.replaceAll("</html>","");
		contentToDisplay = contentToDisplay.replaceAll("<body>","");
		contentToDisplay = contentToDisplay.replaceAll("</body>","");
		//SpyJLogger.debug(contentToDisplay);		
		_targetPane.setText("<html><body>"+contentToDisplay+"</body></html>");
	}
}
