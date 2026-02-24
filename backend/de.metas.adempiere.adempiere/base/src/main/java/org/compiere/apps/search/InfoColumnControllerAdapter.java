package org.compiere.apps.search;

 


public abstract class InfoColumnControllerAdapter implements IInfoColumnController
{
	
	/**
	 *  Signals the other controllers to return null.
	 *  We use this constant because if a controller returns null, it may have not applied and we need a fallback.
	 */
	public static final String RETURN_NULL = "Return_null";
	
	@Override
	public void afterInfoWindowInit(IInfoSimple infoWindow)
	{
		// nothing
	}

	/**
	 * @return <code>data</code>
	 */
	@Override
	public Object gridConvertAfterLoad(final Info_Column infoColumn, final int rowIndexModel, int rowRecordId, Object data)
	{
		return data;
	}

}
