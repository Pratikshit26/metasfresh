package de.metas.contracts.commission.commissioninstance.services;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionAlgorithm;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionType;
import de.metas.contracts.commission.commissioninstance.businesslogic.CreateCommissionSharesRequest;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionShare;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerChange;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/*
 * #%L
 * de.metas.contracts
     
 * #L%
 */

@Service
public class CommissionAlgorithmInvoker
{
	@NonNull
	private final Map<CommissionType,CommissionAlgorithmFactory> commissionType2AlgorithmFactory;

	public CommissionAlgorithmInvoker(@NonNull final List<CommissionAlgorithmFactory> commissionAlgorithmFactories)
	{
		this.commissionType2AlgorithmFactory = Maps.uniqueIndex(commissionAlgorithmFactories, CommissionAlgorithmFactory::getSupportedCommissionType);
	}

	@NonNull
	public ImmutableList<CommissionShare> createCommissionShares(@NonNull final CreateCommissionSharesRequest request)
	{
		try
		{
			final ImmutableList<CommissionType> commissionTypes = CollectionUtils.extractDistinctElements(
					request.getConfigs(),
					CommissionConfig::getCommissionType);

			final ImmutableList.Builder<CommissionShare> result = ImmutableList.builder();
			for (final CommissionType commissionType : commissionTypes)
			{
				try (final MDCCloseable ignore = MDC.putCloseable("commissionType", commissionType.name()))
				{
					final CommissionAlgorithm algorithm;
					algorithm = createAlgorithmInstance(commissionType);

					// invoke the algorithm
					final ImmutableList<CommissionShare> sharesFromAlgorithm = algorithm.createCommissionShares(request);
					result.addAll(sharesFromAlgorithm);
				}
			}
			return result.build();
		}
		catch (final RuntimeException e)
		{
			throw AdempiereException.wrapIfNeeded(e).setParameter("request", request); // augment&rethrow
		}
	}

	public void updateCommissionShares(@NonNull final CommissionTriggerChange change)
	{
		try
		{
			final ImmutableList<CommissionType> commissionTypes = CollectionUtils.extractDistinctElements(
					change.getInstanceToUpdate().getShares(),
					share -> share.getConfig().getCommissionType());

			for (final CommissionType commissionType : commissionTypes)
			{
				try (final MDCCloseable ignore = MDC.putCloseable("commissionType", commissionType.name()))
				{
					final CommissionAlgorithm algorithm = createAlgorithmInstance(commissionType);

					// invoke the algorithm
					algorithm.applyTriggerChangeToShares(change);
				}
			}
		}
		catch (final RuntimeException e)
		{
			throw AdempiereException.wrapIfNeeded(e).setParameter("change", change);
		}
	}

	@NonNull
	private CommissionAlgorithm createAlgorithmInstance(@NonNull final CommissionType commissionType)
	{
		final CommissionAlgorithmFactory commissionAlgorithmFactory = commissionType2AlgorithmFactory.get(commissionType);

		if (commissionAlgorithmFactory != null)
		{
			return commissionAlgorithmFactory.instantiateAlgorithm();
		}

		final Class<? extends CommissionAlgorithm> algorithmClass = commissionType.getAlgorithmClass();
		final CommissionAlgorithm algorithm;
		try
		{
			algorithm = algorithmClass.newInstance();
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			throw new AdempiereException("Unable to instantiate commission algorithm from class " + algorithmClass)
					.appendParametersToMessage()
					.setParameter("commissionType", commissionType);
		}
		return algorithm;
	}
}
