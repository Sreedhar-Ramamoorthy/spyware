package org.rifluxyss.javadev.harddrivescanner.commons.gui.helper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;

import javax.swing.JEditorPane;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

import org.rifluxyss.javadev.harddrivescanner.commons.SpyJLogger;

public class HTMLParser extends HTMLEditorKit.ParserCallback{
	
	private static HTMLParser thisClass;	
	private String _encoding = "ISO-8859-1";	
	private File _logPath = null;	
	private JEditorPane _targetPane = null;	
	private String IM = null;
	private String TagClass = "";
	private String _contentToDisplay = "<html><body>" +
			"<table width=\"100%\" border=\"0\" cellpadding=\"2\" cellspacing=\"2\">";
	
	public HTMLParser(String IM, File logPath, JEditorPane targetPane) throws Exception {		
		_logPath = logPath;
		_targetPane = targetPane;
		this.IM = IM;
		parse();
	}
	
	private int level = 0;
	
	private boolean inHeader = false;
	
	private static String lineSeparator = System.getProperty("line.separator", "\r\n");
	 
	@Override
	public void handleStartTag(HTML.Tag tag, MutableAttributeSet attributes, int position) {
		Enumeration e = attributes.getAttributeNames();
	    while (e.hasMoreElements()) {
	      Object key =  e.nextElement();
	      //System.out.println(key + " -- " + attributes.getAttribute(key));
	      if(key.toString().equals("class")){
	    	  TagClass = attributes.getAttribute(key).toString();
	    	  SpyJLogger.debug(TagClass);
	      }
	}
	
	this.inHeader = true;
    
  }

  @Override
public void handleEndTag(HTML.Tag tag, int position) {

    if (tag == HTML.Tag.H2 || tag == HTML.Tag.TD) {
      inHeader = false;
    }

    // work around bug in the parser that fails to call flush
    if (tag == HTML.Tag.HTML){
    	_contentToDisplay += "</table></body></html>";
    	_targetPane.setText(_contentToDisplay);
    }
  }
  

  @Override
public void handleText(char[] text, int position) {
    if (inHeader) {
    	if(TagClass.equals("time")){
    		_contentToDisplay += "<tr><td colspan=\"2\" style=\"font-family:Arial;font-size:14pt;font-weight:bold;\">";
    		_contentToDisplay += new String(text);
    		_contentToDisplay += "</td></tr><BR>";
    	}else if(TagClass.equals("local")){
    		_contentToDisplay += "<tr><td valign=\"top\" style=\"font-family:Arial;font-size:12pt;font-weight:bold;color:green;text-align:right;\">";
    		_contentToDisplay += new String(text);
    		_contentToDisplay += "</td>";
    	}else if(TagClass.equals("remote")){
    		_contentToDisplay += "<tr><td valign=\"top\" style=\"font-family:Arial;font-size:12pt;font-weight:bold;color:blue;text-align:right;\">";
    		_contentToDisplay += new String(text);
    		_contentToDisplay += "</td>";
    	}else if(TagClass.equals("msg")){
    		_contentToDisplay += "<td style=\"font-family:Arial;font-size:12pt;\">";
    		_contentToDisplay += new String(text);
    		_contentToDisplay += "</td></tr>";
    	}    	
		System.out.println(text);
    }

  }

  private void parse() throws IOException {
    ParserGetter kit = new ParserGetter();
    HTMLEditorKit.Parser parser = kit.getParser();
    if(IM.equals("AIM")){		
    	URL logUrl = new URL("file:////" + _logPath.toString());
    	SpyJLogger.debug(logUrl);
	    InputStream in = logUrl.openStream();
	    InputStreamReader r = new InputStreamReader(in, _encoding);
	    HTMLEditorKit.ParserCallback callback = this;
	    parser.parse(r, callback, true);
    }
  }
}

class ParserGetter extends HTMLEditorKit {
  @Override
public HTMLEditorKit.Parser getParser() {
    return super.getParser();
  }
}