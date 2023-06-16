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
import org.rifluxyss.javadev.harddrivescanner.commons.gui.custom.SpyJSpacer;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.listeners.CategorySelectionListener;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.listeners.ClickListener;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.tablemodels.CategoryTableModel;

public class CategoryListPanel extends JPanel {
	
	public CategoryListPanel(JPanel _basicListPanel) {
		
		setLayout(null);
		setBackground(Color.WHITE);
		
		ClickListener _clickListener = new ClickListener(_basicListPanel);
		
		SpyJButton _refreshButton = new SpyJButton(SpyJIcons.REFRESH,null,null);
    	_refreshButton.setActionCommand("BACK_CATEGORY");
    	_refreshButton.setBounds(835, 5, 22, 22);
    	_refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    	_refreshButton.setToolTipText(SpyJUtilities.setCustomToolTipText("Refresh"));
    	_refreshButton.addActionListener(_clickListener);
    	add(_refreshButton);
		
		JPanel _topSpacer = new SpyJSpacer(0, 0, 30, 870);
		_topSpacer.setBackground(Color.DARK_GRAY);
		add(_topSpacer);
		
		CategoryTableModel categoryTableModel = SpyJUtilities.getCategoryTableModel();		
		JTable _categoryListtable = new JTable(categoryTableModel);
		RowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(categoryTableModel);
		
		_categoryListtable.setRowHeight(26);
		_categoryListtable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		_categoryListtable.setGridColor(Color.WHITE);
		
		 final TableCellRenderer tcr = _categoryListtable.getTableHeader().getDefaultRenderer();
		 _categoryListtable.getTableHeader().setDefaultRenderer(new TableCellRenderer() {
	 
            @Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) tcr.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
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
	    
		final TableColumnModel columnModel = _categoryListtable.getColumnModel();

		TextWithIconCellRenderer renderer = new TextWithIconCellRenderer();
		columnModel.getColumn(0).setCellRenderer(renderer);
		
	    ListSelectionModel cellSelectionModel = _categoryListtable.getSelectionModel();
	    cellSelectionModel.addListSelectionListener(new CategorySelectionListener(_basicListPanel, _categoryListtable, rowSorter));

		DefaultTableCellRenderer alignRenderer = new DefaultTableCellRenderer();
	    columnModel.getColumn(1).setCellRenderer(alignRenderer);
		
		final TableCellRenderer gettingCellRenderer = columnModel.getColumn(1).getCellRenderer();
		columnModel.getColumn(1).setCellRenderer(new TableCellRenderer() {
            @Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) gettingCellRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setFont(SpyJGlobals.SansPlain12);
                lbl.setBorder(BorderFactory.createCompoundBorder(lbl.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
                lbl.setHorizontalAlignment(SwingConstants.LEFT);
                return lbl;
            }
        });
	    
		_categoryListtable.setShowHorizontalLines(false);
		_categoryListtable.setIntercellSpacing(new Dimension(1, 0));
		_categoryListtable.setSelectionBackground(SpyJGlobals.lightgray);
		_categoryListtable.setFillsViewportHeight(true);
	    
	    
	    _categoryListtable.setRowSorter(rowSorter);

		JScrollPane categoryListPane;
		 // Add the stripe renderer.
	    categoryListPane = new JScrollPane(_categoryListtable);

	    categoryListPane.setBounds(0, 40, 870, 250);
		add(categoryListPane);			
	}
}