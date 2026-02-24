package de.metas.acct.interceptor;

 


import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_AcctSchema_Default;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.ModelValidator;

/**
 * 
 * @author ts
 * @implSpec task 08354
 */
@Interceptor(I_C_BP_BankAccount.class)
public class C_BP_BankAccount
{
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_NEW_REPLICATION })
	public void insertAccounting(final I_C_BP_BankAccount bankAccount)
	{
		final String nullWhereClause = null;

		InterfaceWrapperHelper.getPO(bankAccount).insert_Accounting(
				I_C_BP_BankAccount.Table_Name + "_Acct",
				I_C_AcctSchema_Default.Table_Name,
				nullWhereClause);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE, ModelValidator.TYPE_BEFORE_DELETE_REPLICATION })
	public void deleteAccounting(final I_C_BP_BankAccount bankAccount)
	{
		InterfaceWrapperHelper.getPO(bankAccount).delete_Accounting(I_C_BP_BankAccount.Table_Name + "_Acct");
	}
}
