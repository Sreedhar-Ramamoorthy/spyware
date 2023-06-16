package org.rifluxyss.javadev.harddrivescanner.commons;

import java.awt.Container;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.rifluxyss.javadev.harddrivescanner.commons.gui.custom.SpyJLabel;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.helper.DataWithIcon;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.tablemodels.CategoryTableModel;
import org.rifluxyss.javadev.harddrivescanner.gui.DriveSelectionPanel;
import org.rifluxyss.javadev.harddrivescanner.gui.HomeScreenPanel;
import org.rifluxyss.javadev.harddrivescanner.gui.ScannerPanel;

public class SpyJUtilities {
	
	private static final GridBagConstraints gbc;    

	public static ArrayList[] FILES_LIST;	
	public static ArrayList<String> ScanDrives;	
	public static Object[][] FILES_TYPE_COUNT;	
	public static ArrayList[][] FILES_TYPE_LIST;	
	public static ArrayList<File>[] DirectoryCollections;	
	public static ArrayList<File> AIMUserAccounts;	
	public static ArrayList<File> PidginUserAccounts;	
	static CategoryTableModel categoryTableModel;
	public static ArrayList<File> MozillaHistoryAccounts;	
	public static ArrayList<File> IEHistoryAccounts;
	public static ArrayList<File> IEHistoryDatFiles;
	
	public SpyJUtilities(){
		FILES_LIST = new ArrayList[getCategoryCount()];
		initializeFileArray(FILES_LIST);
		ScanDrives = new ArrayList<String>();
		categoryTableModel = new CategoryTableModel();
		AIMUserAccounts = new ArrayList<File>();
		PidginUserAccounts = new ArrayList<File>();
		MozillaHistoryAccounts = new ArrayList<File>();
		IEHistoryAccounts = new ArrayList<File>();
		IEHistoryDatFiles = new ArrayList<File>();
	}
	
	 public static CategoryTableModel getCategoryTableModel(){
		return categoryTableModel;
	 }
    
    private void initializeFileArray(ArrayList[] _filesList) {
    	for(int count=0; _filesList.length > count; count++){
    		FILES_LIST[count] = new ArrayList<File>();
    	}
    	FILES_TYPE_COUNT = new Object[getCategoryCount()][];
		FILES_TYPE_LIST = new ArrayList[getCategoryCount()][];
	}
    
    public static void initializeExtensionsArray(){
    	for(int count=0; getCategoryCount() > count; count++){
    		FILES_TYPE_COUNT[count] = new Object[getExtensionsCountByCategory(count)];
    		FILES_TYPE_LIST[count] = new ArrayList[getExtensionsCountByCategory(count)];
    		for(int extensionCount=0; getExtensionsCountByCategory(count) > extensionCount; extensionCount++){
    			FILES_TYPE_COUNT[count][extensionCount] = 0;
    			FILES_TYPE_LIST[count][extensionCount] = new ArrayList<File>();
    		}
    	}
    }
    
    public static void pringFileArray(ArrayList[] _filesList) {
    	for(int count=0; _filesList.length > count; count++){
    		for(int filecount=0; _filesList[count].size() > filecount; filecount++){
    			SpyJLogger.debug("File Name => "+((File)FILES_LIST[count].get(filecount)).getName());
    			SpyJLogger.debug("File Path => "+((File)FILES_LIST[count].get(filecount)).getAbsolutePath());
    			SpyJLogger.debug("File Size => "+((File)FILES_LIST[count].get(filecount)).length());
    		}
    	}
	}

	static {
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHWEST;
    }
	
	public static void traverseDirectories(File directoryToScan, int drive, SpyJLabel pathIndicator) {
		 if (directoryToScan.isDirectory()) {	
			 SpyJUtilities.DirectoryCollections[drive].add(directoryToScan);
			pathIndicator.setText(directoryToScan.toString());
			pathIndicator.setToolTipText(directoryToScan.toString());
			
		    String[] subDirectories = directoryToScan.list();
		    
		    if(subDirectories != null){
		    	 for (int directory = 0; directory < subDirectories.length; directory++) {
		    		 traverseDirectories(new File(directoryToScan, subDirectories[directory]), drive, pathIndicator);
		    	 }
		 	}
	    }
	 }
	
