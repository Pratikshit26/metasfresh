package org.adempiere.plaf;

 


import java.beans.PropertyChangeListener;

/**
 * Immutable implementation of {@link javax.swing.Action}.
 * 
 * NOTE: this is a replacement of sun.swing.UIAction.
 * 
 * @author tsa
 *
 */
public abstract class UIAction implements javax.swing.Action
{
	private final String name;

	public UIAction(final String name)
	{
		super();
		this.name = name;
	}
	
	public final String getName()
	{
		return name;
	}

	@Override
	public final Object getValue(String key)
	{
		if (NAME.equals(key))
		{
			return name;
		}
		return null;
	}

	@Override
	public final void putValue(String key, Object value)
	{
		// immutable, nothing to do
	}

	@Override
	public final void setEnabled(boolean enabled)
	{
		// immutable, nothing to do
	}

	@Override
	public final boolean isEnabled()
	{
		return true;
	}

	@Override
	public final void addPropertyChangeListener(PropertyChangeListener listener)
	{
		// immutable, nothing to do
	}

	@Override
	public final void removePropertyChangeListener(PropertyChangeListener listener)
	{
		// immutable, nothing to do
	}

}
