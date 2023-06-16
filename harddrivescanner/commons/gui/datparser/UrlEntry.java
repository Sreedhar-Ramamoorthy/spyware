package org.rifluxyss.javadev.harddrivescanner.commons.gui.datparser;

public class UrlEntry {
	public String Url;

	public long LastModifiedTime;
	
	public String strLastModifiedTime;

	public long LastAccessTime;

	public String FileName;

	public String DirectoryName;

	public String Headers;
	
	public String getHeaders(){
		return Headers;
	}
	public void setHeaders(String _Headers){
		Headers = _Headers;
	}
	
	public String getUrl(){
		return Url;
	}
	public void setUrl(String _Url){
		Url = _Url;
	}
	
	public String getFileName(){
		return FileName;
	}
	public void setFileName(String _FileName){
		FileName = _FileName;
	}
	
	public String getDirectoryName(){
		return DirectoryName;
	}
	public void setDirectoryName(String _DirectoryName){
		DirectoryName = _DirectoryName;
	}
	
	public long getLastAccessTime(){
		return LastAccessTime;
	}
	public void setLastAccessTime(long _LastAccessTime){
		LastAccessTime = _LastAccessTime;
	}
}
