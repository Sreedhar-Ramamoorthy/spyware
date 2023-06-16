package org.rifluxyss.javadev.harddrivescanner.gui;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;

import org.rifluxyss.javadev.harddrivescanner.commons.SpyJGlobals;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJIcons;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJLogger;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJStrings;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJStyles;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJUtilities;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.custom.SpyJButton;

public class HomeScreenPanel extends JPanel{    
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Container _rootPane;
    
    // Set up contraints so that the user supplied component and the
    // background image label overlap and resize identically	
	public HomeScreenPanel(Container rootPane){
		
        // Create some GUI
		JPanel HomeComponentPanel = HomeComponentPanel();		
		_rootPane = rootPane;
		_rootPane.add(wrapInBackgroundImage(HomeComponentPanel,
				SpyJIcons.HOME_BACKGROUND));
		new SpyJUtilities();
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
 
    public JPanel HomeComponentPanel(){
    	
    	new SpyJStyles();
    	
    	ClickListener _clickListener = new ClickListener();
    	
    	JPanel homeComponentPanel = new JPanel(null);
    	homeComponentPanel.setBorder(
                BorderFactory.createEmptyBorder(10,10,10,10));
    	homeComponentPanel.setOpaque(false);  
    	
    	JLabel _headerLabel = new JLabel("Please Select Your Scan Type",SpyJIcons.SCAN,SwingConstants.LEFT);
    	_headerLabel.setBounds(35, 30, 400, 36);
    	_headerLabel.setIconTextGap(10);
    	_headerLabel.setFont(SpyJGlobals.TitleFont18);  	
    	homeComponentPanel.add(_headerLabel);
    	
    	/*
    	SpyJButton _helpButton = new SpyJButton(SpyJIcons.HELP,null,null);
    	_helpButton.setLayout(null);
    	_helpButton.setToolTipText(SpyJUtilities.setCustomToolTipText(SpyJGlobals.HomeHelp));
    	_helpButton.setBounds(860, 28, 43, 43);
    	_helpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    	homeComponentPanel.add(_helpButton);
    	*/
    	
    	SpyJButton _basicButton = new SpyJButton(SpyJIcons.BASIC_NORMAL,SpyJIcons.BASIC_PRESSED,SpyJIcons.BASIC_ROLLOVER);
    	_basicButton.setLayout(null);
    	_basicButton.setActionCommand("BASIC");
    	_basicButton.setToolTipText(SpyJUtilities.setCustomToolTipText("Basic Scan Type"));
    	_basicButton.setBounds(125, 119, 129, 36);
    	_basicButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    	_basicButton.addActionListener(_clickListener);
    	homeComponentPanel.add(_basicButton);
    	
    	SpyJButton _advancedButton = new SpyJButton(SpyJIcons.ADVANCED_NORMAL,SpyJIcons.ADVANCED_PRESSED,SpyJIcons.ADVANCED_ROLLOVER);
    	_advancedButton.setLayout(null);
    	_advancedButton.setActionCommand("ADVANCED");
    	_advancedButton.setBounds(125, 209, 129, 36);
    	_advancedButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    	_advancedButton.setToolTipText(SpyJUtilities.setCustomToolTipText("Advanced Scan Type"));
    	_advancedButton.addActionListener(_clickListener);
    	homeComponentPanel.add(_advancedButton);
    	
        DefaultStyledDocument _styledDocBasic = new DefaultStyledDocument(SpyJStyles.HomeContentStyleContext);
        
        JTextPane _basicTextPane = new JTextPane(_styledDocBasic);
        _basicTextPane.setLayout(null);
        _basicTextPane.setBounds(275, 110, 550, 76);
        _basicTextPane.setEditable(false);
        _basicTextPane.setOpaque(false);
        _basicTextPane.setSelectionColor(getBackground());
        try {
        	_styledDocBasic.insertString(0, SpyJStrings.HomeBasicText, null);
        	_styledDocBasic.setParagraphAttributes(0, 1, SpyJStyles.HomeTextStyle, false);
		} catch (BadLocationException e) {
			SpyJLogger.debug(e.getMessage());
		}
        homeComponentPanel.add(_basicTextPane);
        
        DefaultStyledDocument _styledDocAdvanced = new DefaultStyledDocument(SpyJStyles.HomeContentStyleContext);
        JTextPane _advancedTextPane = new JTextPane(_styledDocAdvanced);
        _advancedTextPane.setLayout(null);
        _advancedTextPane.setBounds(275, 200, 550, 76);
        _advancedTextPane.setEditable(false);
        _advancedTextPane.setOpaque(false);
        _advancedTextPane.setSelectionColor(getBackground());
        try {
        	_styledDocAdvanced.insertString(0, SpyJStrings.HomeAdvancedText, null);
        	_styledDocAdvanced.setParagraphAttributes(0, 1, SpyJStyles.HomeTextStyle, false);
		} catch (BadLocationException e) {
			SpyJLogger.error(e.getMessage());
		}
        homeComponentPanel.add(_advancedTextPane);
    	
        return homeComponentPanel;
        
    }
    
    private class ClickListener implements ActionListener {
	    	
	    @Override
	    public void actionPerformed(ActionEvent e) {
	    	SpyJGlobals.SCAN_TYPE = e.getActionCommand();
	    	if(SpyJGlobals.SCAN_TYPE.equals("ADVANCED")){
		    	SpyJLogger.debug("Scan Type=>"+SpyJGlobals.SCAN_TYPE);
		    	SpyJUtilities.doRedirect("Drive", _rootPane);
	    	}else {
	    		File[] roots = File.listRoots();	    		
	    		for (int i = 0; i < roots.length; i++) {
	    			String driveType = FileSystemView.getFileSystemView().getSystemTypeDescription(roots[i]);
	    			if(driveType.equals("Local Disk")){
		    			String driveName = roots[i].toString();	    			
		    			driveName = driveName.replaceAll(":\\\\", "");
		    			SpyJUtilities.ScanDrives.add(driveName);
	    			}
	    		}
	    		SpyJUtilities.doRedirect("Scan", _rootPane);
	    	}
	    }
		
	}
    
}