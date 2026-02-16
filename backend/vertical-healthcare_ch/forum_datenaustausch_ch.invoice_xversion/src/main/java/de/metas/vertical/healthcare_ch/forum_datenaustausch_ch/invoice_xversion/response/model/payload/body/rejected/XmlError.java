package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.payload.body.rejected;

import java.math.BigInteger;

import javax.annotation.Nullable;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_xversion
     
 * #L%
 */

@Value
@Builder
public class XmlError
{
	@NonNull
	String code;

	@NonNull
	String text;

	@Nullable
	String errorValue;

	@Nullable
	String validValue;

	@Nullable
	BigInteger recordId;
}
