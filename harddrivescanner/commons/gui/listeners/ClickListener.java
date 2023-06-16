package org.rifluxyss.javadev.harddrivescanner.commons.gui.listeners;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import org.rifluxyss.javadev.harddrivescanner.HardDriveSpy;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJGlobals;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJUtilities;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.custom.SpyJHelpFrame;
import org.rifluxyss.javadev.harddrivescanner.gui.AdvancedFileListPanel;
import org.rifluxyss.javadev.harddrivescanner.gui.BasicFileListPanel;
import org.rifluxyss.javadev.harddrivescanner.gui.BrowserHistoryPanel;
import org.rifluxyss.javadev.harddrivescanner.gui.CategoryListPanel;
import org.rifluxyss.javadev.harddrivescanner.gui.FileTypePanel;
import org.rifluxyss.javadev.harddrivescanner.gui.IMHistoryPanel;



public class ClickListener implements ActionListener {
	
	Container _rootPane;
	JPanel _basicListPanel;
	int _categoryIndex, _extensionIndex;
	SpyJHelpFrame helpWindow = null;
	
	public ClickListener(){
		this._rootPane = HardDriveSpy.getRootContentPane();
	}
	
	public ClickListener(JPanel _basicListPanel){
		this._basicListPanel  = _basicListPanel;
		this._rootPane = HardDriveSpy.getRootContentPane();
	}
	
	public ClickListener(JPanel _basicListPanel, int _categoryIndex){
		this._rootPane = HardDriveSpy.getRootContentPane();
		this._basicListPanel  = _basicListPanel;
		this._categoryIndex = _categoryIndex;
	}
	
	public ClickListener(JPanel _basicListPanel, int _categoryIndex, int _extensionIndex){
		this._rootPane = HardDriveSpy.getRootContentPane();
		this._basicListPanel  = _basicListPanel;
		this._categoryIndex = _categoryIndex;
		this._extensionIndex = _extensionIndex;
	}
	
    @Override
    public void actionPerformed(ActionEvent e) {
    	if(e.getActionCommand().equals("BACK")){
    		if(SpyJGlobals.SCAN_TYPE.equals("ADVANCED")){	
    			SpyJUtilities.doRedirect("Drive", _rootPane);
    		}else{
    			SpyJUtilities.doRedirect("Home", _rootPane);
    		}
    	}else if(e.getActionCommand().equals("BACK_CATEGORY")){
    		_basicListPanel.removeAll();
    		JPanel categoryPanel = new CategoryListPanel(_basicListPanel);
			categoryPanel.setBounds(0, 0, 870, 300);
			_basicListPanel.add(categoryPanel);
			this._rootPane.paintAll(this._rootPane.getGraphics());
    	}else if(e.getActionCommand().equals("REFRESH_FILE_LIST")){
    		_basicListPanel.removeAll();
    		JPanel fileListPanel = new BasicFileListPanel(_basicListPanel, _categoryIndex);
    		fileListPanel.setBounds(0, 0, 870, 300);
			_basicListPanel.add(fileListPanel);
			this._rootPane.paintAll(this._rootPane.getGraphics());
    	}else if(e.getActionCommand().equals("REFRESH_FILE_TYPE")){
    		_basicListPanel.removeAll();
    		JPanel fileTypePanel = new FileTypePanel(_basicListPanel, _categoryIndex);
    		fileTypePanel.setBounds(0, 0, 870, 300);
			_basicListPanel.add(fileTypePanel);
			this._rootPane.paintAll(this._rootPane.getGraphics());
    	}else if(e.getActionCommand().equals("REFRESH_ADVANCED_FILE_LIST")){
    		_basicListPanel.removeAll();
    		JPanel advancedFileListPanel = new AdvancedFileListPanel(_basicListPanel, _categoryIndex, _extensionIndex);
    		advancedFileListPanel.setBounds(0, 0, 870, 300);
			_basicListPanel.add(advancedFileListPanel);
			this._rootPane.paintAll(this._rootPane.getGraphics());
    	}else if(e.getActionCommand().equals("REFRESH_IM_HISTORY")){
    		_basicListPanel.removeAll();
    		JPanel imHistoryPanel = new IMHistoryPanel();
    		imHistoryPanel.setBounds(0, 0, 870, 300);
			_basicListPanel.add(imHistoryPanel);
			this._rootPane.paintAll(this._rootPane.getGraphics());
    	}else if(e.getActionCommand().equals("REFRESH_BROWSER_HISTORY")){
    		_basicListPanel.removeAll();
    		JPanel BrowserHistoryPanel = new BrowserHistoryPanel();
    		BrowserHistoryPanel.setBounds(0, 0, 870, 300);
			_basicListPanel.add(BrowserHistoryPanel);
			this._rootPane.paintAll(this._rootPane.getGraphics());
    	}else if(e.getActionCommand().equals("HELP")){
    		if(helpWindow == null){
    			helpWindow = new SpyJHelpFrame();
    		}else{
    			helpWindow.setVisible(true);
    		}
    	}
    }
}