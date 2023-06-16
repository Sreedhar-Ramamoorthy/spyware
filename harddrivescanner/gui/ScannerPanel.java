package org.rifluxyss.javadev.harddrivescanner.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.rifluxyss.javadev.harddrivescanner.HardDriveSpy;
import org.rifluxyss.javadev.harddrivescanner.commons.*;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.helper.*;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.listeners.ClickListener;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.tablemodels.CategoryTableModel;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.custom.*;

public class ScannerPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Container _rootPane;
	
	CategoryTableModel categoryTableModel;
	SpyJLabel _headerLabel,_estimatedTimeLabel,_filePath, _statusLabel;
	JProgressBar _progBar;
	BasicScannerTask basicScannerTask;
	AdvancedScannerTask advancedScannerTask;
	SpyJLabel remainingPercent;
	SpyJTabbedPane jtp;
	JPanel _basicListPanel, _fileListPanel;
	SpyJButton _backButton;
	
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
	
	public ScannerPanel(){
		
		//setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		
		categoryTableModel = SpyJUtilities.getCategoryTableModel();
		
		JPanel scannerComponentPanel = ScannerComponentPanel();
		
		this._rootPane = HardDriveSpy.getRootContentPane();
		this._rootPane.add(wrapInBackgroundImage(scannerComponentPanel,
				SpyJIcons.BACKGROUND));
		runProgress();
	}
	
	public JPanel ScannerComponentPanel(){
		
		ClickListener _clickListener = new ClickListener();
		
		JPanel scannerComponentPanel = new JPanel(null);
		scannerComponentPanel.setBorder(
                BorderFactory.createEmptyBorder(10,10,10,10));
		scannerComponentPanel.setOpaque(false);
		
		_headerLabel = new SpyJLabel("Analyzing...",SpyJIcons.SCAN,SwingConstants.LEFT);
    	_headerLabel.setBounds(35, 30, 400, 36);
    	_headerLabel.setIconTextGap(10);
    	_headerLabel.setFont(SpyJGlobals.TitleFont18);	
    	scannerComponentPanel.add(_headerLabel);
    	
    	_estimatedTimeLabel = new SpyJLabel("Estimated Time Remaining - 00:00:00");
    	_estimatedTimeLabel.setBounds(615, 30, 210, 36);
    	_estimatedTimeLabel.setIconTextGap(10);
    	_estimatedTimeLabel.setFont(SpyJGlobals.TitleFont12);
    	_estimatedTimeLabel.setVisible(false);
    	scannerComponentPanel.add(_estimatedTimeLabel);
    	
		_progBar = new JProgressBar();
		_progBar.setBounds(30, 90, 830, 25);
		_progBar.setForeground(SpyJGlobals.orange);
		_progBar.setBackground(SpyJGlobals.gray);
		_progBar.setBorder(BorderFactory.createLineBorder(Color.white, 0));
		_progBar.setBorderPainted(false);
		scannerComponentPanel.add(_progBar);
		
		_filePath = new SpyJLabel("");
		_filePath.setBounds(30, 126, 600, 36);
		_filePath.setIconTextGap(10);
		_filePath.setFont(SpyJGlobals.TitleFontPlain12);
    	//_estimatedTimeLabel.setVisible(false);
    	scannerComponentPanel.add(_filePath);
    	
    	_backButton = new SpyJButton(SpyJIcons.BACK,null,null);
    	_backButton.setLayout(null);
    	_backButton.setActionCommand("BACK");
    	_backButton.setBounds(820, 32, 32, 32);
    	_backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    	_backButton.setToolTipText(SpyJUtilities.setCustomToolTipText("Go Back"));
    	_backButton.addActionListener(_clickListener);
    	_backButton.setVisible(false);
    	scannerComponentPanel.add(_backButton);
    	
    	SpyJButton _helpButton = new SpyJButton(SpyJIcons.HELP,null,null);
    	_helpButton.setLayout(null);
    	_helpButton.setToolTipText(SpyJUtilities.setCustomToolTipText("Click here to view Help!"));
    	_helpButton.setBounds(860, 28, 43, 43);
    	_helpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    	_helpButton.setActionCommand("HELP");
    	_helpButton.addActionListener(_clickListener);
    	scannerComponentPanel.add(_helpButton);
		
    	_statusLabel = new SpyJLabel("(This may take several minutes to complete depending upon your system)");
    	_statusLabel.setBounds(280, 105, 400, 36);
    	_statusLabel.setIconTextGap(10);
    	_statusLabel.setFont(SpyJGlobals.TitleFontItalic10);
    	//_estimatedTimeLabel.setVisible(false);
    	scannerComponentPanel.add(_statusLabel);
		
		remainingPercent = new SpyJLabel(" 0%");
		remainingPercent.setBounds(865, 90, 55, 25);
		remainingPercent.setFont(SpyJGlobals.TitleFont16);
		remainingPercent.setOpaque(false);
		scannerComponentPanel.add(remainingPercent);
		
		jtp = new SpyJTabbedPane();
		jtp.setCursor(new Cursor(Cursor.HAND_CURSOR));
		jtp.setBounds(30, 200, 870, 315);
		jtp.setUI(new SpyJTabbedPaneUI(145));
		
		_basicListPanel = new ListPanel();
		
		jtp.addTab("Files", SpyJIcons.FILES_ICON, _basicListPanel, SpyJUtilities.setCustomToolTipText("Files"));
		
		jtp.addTab("Instant Messages", SpyJIcons.IM_ICON, new IMHistoryPanel(), SpyJUtilities.setCustomToolTipText("Instant Messages"));

		jtp.addTab("Web History", SpyJIcons.HISTORY_ICON, new BrowserHistoryPanel(), SpyJUtilities.setCustomToolTipText("Web History"));
		
		tabSelected(jtp, 0);
		
		
		ChangeListener changeListener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent
						.getSource();
				int index = sourceTabbedPane.getSelectedIndex();				
				//System.out.println("Tab changed to: "
				//		+ sourceTabbedPane.getTitleAt(index));
				tabSelected(jtp, index);
			}
		};
		jtp.addChangeListener(changeListener);
		//jtp.setBackground(Color.WHITE);
		scannerComponentPanel.add(jtp);
		
		return scannerComponentPanel;
	}
	
	public Dimension getProgressBarSize(){
		Dimension ProgressBarSize = new Dimension();
		ProgressBarSize.height = 25;
		ProgressBarSize.width = 600;
		return ProgressBarSize;
	}

	public void runProgress(){
		_progBar.setIndeterminate(true);

		setCursor(new Cursor(Cursor.WAIT_CURSOR));
		
        //startButton.setEnabled(false);
        //Instances of javax.swing.SwingWorker are not reusuable, so
        //we create new instances as needed.
		
		SpyJLogger.debug("Scan Type=>"+SpyJGlobals.SCAN_TYPE);
		
		if(SpyJGlobals.SCAN_TYPE.equals("ADVANCED")){	
			
			SpyJUtilities.initializeExtensionsArray();
			advancedScannerTask = new AdvancedScannerTask(_headerLabel,  _estimatedTimeLabel, SpyJUtilities.getFileExtensions(), _filePath, _statusLabel, _backButton);
			advancedScannerTask.addPropertyChangeListener(new PropertyChange());
			advancedScannerTask.execute();
			
		}else{
			basicScannerTask = new BasicScannerTask(_headerLabel,  _estimatedTimeLabel, SpyJUtilities.getFileExtensions(), _filePath, _statusLabel, _backButton);
			basicScannerTask.addPropertyChangeListener(new PropertyChange());
			basicScannerTask.execute();
		}
	}
	
	
	 /**
     * Invoked when task's progress property changes.
     */
	class PropertyChange implements PropertyChangeListener {
    	
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if ("progress" == evt.getPropertyName()) {
                int progress = (Integer) evt.getNewValue();
                _progBar.setIndeterminate(false);
                _progBar.setValue(progress);
                remainingPercent.setText(" " + progress + "%");
                
                if(progress == 100){
                	//jtp.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					_headerLabel.setText("Scan Complete");
				}
            }
		}
    }
	
	public JPanel getProgressPanel (){
		return this;
	}
	
	void tabSelected(JTabbedPane jtp, int pos) {
		for (int tab_i = 0; tab_i < jtp.getTabCount(); tab_i++) {
			jtp.setForegroundAt(tab_i, Color.BLACK);
		}
		jtp.setForegroundAt(pos, Color.WHITE);
		SpyJLogger.debug("Tab Position=>"+pos);
		if(pos == 0){
			JPanel _basicListPanel = new ListPanel();
			new CategoryListPanel(_basicListPanel);
		}else if(pos == 1){			
			JPanel IMHistoryPanelInstance = IMHistoryPanel.getIMHistoryPanel();
			if(IMHistoryPanelInstance != null){
				IMHistoryPanelInstance.removeAll();
				JPanel imHistoryPanel = new IMHistoryPanel();
	    		imHistoryPanel.setBounds(0, 0, 870, 300);
	    		IMHistoryPanelInstance.add(imHistoryPanel);
	    		IMHistoryPanelInstance.paintAll(IMHistoryPanelInstance.getGraphics());
			}
		}else if(pos == 2){			
			JPanel BrowserHistoryPanelInstance = BrowserHistoryPanel.getBrowserHistoryPanel();
			if(BrowserHistoryPanelInstance != null){
				BrowserHistoryPanelInstance.removeAll();
				JPanel browserHistoryPanel = new BrowserHistoryPanel();
				browserHistoryPanel.setBounds(0, 0, 870, 300);
				BrowserHistoryPanelInstance.add(browserHistoryPanel);
				BrowserHistoryPanelInstance.paintAll(BrowserHistoryPanelInstance.getGraphics());
			}
			
		}
	}

	class ListPanel extends JPanel {
		
		public ListPanel(){
			setLayout(null);
			setBackground(Color.WHITE);
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			JPanel categoryPanel = new CategoryListPanel(this);
			categoryPanel.setBounds(0, 0, 870, 300);
			add(categoryPanel);
		}
		
		public ListPanel(int _categoryIndex) {
			//setLayout(null);
			//setBackground(Color.WHITE);
			//JPanel fileListPanel = new FileListPanel(this, _categoryIndex);
			//fileListPanel.setBounds(0, 0, 870, 300);
			//add(fileListPanel);
		}
	}
	
	
	public static void showPanel(String _destinationPanel){
		if(_destinationPanel.equals("FileListPanel")){
			showFileListPanel();
		}
	}
	
	
	
	public static void showFileListPanel(){
		//showFileListPanel();
	}
	
	
}









