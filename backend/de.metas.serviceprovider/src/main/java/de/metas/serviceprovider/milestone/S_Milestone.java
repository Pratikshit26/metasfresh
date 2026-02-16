/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.milestone;

import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.organization.OrgId;
import de.metas.serviceprovider.external.reference.ExternalServiceReferenceType;
import de.metas.serviceprovider.model.I_S_Milestone;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_S_Milestone.class)
@Component
public class S_Milestone
{
	private final ExternalReferenceRepository externalReferenceRepository;

	public S_Milestone(@NonNull final ExternalReferenceRepository externalReferenceRepository)
	{
		this.externalReferenceRepository = externalReferenceRepository;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void afterDelete(@NonNull final I_S_Milestone record){
		externalReferenceRepository.deleteByRecordIdAndType(record.getS_Milestone_ID(), ExternalServiceReferenceType.MILESTONE_ID);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_S_Milestone.COLUMNNAME_AD_Org_ID)
	public void updateLinkedExternalReferencesOrgId(@NonNull final I_S_Milestone record)
	{
			externalReferenceRepository.updateOrgIdByRecordIdAndType(record.getS_Milestone_ID(), ExternalServiceReferenceType.MILESTONE_ID, OrgId.ofRepoId(record.getAD_Org_ID()));
	}

}
