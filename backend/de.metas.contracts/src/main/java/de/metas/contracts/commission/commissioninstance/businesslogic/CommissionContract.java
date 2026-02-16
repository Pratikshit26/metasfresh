package de.metas.contracts.commission.commissioninstance.businesslogic;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.hierarchy.HierarchyContract;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

/*
 * #%L
 * de.metas.commission
     
 * #L%
 */

/** Contains settings that can vary between different {@link Beneficiary} even within the same commission instance. */
@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
		@JsonSubTypes.Type(value = HierarchyContract.class, name = "HierarchyContract"),
})
public interface CommissionContract
{
	/** @return never {@code null} */
	FlatrateTermId getId();

	boolean isSimulation();
}
