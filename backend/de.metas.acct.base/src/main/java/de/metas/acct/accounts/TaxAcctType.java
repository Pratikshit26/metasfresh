package de.metas.acct.accounts;

/*
 * #%L
 * de.metas.acct.base
     
 * #L%
 */

import de.metas.acct.AccountConceptualName;
import de.metas.acct.AccountConceptualNameAware;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.I_C_Tax_Acct;

@Getter
public enum TaxAcctType implements AccountConceptualNameAware
{
	TaxDue(I_C_Tax_Acct.COLUMNNAME_T_Due_Acct),
	TaxLiability(I_C_Tax_Acct.COLUMNNAME_T_Liability_Acct),
	TaxCredit(I_C_Tax_Acct.COLUMNNAME_T_Credit_Acct),
	TaxReceivables(I_C_Tax_Acct.COLUMNNAME_T_Receivables_Acct),
	TaxExpense(I_C_Tax_Acct.COLUMNNAME_T_Expense_Acct),
	T_Revenue_Acct(I_C_Tax_Acct.COLUMNNAME_T_Revenue_Acct),
	T_PayDiscount_Exp_Acct(I_C_Tax_Acct.COLUMNNAME_T_PayDiscount_Exp_Acct),
	T_PayDiscount_Rev_Acct(I_C_Tax_Acct.COLUMNNAME_T_PayDiscount_Rev_Acct),
	;

	@NonNull private final AccountConceptualName accountConceptualName;

	TaxAcctType(@NonNull final String accountConceptualName)
	{
		this.accountConceptualName = AccountConceptualName.ofString(accountConceptualName);
	}

	/**
	 * @return AP tax type (Credit or Expense)
	 */
	public static TaxAcctType getAPTaxType(final boolean isSalesTax) {return isSalesTax ? TaxExpense : TaxCredit;}

	public static boolean isInvoiceTax(final AccountConceptualName accountConceptualName)
	{
		return TaxDue.accountConceptualName.equals(accountConceptualName) // sales invoice
				|| TaxCredit.accountConceptualName.equals(accountConceptualName) // purchase invoice, VAT (not sales tax)
				|| TaxExpense.accountConceptualName.equals(accountConceptualName) // purchase invoice, sales tax
				;
	}
}
