package de.metas.vertical.pharma.msv3.server.peer.protocol;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

/*
 * #%L
 * metasfresh-pharma.msv3.server-peer
     
 * #L%
 */

@Value
public class MSV3MetasfreshUserId
{
	@JsonCreator
	public static MSV3MetasfreshUserId of(@JsonProperty("id") final int id)
	{
		return new MSV3MetasfreshUserId(id);
	}

	int id;

	private MSV3MetasfreshUserId(final int id)
	{
		this.id = id;
	}
}
