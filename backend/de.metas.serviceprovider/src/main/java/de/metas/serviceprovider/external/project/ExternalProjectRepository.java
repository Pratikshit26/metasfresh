/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.external.project;

import com.google.common.collect.ImmutableList;
import de.metas.organization.OrgId;
import de.metas.project.ProjectId;
import de.metas.serviceprovider.external.ExternalSystem;
import de.metas.serviceprovider.model.I_S_ExternalProjectReference;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ExternalProjectRepository
{
	private final IQueryBL queryBL;

	public ExternalProjectRepository(final IQueryBL queryBL)
	{
		this.queryBL = queryBL;
	}

	@NonNull
	public ImmutableList<ExternalProjectReference> getByExternalSystem(@NonNull final ExternalSystem externalSystem)
	{
		return queryBL.createQueryBuilder(I_S_ExternalProjectReference.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_ExternalProjectReference.COLUMN_ExternalSystem, externalSystem.getCode())
				.orderBy(I_S_ExternalProjectReference.COLUMNNAME_SeqNo)
				.create()
				.list()
				.stream()
				.map(ExternalProjectRepository::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public Optional<ExternalProjectReference> getByRequestOptional(@NonNull final GetExternalProjectRequest getExternalProjectRequest)
	{
		return queryBL.createQueryBuilder(I_S_ExternalProjectReference.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_ExternalProjectReference.COLUMN_ExternalSystem, getExternalProjectRequest.getExternalSystem().getCode())
				.addEqualsFilter(I_S_ExternalProjectReference.COLUMNNAME_ExternalReference, getExternalProjectRequest.getExternalReference())
				.addEqualsFilter(I_S_ExternalProjectReference.COLUMNNAME_ExternalProjectOwner, getExternalProjectRequest.getExternalProjectOwner())
				.addEqualsFilter(I_S_ExternalProjectReference.COLUMNNAME_AD_Org_ID, getExternalProjectRequest.getOrgId())
				.create()
				.firstOnlyOptional(I_S_ExternalProjectReference.class)
				.map(ExternalProjectRepository::ofRecord);
	}

	@NonNull
	public ExternalProjectReference getById(@NonNull final ExternalProjectReferenceId externalProjectReferenceId)
	{
		final I_S_ExternalProjectReference record = InterfaceWrapperHelper.load(externalProjectReferenceId, I_S_ExternalProjectReference.class);
		return ofRecord(record);
	}

	@NonNull
	private static ExternalProjectReference ofRecord(@NonNull final I_S_ExternalProjectReference record)
	{
		final Optional<ExternalProjectType> externalProjectType = ExternalProjectType.getTypeByValue(record.getProjectType());

		if (!externalProjectType.isPresent())
		{
			throw new AdempiereException("Unknown project type!")
					.appendParametersToMessage()
					.setParameter(I_S_ExternalProjectReference.COLUMNNAME_S_ExternalProjectReference_ID, record.getS_ExternalProjectReference_ID())
					.setParameter(I_S_ExternalProjectReference.COLUMNNAME_ProjectType, record.getProjectType());
		}

		return ExternalProjectReference.builder()
				.externalProjectReferenceId(ExternalProjectReferenceId.ofRepoId(record.getS_ExternalProjectReference_ID()))
				.externalProjectReference(record.getExternalReference())
				.projectOwner(record.getExternalProjectOwner())
				.externalProjectType(externalProjectType.get())
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.projectId(ProjectId.ofRepoIdOrNull(record.getC_Project_ID()))
				.externalProjectReferenceEffortId(ExternalProjectReferenceId.ofRepoIdOrNull(record.getS_ExternalProjectReference_Effort_ID()))
				.build();
	}
}
