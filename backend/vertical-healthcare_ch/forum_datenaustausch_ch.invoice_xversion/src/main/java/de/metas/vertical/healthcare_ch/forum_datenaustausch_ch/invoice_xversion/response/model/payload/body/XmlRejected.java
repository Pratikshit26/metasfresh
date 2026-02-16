package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.payload.body;

import java.util.List;

import javax.annotation.Nullable;

import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.payload.body.rejected.XmlError;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_xversion
     
 * #L%
 */

@Value
@Builder
public class XmlRejected
{
	@NonNull
	String statusIn;

	@NonNull
	String statusOut;

	@Nullable
	String explanation;

	@Singular
	List<XmlError> errors;
}
