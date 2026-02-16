package de.metas.material.event.attributes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.commons.EventDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;

import java.math.BigDecimal;
import java.time.Instant;

import static de.metas.material.event.MaterialEventConstants.M_HU_TABLE_NAME;

/*
 * #%L
 * metasfresh-material-event
     
 * #L%
 */

@Value
@Builder
public class AttributesChangedEvent implements MaterialEvent
{
	public static final String TYPE = "AttributesChangedEvent";

	EventDescriptor eventDescriptor;

	@NonNull
	WarehouseId warehouseId;

	@NonNull
	Instant date;

	int productId;

	@NonNull
	BigDecimal qty;

	@NonNull
	AttributesKeyWithASI oldStorageAttributes;
	@NonNull
	AttributesKeyWithASI newStorageAttributes;

	int huId;

	@JsonCreator
	@Builder
	private AttributesChangedEvent(
			@JsonProperty("eventDescriptor") final EventDescriptor eventDescriptor,
			@JsonProperty("warehouseId") @NonNull final WarehouseId warehouseId,
			@JsonProperty("date") @NonNull final Instant date,
			@JsonProperty("productId") final int productId,
			@JsonProperty("qty") @NonNull final BigDecimal qty,
			@JsonProperty("oldStorageAttributes") @NonNull final AttributesKeyWithASI oldStorageAttributes,
			@JsonProperty("newStorageAttributes") @NonNull final AttributesKeyWithASI newStorageAttributes,
			@JsonProperty("huId") final int huId)
	{
		this.eventDescriptor = eventDescriptor;
		this.warehouseId = warehouseId;
		this.date = date;
		this.productId = productId;
		this.qty = qty;
		this.oldStorageAttributes = oldStorageAttributes;
		this.newStorageAttributes = newStorageAttributes;
		this.huId = huId;
	}

	@Override
	public TableRecordReference getSourceTableReference()
	{
		return TableRecordReference.of(M_HU_TABLE_NAME, huId);
	}

	@Override
	public String getEventName() {return TYPE;}
}