	/**
     * Wraps a Swing JComponent in a background image. The vertical and horizontal
     * alignment of background image can be specified using the alignment
     * contants from JLabel.
     *
     * @param component - to wrap in the a background image
     * @param backgroundIcon - the background image (Icon)
     * @param verticalAlignment - vertical alignment. See contants in JLabel.
     * @param horizontalAlignment - horizontal alignment. See contants in JLabel.
     * @return the wrapping JPanel
     */
    public static JPanel wrapInBackgroundImage(JComponent component,
            Icon backgroundIcon,
            int verticalAlignment,
            int horizontalAlignment) {
        
        // make the passed in swing component transparent
        component.setOpaque(false);
        
        // create wrapper JPanel
        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        
        // add the passed in swing component first to ensure that it is in front
        backgroundPanel.add(component, gbc);
        
        // create a label to paint the background image
        JLabel backgroundImage = new JLabel(backgroundIcon);
        
        // set minimum and preferred sizes so that the size of the image
        // does not affect the layout size
        backgroundImage.setPreferredSize(new Dimension(1,1));
        backgroundImage.setMinimumSize(new Dimension(1,1));
        
        // align the image as specified.
        backgroundImage.setVerticalAlignment(verticalAlignment);
        backgroundImage.setHorizontalAlignment(horizontalAlignment);
        
        // add the background label
        backgroundPanel.add(backgroundImage, gbc);
        
        // return the wrapper
        return backgroundPanel;
    }
    
    public static void doRedirect(String _target, Container _rootPane){
    	_rootPane.removeAll();
    	if(_target.equals("Home")){
    		new HomeScreenPanel(_rootPane);
    	}else if(_target.equals("Drive")){
    		new DriveSelectionPanel(_rootPane);
    	}else if(_target.equals("Scan")){
    		new ScannerPanel();
    	}
    	_rootPane.paintAll(_rootPane.getGraphics());
    }
    
    public static String setCustomToolTipText(String _tip){
    	String CustomTip = "<html>" +
    			"<body bgcolor=\"white\" leftmargin=\"0\" rightmargin=\"0\" " +
				"bottommargin=\"0\" topmargin=\"0\">" +
				"<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border:1px solid black;\" width=\"100%\">" +
				"<tr><td colspan=\"2\" nowrap>"+_tip+"</td></tr>" +
				"</table></body></html>"; 	
    	return CustomTip;
    }
    
    public static String[] getCategoryArray(){
    	String _typeLabels = "";
    	if(SpyJGlobals.Categories != ""){
    		_typeLabels = SpyJGlobals.Categories;
    	}else{
    		//SpyJLogger.debug("No Parameters");
    		_typeLabels = "Images,Videos,Music,Documents,Financial Data";
    	}
    	return _typeLabels.split(",");
    }
    
    public static String[] getCategoryIconsArray(){
    	String _labelIcons = "";
    	if(SpyJGlobals.Categories != ""){
    		_labelIcons = SpyJGlobals.CategoryIcons;
    	}else{
    		//SpyJLogger.debug("No Parameters");
    		_labelIcons = "http://demo.rifluxyss.com/webscanner/admin/images/category_icons/image_icon.png" +
    				",http://demo.rifluxyss.com/webscanner/admin/images/category_icons/video_icon.png" +
    				",http://demo.rifluxyss.com/webscanner/admin/images/category_icons/music_icon.png" +
    				",http://demo.rifluxyss.com/webscanner/admin/images/category_icons/document_icon.png" +
    				",http://demo.rifluxyss.com/webscanner/admin/images/category_icons/financial_data_icon.png";
    	}
    	return _labelIcons.split(",");
    }
    
    public static Object[][] getFileTypes(){
    	
    	String[] typeLabels = getCategoryArray();
    	int fileTypesLength = typeLabels.length;
    	
    	String[] labelIcons = getCategoryIconsArray();
    		
    	Object fileTypes[][] = new Object[fileTypesLength][2];    	
    	for(int count=0; fileTypesLength > count; count++){
    		/*
    		if(typeLabels[count].equals("Images")){
    			fileTypes[count][0] = new DataWithIcon("Images", SpyJIcons.IMAGES_ICON);
    			fileTypes[count][1] = 0;
    		}else if(typeLabels[count].equals("Videos")){
    			fileTypes[count][0] = new DataWithIcon("Videos", SpyJIcons.VIDEO_ICON);
    			fileTypes[count][1] = 0;
    		}else if(typeLabels[count].equals("Music")){
    			fileTypes[count][0] = new DataWithIcon("Music", SpyJIcons.MUSIC_ICON);
    			fileTypes[count][1] = 0;
    		}else if(typeLabels[count].equals("Documents")){
    			fileTypes[count][0] = new DataWithIcon("Documents", SpyJIcons.DOCUMENT_ICON);
    			fileTypes[count][1] = 0;
    		}else if(typeLabels[count].equals("Financial Data")){
    			fileTypes[count][0] = new DataWithIcon("Financial Data", SpyJIcons.FINANCIAL_DATA_ICON);
    			fileTypes[count][1] = 0;
    		}else{
    			fileTypes[count][0] = new DataWithIcon(typeLabels[count], SpyJIcons.DEFAULT_ICON);
    			fileTypes[count][1] = 0;
    		}
    		*/
    		ImageIcon _imageIcon = null;
    		try {
    			_imageIcon = new ImageIcon(new URL(labelIcons[count]));
			} catch (MalformedURLException e) {
				SpyJLogger.error(e.getMessage(), e);
				_imageIcon = SpyJIcons.DEFAULT_ICON;
			}
			fileTypes[count][0] = new DataWithIcon(typeLabels[count], _imageIcon);
			fileTypes[count][1] = 0;
    	}
    	return fileTypes;
    }
    
