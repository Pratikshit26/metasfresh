package de.metas.order.compensationGroup;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.eevolution.api.ProductBOMId;

import javax.annotation.Nullable;
import java.util.Optional;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@Value
@Builder
public class OrderGroupInfo
{
	@NonNull
	GroupId groupId;

	@NonNull
	String name;

	@NonNull
	Optional<ProductBOMId> bomId;

	@Nullable
	GroupCompensationOrderBy groupCompensationOrderBy;
}
