/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.milestone;

import de.metas.serviceprovider.model.I_S_Milestone;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class MilestoneRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void save(@NonNull final Milestone milestone)
	{
		final I_S_Milestone record = InterfaceWrapperHelper.loadOrNew(milestone.getMilestoneId(), I_S_Milestone.class);

		if (milestone.getDueDate() != null)
		{
			record.setMilestone_DueDate(Timestamp.from(milestone.getDueDate()));
		}

		record.setAD_Org_ID(milestone.getOrgId().getRepoId());
		record.setName(milestone.getName());
		record.setValue(milestone.getValue());
		record.setDescription(milestone.getDescription());
		record.setProcessed(milestone.isProcessed());

		record.setExternalUrl(milestone.getExternalURL());

		InterfaceWrapperHelper.saveRecord(record);

		milestone.setMilestoneId(MilestoneId.ofRepoId(record.getS_Milestone_ID()));
	}

	public boolean exists(@NonNull final MilestoneId milestoneId)
	{
		return queryBL
				.createQueryBuilder(I_S_Milestone.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_S_Milestone.COLUMNNAME_S_Milestone_ID, milestoneId.getRepoId())
				.create()
				.anyMatch();
	}
}
