package de.metas.tax.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@Value
public class TaxId implements RepoIdAware
{
	@JsonCreator
	@NonNull
	public static TaxId ofRepoId(final int repoId)
	{
		return new TaxId(repoId);
	}

	@Nullable
	public static TaxId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static Optional<TaxId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(@Nullable final TaxId id)
	{
		return id != null ? id.getRepoId() : -1;
	}

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	public static int toRepoId(@Nullable final Optional<TaxId> optional)
	{
		//noinspection OptionalAssignedToNull
		final TaxId id = optional != null ? optional.orElse(null) : null;
		return toRepoId(id);
	}

	public static int toRepoIdOrNoTaxId(@Nullable final TaxId id)
	{
		return id != null ? id.getRepoId() : Tax.C_TAX_ID_NO_TAX_FOUND;
	}

	int repoId;

	private TaxId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "C_Tax_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

	public boolean isNoTaxId()
	{
		return repoId == Tax.C_TAX_ID_NO_TAX_FOUND;
	}

	public static boolean equals(@Nullable TaxId taxId1, @Nullable TaxId taxId2) {return Objects.equals(taxId1, taxId2);}
}
