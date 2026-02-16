package de.metas.marketing.base.process;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.model.I_C_BPartner;

import de.metas.marketing.base.bpartner.DefaultAddressType;
import de.metas.marketing.base.model.CampaignId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * marketing-base
     
 * #L%
 */

@Value
@Builder
public class MKTG_ContactPerson_ProcessParams
{
	@Nullable
	private IQueryFilter<I_C_BPartner> selectionFilter;
	@NonNull
	private CampaignId campaignId;
	@Nullable
	private DefaultAddressType addresType;
	private boolean removeAllExistingContactsFromCampaign;

}
