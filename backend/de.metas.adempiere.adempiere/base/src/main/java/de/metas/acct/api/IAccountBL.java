package de.metas.acct.api;

 

import de.metas.util.ISingletonService;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.I_C_ValidCombination;

/**
 * Business logic used to manipulate accounts (i.e. {@link I_C_ValidCombination}s)
 * 
 * @author tsa
 *
 */
public interface IAccountBL extends ISingletonService
{
	/**
	 * Build and set {@link I_C_ValidCombination#COLUMNNAME_Combination}, {@link I_C_ValidCombination#COLUMNNAME_Description} and {@link I_C_ValidCombination#COLUMNNAME_IsFullyQualified}.
	 */
	void setValueDescription(I_C_ValidCombination account);

	/**
	 * Create a new {@link IAccountDimensionValidator} for given accounting schema.
	 * 
	 * @return accounting dimension validator
	 */
	IAccountDimensionValidator createAccountDimensionValidator(AcctSchema acctSchema);

	/**
	 * Validate account
	 */
	void validate(I_C_ValidCombination account);

	AccountDimension createAccountDimension(I_C_ElementValue ev, AcctSchemaId acctSchemaId);
}
