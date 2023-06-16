package org.rifluxyss.javadev.harddrivescanner.commons.gui.custom;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.rifluxyss.javadev.harddrivescanner.commons.SpyJGlobals;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJIcons;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJLogger;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJUtilities;

public class SpyJList extends JPanel{
	
	Object[][] _elements;
	
	public SpyJList(){
		super(null);
		setLayout(null);
		
		setBackground(Color.RED);
		setBounds(0, 0, 200, 225);
		
		int labelYPos = 0;
		
		int userAccountsCount = SpyJUtilities.AIMUserAccounts.size();
		
		if(userAccountsCount>0){
			for(int position=0; userAccountsCount > position; position++){
				SpyJLogger.debug(position);
				SpyJLabel _label = new SpyJLabel(new String(SpyJUtilities.AIMUserAccounts.get(position).getName()), SpyJIcons.AIM, SwingConstants.LEFT);
				_label.setBounds(0, labelYPos, 200, 20);
				_label.setFont(SpyJGlobals.TitleFont12);
				_label.setOpaque(false);
				_label.setBackground(Color.GREEN);
				_label.setLayout(null);
				add(_label);
				labelYPos += 20;		
			}
		}else{
		}
	}
}
