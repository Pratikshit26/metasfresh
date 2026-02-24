package org.compiere.swing;

 


import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Convenient class to implement a {@link ListCellRenderer} which is rendering the value to a string label.
 * 
 * Usually, developer will extend this class inline and will implement {@link #renderToString(Object)} method.
 * 
 * @author tsa
 *
 * @param <T> value type
 */
public class ToStringListCellRenderer<T> extends DefaultListCellRenderer
{
	/**
	 *
	 */
	private static final long serialVersionUID = -3068158390813947550L;

	@Override
	public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus)
	{
		@SuppressWarnings("unchecked")
		final T valueCasted = (T)value;
		final String valueAsString = renderToString(valueCasted);
		return super.getListCellRendererComponent(list, valueAsString, index, isSelected, cellHasFocus);
	}

	protected String renderToString(final T value)
	{
		return value == null ? "" : value.toString();
	}
}
