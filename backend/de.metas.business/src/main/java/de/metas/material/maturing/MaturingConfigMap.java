package de.metas.material.maturing;

import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.List;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

final class MaturingConfigMap
{
	private final ImmutableMap<MaturingConfigLineId, MaturingConfigLine> byId;
	private final ImmutableListMultimap<ProductId, MaturingConfigLine> byMaturedProductId;
	private final ImmutableListMultimap<ProductId, MaturingConfigLine> byFromProductId;

	public MaturingConfigMap(@NonNull final List<MaturingConfigLine> maturingConfigLines)
	{
		byId = Maps.uniqueIndex(maturingConfigLines, MaturingConfigLine::getId);

		byMaturedProductId = maturingConfigLines.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(MaturingConfigLine::getMaturedProductId, line -> line));

		byFromProductId = maturingConfigLines.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(MaturingConfigLine::getFromProductId, line -> line));
	}

	@NonNull
	public MaturingConfigLine getById(@NonNull final MaturingConfigLineId id)
	{
		final MaturingConfigLine line = byId.get(id);
		if (line == null)
		{
			throw new AdempiereException("@NotFound@ @M_MaturingConfig_Line_ID@: " + id);
		}
		return line;
	}

	public List<MaturingConfigLine> getByMaturedProductId(@NonNull final ProductId maturedProductId)
	{
		return byMaturedProductId.get(maturedProductId);
	}

	@NonNull
	public List<MaturingConfigLine> getByFromProductId(@NonNull final ProductId maturedProductId)
	{
		return byFromProductId.get(maturedProductId);
	}
}
