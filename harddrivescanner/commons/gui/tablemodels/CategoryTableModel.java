package org.rifluxyss.javadev.harddrivescanner.commons.gui.tablemodels;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import org.rifluxyss.javadev.harddrivescanner.commons.SpyJUtilities;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.helper.DataWithIcon;

public class CategoryTableModel extends AbstractTableModel {

	Object rows[] = SpyJUtilities.FILES_LIST;

	Object columnNames[] = {"File Type"," File Count"};
	
	Object ColumnClasses[] = {DataWithIcon.class,Integer.class};
	
	  protected static final int COLUMN_COUNT = 2;
	  
	  public CategoryTableModel() {
		 
	  }

	  // Implementation of TableModel interface
	  @Override
	public int getRowCount() {
	    return rows.length;
	  }

	  @Override
	public int getColumnCount() {
	    return COLUMN_COUNT;
	  }

	  @Override
	public Object getValueAt(int row, int column) {
		  Object returnValue = "";
		  switch (column){
		  	case 0:
		  		returnValue = SpyJUtilities.getFileTypes(row);
		  		break;
		  	case 1:
		  		returnValue = ((ArrayList) rows[row]).size();
		  		break;
		  }
		  return returnValue;
	  }
	  
	  @Override
	  public void setValueAt(Object value, int row, int column) {
		  //rows[row][column] = value;
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