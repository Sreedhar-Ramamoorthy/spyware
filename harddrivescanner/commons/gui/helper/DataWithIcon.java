package org.rifluxyss.javadev.harddrivescanner.commons.gui.helper;

import javax.swing.Icon;

public class DataWithIcon {
	  public DataWithIcon(Object data, Icon icon2) {
	    this.data = data;
	    this.icon = icon2;
	  }

	  public Icon getIcon() {
	    return icon;
	  }

	  public Object getData() {
	    return data;
	  }

	  @Override
	public String toString() {
	    return data.toString();
	  }

	  protected Icon icon;

	  protected Object data;
}
