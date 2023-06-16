package org.rifluxyss.javadev.harddrivescanner.commons.gui.tablemodels;

import java.text.NumberFormat;
import javax.swing.table.AbstractTableModel;

import org.rifluxyss.javadev.harddrivescanner.commons.SpyJLogger;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJUtilities;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.helper.DataWithIcon;

public class FileTypeTableModel extends AbstractTableModel {

	Object[][] filesType = SpyJUtilities.FILES_TYPE_COUNT;
	
	Object[][] data;
	
	Object columnNames[] = {"File Type"," File Count"};
	
	int _categoryIndex;
	
	long KILOBYTE = 1024L;
	
	Object ColumnClasses[] = {DataWithIcon.class,Integer.class};
	
	protected static NumberFormat formatter = NumberFormat.getInstance();
	
	  protected static final int COLUMN_COUNT = 2;
	  
	  public FileTypeTableModel(int _categoryIndex) {
		  SpyJLogger.debug("Category Index in Model =>" + _categoryIndex);
		  this._categoryIndex = _categoryIndex;
		  formatter.setMinimumIntegerDigits(2);
		  formatter.setMaximumFractionDigits(2);
		  formatter.setMinimumFractionDigits(2);
		  
		  data = new Object[filesType[_categoryIndex].length][2];
		  
		  String[] extensions = SpyJUtilities.getExtensionsByCategory(_categoryIndex);
		  
		  for (int i = 0; i < filesType[_categoryIndex].length; i++) {
			  data[i][0] = new DataWithIcon(extensions[i].toUpperCase(), SpyJUtilities.getExtensionIcon(extensions[i]));
			  data[i][1] = new Integer((Integer) filesType[_categoryIndex][i]);
		    }
	  }

	  // Implementation of TableModel interface
	  @Override
	public int getRowCount() {
	    return filesType[_categoryIndex].length;
	  }

	  @Override
	public int getColumnCount() {
	    return COLUMN_COUNT;
	  }

	  @Override
	public Object getValueAt(int row, int column) {
		  //Utilities.debug("row=>" + row + ", column" + column);
		  return data[row][column];
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