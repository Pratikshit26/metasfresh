/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

package de.metas.shipping.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class ShippingPackageId implements RepoIdAware
{
	int repoId;

	@NonNull
	@JsonCreator
	public static ShippingPackageId ofRepoId(final int repoId)
	{
		return new ShippingPackageId(repoId);
	}

	@Nullable
	public static ShippingPackageId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	private ShippingPackageId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "M_ShippingPackage_ID");
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
