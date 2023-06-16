package org.rifluxyss.javadev.harddrivescanner.commons.gui.cellrenderers;

import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import org.rifluxyss.javadev.harddrivescanner.commons.SpyJGlobals;
import org.rifluxyss.javadev.harddrivescanner.commons.gui.helper.DataWithIcon;

public class TextWithIconCellRenderer extends DefaultTableCellRenderer {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void setValue(Object value) {
	    if (value instanceof DataWithIcon) {
	      if (value != null) {
	        DataWithIcon d = (DataWithIcon) value;
	        Object dataValue = d.getData();
            
	        setFont(SpyJGlobals.SansBold12);
            setBorder(BorderFactory.createCompoundBorder(getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 0)));
            
	        setText(dataValue == null ? "" : dataValue.toString());
	        setIcon(d.getIcon());
	        setHorizontalTextPosition(SwingConstants.RIGHT);
	        setVerticalTextPosition(SwingConstants.CENTER);
	        setHorizontalAlignment(SwingConstants.LEFT);
	        setVerticalAlignment(SwingConstants.CENTER);
	      } else {
	        setText("");
	        setIcon(null);
	      }
	    } else {
	      super.setValue(value);
	    }
	 }
}


