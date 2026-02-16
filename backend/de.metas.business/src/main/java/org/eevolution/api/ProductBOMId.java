package org.eevolution.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@Value
public class ProductBOMId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static ProductBOMId ofRepoId(final int repoId)
	{
		return new ProductBOMId(repoId);
	}

	@Nullable
	public static ProductBOMId ofRepoIdOrNull(@Nullable final Integer repoId)
	{
		return repoId != null && repoId > 0 ? new ProductBOMId(repoId) : null;
	}

	@Nullable
	public static ProductBOMId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new ProductBOMId(repoId) : null;
	}

	@NonNull
	public static Optional<ProductBOMId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	@NonNull
	public static Set<ProductBOMId> ofRepoIds(final Collection<Integer> repoIds)
	{
		return repoIds.stream()
				.filter(repoId -> repoId != null && repoId > 0)
				.map(ProductBOMId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public static int toRepoId(@Nullable final ProductBOMId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	public static boolean equals(
			@Nullable final ProductBOMId o1,
			@Nullable final ProductBOMId o2)
	{
		return Objects.equals(o1, o2);
	}

	private ProductBOMId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "PP_Product_BOM_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
