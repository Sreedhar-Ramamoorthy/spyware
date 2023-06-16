package org.rifluxyss.javadev.harddrivescanner.commons.gui.listeners;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;

import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import org.rifluxyss.javadev.harddrivescanner.commons.SpyJGlobals;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJLogger;

public class URISelectionListener implements ListSelectionListener {
	TableModel _fileListModel;
	JTable _fileListTable;
	static int staticFileOpenCounter;
	RowSorter<TableModel> _rowSorter;
	
	public URISelectionListener(JTable _fileListTable, TableModel _fileListModel, RowSorter<TableModel> rowSorter) {
		this._fileListModel = _fileListModel;
		this._fileListTable = _fileListTable;
		this._rowSorter = rowSorter;
	}

	public void valueChangedDmy(ListSelectionEvent e) {
		SpyJLogger.debug("Inside valueChanged()..." + e.getFirstIndex());
	}
	 
	@Override
	public void valueChanged(ListSelectionEvent e) {
//		SpyJLogger.debug("Inside valueChanged()...");  
		staticFileOpenCounter++;
		int[] selectedRow = _fileListTable.getSelectedRows();
		if(staticFileOpenCounter == 2 || SpyJGlobals.isThroughKeyPress){
			staticFileOpenCounter=0;
			SpyJLogger.info("Source=>" + e.getSource().toString());
			try
			{
				if (SpyJGlobals.canOpenFile){
					//Desktop.getDesktop().browse( new URI((String) _fileListModel.getValueAt(_rowSorter.convertRowIndexToModel(selectedRow[0]), 0)));
					String cmd = SpyJGlobals.WIN_PATH + " " + SpyJGlobals.WIN_FLAG + " " + new File((String) _fileListModel.getValueAt(_rowSorter.convertRowIndexToModel(selectedRow[0]), 0));
					Runtime.getRuntime().exec(cmd);
					//SpyJLogger.debug(cmd);
					//SpyJLogger.debug("called open");
                }
				SpyJGlobals.canOpenFile = true;
			}
			catch (Exception  e1)
			{
				// IOException, URISyntaxException
			  SpyJLogger.debug(e1.getMessage());
			}
			
		}
	  }
 }