package de.metas.common.ordercandidates.v1.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/*
 * #%L
 * de.metas.business.rest-api
     
 * #L%
 */

@Schema(enumAsRef = true, description = "JSONPaymentRule: \n" +
		"* `Paypal` - Specifies that the order will have paymentRule = Paypal\n" +
		"* `OnCredit` - Specifies that the order will have paymentRule = On Credit\n" +
		"* `DirectDebit` - Specifies that the order will have paymentRule = Direct Debit\n" +
		"")
public enum JSONPaymentRule
{
	Paypal("L"),
	OnCredit("P"),
	DirectDebit("D");

	@Getter
	private final String code;

	JSONPaymentRule(final String code)
	{
		this.code = code;
	}

}
