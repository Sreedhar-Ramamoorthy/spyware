package org.rifluxyss.javadev.harddrivescanner.commons;

import java.awt.Color;
import java.awt.Font;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

public class SpyJGlobals {
	
	public static int APPLET_WIDTH = 940;
	public static int APPLET_HEIGHT = 540;
	
	public static String SCAN_TYPE = "BASIC";
	
	public static int _pTimerLength=10;	
	public static Color orange = new Color(255, 122, 56);
	public static Color gray = new Color(95, 95, 95);
	public static Color lightgray = new Color(225, 225, 225);
	
	public static Color lightgray2 = new Color(238, 238, 238);
	
	public static URL docCodeBase = null;
	public static Font TitleFont16 = new Font("Arial", Font.BOLD, 16); 
	
	public static Font TitleFont12 = new Font("Arial", Font.BOLD, 12); 
	
	public static Font TitleFont14 = new Font("Arial", Font.BOLD, 14); 
	
	public static Font TitleFontPlain12 = new Font("Arial", Font.PLAIN, 12);
	
	public static Font TitleFontItalic10 = new Font("Arial", Font.ITALIC, 10);
	
	public static Font TitleFont18 = new Font("Arial", Font.BOLD, 18);
	
	public static Font SansBold12 = new Font("SansSerif", Font.BOLD, 12);
	public static Font SansPlain12 = new Font("SansSerif", Font.PLAIN, 12);
	
	public static String Categories = "";
	
	public static String[] CategoryExtensionsArray;
	public static String CategoryIcons ="";
	public static String RootPath="";
	
	public static String HelpUrl = "";
	
	public static boolean canOpenFile = true;
	
	public static boolean isThroughKeyPress = false;
	
	public static final boolean DEBUG = true;
	
	// The default system browser under windows.
	public static final String WIN_PATH = "rundll32";
	// The flag to display a url.
	public static final String WIN_FLAG = "url.dll,FileProtocolHandler";
	
	public static String User_IP = "";
	public static int SCAN_ID;
	
	public SpyJGlobals(){
		SCAN_TYPE = "BASIC";
		InetAddress thisIp;
		try {
			thisIp = InetAddress.getLocalHost();
			User_IP = thisIp.getHostAddress();
			System.out.println("IP:"+User_IP);
		} catch (UnknownHostException e) {
			SpyJLogger.error("Error Getting IP Address", e);
		}
	}

}
