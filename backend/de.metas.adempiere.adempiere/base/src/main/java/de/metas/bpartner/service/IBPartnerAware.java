package de.metas.bpartner.service;

import org.compiere.model.I_C_BPartner;

 

/**
 * {@link org.adempiere.model.InterfaceWrapperHelper#asColumnReferenceAwareOrNull(Object, Class)} to obtain an instance.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IBPartnerAware
{
	int getC_BPartner_ID();

	I_C_BPartner getC_BPartner();
}
