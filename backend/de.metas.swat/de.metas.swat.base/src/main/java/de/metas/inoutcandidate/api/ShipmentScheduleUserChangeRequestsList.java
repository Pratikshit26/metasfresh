package de.metas.inoutcandidate.api;

import java.util.List;
import java.util.Set;

import de.metas.inout.ShipmentScheduleId;
import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.swat.base
     
 * #L%
 */

@ToString
@EqualsAndHashCode
public class ShipmentScheduleUserChangeRequestsList
{
	public static ShipmentScheduleUserChangeRequestsList of(@NonNull final List<ShipmentScheduleUserChangeRequest> userChanges)
	{
		return new ShipmentScheduleUserChangeRequestsList(userChanges);
	}

	private final ImmutableMap<ShipmentScheduleId, ShipmentScheduleUserChangeRequest> userChanges;

	private ShipmentScheduleUserChangeRequestsList(@NonNull final List<ShipmentScheduleUserChangeRequest> userChanges)
	{
		Check.assumeNotEmpty(userChanges, "userChanges is not empty");
		this.userChanges = Maps.uniqueIndex(userChanges, ShipmentScheduleUserChangeRequest::getShipmentScheduleId);
	}

	public Set<ShipmentScheduleId> getShipmentScheduleIds()
	{
		return userChanges.keySet();
	}

	public ShipmentScheduleUserChangeRequest getByShipmentScheduleId(final ShipmentScheduleId shipmentScheduleId)
	{
		final ShipmentScheduleUserChangeRequest userChange = userChanges.get(shipmentScheduleId);
		if (userChange == null)
		{
			throw new AdempiereException("No user change found for " + shipmentScheduleId);
		}
		return userChange;
	}
}
