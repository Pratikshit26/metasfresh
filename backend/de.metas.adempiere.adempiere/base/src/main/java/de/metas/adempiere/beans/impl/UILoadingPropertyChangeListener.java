package de.metas.adempiere.beans.impl;

 


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.ref.WeakReference;

import de.metas.adempiere.form.IClientUI;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Property change listener for long operations (i.e display loading cursor)
 *
 * @author al
 */
public abstract class UILoadingPropertyChangeListener implements PropertyChangeListener
{
	private final WeakReference<Object> componentRef;

	public UILoadingPropertyChangeListener(final Object component)
	{
		super();

		Check.assumeNotNull(component, "component not null");
		this.componentRef = new WeakReference<Object>(component);
	}

	@Override
	public final void propertyChange(final PropertyChangeEvent evt)
	{
		final Object component = componentRef.get();
		if (component == null)
		{
			// NOTE: component reference expired
			return;
		}
		
		Services.get(IClientUI.class).executeLongOperation(component, new Runnable()
		{
			@Override
			public void run()
			{
				propertyChange0(evt);
			}
		});
	}

	protected abstract void propertyChange0(PropertyChangeEvent evt);
}
