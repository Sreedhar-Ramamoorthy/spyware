package org.rifluxyss.javadev.harddrivescanner.commons.gui.custom;

import java.awt.Dimension;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.text.EditorKit;

import org.rifluxyss.javadev.harddrivescanner.commons.SpyJGlobals;

public class SpyJHelpFrame extends JFrame{
	
	public SpyJHelpFrame(){
        super("HardDrive Spy Help");
        //We want to reuse the internal frame, so we need to
        //make it hide (instead of being disposed of, which is
        //the default) when the user closes it.
        setDefaultCloseOperation(
          WindowConstants.HIDE_ON_CLOSE);
        setSize(new Dimension(300, 400));
        setLocation(SpyJGlobals.APPLET_WIDTH - getWidth(), SpyJGlobals.APPLET_HEIGHT - getHeight() - 100);
        //Add an internal frame listener so we can see
        //what internal frame events it generates.
        //And we mustn't forget to add it to the desktop pane!
        //Set its size and location.  We'd use pack() to set the size
        //if the window contained anything.
        //http://demo.rifluxyss.com/webscanner/index.php?section=help
        JEditorPane historyPane = new JEditorPane();
        
        historyPane.setEditable(false);
		EditorKit kit = historyPane.getEditorKitForContentType("text/html");
		historyPane.setEditorKit(kit);  
	    JScrollPane historyPaneScrollPane = new JScrollPane(historyPane);
	    historyPaneScrollPane.setBounds(0, 0, getWidth(), getHeight());
	    try {
			historyPane.setPage(new URL(SpyJGlobals.HelpUrl));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        add(historyPaneScrollPane);
        setVisible(true);
    }

    //Show the internal frame.
    

}
