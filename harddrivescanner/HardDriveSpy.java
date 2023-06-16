package org.rifluxyss.javadev.harddrivescanner;


import java.awt.Container;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.UIManager;

import org.rifluxyss.javadev.harddrivescanner.commons.SpyJGlobals;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJIcons;
import org.rifluxyss.javadev.harddrivescanner.commons.SpyJUtilities;


public class HardDriveSpy extends JApplet {

	public static String JavaLookAndFeel = "javax.swing.plaf.metal.MetalLookAndFeel";
	public static String MSLookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
	public static String DefaultLookAndFeel = UIManager
			.getSystemLookAndFeelClassName();
	final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	final static boolean RIGHT_TO_LEFT = false;
	public static Container _rootPane;
	
	static JApplet thisApplet;
	
	@Override
	public void init() {		
		new SpyJIcons();
		new SpyJGlobals();		
		//SpyJGlobals.docCodeBase = getCodeBase();		
		thisApplet = this;		
		//Utilities.debug(this);	
		_rootPane = getContentPane();
		SpyJUtilities.setParameters(thisApplet);
		SpyJUtilities.doRedirect("Home",_rootPane);
	}
	
	public static void main(String[] args) {		
		run(new HardDriveSpy(), SpyJGlobals.APPLET_WIDTH, SpyJGlobals.APPLET_HEIGHT);		
	}
	
	public static void run(JApplet applet, int width, int height) {
		JFrame frame = new JFrame("HardDrive Spy Scanner");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(applet);
		frame.setSize(width, height);			
		applet.init();
		applet.start();
		frame.pack();
		frame.setVisible(true);	
		
	}
	
	public static Container getRootContentPane() {
		return _rootPane;
	}
	
}
