/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

package de.metas.location;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Value;

@Value
@JsonDeserialize(builder = CountryCode.CountryCodeBuilder.class)
public class CountryCode
{
	@JsonProperty("alpha2")
	String alpha2;

	@JsonProperty("alpha3")
	String alpha3;

	@Builder
	public CountryCode(final String alpha2, final String alpha3)
	{
		Check.assumeNotEmpty(alpha2, "alpha2 is not empty");
		Check.assumeNotEmpty(alpha3, "alpha3 is not empty");

		this.alpha2 = alpha2;
		this.alpha3 = alpha3;
	}

}
