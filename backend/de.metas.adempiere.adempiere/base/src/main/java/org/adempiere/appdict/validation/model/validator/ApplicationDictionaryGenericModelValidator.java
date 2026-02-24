package org.adempiere.appdict.validation.model.validator;

 


import org.adempiere.appdict.validation.spi.IADValidator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

public class ApplicationDictionaryGenericModelValidator<T> implements ModelValidator
{
	private final Class<T> itemClass;
	private final IADValidator<T> validator;
	private final String tableName;

	private int adClientId = -1;

	public ApplicationDictionaryGenericModelValidator(final Class<T> itemClass, final IADValidator<T> validator)
	{
		this.itemClass = itemClass;
		this.validator = validator;
		this.tableName = InterfaceWrapperHelper.getTableName(itemClass);
	}

	@Override
	public void initialize(ModelValidationEngine engine, MClient client)
	{
		adClientId = client == null ? -1 : client.getAD_Client_ID();
		engine.addModelChange(tableName, this);
	}

	@Override
	public int getAD_Client_ID()
	{
		return adClientId;
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(PO po, int type) throws Exception
	{
		if (type == TYPE_BEFORE_NEW || type == TYPE_BEFORE_CHANGE)
		{
			final T item = InterfaceWrapperHelper.create(po, itemClass);
			validator.validate(item);
		}
		return null;
	}

	@Override
	public String docValidate(PO po, int timing)
	{
		return null;
	}
}
