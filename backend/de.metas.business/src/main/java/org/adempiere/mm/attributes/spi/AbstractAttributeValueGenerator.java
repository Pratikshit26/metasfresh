package org.adempiere.mm.attributes.spi;

 


import java.math.BigDecimal;
import java.util.Date;
import java.util.Properties;

import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.compiere.model.I_M_Attribute;

/**
 * Base {@link IAttributeValueGenerator} implementation.
 * 
 * @author tsa
 *
 */
public abstract class AbstractAttributeValueGenerator implements IAttributeValueGenerator
{
	@Override
	public String generateStringValue(Properties ctx, IAttributeSet attributeSet, I_M_Attribute attribute)
	{
		throw new UnsupportedOperationException("Not supported");
	}

	@Override
	public BigDecimal generateNumericValue(Properties ctx, IAttributeSet attributeSet, I_M_Attribute attribute)
	{
		throw new UnsupportedOperationException("Not supported");
	}

	@Override
	public Date generateDateValue(Properties ctx, IAttributeSet attributeSet, I_M_Attribute attribute)
	{
		throw new UnsupportedOperationException("Not supported");
	}

	@Override
	public AttributeListValue generateAttributeValue(Properties ctx, int tableId, int recordId, boolean isSOTrx, String trxName)
	{
		throw new UnsupportedOperationException("Not supported");
	}

}
