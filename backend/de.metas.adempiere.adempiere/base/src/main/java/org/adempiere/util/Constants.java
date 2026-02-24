package org.adempiere.util;

 


import java.math.BigDecimal;

import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Product;

public final class Constants
{

	public static final String ADV_DONT_CHANGE_RESULT = "Adv_Dont_Change_Result";

	final static public BigDecimal HUNDRET = new BigDecimal("100");

	/**
	 * AD_Column_ID of column {@link I_M_InOut#COLUMNNAME_M_InOut_ID}.
	 */
	public static final int AD_COLUMN_ID_M_INOUT_M_INOUT_ID = 3521;

	/**
	 * AD_Column_ID of column {@link I_M_Product#COLUMNNAME_M_Product_ID}.
	 */
	public static final int AD_COLUMN_ID_M_PRODUCT_M_PRODUCT_ID = 1402;

	public static final int AD_REFERENCE_ID_DOCUMENT_ACTION = 135;

	public static final int C_CHARGE_ID_GESAMTAUFTRAGSRABATT = 501671;

	public static final int C_GREETING_ID_FRAU = 501635;

	public static final int C_GREETING_ID_HERR = 501634;

	public static final int C_GREETING_ID_FIRMA = 500069;

	public static final String REPLENISHTYPE_EnsureFutureQty = "7";
}
