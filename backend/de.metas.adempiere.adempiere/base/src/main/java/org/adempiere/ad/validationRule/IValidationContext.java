package org.adempiere.ad.validationRule;

 


import org.compiere.util.Evaluatee;

public interface IValidationContext extends Evaluatee
{
	/**
	 * NO context. In most of the places, when NULL context is passed then implicit/default context will be used.
	 */
	IValidationContext NULL = null;

	/**
	 * Specifies that the validation is disabled.
	 * 
	 * e.g. if you are trying to get the displayed value from a lookup and you are using this context then the value shall be fetched but NO validation shall be applied
	 */
	IValidationContext DISABLED = new NullValidationContext();
	
	/**
	 * Specifies that the validation is disabled and ONLY current cache will be used
	 */
	IValidationContext CACHED = new NullValidationContext();

	/**
	 * Parameter to identify which is the context table name in which the validation rule is running.
	 * e.g. for C_Invoice.C_BPartner_ID lookup, the context table name is C_Invoice.
	 */
	String PARAMETER_ContextTableName = "_ContextTableName";

	/**
	 * Table Name from which validation records are
	 * 
	 * @return table name or <code>null</code>
	 */
	String getTableName();

	@Override
	String get_ValueAsString(String variableName);
}
