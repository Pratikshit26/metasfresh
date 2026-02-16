package de.metas.product;

import de.metas.material.planning.ddorder.DistributionNetworkId;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@Data
@Builder(toBuilder = true)
public class ProductPlanningSchema
{
	@Nullable
	private ProductPlanningSchemaId id;

	@NonNull
	private final ProductPlanningSchemaSelector selector;

	@NonNull
	private final OrgId orgId;

	@Nullable
	private final ResourceId plantId;

	@Nullable
	private final WarehouseId warehouseId;

	private final boolean attributeDependant;

	@Nullable
	private final UserId plannerId;

	@Nullable
	private final Boolean manufactured;

	private final boolean createPlan;
	private final boolean completeGeneratedDocuments;
	private final boolean pickDirectlyIfFeasible;

	@Nullable
	private final PPRoutingId routingId;

	// @Nullable
	private final DistributionNetworkId distributionNetworkId;

	@NonNull
	private final OnMaterialReceiptWithDestWarehouse onMaterialReceiptWithDestWarehouse;

	private final int manufacturingAggregationId;

	public ProductPlanningSchemaId getIdNotNull() {return Check.assumeNotNull(id, "id is set {}", this);}
}
