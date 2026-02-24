package org.adempiere.model;

 


import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.WeakReference;
import org.compiere.model.AbstractPOCacheLocal;

/*package*/class POWrapperCacheLocal extends AbstractPOCacheLocal
{
	private final WeakReference<POWrapper> parentPOWrapperRef;
	private final int parentColumnIndex;

	public POWrapperCacheLocal(final POWrapper parentPOWrapper, final String parentColumnName, final String refTableName)
	{
		super(parentColumnName, refTableName);
		this.parentPOWrapperRef = new WeakReference<>(parentPOWrapper);
		this.parentColumnIndex = parentPOWrapper.getColumnIndex(parentColumnName);
	}

	private final POWrapper getParentPOWrapper()
	{
		final POWrapper poWrapper = parentPOWrapperRef.getValue();
		if (poWrapper == null)
		{
			throw new AdempiereException("POWrapper reference expired");
		}
		return poWrapper;
	}

	@Override
	protected Properties getParentCtx()
	{
		return getParentPOWrapper().getCtx();
	}

	@Override
	protected String getParentTrxName()
	{
		return getParentPOWrapper().getTrxName();
	}

	@Override
	protected int getId()
	{
		return getParentPOWrapper().getValueAsInt(parentColumnIndex);
	}

	@Override
	protected boolean setId(int id)
	{
		getParentPOWrapper().setValue(getParentColumnName(), id);
		return true;
	}

}
