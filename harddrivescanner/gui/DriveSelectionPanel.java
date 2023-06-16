package org.rifluxyss.javadev.harddrivescanner.gui;

/*
Core SWING Advanced Programming 
By Kim Topley
ISBN: 0 13 083292 8       
Publisher: Prentice Hall  
*/


import java.awt.Color;



import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
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
import org.rifluxyss.javadev.harddrivescanner.commons.gui.custom.SpyJHelpFrame;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.tablemodels.DriveTableModel;

public class DriveSelectionPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Container _rootPane;
	SpyJHelpFrame helpWindow = null;
	
	public DriveSelectionPanel(Container rootPane){
		
		new SpyJUtilities();
		
	    _rootPane = rootPane;
	    JPanel DriveSelectionPanel = DriveSelectionPanelComponents();
		_rootPane.add(wrapInBackgroundImage(DriveSelectionPanel,
				SpyJIcons.BACKGROUND));
		
	}
	
	/**
     * Wraps a Swing JComponent in a background image. Simply invokes the overloded
     * variant with Top/Leading alignment for background image.
     *
     * @param component - to wrap in the a background image
     * @param backgroundIcon - the background image (Icon)
     * @return the wrapping JPanel
     */
    public JPanel wrapInBackgroundImage(JComponent component,
            Icon backgroundIcon) {
        return SpyJUtilities.wrapInBackgroundImage(
                component,
                backgroundIcon,
                SwingConstants.TOP,
                SwingConstants.LEADING);
    }
	
	public JPanel DriveSelectionPanelComponents(){
		
		ClickListener _clickListener = new ClickListener();
		
		JPanel _driveSelectionPanelComponents = new JPanel(null);
		_driveSelectionPanelComponents.setBorder(
                BorderFactory.createEmptyBorder(10,10,10,10));
		_driveSelectionPanelComponents.setOpaque(false);
		
		JLabel _headerLabel = new JLabel("Please Select Drives to Scan",SpyJIcons.SCAN,SwingConstants.LEFT);
		_headerLabel.setLayout(null);
		_headerLabel.setBounds(35, 30, 400, 36);
		_headerLabel.setIconTextGap(10);    	
    	_headerLabel.setFont(SpyJGlobals.TitleFont18);  	
    	_driveSelectionPanelComponents.add(_headerLabel);
    	
    	SpyJButton _backButton = new SpyJButton(SpyJIcons.BACK,null,null);
    	_backButton.setLayout(null);
    	_backButton.setActionCommand("BACK");
    	_backButton.setBounds(820, 32, 32, 32);
    	_backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    	_backButton.setToolTipText(SpyJUtilities.setCustomToolTipText("Go Back"));
    	_backButton.addActionListener(_clickListener);
    	_driveSelectionPanelComponents.add(_backButton);
    	
    	SpyJButton _helpButton = new SpyJButton(SpyJIcons.HELP,null,null);
    	_helpButton.setLayout(null);
    	_helpButton.setToolTipText(SpyJUtilities.setCustomToolTipText("Click here to view Help!"));
    	_helpButton.setBounds(860, 28, 43, 43);
    	_helpButton.setActionCommand("HELP");
    	_helpButton.addActionListener(_clickListener);
    	_helpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    	_driveSelectionPanelComponents.add(_helpButton);
    	
    	SpyJButton _basicButton = new SpyJButton(SpyJIcons.CONTINUE_NORMAL,SpyJIcons.CONTINUE_PRESSED,SpyJIcons.CONTINUE_ROLLOVER);
    	_basicButton.setLayout(null);
    	_basicButton.setActionCommand("CONTINUE");
    	_basicButton.setBounds(440, 350, 108, 36);
    	_basicButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    	_basicButton.setToolTipText(SpyJUtilities.setCustomToolTipText("Start Scan"));
    	_basicButton.addActionListener(_clickListener);
    	_driveSelectionPanelComponents.add(_basicButton);
    	
    	TableModel _customTableModel = new DriveTableModel();    	
		JTable tbl = new JTable(_customTableModel);	
		
	    tbl.setRowHeight(26);
	    tbl.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
	    tbl.setGridColor(Color.white);
		
	    final TableCellRenderer tcr = tbl.getTableHeader().getDefaultRenderer();
	    tbl.getTableHeader().setDefaultRenderer(new TableCellRenderer() {
	    	
            @Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) tcr.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setFont(SpyJGlobals.SansBold12);
                lbl.setBorder(BorderFactory.createCompoundBorder(lbl.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                return lbl;
            }
        });
	    
	    TableColumnModel tcm = tbl.getColumnModel();
	    
	    tcm.getColumn(0).setPreferredWidth(20);
	    tcm.getColumn(0).setMinWidth(20);
	    tcm.getColumn(0).setMaxWidth(20);
	    
	    tcm.getColumn(1).setPreferredWidth(200);
	    tcm.getColumn(1).setMinWidth(200);
	    
	    TextWithIconCellRenderer renderer = new TextWithIconCellRenderer();
	    tcm.getColumn(1).setCellRenderer(renderer);
	    
	    DefaultTableCellRenderer alignRenderer = new DefaultTableCellRenderer();
	    tcm.getColumn(2).setCellRenderer(alignRenderer);
	    tcm.getColumn(2).setPreferredWidth(125);
	    tcm.getColumn(2).setMinWidth(125);
	    
	    final TableCellRenderer gettingCell2Renderer = tcm.getColumn(2).getCellRenderer();
	    tcm.getColumn(2).setCellRenderer(new TableCellRenderer() {
            @Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) gettingCell2Renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setFont(SpyJGlobals.SansPlain12);
                lbl.setBorder(BorderFactory.createCompoundBorder(lbl.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                return lbl;
            }
        });

	    tcm.getColumn(3).setCellRenderer(alignRenderer);
	    tcm.getColumn(3).setPreferredWidth(100);
	    tcm.getColumn(3).setMinWidth(100);
	    
	    final TableCellRenderer gettingCell3Renderer = tcm.getColumn(3).getCellRenderer();
	    tcm.getColumn(3).setCellRenderer(new TableCellRenderer() {
            @Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) gettingCell3Renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setFont(SpyJGlobals.SansPlain12);
                lbl.setBorder(BorderFactory.createCompoundBorder(lbl.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                return lbl;
            }
        });
	    
	    tcm.getColumn(4).setCellRenderer(alignRenderer);
	    tcm.getColumn(4).setPreferredWidth(100);
	    tcm.getColumn(4).setMinWidth(100);
	    
	    final TableCellRenderer gettingCell4Renderer = tcm.getColumn(3).getCellRenderer();
	    tcm.getColumn(3).setCellRenderer(new TableCellRenderer() {
            @Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) gettingCell4Renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                lbl.setFont(SpyJGlobals.SansPlain12);
                lbl.setBorder(BorderFactory.createCompoundBorder(lbl.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
                lbl.setHorizontalAlignment(SwingConstants.CENTER);
                return lbl;
            }
        });
	    
	    tbl.setShowHorizontalLines(false);
	    tbl.setIntercellSpacing(new Dimension(1, 0));
	    
	    //tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    //tbl.setPreferredScrollableViewportSize(tbl.getPreferredSize());
	    tbl.setPreferredScrollableViewportSize(new Dimension(500, 78));
	    tbl.setSelectionBackground(SpyJGlobals.lightgray);
	    tbl.setFillsViewportHeight(true);	    
	    
	    RowSorter<TableModel> _rowSorter = new TableRowSorter<TableModel>(_customTableModel);
	    tbl.setRowSorter(_rowSorter);
	    
	    JScrollPane sp = new JScrollPane(tbl);
	    sp.setBounds(175, 120, 600, 204);
	    _driveSelectionPanelComponents.add(sp);
	    
	    return _driveSelectionPanelComponents;
	}
	
    private class ClickListener implements ActionListener {
    	
	    @Override
	    public void actionPerformed(ActionEvent e) {
	    	if(e.getActionCommand().equals("BACK")){
	    		SpyJUtilities.doRedirect("Home", _rootPane);
	    	}else if(e.getActionCommand().equals("CONTINUE")){
	    		if(SpyJUtilities.ScanDrives.size() > 0){
	    			SpyJUtilities.doRedirect("Scan", _rootPane);
	    		}else{
	    			UIManager um = new UIManager();
	    			UIManager.put("OptionPane.messageForeground", Color.red);
	    			UIManager.put("OptionPane.background", Color.white);
	    			UIManager.put("Panel.background", Color.white);
	    			JOptionPane.showMessageDialog(_rootPane, "You must select atleast one drive to scan!", 
	    					"HardDrive Spy", JOptionPane.WARNING_MESSAGE , null);
	    		}
	    	}else if(e.getActionCommand().equals("HELP")){
	    		if(helpWindow == null){
	    			helpWindow = new SpyJHelpFrame();
	    		}else{
	    			helpWindow.setVisible(true);
	    		}
	    	}
	    }
	}
   
}














