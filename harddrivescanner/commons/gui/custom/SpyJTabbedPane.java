package org.rifluxyss.javadev.harddrivescanner.commons.gui.custom;

import java.awt.Color;

import javax.swing.JTabbedPane;
import javax.swing.JToolTip;
import javax.swing.ToolTipManager;

public class SpyJTabbedPane extends JTabbedPane{
	
	@Override
	public JToolTip createToolTip(){
        JToolTip tip = super.createToolTip();
        ToolTipManager.sharedInstance().setInitialDelay(0);
        tip.setBackground(Color.WHITE);
        tip.setForeground(Color.DARK_GRAY);
        return tip;
     }

}
