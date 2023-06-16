package org.rifluxyss.javadev.harddrivescanner.gui;

import java.awt.Color;

import java.awt.Cursor;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.EditorKit;

import org.rifluxyss.javadev.harddrivescanner.commons.SpyJGlobals;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJIcons;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJLogger;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJUtilities;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.cellrenderers.IMChatWithCellRenderer;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.cellrenderers.IMUserAccountCellRenderer;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.custom.SpyJButton;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.custom.SpyJLabel;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.custom.SpyJSpacer;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.listeners.ClickListener;

public class IMHistoryPanel extends JPanel {
	
	static IMHistoryPanel thisClass;
	
	public IMHistoryPanel() {
		
		thisClass = this;
		
		setLayout(null);
		setBackground(Color.WHITE);
		
		ClickListener _clickListener = new ClickListener(this);
		
		SpyJButton _refreshButton = new SpyJButton(SpyJIcons.REFRESH,null,null);
    	_refreshButton.setActionCommand("REFRESH_IM_HISTORY");
    	_refreshButton.setBounds(835, 5, 22, 22);
    	_refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    	_refreshButton.setToolTipText(SpyJUtilities.setCustomToolTipText("Refresh"));
    	_refreshButton.addActionListener(_clickListener);
    	add(_refreshButton);
		
    	SpyJSpacer _topSpacer = new SpyJSpacer(0, 0, 30, 870);
		_topSpacer.setBackground(Color.DARK_GRAY);
		add(_topSpacer);
		
		SpyJLabel _UserAccountHeader = new SpyJLabel("   User Accounts");
		_UserAccountHeader.setBounds(5, 40, 200, 25);
		_UserAccountHeader.setFont(SpyJGlobals.TitleFont12);
		_UserAccountHeader.setOpaque(true);
		_UserAccountHeader.setBackground(SpyJGlobals.lightgray2);
		add(_UserAccountHeader);
		
		int aimUserAccountsCount = SpyJUtilities.AIMUserAccounts.size();
		
		int pidginUserAccountsCount = SpyJUtilities.PidginUserAccounts.size();
		
		
		int userAccountsCount = aimUserAccountsCount + pidginUserAccountsCount;
		
		SpyJLogger.debug(userAccountsCount);
		
		Object[][] userAccountsData = new Object[userAccountsCount][3];
		
		for(int position=0; aimUserAccountsCount > position; position++){
			userAccountsData[position][0] = SpyJIcons.AIM;
			userAccountsData[position][1] = SpyJUtilities.AIMUserAccounts.get(position).getName();
			userAccountsData[position][2] = "AIM";
		}
		
		for(int position=0; pidginUserAccountsCount > position; position++){
			
			int dataPos = aimUserAccountsCount + position;
			
			userAccountsData[dataPos][0] = SpyJIcons.PIDGIN;
			userAccountsData[dataPos][1] = SpyJUtilities.PidginUserAccounts.get(position).getName();
			userAccountsData[dataPos][2] = "PIDGIN";
		}
		
		JList userAccountsList = new JList(userAccountsData);		
		JList chatWithUserList = new JList();		
		JEditorPane historyPane = new JEditorPane();
		
		userAccountsList.setBounds(0, 0, 200, 225);
		userAccountsList.setLayout(null);
	    userAccountsList.setCellRenderer(new IMUserAccountCellRenderer(chatWithUserList, historyPane));
	    JScrollPane userAccountsListScrollPane = new JScrollPane(userAccountsList);
	    userAccountsListScrollPane.setBounds(5, 65, 200, 225);
		add(userAccountsListScrollPane);
		
		SpyJLabel _chatWithUserHeader = new SpyJLabel("  Chat With");
		_chatWithUserHeader.setBounds(225, 40, 200, 25);
		_chatWithUserHeader.setFont(SpyJGlobals.TitleFont12);
		_chatWithUserHeader.setOpaque(true);
		_chatWithUserHeader.setBackground(SpyJGlobals.lightgray2);
		add(_chatWithUserHeader);
		
		chatWithUserList.setCellRenderer(new IMChatWithCellRenderer(historyPane));
	    JScrollPane chatWithUserListScrollPane = new JScrollPane(chatWithUserList);
	    chatWithUserListScrollPane.setBounds(225, 65, 200, 225);
		add(chatWithUserListScrollPane);
		
		SpyJLabel _historyHeader = new SpyJLabel("  History");
		_historyHeader.setBounds(440, 40, 425, 25);
		_historyHeader.setFont(SpyJGlobals.TitleFont12);
		_historyHeader.setOpaque(true);
		_historyHeader.setBackground(SpyJGlobals.lightgray2);
		add(_historyHeader);
		
		historyPane.setEditable(false);
		EditorKit kit = historyPane.getEditorKitForContentType("text/html");
		historyPane.setEditorKit(kit);	    
	    JScrollPane historyPaneScrollPane = new JScrollPane(historyPane);
	    historyPaneScrollPane.setBounds(440, 65, 425, 225);
	    
		add(historyPaneScrollPane);
		
	}
	
	public static IMHistoryPanel getIMHistoryPanel(){
		return thisClass;
	}
}