package de.metas.location;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;

import javax.annotation.Nullable;
import java.util.Objects;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
@Builder(toBuilder = true)
public class LocationCreateRequest
{
	@Nullable LocationId existingLocationId;

	@Nullable String address1;
	@Nullable String address2;
	@Nullable String address3;
	@Nullable String address4;

	@Nullable @With PostalId postalId;
	@Nullable String postal;
	@Nullable String postalAdd;

	@Nullable String city;

	int regionId;
	@Nullable String regionName;

	@NonNull CountryId countryId;

	@Nullable String poBox;

	public static boolean equals(@Nullable LocationCreateRequest request1, @Nullable LocationCreateRequest request2) {return Objects.equals(request1, request2);}
}
