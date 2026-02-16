package de.metas.material.dispo.service.event.handler.pporder;

import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.pporder.PPOrderLine;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.util.Optional;

/*
 * #%L
 * metasfresh-material-dispo-service
     
 * #L%
 */

@UtilityClass
public final class PPOrderHandlerUtils
{
	public static CandidateType extractCandidateType(final PPOrderLine ppOrderLine)
	{
		return ppOrderLine.getPpOrderLineData().isReceipt() ? CandidateType.SUPPLY : CandidateType.DEMAND;
	}

	/**
	 * Creates and returns a {@link DemandDetail} for the given {@code supplyRequiredDescriptor},
	 * if the respective candidate should have one.
	 * Supply candidates that are about *another* product that the required one (i.e. co- and by-products) may not have that demand detail.
	 * (Otherwise, their stock candidate would be connected to the resp. demand record)
	 */
	@NonNull
	public static Optional<DemandDetail> computeDemandDetail(
			@NonNull final CandidateType lineCandidateType,
			@Nullable final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final MaterialDescriptor materialDescriptor)
	{
		if (supplyRequiredDescriptor == null)
		{
			return Optional.empty();
		}

		if (lineCandidateType == CandidateType.DEMAND)
		{
			return Optional.of(DemandDetail.forSupplyRequiredDescriptor(supplyRequiredDescriptor));
		}

		if (lineCandidateType == CandidateType.SUPPLY
				&& supplyRequiredDescriptor.getProductId() == materialDescriptor.getProductId()
				&& supplyRequiredDescriptor.getStorageAttributesKey().equals(materialDescriptor.getStorageAttributesKey()))
		{
			return Optional.of(DemandDetail.forSupplyRequiredDescriptor(supplyRequiredDescriptor));
		}

		return Optional.empty();
	}

	public static MaterialDescriptorQuery createMaterialDescriptorQuery(@NonNull final ProductDescriptor productDescriptor)
	{
		return MaterialDescriptorQuery.builder()
				.productId(productDescriptor.getProductId())
				.storageAttributesKey(productDescriptor.getStorageAttributesKey())
				.build();
	}
}
