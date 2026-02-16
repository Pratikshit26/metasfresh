package de.metas.material.cockpit.availableforsales;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * metasfresh-available-for-sales
     
 * #L%
 */

@Value
@Builder
public class AvailableForSalesLookupResult
{
	@Singular
	ImmutableList<AvailableForSalesLookupBucketResult> availableForSalesResults;
}
