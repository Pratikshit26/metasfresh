package org.adempiere.ad.persistence;

 


import java.lang.reflect.Method;

/**
 * Model setter handler
 * 
 * e.g. org.compiere.model.I_C_Invoice.setC_BPartner(I_C_BPartner bpartner)
 * 
 * @author tsa
 *
 */
/*package*/class ModelSetterMethodInfo extends AbstractModelMethodInfo
{
	private final Class<?> parameterType;

	private final String idPropertyName;

	public ModelSetterMethodInfo(final Method interfaceMethod, final Class<?> parameterType, final String idPropertyName)
	{
		super(interfaceMethod);

		this.parameterType = parameterType;
		this.idPropertyName = idPropertyName;
	}

	@Override
	public Object invoke(final IModelInternalAccessor model, final Object[] methodArgs)
	{
		final Object value = methodArgs[0];
		model.setValueFromPO(idPropertyName, parameterType, value);
		return null;
	}
}
