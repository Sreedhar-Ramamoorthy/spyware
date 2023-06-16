package org.rifluxyss.javadev.harddrivescanner.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.rifluxyss.javadev.harddrivescanner.commons.SpyJGlobals;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJIcons;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJUtilities;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.custom.SpyJButton;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.custom.SpyJLabel;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.custom.SpyJSpacer;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.custom.SpyJTextField;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.listeners.ClickListener;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.listeners.FileSelectionListener;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.listeners.RowListener;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.tablemodels.BasicFileListTableModel;

public class BasicFileListPanel extends JPanel{

    static int lastSelectedRow = -1;
    static FileSelectionListener selectionListener; 
	public BasicFileListPanel(JPanel _basicListPanel, int _categoryIndex){
		
		setLayout(null);
		setBackground(Color.WHITE);
		
		ClickListener _clickListener = new ClickListener(_basicListPanel, _categoryIndex);
		
		SpyJButton _searchIcon = new SpyJButton(SpyJIcons.SEARCH,null,null);
		_searchIcon.setBounds(645, 5, 22, 22);
		_searchIcon.setToolTipText(SpyJUtilities.setCustomToolTipText("Enter Search Keyword!"));
    	add(_searchIcon);
		
		JTextField _searchField = new SpyJTextField();
		_searchField.setBounds(675, 5, 120, 18);
		_searchField.setBorder(BorderFactory.createLineBorder(Color.white, 0));
		_searchField.setToolTipText(SpyJUtilities.setCustomToolTipText("Enter Search Keyword!"));
    	add(_searchField);
		
		SpyJButton _backButton = new SpyJButton(SpyJIcons.BACK_SMALL,null,null);
    	_backButton.setActionCommand("BACK_CATEGORY");
    	_backButton.setBounds(835, 5, 22, 22);
    	_backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    	_backButton.setToolTipText(SpyJUtilities.setCustomToolTipText("Go Back"));
    	_backButton.addActionListener(_clickListener);
    	add(_backButton);
    	
    	SpyJButton _refreshButton = new SpyJButton(SpyJIcons.REFRESH,null,null);
    	_refreshButton.setActionCommand("REFRESH_FILE_LIST");
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
		
		TableModel fileListTableModel = new BasicFileListTableModel(_categoryIndex);
		JTable fileListTable = new JTable(fileListTableModel);
		RowSorter<TableModel> rowSorter = new TableRowSorter<TableModel>(fileListTableModel);
		
		fileListTable.setRowHeight(26);
		fileListTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		fileListTable.setGridColor(Color.WHITE);
		
		 final TableCellRenderer tcrFilelist = fileListTable.getTableHeader().getDefaultRenderer();
		 fileListTable.getTableHeader().setDefaultRenderer(new TableCellRenderer() {
			 
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
	    
		final TableColumnModel fileListColumnModel = fileListTable.getColumnModel();

		DefaultTableCellRenderer alignRendererNew = new DefaultTableCellRenderer();
		fileListColumnModel.getColumn(0).setCellRenderer(alignRendererNew);
		final TableCellRenderer fileNameCellRenderer = fileListColumnModel.getColumn(0).getCellRenderer();
		fileListColumnModel.getColumn(0).setCellRenderer(new TableCellRenderer() {
            @Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) fileNameCellRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setFont(SpyJGlobals.SansPlain12);
                lbl.setBorder(BorderFactory.createCompoundBorder(lbl.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
                lbl.setHorizontalAlignment(SwingConstants.LEFT);
                return lbl;
            }
        });
		fileListColumnModel.getColumn(0).setPreferredWidth(150);
		fileListColumnModel.getColumn(1).setPreferredWidth(400);
		fileListColumnModel.getColumn(2).setPreferredWidth(50);
		
		fileListTable.setColumnSelectionAllowed(false);
		ListSelectionModel rowSelectionModel = fileListTable.getSelectionModel();
		selectionListener = new FileSelectionListener(_basicListPanel, fileListTable, fileListTableModel, rowSorter);
		rowSelectionModel.addListSelectionListener(selectionListener);
		
		fileListColumnModel.getColumn(1).setCellRenderer(alignRendererNew);
		fileListColumnModel.getColumn(2).setCellRenderer(alignRendererNew);
		
		final TableCellRenderer filePathCellRenderer = fileListColumnModel.getColumn(1).getCellRenderer();
		fileListColumnModel.getColumn(1).setCellRenderer(new TableCellRenderer() {
            @Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) filePathCellRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setFont(SpyJGlobals.SansPlain12);
                lbl.setBorder(BorderFactory.createCompoundBorder(lbl.getBorder(), BorderFactory.createEmptyBorder(0, 10, 0, 0)));
                lbl.setHorizontalAlignment(SwingConstants.LEFT);
                return lbl;
            }
        });
		
		final TableCellRenderer fileSizeCellRenderer = fileListColumnModel.getColumn(2).getCellRenderer();
		fileListColumnModel.getColumn(2).setCellRenderer(new TableCellRenderer() {
            @Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) fileSizeCellRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setFont(SpyJGlobals.SansPlain12);
                lbl.setBorder(BorderFactory.createCompoundBorder(lbl.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
                lbl.setHorizontalAlignment(SwingConstants.LEFT);
                return lbl;
            }
        });
	    
		fileListTable.setShowHorizontalLines(false);
		fileListTable.setIntercellSpacing(new Dimension(1, 0));
	    fileListTable.setSelectionBackground(SpyJGlobals.lightgray);
	    fileListTable.setFillsViewportHeight(true);
	    
	    setQuickSearch(fileListTable, _searchField);
	    
	    rowSorter.addRowSorterListener(new RowListener(fileListTable));
	    fileListTable.setRowSorter(rowSorter);
	    
		 // Add the stripe renderer.
		JScrollPane fileListScrollPane = new JScrollPane(fileListTable);
		
		fileListScrollPane.setBounds(0, 40, 870, 250);
		add(fileListScrollPane);
	}
	
