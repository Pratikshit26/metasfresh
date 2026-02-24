package org.adempiere.test;

 


import de.metas.adempiere.form.AbstractClientUIInvoker;
import de.metas.adempiere.form.IClientUIInstance;
import de.metas.adempiere.form.IClientUIInvoker;

public class TestClientUIInvoker extends AbstractClientUIInvoker
{

	public TestClientUIInvoker(IClientUIInstance clientUI)
	{
		super(clientUI);
	}

	@Override
	protected Runnable asInvokeLaterRunnable(Runnable runnable)
	{
		return runnable;
	}

	@Override
	protected Runnable asLongOperationRunnable(Runnable runnable)
	{
		return runnable;
	}
	
	@Override
	protected Runnable asShowGlassPaneRunnable(Runnable runnable)
	{
		return runnable;
	}

	@Override
	public IClientUIInvoker setParentComponent(Object parentComponent)
	{
		return this;
	}

	@Override
	public IClientUIInvoker setParentComponentByWindowNo(int windowNo)
	{
		return this;
	}

}
