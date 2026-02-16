package de.metas.bpartner;

import de.metas.bpartner.service.IBPGroupDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_BP_Group;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.loadOrNew;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Repository
public class BPGroupRepository
{
	public BPGroup getbyId(@NonNull final BPGroupId groupId)
	{
		return ofRecord(loadOutOfTrx(groupId, I_C_BP_Group.class)).get();
	}

	public Optional<BPGroup> getByNameAndOrgId(@NonNull final String name, @NonNull final OrgId orgId)
	{
		final I_C_BP_Group groupRecord = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BP_Group.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BP_Group.COLUMNNAME_Name, name)
				.addInArrayFilter(I_C_BP_Group.COLUMNNAME_AD_Org_ID, orgId, OrgId.ANY)
				.create()
				.firstOnly(I_C_BP_Group.class);

		return ofRecord(groupRecord);
	}

	public Optional<BPGroup> getDefaultGroup()
	{
		final I_C_BP_Group groupRecord = Services.get(IBPGroupDAO.class)
				.getDefaultByClientId(ClientId.METASFRESH);

		return ofRecord(groupRecord);
	}

	private Optional<BPGroup> ofRecord(@Nullable final I_C_BP_Group groupRecord)
	{
		if (groupRecord == null)
		{
			return Optional.empty();
		}

		return Optional.of(BPGroup.of(
				OrgId.ofRepoIdOrAny(groupRecord.getAD_Org_ID()),
				BPGroupId.ofRepoId(groupRecord.getC_BP_Group_ID()),
				groupRecord.getName()));

	}

	public BPGroupId save(@NonNull final BPGroup bpGroup)
	{
		final I_C_BP_Group groupRecord = loadOrNew(bpGroup.getId(), I_C_BP_Group.class);

		groupRecord.setAD_Org_ID(bpGroup.getOrgId().getRepoId());
		groupRecord.setName(bpGroup.getName());

		saveRecord(groupRecord);
		return BPGroupId.ofRepoId(groupRecord.getC_BP_Group_ID());
	}
}
