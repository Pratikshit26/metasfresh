/*
 * #%L
 * de.metas.rfq
     
 * #L%
 */

package de.metas.rfq;

import de.metas.rfq.model.I_C_RfQ;
import de.metas.rfq.model.interceptor.C_RfQ;
import de.metas.rfq.model.interceptor.C_RfQ_TabCallout;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.ui.api.ITabCalloutFactory;

public class RfQModuleInterceptor extends AbstractModuleInterceptor
{
	public static final transient RfQModuleInterceptor instance = new RfQModuleInterceptor();

	private RfQModuleInterceptor()
	{
		super();
	}

	@Override
	protected void registerCallouts(final IProgramaticCalloutProvider calloutsRegistry)
	{
		calloutsRegistry.registerAnnotatedCallout(new C_RfQ());
	}

	@Override
	protected void registerTabCallouts(final ITabCalloutFactory tabCalloutsRegistry)
	{
		tabCalloutsRegistry.registerTabCalloutForTable(I_C_RfQ.Table_Name, C_RfQ_TabCallout.class);
	}

}
