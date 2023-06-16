package org.rifluxyss.javadev.harddrivescanner.commons;

import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class SpyJStyles {

	public static StyleContext HomeContentStyleContext;
	public static Style HomeTextStyle;
	
	public SpyJStyles(){
		
		HomeContentStyleContext = new StyleContext();
		HomeTextStyle = HomeContentStyleContext.addStyle("HomeTextStyle", null);
	    //HomeTextStyle.addAttribute(StyleConstants.Foreground, Color.red);
		HomeTextStyle.addAttribute(StyleConstants.FontSize, new Integer(16));
		HomeTextStyle.addAttribute(StyleConstants.FontFamily, "serif");
		HomeTextStyle.addAttribute(StyleConstants.Bold, new Boolean(true));
	}
	
	
}
