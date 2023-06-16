package org.rifluxyss.javadev.harddrivescanner.commons.gui.custom;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JToolTip;
import javax.swing.ToolTipManager;

public class SpyJLabel extends JLabel{
	
	public SpyJLabel(String string, ImageIcon icon, int left) {
		super(string, icon, left);
	}
	
	public SpyJLabel(String string) {
		super(string);
	}

	@Override
	public JToolTip createToolTip() {
        JToolTip tip = super.createToolTip();
        ToolTipManager.sharedInstance().setInitialDelay(0);
        tip.setBackground(Color.WHITE);
        tip.setForeground(Color.DARK_GRAY);
        return tip;
    }
}
