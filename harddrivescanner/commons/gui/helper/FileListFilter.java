package org.rifluxyss.javadev.harddrivescanner.commons.gui.helper;

import java.io.File;
import java.io.FilenameFilter;

class FileListFilter implements FilenameFilter {

  String _fileToSearch = "";
	
  public FileListFilter(String fileToSearch) {	
		  _fileToSearch = fileToSearch;
  }

  @Override
public boolean accept(File directory, String filename) {
	 if(_fileToSearch.equals("DIRECTORY"))
		 return new File(directory, filename).isDirectory();
	 else
		 return filename.equals(_fileToSearch);
  }
}