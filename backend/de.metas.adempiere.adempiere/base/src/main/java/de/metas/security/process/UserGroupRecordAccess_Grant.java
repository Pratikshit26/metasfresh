package de.metas.security.process;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

public class UserGroupRecordAccess_Grant extends UserGroupRecordAccess_Base
{
	@Override
	protected String doIt()
	{
		grantAccessToRecord();
		return MSG_OK;
	}
}
