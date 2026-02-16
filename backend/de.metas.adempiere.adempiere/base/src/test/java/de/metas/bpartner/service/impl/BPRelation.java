package de.metas.bpartner.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.compiere.model.I_C_BP_Relation;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import lombok.Builder;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Builder
public class BPRelation
{

	private boolean billTo;

	private final BPartnerId bpartnerId;

	private final BPartnerLocationId bpLocationId;

	private final BPartnerId relBPartnerId;

	private final BPartnerLocationId relBPLocationId;

	private final String name;

	public I_C_BP_Relation createRecord()
	{
		final I_C_BP_Relation bpRelation = newInstance(I_C_BP_Relation.class);
		bpRelation.setC_BPartner_ID(bpartnerId.getRepoId());
		bpRelation.setC_BPartner_Location_ID(bpLocationId.getRepoId());
		bpRelation.setC_BPartnerRelation_ID(relBPartnerId.getRepoId());
		bpRelation.setC_BPartnerRelation_Location_ID(relBPLocationId.getRepoId());
		bpRelation.setIsBillTo(billTo);
		bpRelation.setName(name);
		saveRecord(bpRelation);

		return bpRelation;
	}
}
