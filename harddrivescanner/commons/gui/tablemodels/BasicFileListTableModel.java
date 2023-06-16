package org.rifluxyss.javadev.harddrivescanner.commons.gui.tablemodels;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

import org.rifluxyss.javadev.harddrivescanner.commons.SpyJLogger;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJUtilities;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.helper.DataWithIcon;

public class BasicFileListTableModel extends AbstractTableModel {

	ArrayList[] filesList = SpyJUtilities.FILES_LIST;
	
	Object columnNames[] = {"File Name"," File Path", "Size"};
	int _categoryIndex;
	
	long KILOBYTE = 1024L;
	
	Object ColumnClasses[] = {DataWithIcon.class,Integer.class};
	
	protected static NumberFormat formatter = NumberFormat.getInstance();
	
	  protected static final int COLUMN_COUNT = 3;
	  
	  public BasicFileListTableModel(int _categoryIndex) {
		  SpyJLogger.debug("Category Index in Model =>" + _categoryIndex);
		  this._categoryIndex = _categoryIndex;
		  SpyJLogger.debug("Row Size=>"+filesList[_categoryIndex].size());
		  
		  formatter.setMinimumIntegerDigits(2);
		  formatter.setMaximumFractionDigits(2);
		  formatter.setMinimumFractionDigits(2);
		  
	  }

	  // Implementation of TableModel interface
	  @Override
	public int getRowCount() {
	    return filesList[_categoryIndex].size();
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
		  		returnValue = ((File)filesList[_categoryIndex].get(row)).getName();
		  		break;
		  	case 1:
		  		returnValue = ((File)filesList[_categoryIndex].get(row)).getAbsolutePath();
		  		break;
		  	case 2:
		  		returnValue = formatter.format((double)((File)filesList[_categoryIndex].get(row)).length()/KILOBYTE) + " KB";
		  		break;
		  }
		  return returnValue;
	  }
	  
	  @Override
	  public void setValueAt(Object value, int row, int column) {
		  switch (column){
		  	case 0:
		  		((File)filesList[_categoryIndex].get(row)).getName();
		  	case 1:
		  		((File)filesList[_categoryIndex].get(row)).getAbsolutePath();
		  	case 2:
		  		((File)filesList[_categoryIndex].get(row)).length();
		  }
	      fireTableCellUpdated(row, column);
	  }
	  
	  @Override
	  public boolean isCellEditable(int row, int col)
	  {  
		  return false;
	  }
	  
	  @Override
	public Class getColumnClass(int column) {
		Class returnValue;
        if ((column >= 0) && (column < getColumnCount())) {
          returnValue = getValueAt(0, column).getClass();
        } else {
          returnValue = Object.class;
        }
        return returnValue;
	  }

	  @Override
	public String getColumnName(int column) {
	    return (String) columnNames[column];
	  }
}