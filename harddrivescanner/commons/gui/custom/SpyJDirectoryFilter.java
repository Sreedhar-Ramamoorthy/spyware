package org.rifluxyss.javadev.harddrivescanner.commons.gui.custom;

import java.io.File;
import org.apache.commons.io.filefilter.DirectoryFileFilter;

public class SpyJDirectoryFilter extends DirectoryFileFilter{

    public SpyJDirectoryFilter()
    {
    	
    }

	@Override
	public boolean accept(File f) {
		return f.isDirectory();
	}
}
