package de.metas.pricing;

import org.adempiere.mm.attributes.AttributeListValue;

 


import org.compiere.model.I_M_Attribute;

/**
 * Contains information about a particular pricing relevant attribute and its value. <br>
 * A list of instances can be obtained from {@link IPricingResult#getPricingAttributes()}, if there were attributes relevant for the price calculation.
 *
 */
public interface IPricingAttribute
{
	/**
	 * 
	 * @return never <code>null</code>
	 */
	I_M_Attribute getAttribute();

	/**
	 * Might be <code>null</code>
	 * 
	 * @return
	 */
	AttributeListValue getAttributeValue();
}
