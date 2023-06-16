package org.rifluxyss.javadev.harddrivescanner.commons.gui.tablemodels;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.filechooser.FileSystemView;
import javax.swing.table.AbstractTableModel;

import org.rifluxyss.javadev.harddrivescanner.commons.SpyJGlobals;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJIcons;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJLogger;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJUtilities;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.helper.DataWithIcon;

public class DriveTableModel extends AbstractTableModel {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  protected String[] columnNames = {" ", "Drive", "Type", "Total Size", "Free Space"};
	
  protected static final int COLUMN_COUNT = 5;

  protected static final Class thisClass = DriveTableModel.class;
  
  protected static NumberFormat formatter = NumberFormat.getInstance();
  
  long GIGABYTE = 1024L * 1024L * 1024;

  File[] roots = File.listRoots();
  
  ArrayList<String> Drives = new ArrayList<String>();
  
  protected Object[][] data  = new Object[roots.length][5];
  
  // Constructor: calculate currency change to create the last column
  
  public DriveTableModel() {
	  
	 formatter.setMinimumIntegerDigits(2);
     formatter.setMaximumFractionDigits(2);
     formatter.setMinimumFractionDigits(2);
	 
	 for (int i = 0; i < roots.length; i++) {
		
		String driveName = FileSystemView.getFileSystemView().getSystemDisplayName(roots[i]);
		
		String drive = roots[i].toString();
		drive = drive.replaceAll(":\\\\", "");
		Drives.add(drive);
		
		data[i][0] = new Boolean (false);
		data[i][1] = new DataWithIcon(driveName, FileSystemView.getFileSystemView().getSystemIcon(roots[i]));
		data[i][2] = new String(FileSystemView.getFileSystemView().getSystemTypeDescription(roots[i]));
		data[i][3] = new String (formatter.format((double) roots[i].getTotalSpace()/GIGABYTE) + " GB");
		data[i][4] = new String (formatter.format((double) roots[i].getFreeSpace()/GIGABYTE) + " GB");
		
    }
  }

  // Implementation of TableModel interface
  @Override
  public int getRowCount() {
    return data.length;
  }

  @Override
  public int getColumnCount() {
    return COLUMN_COUNT;
  }

  @Override
  public Object getValueAt(int row, int column) {
    return data[row][column];
  }
  
  @Override
  public boolean isCellEditable(int row, int col)  {  
	  if(Boolean.parseBoolean(getValueAt(row, 0).toString())){
		  setValueAt(false, row, 0);
		  SpyJUtilities.ScanDrives.remove(SpyJUtilities.ScanDrives.indexOf(Drives.get(row)));
	  }else{
		  setValueAt(true, row, 0);
		  SpyJUtilities.ScanDrives.add(Drives.get(row));
	  }
	  return false;
  }
  
  /*
   * Don't need to implement this method unless your table's
   * data can change.
   */
  @Override
  public void setValueAt(Object value, int row, int col) {	  
      data[row][col] = value;
      fireTableCellUpdated(row, col);
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
    return columnNames[column];
  }
}