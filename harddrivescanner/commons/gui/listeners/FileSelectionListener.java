package org.rifluxyss.javadev.harddrivescanner.commons.gui.listeners;

import java.awt.Desktop;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.rifluxyss.javadev.harddrivescanner.commons.SpyJGlobals;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJLogger;

public class FileSelectionListener implements ListSelectionListener {
	TableModel _fileListModel;
	JPanel _basicListPanel;
	JTable _fileListTable;
	static int staticFileOpenCounter;
	RowSorter<TableModel> _rowSorter;
	
	public FileSelectionListener(JPanel _basicListPanel, JTable _fileListTable, TableModel _fileListModel, RowSorter<TableModel> rowSorter) {
		this._fileListModel = _fileListModel;
		this._basicListPanel = _basicListPanel;
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
					
					String cmd = SpyJGlobals.WIN_PATH + " " + SpyJGlobals.WIN_FLAG + " " + new File((String) _fileListModel.getValueAt(_rowSorter.convertRowIndexToModel(selectedRow[0]), 1));
					Runtime.getRuntime().exec(cmd);
					//SpyJLogger.debug(cmd);

					//Desktop.getDesktop().open( new File((String) _fileListModel.getValueAt(_rowSorter.convertRowIndexToModel(selectedRow[0]), 1)));
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