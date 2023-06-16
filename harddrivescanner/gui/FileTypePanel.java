package org.rifluxyss.javadev.harddrivescanner.gui;

import java.awt.Color;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJIcons;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJUtilities;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.cellrenderers.TextWithIconCellRenderer;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.custom.SpyJButton;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.custom.SpyJLabel;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.custom.SpyJSpacer;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.listeners.ClickListener;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.listeners.FileTypeSelectionListener;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.tablemodels.FileTypeTableModel;

public class FileTypePanel extends JPanel{
	
	public FileTypePanel(JPanel _basicListPanel, int _categoryIndex){
		
		setLayout(null);
		setBackground(Color.WHITE);
		
		ClickListener _clickListener = new ClickListener(_basicListPanel, _categoryIndex);
		
		SpyJButton _backButton = new SpyJButton(SpyJIcons.BACK_SMALL,null,null);
    	_backButton.setActionCommand("BACK_CATEGORY");
    	_backButton.setBounds(835, 5, 22, 22);
    	_backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    	_backButton.setToolTipText(SpyJUtilities.setCustomToolTipText("Go Back"));
    	_backButton.addActionListener(_clickListener);
    	add(_backButton);
    	
    	SpyJButton _refreshButton = new SpyJButton(SpyJIcons.REFRESH,null,null);
    	_refreshButton.setActionCommand("REFRESH_FILE_TYPE");
    	_refreshButton.setBounds(805, 5, 22, 22);
    	_refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    	_refreshButton.setToolTipText(SpyJUtilities.setCustomToolTipText("Refresh"));
    	_refreshButton.addActionListener(_clickListener);
    	add(_refreshButton);
    	
    	SpyJLabel _categoryTitleLabel = new SpyJLabel(SpyJUtilities.getCategoryName(_categoryIndex));
    	_categoryTitleLabel.setBounds(10, 0, 200, 30);
    	_categoryTitleLabel.setFont(SpyJGlobals.TitleFont14);
    	_categoryTitleLabel.setForeground(Color.white);
    	_categoryTitleLabel.setVisible(true);
    	add(_categoryTitleLabel);
		
		JPanel _fileListTopSpacer = new SpyJSpacer(0, 0, 30, 870);
		_fileListTopSpacer.setBackground(Color.DARK_GRAY);
		add(_fileListTopSpacer);
		
		TableModel fileTypeListTableModel = new FileTypeTableModel(_categoryIndex);
		JTable fileTypeListTable = new JTable(fileTypeListTableModel);
	    RowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(fileTypeListTableModel);
		
		fileTypeListTable.setRowHeight(26);
		fileTypeListTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		fileTypeListTable.setGridColor(Color.WHITE);
		
		 final TableCellRenderer tcrFilelist = fileTypeListTable.getTableHeader().getDefaultRenderer();
		 fileTypeListTable.getTableHeader().setDefaultRenderer(new TableCellRenderer() {
			 
            @Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) tcrFilelist.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setFont(SpyJGlobals.SansBold12);
                lbl.setBorder(BorderFactory.createCompoundBorder(lbl.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
                lbl.setHorizontalAlignment(SwingConstants.LEFT);
                return lbl;
            }
        });
	    
		/*
		JTable table = new JTable(rows, columns) {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}

			public Component prepareRenderer(TableCellRenderer renderer,
					int Index_row, int Index_col) {
				Component comp = super.prepareRenderer(renderer, Index_row,
						Index_col);
				if (Index_row % 2 == 0
						&& !isCellSelected(Index_row, Index_col)) {
					comp.setBackground(new Color(236, 236, 236));
				} else {
					comp.setBackground(new Color(246, 246, 246));
				}
				return comp;
			}
		};
		*/			
	    
		final TableColumnModel fileTypeColumnModel = fileTypeListTable.getColumnModel();
		
		TextWithIconCellRenderer renderer = new TextWithIconCellRenderer();
		fileTypeColumnModel.getColumn(0).setCellRenderer(renderer);
		
		ListSelectionModel rowSelectionModel = fileTypeListTable.getSelectionModel();
		rowSelectionModel.addListSelectionListener(new FileTypeSelectionListener(_basicListPanel, _categoryIndex, fileTypeListTable, rowSorter));
		
		DefaultTableCellRenderer alignRendererNew = new DefaultTableCellRenderer();
		fileTypeColumnModel.getColumn(1).setCellRenderer(alignRendererNew);
		
		final TableCellRenderer filePathCellRenderer = fileTypeColumnModel.getColumn(1).getCellRenderer();
		fileTypeColumnModel.getColumn(1).setCellRenderer(new TableCellRenderer() {
            @Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) filePathCellRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setFont(SpyJGlobals.SansPlain12);
                lbl.setBorder(BorderFactory.createCompoundBorder(lbl.getBorder(), BorderFactory.createEmptyBorder(0, 10, 0, 0)));
                lbl.setHorizontalAlignment(SwingConstants.LEFT);
                return lbl;
            }
        });
		
		fileTypeListTable.setShowHorizontalLines(false);
		fileTypeListTable.setIntercellSpacing(new Dimension(1, 0));
		fileTypeListTable.setSelectionBackground(SpyJGlobals.lightgray);
		fileTypeListTable.setFillsViewportHeight(true);
	    
	    fileTypeListTable.setRowSorter(rowSorter);
	    
		 // Add the stripe renderer.
		JScrollPane fileListScrollPane = new JScrollPane(fileTypeListTable);
		
		fileListScrollPane.setBounds(0, 40, 870, 250);
		add(fileListScrollPane);
	}
}
