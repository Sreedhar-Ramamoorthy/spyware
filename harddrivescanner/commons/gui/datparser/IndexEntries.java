package org.rifluxyss.javadev.harddrivescanner.commons.gui.datparser;
import java.util.ArrayList;

public class IndexEntries {
	public String FileVersion;

	public int Skipped;
	public ArrayList<UrlEntry> UrlEntries;

	public ArrayList<RedirectEntry> RedirectEntries;

	public IndexEntries() {
		UrlEntries = new ArrayList<UrlEntry>();
		RedirectEntries = new ArrayList<RedirectEntry>();
	}
}
