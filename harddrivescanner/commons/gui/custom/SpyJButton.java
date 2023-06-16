package org.rifluxyss.javadev.harddrivescanner.commons.gui.custom;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JToolTip;
import javax.swing.ToolTipManager;

public class SpyJButton extends JButton{

	public SpyJButton(Icon normal, Icon pressed, Icon rollover) {
    	super(normal);    	 	
    	this.setFocusPainted(false);
    	this.setRolloverEnabled(true);
    	this.setRolloverIcon(rollover);
    	this.setPressedIcon(pressed);
    	this.setBorderPainted(false);
    	this.setContentAreaFilled(false); 
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
