package org.rifluxyss.javadev.harddrivescanner.commons;

import java.io.PrintWriter;
import java.io.StringWriter;

public class SpyJLogger {
	
	SpyJLogger(){
		
	}
	
	public static void info(Object strToWrite){
    	if(SpyJGlobals.DEBUG)
    		System.out.println("INFO : "+strToWrite);
    }
	
	public static void debug(Object strToWrite){
    	if(SpyJGlobals.DEBUG)
    		System.out.println("DEBUG : "+strToWrite);
    }
	
	public static void error(Object strToWrite){
    	if(SpyJGlobals.DEBUG)
    		System.err.println("ERROR : "+strToWrite);
    }
	
	public static void error(Object strToWrite, Exception e){
    	if(SpyJGlobals.DEBUG){
    	   StringWriter sw = new StringWriter();
    	   e.printStackTrace(new PrintWriter(sw));
    	   System.err.println("ERROR : "+strToWrite);
    	   System.err.println("\nDebug Info:" + "\n-----------\n");
    	   System.err.println(sw);    		
    	}
    }
	
	

}
