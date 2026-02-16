package de.metas.material.cockpit.availableforsales;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;

/*
 * #%L
 * metasfresh-available-for-sales
     
 * #L%
 */

@Value
@Builder
public class AvailableForSalesMultiQuery
{
	@NonNull
	public static AvailableForSalesMultiQuery of(@NonNull final AvailableForSalesQuery availableForSalesQuery)
	{
		return new AvailableForSalesMultiQuery(ImmutableList.of(availableForSalesQuery));
	}

	@Singular
	List<AvailableForSalesQuery> availableForSalesQueries;
}
