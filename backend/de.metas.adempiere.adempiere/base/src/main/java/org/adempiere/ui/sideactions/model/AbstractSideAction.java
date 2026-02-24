package org.adempiere.ui.sideactions.model;

 


import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Base {@link ISideAction} implementation which implements general methods and features.
 * 
 * @author tsa
 *
 */
public abstract class AbstractSideAction implements ISideAction
{
	private static final transient String GENERATED_ID_Prefix = "ID_" + UUID.randomUUID() + "_";
	private static final transient AtomicLong GENERATED_ID_Next = new AtomicLong(0);

	private final String id;

	public AbstractSideAction()
	{
		this((String)null); // id=null => generate
	}

	public AbstractSideAction(final String id)
	{
		super();

		//
		// Set provided ID or generate a new one
		if (id == null)
		{
			this.id = GENERATED_ID_Prefix + GENERATED_ID_Next.incrementAndGet();
		}
		else
		{
			this.id = id;
		}
	}

	@Override
	public final String getId()
	{
		return id;
	}
}
