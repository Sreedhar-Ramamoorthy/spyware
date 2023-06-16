package org.rifluxyss.javadev.harddrivescanner.gui;

import java.awt.Color;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJGlobals;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJIcons;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJLogger;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJUtilities;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.cellrenderers.HistoryUserAccountCellRenderer;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.custom.SpyJButton;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.custom.SpyJHistoryTable;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.custom.SpyJLabel;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.custom.SpyJSpacer;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.listeners.ClickListener;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.listeners.URISelectionListener;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.tablemodels.HistoryTableModel;

public class BrowserHistoryPanel extends JPanel {
	
	static BrowserHistoryPanel thisClass;
	
	public BrowserHistoryPanel() {
		
		thisClass = this;
		
		setLayout(null);
		setBackground(Color.WHITE);
		
		ClickListener _clickListener = new ClickListener(this);
		
		SpyJButton _refreshButton = new SpyJButton(SpyJIcons.REFRESH,null,null);
    	_refreshButton.setActionCommand("REFRESH_BROWSER_HISTORY");
    	_refreshButton.setBounds(835, 5, 22, 22);
    	_refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    	_refreshButton.setToolTipText(SpyJUtilities.setCustomToolTipText("Refresh"));
    	_refreshButton.addActionListener(_clickListener);
    	add(_refreshButton);
		
    	SpyJSpacer _topSpacer = new SpyJSpacer(0, 0, 30, 870);
		_topSpacer.setBackground(Color.DARK_GRAY);
		add(_topSpacer);
		
		SpyJLabel _UserAccountHeader = new SpyJLabel("   User Accounts");
		_UserAccountHeader.setBounds(5, 40, 200, 20);
		_UserAccountHeader.setFont(SpyJGlobals.TitleFont12);
		_UserAccountHeader.setOpaque(true);
		_UserAccountHeader.setBackground(SpyJGlobals.lightgray2);
		add(_UserAccountHeader);
		
		int MozillaHistoryUserAccountsCount = SpyJUtilities.MozillaHistoryAccounts.size();	
		int IEHistoryUserAccountsCount = SpyJUtilities.IEHistoryAccounts.size();
		
		int userAccountsCount = MozillaHistoryUserAccountsCount + IEHistoryUserAccountsCount;
		
		SpyJLogger.debug(userAccountsCount);
		Object[][] userAccountsData = new Object[userAccountsCount][3];
		
		for(int position=0; MozillaHistoryUserAccountsCount > position; position++){
			userAccountsData[position][0] = SpyJIcons.MOZILLA;
			String[] pathArray = SpyJUtilities.MozillaHistoryAccounts.get(position).getAbsolutePath().split("\\\\");
			SpyJLogger.info(pathArray[2]);
			userAccountsData[position][1] = pathArray[2];
			userAccountsData[position][2] = "MOZILLA";
		}
		
		for(int position=0; IEHistoryUserAccountsCount > position; position++){			
			int dataPos = MozillaHistoryUserAccountsCount + position;			
			userAccountsData[dataPos][0] = SpyJIcons.IE;
			String[] pathArray = SpyJUtilities.IEHistoryAccounts.get(position).getAbsolutePath().split("\\\\");
			SpyJLogger.info(pathArray[2]);
			userAccountsData[dataPos][1] = pathArray[2];
			userAccountsData[dataPos][2] = "IE";
		}
		
		SpyJHistoryTable JHistoryTable =  new SpyJHistoryTable(new HistoryTableModel(null, null));
		
		JScrollPane historyListPane = new JScrollPane(JHistoryTable);
		
		JList userAccountsList = new JList(userAccountsData);
		userAccountsList.setLayout(null);
	    userAccountsList.setCellRenderer(new HistoryUserAccountCellRenderer(this));
	    JScrollPane userAccountsListScrollPane = new JScrollPane(userAccountsList);
	    userAccountsListScrollPane.setBounds(5, 60, 200, 230);
		add(userAccountsListScrollPane);
		
		 // Add the stripe renderer.
		historyListPane.setBounds(225, 40, 645, 250);
		add(historyListPane);
		
	}
	
	public static BrowserHistoryPanel getBrowserHistoryPanel(){
		return thisClass;
	}
}