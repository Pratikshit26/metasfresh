package org.adempiere.ad.persistence;

 


import java.lang.reflect.Method;

/**
 * Model getter handler.
 * 
 * e.g. org.compiere.model.I_C_Invoice.getC_BPartner()
 * 
 * @author tsa
 *
 */
public class ModelGetterMethodInfo extends AbstractModelMethodInfo
{

	private final String valueColumnName;

	/**
	 * 
	 * @param interfaceMethod
	 * @param valueColumnName value column name (e.g. AD_Client_ID, M_Warehouse_ID etc)
	 */
	public ModelGetterMethodInfo(final Method interfaceMethod, final String valueColumnName)
	{
		super(interfaceMethod);

		this.valueColumnName = valueColumnName;
	}

	@Override
	public Object invoke(final IModelInternalAccessor model, final Object[] methodArgs) throws Exception
	{
		return model.getReferencedObject(valueColumnName, getInterfaceMethod());
	}

}