    public static DataWithIcon getFileTypes(int _categoryIndex){
    	
    	return new DataWithIcon(getCategoryName(_categoryIndex), getCategoryIcon(_categoryIndex));
    	
    	/*
    	if(typeLabel.equals("Images")){
			return new DataWithIcon("Images", SpyJIcons.IMAGES_ICON);
		}else if(typeLabel.equals("Videos")){
			return new DataWithIcon("Videos", SpyJIcons.VIDEO_ICON);
		
		}else if(typeLabel.equals("Music")){
			return new DataWithIcon("Music", SpyJIcons.MUSIC_ICON);

		}else if(typeLabel.equals("Documents")){
			return new DataWithIcon("Documents", SpyJIcons.DOCUMENT_ICON);
			
		}else if(typeLabel.equals("Financial Data")){
			return new DataWithIcon("Financial Data", SpyJIcons.FINANCIAL_DATA_ICON);
			
		}else{
			return new DataWithIcon(typeLabel, SpyJIcons.DEFAULT_ICON);
		}
		*/
    }
    
    public static int getCategoryCount(){
    	return getCategoryArray().length;
	}
    
    public static void setParameters(JApplet _applet){
//	    if(_applet.getParameter("categories") != null){
//			//SpyJLogger.debug("categories==>"+_applet.getParameter("categories"));
//			SpyJGlobals.Categories = _applet.getParameter("categories");
//			SpyJGlobals.CategoryIcons = _applet.getParameter("category_icons");
//			//SpyJLogger.debug("Category Icons==>"+_applet.getParameter("category_icons"));
//			SpyJGlobals.RootPath = _applet.getParameter("root_path");
//			SpyJGlobals.HelpUrl = _applet.getParameter("help_url");
//			setCategoryExtensionsArray(_applet);
//		}
	}
    
    public static String getRootPath(){
    	if(SpyJGlobals.RootPath != "")
    		return SpyJGlobals.RootPath;
    	else
    		return "http://demo.rifluxyss.com/webscanner/";
	}
    
    private static void setCategoryExtensionsArray(JApplet _applet) {
    	String[] categoryArray = getCategoryArray();
    	SpyJGlobals.CategoryExtensionsArray = new String[getCategoryCount()];
    	for(int count=0; categoryArray.length > count; count++){
			if(_applet.getParameter(categoryArray[count]) != null){
				//SpyJLogger.debug(categoryArray[count] + "==>" +_applet.getParameter(categoryArray[count]));
				SpyJGlobals.CategoryExtensionsArray[count] = _applet.getParameter(categoryArray[count]).toLowerCase();
			}
    	}
	}
    
    public static String[] getFileExtensionsArray(){
    	
    	String _fileExtensions[] = new String[getCategoryCount()];
    	
    	if(SpyJGlobals.CategoryExtensionsArray != null && SpyJGlobals.CategoryExtensionsArray.length > 0){
    		_fileExtensions = SpyJGlobals.CategoryExtensionsArray;
    	}else{
	    	_fileExtensions[0] = "BMP,TIFF,JPG".toLowerCase();
	    	_fileExtensions[1] = "AVI,MP4,MPEG,MKV,MOV".toLowerCase();
	    	_fileExtensions[2] = "WAV,MP3,OGG,M4A,WMA".toLowerCase();
	    	_fileExtensions[3] = "DOC,TXT,PDF,XLS,PPT,WPD".toLowerCase();
	    	_fileExtensions[4] = "QBB,QBW,QDF,QEL,QPH,QIF,MNY".toLowerCase();
    	}
    	return _fileExtensions;
    }
    
    public static int getExtensionsCountByCategory(int index){
    	String[] _fileExtentsions = getFileExtensionsArray();
    	return _fileExtentsions[index].split(",").length;
    }
    
