package org.rifluxyss.javadev.harddrivescanner.commons.gui.tablemodels;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

import org.rifluxyss.javadev.harddrivescanner.commons.SpyJLogger;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJUtilities;

public class AdvancedFileListTableModel extends AbstractTableModel {

	ArrayList[][] filesList = SpyJUtilities.FILES_TYPE_LIST;
	
	Object columnNames[] = {"File Name"," File Path", "Date Modified",  "Size"};
	int _categoryIndex, _extensionIndex;
	
	long KILOBYTE = 1024L;
	
	Class ColumnClasses[] = new Class[] {String.class, String.class, Date.class, Integer.class};
	
	protected static NumberFormat formatter = NumberFormat.getInstance();
	
	protected static final int COLUMN_COUNT = 4;
	  
	public AdvancedFileListTableModel(int _categoryIndex, int _extensionIndex) {
		SpyJLogger.debug("Category Index in Model =>" + _categoryIndex);
		this._categoryIndex = _categoryIndex;
		this._extensionIndex = _extensionIndex;
		  
		SpyJLogger.debug("Row Size=>"+filesList[_categoryIndex][_extensionIndex].size());
		 
		formatter.setMinimumIntegerDigits(2);
		formatter.setMaximumFractionDigits(2);
		formatter.setMinimumFractionDigits(2);
	}

	  // Implementation of TableModel interface
	  @Override
	public int getRowCount() {
	    return filesList[_categoryIndex][_extensionIndex].size();
	  }

	  @Override
	public int getColumnCount() {
	    return COLUMN_COUNT;
	  }

	  @Override
	public Object getValueAt(int row, int column) {
		  //Utilities.debug("row=>" + row + ", column" + column);
		  Object returnValue = "";
		  switch (column){
		  	case 0:
		  		returnValue = ((File) filesList[_categoryIndex][_extensionIndex].get(row)).getName();
		  		break;
		  	case 1:
		  		returnValue = ((File) filesList[_categoryIndex][_extensionIndex].get(row)).getAbsolutePath();
		  		break;
		  	case 2:
		  		returnValue = new Date(((File) filesList[_categoryIndex][_extensionIndex].get(row)).lastModified());
		  		break;
		  	case 3:
		  		returnValue = (formatter.format((double)((File) filesList[_categoryIndex][_extensionIndex].get(row)).length()/KILOBYTE)) + " KB";
		  		break;
		  }
		  return returnValue;
	  }
	  
	  @Override
	  public void setValueAt(Object value, int row, int column) {
		  
	  }
	  
	  @Override
	  public boolean isCellEditable(int row, int col)
	  {  
		  return false;
	  }
	  
	  @Override
	public Class getColumnClass(int column) {
		  return ColumnClasses[column];
	  }

	  @Override
	public String getColumnName(int column) {
	    return (String) columnNames[column];
	  }	  
	  
}