package org.rifluxyss.javadev.harddrivescanner.commons.gui.custom;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.rifluxyss.javadev.harddrivescanner.commons.SpyJGlobals;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.listeners.RowListener;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.listeners.URISelectionListener;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.tablemodels.HistoryTableModel;

public class SpyJHistoryTable extends JTable{
	
	public SpyJHistoryTable(HistoryTableModel historyTableModel){
		super(historyTableModel);
		
		RowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(historyTableModel);
		
		setBackground(Color.white);
		
		setRowHeight(26);
		setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		setGridColor(Color.WHITE);
		
		final TableCellRenderer tcr = getTableHeader().getDefaultRenderer();
		getTableHeader().setDefaultRenderer(new TableCellRenderer() {
	 
	        @Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	            JLabel lbl = (JLabel) tcr.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	            lbl.setFont(SpyJGlobals.SansBold12);
	            lbl.setBorder(BorderFactory.createCompoundBorder(lbl.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
	            lbl.setHorizontalAlignment(SwingConstants.LEFT);
	            return lbl;
	        }
	    });
	    
		final TableColumnModel columnModel = getColumnModel();
		
		ListSelectionModel cellSelectionModel = getSelectionModel();
		cellSelectionModel.addListSelectionListener(new URISelectionListener(this, historyTableModel, rowSorter));
	
		DefaultTableCellRenderer alignRenderer = new DefaultTableCellRenderer();
		columnModel.getColumn(0).setCellRenderer(alignRenderer);
		
		final TableCellRenderer urlCellRenderer = columnModel.getColumn(0).getCellRenderer();
		columnModel.getColumn(0).setCellRenderer(new TableCellRenderer() {
	        @Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	            JLabel lbl = (JLabel) urlCellRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	            lbl.setFont(SpyJGlobals.SansPlain12);
	            lbl.setBorder(BorderFactory.createCompoundBorder(lbl.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
	            lbl.setHorizontalAlignment(SwingConstants.LEFT);
	            return lbl;
	        }
	    });
	    
	    columnModel.getColumn(1).setCellRenderer(alignRenderer);
		
		final TableCellRenderer accessDateTimeCellRenderer = columnModel.getColumn(1).getCellRenderer();
		columnModel.getColumn(1).setCellRenderer(new TableCellRenderer() {
	        @Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	            JLabel lbl = (JLabel) accessDateTimeCellRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	            lbl.setFont(SpyJGlobals.SansPlain12);
	            lbl.setBorder(BorderFactory.createCompoundBorder(lbl.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
	            lbl.setHorizontalAlignment(SwingConstants.LEFT);
	            return lbl;
	        }
	    });
		
		columnModel.getColumn(0).setPreferredWidth(490);
		columnModel.getColumn(1).setPreferredWidth(150);
	    
		setShowHorizontalLines(false);
		setIntercellSpacing(new Dimension(1, 0));
		setSelectionBackground(SpyJGlobals.lightgray);
		setFillsViewportHeight(true);	    
	    
	    rowSorter.addRowSorterListener(new RowListener(this));
	    setRowSorter(rowSorter);
	}
}
