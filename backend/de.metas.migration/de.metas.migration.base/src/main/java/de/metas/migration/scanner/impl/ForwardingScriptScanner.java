package de.metas.migration.scanner.impl;

import de.metas.migration.IScript;
import de.metas.migration.scanner.IScriptFactory;
import de.metas.migration.scanner.IScriptScanner;
import de.metas.migration.scanner.IScriptScannerFactory;

/*
 * #%L
 * de.metas.migration.base
     
 * #L%
 */

public abstract class ForwardingScriptScanner implements IScriptScanner
{
	protected abstract IScriptScanner getDelegate();

	@Override
	public void setScriptScannerFactory(final IScriptScannerFactory scriptScannerFactory)
	{
		getDelegate().setScriptScannerFactory(scriptScannerFactory);
	}

	@Override
	public IScriptScannerFactory getScriptScannerFactory()
	{
		return getDelegate().getScriptScannerFactory();
	}

	@Override
	public IScriptScannerFactory getScriptScannerFactoryToUse()
	{
		return getDelegate().getScriptScannerFactoryToUse();
	}

	@Override
	public IScriptFactory getScriptFactory()
	{
		return getDelegate().getScriptFactory();
	}

	@Override
	public void setScriptFactory(final IScriptFactory scriptFactory)
	{
		getDelegate().setScriptFactory(scriptFactory);
	}

	@Override
	public IScriptFactory getScriptFactoryToUse()
	{
		return getDelegate().getScriptFactoryToUse();
	}

	@Override
	public boolean hasNext()
	{
		return getDelegate().hasNext();
	}

	@Override
	public IScript next()
	{
		return getDelegate().next();
	}

}
