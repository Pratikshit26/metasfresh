package de.metas.vertical.pharma.securpharm.actions;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.inventory.InventoryId;
import de.metas.vertical.pharma.securpharm.product.SecurPharmProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-pharma.securpharm
     
 * #L%
 */

@Value
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class SecurPharmaActionRequest
{
	@JsonProperty("action")
	SecurPharmAction action;

	@JsonProperty("inventoryId")
	InventoryId inventoryId;

	@JsonProperty("productId")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	SecurPharmProductId productId;

	@Builder
	@JsonCreator
	private SecurPharmaActionRequest(
			@JsonProperty("action") @NonNull final SecurPharmAction action,
			@JsonProperty("inventoryId") @NonNull final InventoryId inventoryId,
			@JsonProperty("productId") @Nullable SecurPharmProductId productId)

	{
		this.action = action;
		this.inventoryId = inventoryId;
		this.productId = productId;
	}

}
