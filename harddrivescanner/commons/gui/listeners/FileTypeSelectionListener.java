package org.rifluxyss.javadev.harddrivescanner.commons.gui.listeners;

import java.awt.Container;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import org.rifluxyss.javadev.harddrivescanner.HardDriveSpy;
import org.rifluxyss.javadev.harddrivescanner.gui.AdvancedFileListPanel;

public class FileTypeSelectionListener implements ListSelectionListener {
	JTable _fileTypeListTable;
	JPanel _basicListPanel;
	static int staticFileOpenCounter;
	Container _rootPane;
	int _categoryIndex, _extensionIndex;
	RowSorter<TableModel> _rowSorter;
	
	
	public FileTypeSelectionListener(JPanel _basicListPanel, int _categoryIndex, JTable _fileTypeListTable, RowSorter<TableModel> rowSorter) {
		this._basicListPanel = _basicListPanel;
		this._fileTypeListTable = _fileTypeListTable;
		this._rootPane = HardDriveSpy.getRootContentPane();
		this._categoryIndex = _categoryIndex;
		_rowSorter = rowSorter;
	 }
	
	  @Override
	public void valueChanged(ListSelectionEvent e) {
		staticFileOpenCounter++;
		int[] selectedRow = _fileTypeListTable.getSelectedRows();
		if(staticFileOpenCounter == 2){
			staticFileOpenCounter=0;
			_extensionIndex = _rowSorter.convertRowIndexToModel(selectedRow[0]);
			_basicListPanel.removeAll();
			JPanel fileTypePanel = new AdvancedFileListPanel(_basicListPanel, _categoryIndex, _extensionIndex);
			fileTypePanel.setBounds(0, 0, 870, 300);
			_basicListPanel.add(fileTypePanel);
			this._rootPane.paintAll(this._rootPane.getGraphics());
		}
	  }
 }