    public static String[] getExtensionsByCategory(int index){
    	String[] _fileExtentsions = getFileExtensionsArray();
    	return _fileExtentsions[index].split(",");
    }
    
    public static int getExtensionIndex(int categoryIndex, String extension){
    	String[] _fileExtentsions = getExtensionsByCategory(categoryIndex);
    	
    	int index = 0;
    	for(int count=0; _fileExtentsions.length > count; count++){
    		if(_fileExtentsions[count].equals(extension)){
    			index=count;
    			break;
    		}
    	}
    	return index;
    }
    
    public static String getCategoryName(int index){
    	String[] _fileExtentsions = getCategoryArray();
    	return _fileExtentsions[index];
    }
    
    public static ImageIcon getCategoryIcon(int index){
    	String[] _icons = getCategoryIconsArray();
    	ImageIcon _icon = null;
    	
    	try {
    		_icon = new ImageIcon(new URL(_icons[index]));
		} catch (MalformedURLException e) {
			_icon = SpyJIcons.DEFAULT_ICON;
			SpyJLogger.error(e.getMessage(), e);
		}
		return _icon;
    }
    
    public static String[] getFileExtensions(){    	
    	String _fileExtensions[] = getFileExtensionsArray();    	
    	String _fileExtensionsAll[] = new String[getFileExtensionsCount()];
    	int incrementer=0;
    	for(int count=0; _fileExtensions.length > count; count++){
    		String[] _fileExtensionArray = _fileExtensions[count].split(",");
    		for(int innerCount=0; _fileExtensionArray.length > innerCount; innerCount++){
    				_fileExtensionsAll[incrementer] = _fileExtensionArray[innerCount];
    			incrementer++;
    		}
    	}
    	return _fileExtensionsAll;
    }
    
    public static String getExtension(String filePath){
    	return filePath.substring(filePath.lastIndexOf(".")+1);
    }
    
    public static int getFileExtensionsCount(){
    	String _fileExtensions[] = getFileExtensionsArray();  
    	int  _fileExtensionsCounter = 0;
    	for(int count=0; _fileExtensions.length > count; count++){
    		_fileExtensionsCounter += _fileExtensions[count].split(",").length;
    	}
		return _fileExtensionsCounter;
    }
    
    public static String getExtensionName(int categoryIndex, int _extensionIndex){
    	String _fileExtensions[] = getExtensionsByCategory(categoryIndex); 
    	return _fileExtensions[_extensionIndex];
    }
    
    public static int getCategoryByExtension(String extension){
    	String _fileExtensions[] = getFileExtensionsArray(); 
    	int getCategoryIndex = 0;
    	
    	for(int count=0; _fileExtensions.length > count; count++){
    		if(_fileExtensions[count].contains(extension)){
    			getCategoryIndex = count;
    			break;
    		}
    	}
    	return getCategoryIndex;
    }
    
    public static ImageIcon getExtensionIcon(String _extension) {
		ImageIcon _imageIcon = null;
		String extensionUrl = getRootPath() + "admin/images/file_types/"+_extension+".png";
		try {
			_imageIcon = new ImageIcon(new URL(extensionUrl));
		} catch (Exception e) {
			SpyJLogger.error(e.getMessage(), e);
			_imageIcon = SpyJIcons.DEFAULT_ICON;
		}
		return _imageIcon;
	}
    
    public static String formatTime(long millis) {
    	
		  String output = "00:00:00";
		  
		  long seconds = millis / 1000;
		  long minutes = seconds / 60;
		  long hours = minutes / 60;
	
		  seconds = seconds % 60;
		  minutes = minutes % 60;
		  hours = hours % 60;
	
		  String secondsD = String.valueOf(seconds);
		  String minutesD = String.valueOf(minutes);
		  String hoursD = String.valueOf(hours); 
	
		  if (seconds < 10)
		    secondsD = "0" + seconds;
		  if (minutes < 10)
		    minutesD = "0" + minutes;
		  if (hours < 10)
		    hoursD = "0" + hours;
	
		  output = hoursD + ":" + minutesD + ":" + secondsD;
		  
		  return output;
	}
    
    public static void copyfile(String srFile, String dtFile) {
		try {
			File f1 = new File(srFile);
			File f2 = new File(dtFile);
			InputStream in = new FileInputStream(f1);

			// For Append the file.
			// OutputStream out = new FileOutputStream(f2,true);

			// For Overwrite the file.
			OutputStream out = new FileOutputStream(f2);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
			System.out.println("File copied.");
		} catch (FileNotFoundException ex) {
			System.out
					.println(ex.getMessage() + " in the specified directory.");
			System.exit(0);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}
    
}

