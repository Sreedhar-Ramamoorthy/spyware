package org.rifluxyss.javadev.harddrivescanner.commons.gui.listeners;

import java.awt.Container;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import org.rifluxyss.javadev.harddrivescanner.HardDriveSpy;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJGlobals;
import org.rifluxyss.javadev.harddrivescanner.gui.BasicFileListPanel;
import org.rifluxyss.javadev.harddrivescanner.gui.FileTypePanel;

public class CategorySelectionListener implements ListSelectionListener {
	JTable _categoryListTable;
	JPanel _basicListPanel;
	static int staticCounter = 0;
	Container _rootPane;
	RowSorter<TableModel> _rowSorter;
	
	public CategorySelectionListener(JPanel _basicListPanel, JTable _categoryListTable, RowSorter<TableModel> rowSorter) {
	    this._categoryListTable = _categoryListTable;
	    this._basicListPanel = _basicListPanel;
	    this._rootPane = HardDriveSpy.getRootContentPane();
	    this._rowSorter = rowSorter;
	  }
	
	  @Override
	public void valueChanged(ListSelectionEvent e) {
		staticCounter++;
		int[] selectedRow = _categoryListTable.getSelectedRows();
		if(staticCounter == 2){
			hideCategoryPanel(_basicListPanel, _rowSorter.convertRowIndexToModel(selectedRow[0]));
			staticCounter=0;
		}
	  }
	  
	  public void hideCategoryPanel(JPanel _basicListPanel, int _categoryIndex){
		  if(SpyJGlobals.SCAN_TYPE.equals("ADVANCED")){
			  _basicListPanel.removeAll();
			  JPanel fileTypePanel = new FileTypePanel(_basicListPanel, _categoryIndex);
			  fileTypePanel.setBounds(0, 0, 870, 300);
			  _basicListPanel.add(fileTypePanel);
			  this._rootPane.paintAll(this._rootPane.getGraphics());
		  }else{
			  _basicListPanel.removeAll();
			  JPanel fileListPanel = new BasicFileListPanel(_basicListPanel, _categoryIndex);
			  fileListPanel.setBounds(0, 0, 870, 300);
			  _basicListPanel.add(fileListPanel);
			  this._rootPane.paintAll(this._rootPane.getGraphics());
		  }
	}
 }