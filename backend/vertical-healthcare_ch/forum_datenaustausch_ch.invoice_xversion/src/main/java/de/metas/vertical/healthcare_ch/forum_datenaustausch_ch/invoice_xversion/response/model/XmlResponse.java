package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_xversion
     
 * #L%
 */

@Value
@Builder(toBuilder = true)
public class XmlResponse
{
	@NonNull
	XmlPayload payload;
}
