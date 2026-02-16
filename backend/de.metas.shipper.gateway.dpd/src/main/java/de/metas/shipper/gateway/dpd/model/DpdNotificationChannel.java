/*
 * #%L
 * de.metas.shipper.gateway.dpd
     
 * #L%
 */

package de.metas.shipper.gateway.dpd.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Arrays;

public enum DpdNotificationChannel implements ReferenceListAwareEnum
{
	EMAIL("1"),
	SMS("3");

	@Getter
	private final String code;

	DpdNotificationChannel(final String code)
	{
		this.code = code;
	}

	@NonNull
	public static DpdNotificationChannel ofCode(@NonNull final String code)
	{
		final DpdNotificationChannel type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + DpdNotificationChannel.class + " found for code: " + code);
		}
		return type;
	}

	@NonNull
	public Integer toDpdDataFormat()
	{
		return Integer.valueOf(code);
	}

	private static final ImmutableMap<String, DpdNotificationChannel> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), DpdNotificationChannel::getCode);

}
