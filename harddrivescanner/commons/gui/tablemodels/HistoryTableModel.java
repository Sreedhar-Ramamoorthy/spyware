package org.rifluxyss.javadev.harddrivescanner.commons.gui.tablemodels;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.table.AbstractTableModel;

public class HistoryTableModel extends AbstractTableModel {

	Object columnNames[] = {"URL"," Accessed Date"};
	
	public ArrayList<String> HistoryURL = null;
	public ArrayList<Date> LastVisitedDate = null;
	
	Object ColumnClasses[] = {String.class, Date.class};
	
	protected static final int COLUMN_COUNT = 2;
	
	public HistoryTableModel(ArrayList<String> HistoryURL, ArrayList<Date> LastVisitedDate) {
		 this.HistoryURL = HistoryURL;
		 this.LastVisitedDate = LastVisitedDate;
	}
	
	@Override
	public int getColumnCount() {
	   return COLUMN_COUNT;
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		 Object returnValue = null;
		 if(HistoryURL != null){
			if(column == 0)
				returnValue = HistoryURL.get(row);
			else
				returnValue = LastVisitedDate.get(row);
		}else{
				returnValue = "---";
		}
		//SpyJLogger.debug("returnValue=>"+returnValue);		  
		return returnValue;
	}
	  
	@Override
	public void setValueAt(Object value, int row, int column) {
		//SpyJLogger.debug("value=>"+value+" row=>"+row + " column=>" + column);
	    fireTableCellUpdated(row, column);
	}
	  
	@Override
	public boolean isCellEditable(int row, int col)
	{  
		return false;
	}
	  
	@Override
	public Class getColumnClass(int column) {
	    return (Class) ColumnClasses[column];
	}
	
	@Override
	public String getColumnName(int column) {
	    return (String) columnNames[column];
	}

	@Override
	public int getRowCount() {
		if(HistoryURL != null)
			return HistoryURL.size();
		else{
			return 0;
		}
	}
	
}