package de.metas.vertical.pharma.securpharm.actions;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableSet;

import de.metas.inventory.InventoryId;
import de.metas.util.Services;
import de.metas.vertical.pharma.securpharm.model.I_M_Securpharm_Action_Result;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProductId;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.securpharm
     
 * #L%
 */

@Repository
public class SecurPharmaActionRepository
{
	public Set<SecurPharmProductId> getProductIdsByInventoryId(@NonNull final InventoryId inventoryId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Securpharm_Action_Result.class)
				.addEqualsFilter(I_M_Securpharm_Action_Result.COLUMN_M_Inventory_ID, inventoryId)
				.addNotNull(I_M_Securpharm_Action_Result.COLUMNNAME_M_Securpharm_Productdata_Result_ID)
				.create()
				.listDistinct(I_M_Securpharm_Action_Result.COLUMNNAME_M_Securpharm_Productdata_Result_ID, Integer.class)
				.stream()
				.map(SecurPharmProductId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public void save(@NonNull final DecommissionResponse response)
	{
		if (response.getId() != null)
		{
			throw new AdempiereException("Response was already saved: " + response);
		}

		final I_M_Securpharm_Action_Result record = newInstance(I_M_Securpharm_Action_Result.class);

		record.setIsError(response.isError());
		record.setAction(SecurPharmAction.DECOMMISSION.getCode());
		record.setM_Inventory_ID(InventoryId.toRepoId(response.getInventoryId()));
		record.setM_Securpharm_Productdata_Result_ID(response.getProductId().getRepoId());
		record.setTransactionIDServer(response.getServerTransactionId());

		saveRecord(record);
		response.setId(SecurPharmActionResultId.ofRepoId(record.getM_Securpharm_Action_Result_ID()));
	}

	public void save(@NonNull final UndoDecommissionResponse response)
	{
		if (response.getId() != null)
		{
			throw new AdempiereException("Response was already saved: " + response);
		}

		final I_M_Securpharm_Action_Result record = newInstance(I_M_Securpharm_Action_Result.class);

		record.setIsError(response.isError());
		record.setAction(SecurPharmAction.UNDO_DECOMMISSION.getCode());
		record.setM_Inventory_ID(InventoryId.toRepoId(response.getInventoryId()));
		record.setM_Securpharm_Productdata_Result_ID(response.getProductId().getRepoId());
		record.setTransactionIDServer(response.getServerTransactionId());

		saveRecord(record);
		response.setId(SecurPharmActionResultId.ofRepoId(record.getM_Securpharm_Action_Result_ID()));
	}

}