	public static void setQuickSearch(final JTable table, final JTextField searchField) {
//	    table.putClientProperty("JTable.autoStartsEdit", Boolean.FALSE);
	    
	    class Search {
	 
	        void search() {
	            table.clearSelection();
	            SpyJGlobals.canOpenFile = false;
	            String text = searchField.getText();
	            if (text.length() == 0) {
	                return;
	            }
	            for (int row = 0; row < table.getRowCount(); row++) {
	                Object val = table.getValueAt(row, 0);
	                String value = val != null ? val.toString() : "";
	                if (value.toLowerCase().startsWith(text.toLowerCase())) {
	                    table.changeSelection(row, 0, false, false);
	                    
	                    break;
	                }
	            }
	        }
	    }
	    table.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(SpyJGlobals.isThroughKeyPress){
					table.changeSelection(lastSelectedRow, 0, false, false);
//					SpyJLogger.debug("Inside mouseClicked()..." + lastSelectedRow);  
					ListSelectionEvent e = new ListSelectionEvent(table, lastSelectedRow, lastSelectedRow, true);
					lastSelectedRow = -1;
					SpyJGlobals.canOpenFile = true;
					selectionListener.valueChanged(e);
					SpyJGlobals.isThroughKeyPress = false;
				}
			}
		});
	    
	    searchField.addKeyListener(new KeyAdapter() {
	 
	        @Override
	        public void keyPressed(final KeyEvent evt) {
	        	SpyJGlobals.canOpenFile = false;
//	        	SpyJLogger.debug("keyPressed");
	            char ch = evt.getKeyChar();
	            if (!Character.isLetterOrDigit(ch)) {
	                return;
	            }
	            int selectedRow = table.getSelectedRow();
	            updateLastSelected(selectedRow);
				SpyJGlobals.isThroughKeyPress = true;
	            int selectedColumn = table.getSelectedColumn();
	            Object clientProperty = table.getClientProperty("JTable.autoStartsEdit");
	            if ((clientProperty == null || (Boolean)clientProperty)  &&
	                    selectedRow >= 0 && selectedColumn >= 0 &&
	                    table.isCellEditable(table.getSelectedRow(), table.getSelectedColumn())) {
	                return;
	            }
	            
	           //searchField.setText(String.valueOf(ch));
	            final Search s = new Search();
	            s.search();
	            
	            searchField.getDocument().addDocumentListener(new DocumentListener() {
	 
	                @Override
					public void insertUpdate(final DocumentEvent e) {
	                    s.search();
	                }
	 
	                @Override
					public void removeUpdate(final DocumentEvent e) {
	                    s.search();
	                }
	 
	                @Override
					public void changedUpdate(final DocumentEvent e) {
	                    s.search();
	                }
	            });
	            
	            searchField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	                    KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "exit");
	        }
	    });
	}
	static void updateLastSelected(int selectedRow){
		lastSelectedRow = selectedRow;
	}
}
