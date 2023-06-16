package org.rifluxyss.javadev.harddrivescanner.commons.gui.listeners;

import javax.swing.JTable;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterListener;

public class RowListener implements RowSorterListener {
	
	JTable _sourceTable;		
	public RowListener(JTable sourceTable){
		_sourceTable = sourceTable;
	}
	
	@Override
	public void sorterChanged(RowSorterEvent e) {
		_sourceTable.clearSelection();
	}
	
}
