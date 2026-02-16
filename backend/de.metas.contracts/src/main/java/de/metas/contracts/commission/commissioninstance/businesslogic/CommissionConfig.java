package de.metas.contracts.commission.commissioninstance.businesslogic;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.hierarchy.HierarchyConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTrigger;
import de.metas.product.ProductId;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

/*
 * #%L
 * de.metas.contracts
     
 * #L%
 */

/** Defines how a {@link CommissionInstance} is created for a given {@link CommissionTrigger} and {@link Hierarchy}. */
@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
		@JsonSubTypes.Type(value = HierarchyConfig.class, name = "HierarchyConfig"),
})
public interface CommissionConfig
{
	CommissionType getCommissionType();

	CommissionContract getContractFor(BPartnerId contractualBPartnerId);

	ProductId getCommissionProductId();
}
