package de.metas.handlingunits.generichumodel;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

/*
 * #%L
 * de.metas.handlingunits.base
     
 * #L%
 */

@Value
@Builder
public class PackagingCode
{
	public static final PackagingCode NONE = PackagingCode.builder()
			.id(PackagingCodeId.ofRepoId(Integer.MAX_VALUE))
			.onlyForType(Optional.empty())
			.value("NONE")
			.build();

	@NonNull
	PackagingCodeId id;

	@NonNull
	Optional<HUType> onlyForType;

	@NonNull
	String value;

	public boolean isNone()
	{
		return this.equals(NONE);
	}
}
