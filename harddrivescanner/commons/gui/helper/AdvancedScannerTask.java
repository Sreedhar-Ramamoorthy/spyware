package org.rifluxyss.javadev.harddrivescanner.commons.gui.helper;

import java.awt.Container;


import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import javax.swing.SwingWorker;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.rifluxyss.javadev.harddrivescanner.HardDriveSpy;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJGlobals;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJLogger;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJUtilities;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.custom.SpyJButton;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.custom.SpyJLabel;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.tablemodels.CategoryTableModel;

public class AdvancedScannerTask extends SwingWorker<Void, Void> {
    /*
     * Main task. Executed in background thread.
     */
	
	CategoryTableModel categoryTableModel;
	SpyJLabel _headerTitle, _remainingTime, _filePath, _statusLabel;	
	String[] _fileExtensions;
	SpyJButton _backButton;
	Container _rootPane;
	
	public AdvancedScannerTask(SpyJLabel _headerTitle, 
			SpyJLabel _remainingTime, String[] _fileExtensions, SpyJLabel _filePath, 
			SpyJLabel _statusLabel, SpyJButton _backButton){
		this._remainingTime = _remainingTime;
		this._fileExtensions = _fileExtensions;
		this._filePath = _filePath;
		this._headerTitle = _headerTitle;
		this._statusLabel = _statusLabel;
		this._backButton = _backButton;
		this._rootPane = HardDriveSpy.getRootContentPane();
		this.categoryTableModel = SpyJUtilities.getCategoryTableModel();
	}
	
	
    @Override
    public Void doInBackground() {
    	
    	HttpURLConnection con;
		try {			
			
			//Scan Tracker Initiation			
			System.out.println("Trying to connect demo!");
			con = (HttpURLConnection) new URL(SpyJGlobals.RootPath+"scan_tracker.php?cmd=scan&mode=started&ip=" + SpyJGlobals.User_IP + "&scan_type="+SpyJGlobals.SCAN_TYPE).openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
	    	StringWriter writer = new StringWriter();
	    	IOUtils.copy(con.getInputStream(), writer);
	    	SpyJGlobals.SCAN_ID = Integer.parseInt(writer.toString());
	    	System.out.println("Result: " + writer.toString());   	
	    	
		} catch (Exception e) {
			SpyJLogger.error("Error while sending scan state to server!", e);			
		}
		
        //Initialize progress property.
        setProgress(0);
        //Sleep for at least one second to simulate "startup".
        
        try {        	
        	
        	int totalDirectoriesCount = 0; 
        	
        	Collections.sort(SpyJUtilities.ScanDrives);
        	
        	SpyJUtilities.DirectoryCollections = new ArrayList[SpyJUtilities.ScanDrives.size()];
        	
        	for(int drive=0; drive < SpyJUtilities.ScanDrives.size(); drive++){		
    			
        		_headerTitle.setText("Analyzing... (Drive " + SpyJUtilities.ScanDrives.get(drive).toUpperCase() + ")");
        		
        		SpyJUtilities.DirectoryCollections[drive] = new ArrayList<File>();
        		//File directoryToScan = new File("D:\\Collections");
    			File directoryToScan = new File(SpyJUtilities.ScanDrives.get(drive)+":\\");
    			//File directoryToScan = new File("C:\\Documents and Settings\\P Vignesh\\My Documents\\");
        		//File directoryToScan = new File("C:\\Documents and Settings\\P Vignesh\\Application Data\\");
    		    //files[drive] = (Collection) FileUtils.listFiles(root, _fileExtensions, false);    		    
    		    //SpyJLogger.debug(SpyJUtilities.ScanDrives.get(drive)+":\\ ==>"+files[drive].size());
    		    
    		    SpyJUtilities.traverseDirectories(directoryToScan, drive, _filePath);
    		    totalDirectoriesCount += SpyJUtilities.DirectoryCollections[drive].size();
    		    
    		}
        	_remainingTime.setVisible(true);
        	_statusLabel.setVisible(false);
        	
        	SpyJLogger.debug("totalFilesCount ==> " + totalDirectoriesCount);
            
            int noOfFiles = totalDirectoriesCount;
            
            int currentProgress = 0;
            
            int fileCounter = 0;
            
            int Counter= totalDirectoriesCount * 100;	 
            
	        for(int drive=0; drive < SpyJUtilities.ScanDrives.size(); drive++){	
	        	
	        	_headerTitle.setText("Scanning... (Drive " + SpyJUtilities.ScanDrives.get(drive).toUpperCase() + ")");
	        	
	        	SpyJLogger.debug("Scan Drive ==>"+SpyJUtilities.ScanDrives.get(drive));
	        	
	        	for (Iterator iterator = SpyJUtilities.DirectoryCollections[drive].iterator(); iterator.hasNext();) {
	        		
	              File direcotoryToList = (File) iterator.next();
	              
	              SpyJLogger.debug("direcotoryToList =>" + direcotoryToList);
	              
	              Collection dirFiles = FileUtils.listFiles(direcotoryToList, _fileExtensions, false);
	              
	              if(dirFiles != null){
	              
		              for(Iterator item = dirFiles.iterator(); item.hasNext();){
		            	  
		            	  File file = (File) item.next();
	              
			              String fileExtension = SpyJUtilities.getExtension(file.getAbsolutePath());
			              
			               _filePath.setText(file.getAbsolutePath());
		
			      		  _filePath.setToolTipText(SpyJUtilities.setCustomToolTipText(file.getAbsolutePath()));
			              
			              //SpyJLogger.debug("FileExtension ==>" + fileExtension);
			              
			              int currentCellValue = Integer.parseInt(categoryTableModel.getValueAt(SpyJUtilities.getCategoryByExtension(fileExtension), 1).toString());
			              
			              int categoryIndex = SpyJUtilities.getCategoryByExtension(fileExtension);
			              
			              //SpyJLogger.debug("Category ==> "+categoryIndex);
			              
			              SpyJUtilities.FILES_LIST[categoryIndex].add(file);
			              
			              //SpyJLogger.debug("File Path() ==>" + file.getAbsolutePath());
			              
			              categoryTableModel.setValueAt((currentCellValue+1), SpyJUtilities.getCategoryByExtension(fileExtension), 1);
			              
			              int extensionIndex = SpyJUtilities.getExtensionIndex(categoryIndex, fileExtension);
			              
			              //SpyJLogger.debug("Category ==> "+categoryIndex + " extensionIndex ==>" + extensionIndex);
			              
			              int extensionCount = (Integer) SpyJUtilities.FILES_TYPE_COUNT[categoryIndex][extensionIndex];
			              
			              //SpyJLogger.debug("extensionCount ==>" + extensionCount);
			              
			              SpyJUtilities.FILES_TYPE_COUNT[categoryIndex][extensionIndex] = extensionCount + 1;
			              
			              SpyJUtilities.FILES_TYPE_LIST[categoryIndex][extensionIndex].add(file);			              
			             
			              //SpyJLogger.debug("extensionCount ==>" + extensionCount);
		              }
		              
		          }
	              
	              String dirPath = direcotoryToList.getAbsolutePath(); 
            	  
	              if(direcotoryToList.getName().equals("AIMLogger")){
	            	  String[] userAccounts = direcotoryToList.list();
	            	  if(userAccounts != null){
	            		  for (int account = 0; account < userAccounts.length; account++) {
	            			  File userAccount = new File(direcotoryToList, userAccounts[account]);
	            			  if(userAccount.isDirectory())
	            				  SpyJUtilities.AIMUserAccounts.add(userAccount);
	            		  }
	            	  }
	              }else if(direcotoryToList.getName().equals(".purple")){
            		 File pidginLogs = new File(direcotoryToList, "logs");            		 
            		 if(pidginLogs != null){	 
            			 String[] imAccounts = pidginLogs.list();
            			 if(imAccounts != null){
		            		 for (int im = 0; im < imAccounts.length; im++) {         	
		            			 File imLogs = new File(pidginLogs, imAccounts[im]);
		            			 String[] userAccounts = imLogs.list();
		            			 if(userAccounts != null){
				            		 for (int account = 0; account < userAccounts.length; account++) {
				            			 File userAccount = new File(imLogs, userAccounts[account]);
				            			 if(userAccount.isDirectory())
				            				 SpyJUtilities.PidginUserAccounts.add(userAccount);
				            		 }
		            			 }
		            		 }
            			 }
            		 }
	              }else if(dirPath.contains("Profiles") 
	            		  && dirPath.contains("Mozilla") 
	            		  && dirPath.contains("Firefox")
	            		  && (dirPath.contains("Documents and Settings")
	            		  || dirPath.contains("Users")) 
	            		  && dirPath.contains("default") 
	            		  && !dirPath.contains("Thinstall")
	            		  && !dirPath.contains("\\Default")){
	            	 
	            	  File[] files = direcotoryToList.listFiles(new FileListFilter("places.sqlite"));
	            	  
	            	  if(files != null &&  files.length>0){
	            		  SpyJLogger.debug(direcotoryToList);
	            		  SpyJUtilities.MozillaHistoryAccounts.add(new File(direcotoryToList, "places.sqlite"));
	            		  try{
		            		    int index = 0;
	
								index = SpyJUtilities.MozillaHistoryAccounts.size() - 1;
	
								File sqlitePath = SpyJUtilities.MozillaHistoryAccounts
										.get(index);
	
								if (sqlitePath.exists()) {
									String dbName = "places_" + index + ".sqlite";
	
									SpyJLogger.debug("dbName = " + dbName);
									SpyJUtilities.copyfile(sqlitePath.toString(),
											dbName);
								}
	            		  }catch(Exception e){
	            			  e.printStackTrace();
	            		  }
	            	  }
	              } else if(dirPath.contains("History")
					         && dirPath.contains("Local")
					         && (dirPath.contains("Documents and Settings") || dirPath.contains("Users")) 
					         && !dirPath.contains("Thinstall") 
					         && dirPath.contains("History.IE5") 
					         && !dirPath.contains("MSHist") 
					         && !dirPath.contains("Temp") 
					         && !dirPath.contains("Low") 
					         && !dirPath.contains("Default")
					         && !dirPath.contains("Service")){
						String[] subDirectories = direcotoryToList
						.list(new FileListFilter("DIRECTORY"));						
						if(subDirectories != null){
							SpyJLogger.debug("IE History Path=>" + direcotoryToList);
							SpyJUtilities.IEHistoryAccounts.add(direcotoryToList);
							for(int subDirCount=0; subDirectories.length  > subDirCount; subDirCount++){
								File SubDirectory = new File(direcotoryToList, subDirectories[subDirCount]);
								File[] datFile = SubDirectory.listFiles(new FileListFilter("index.dat"));
								if (datFile != null && datFile.length > 0) {
									SpyJLogger.debug("IE History=>" + SubDirectory);
									SpyJUtilities.IEHistoryDatFiles.add(new File(
											SubDirectory, "index.dat"));				
								}
							}
						}
	              }
	              
	              setProgress(Math.min(currentProgress, 100));
	              
	              currentProgress = (int) (((float) (fileCounter+1) / noOfFiles) * 100);
	              
	              //SpyJLogger.debug("CurrentProgress: " + currentProgress);
	              
	              Counter = Counter - 100;
	              _remainingTime.setText("Estimated Time Remaining - "+ SpyJUtilities.formatTime(Counter));
	              
	              fileCounter++;
	           }
	            
	           //SpyJUtilities.pringFileArray(SpyJUtilities.FILES_LIST);
	        }
	        
	        setProgress(100);
	        
	        _remainingTime.setVisible(false);
	        _filePath.setVisible(false);
	        _backButton.setVisible(true);
            //Thread.sleep(1000 + random.nextInt(2000));
            
        } catch (Exception e) {
        	SpyJLogger.error("Error occurs while scanning!", e);
        }
        
        return null;
    }
    
    /*
     * Executed in event dispatch thread
     */
    @Override
	public void done() {
    	
    	HttpURLConnection con;
		try {
			
			//Scan Tracker Initiation			
			System.out.println("Scanning completed!");
			con = (HttpURLConnection) new URL(SpyJGlobals.RootPath+"scan_tracker.php?cmd=scan&mode=completed&scan_id="+SpyJGlobals.SCAN_ID).openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
	    	StringWriter writer = new StringWriter();
	    	IOUtils.copy(con.getInputStream(), writer);	
	    	System.out.println("Scan Complete Tracker : " + writer.toString());
	    	
		} catch (Exception e) {
			SpyJLogger.error("Error while sending scan state to server!", e);			
		}
    	
        Toolkit.getDefaultToolkit().beep();
        _remainingTime.setVisible(false);
        //startButton.setEnabled(true);
        //taskOutput.append("Done!\n");
    }
   
}
