package de.metas.rest_api.bpartner_pricelist;

import de.metas.Profiles;
import de.metas.common.util.time.SystemTime;
import de.metas.lang.SOTrx;
import de.metas.rest_api.bpartner_pricelist.command.GetPriceListCommand;
import de.metas.rest_api.bpartner_pricelist.response.JsonResponsePriceList;
import de.metas.rest_api.utils.IdentifierString;
import de.metas.util.Check;
import de.metas.util.web.MetasfreshRestAPIConstants;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.NonNull;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nullable;
import java.time.LocalDate;

import static de.metas.common.rest_api.v1.SwaggerDocConstants.BPARTNER_IDENTIFIER_DOC;

/*
 * #%L
 * de.metas.business.rest-api-impl
     
 * #L%
 */

@RequestMapping(value = {
		MetasfreshRestAPIConstants.ENDPOINT_API_DEPRECATED + "/bpartner",
		MetasfreshRestAPIConstants.ENDPOINT_API_V1 + "/bpartner",
		MetasfreshRestAPIConstants.ENDPOINT_API_V2 + "/bpartner" })
@RestController
@Profile(Profiles.PROFILE_App)
public class BpartnerPriceListRestController
{
	private final BpartnerPriceListServicesFacade servicesFacade;

	public BpartnerPriceListRestController(
			@NonNull final BpartnerPriceListServicesFacade servicesFacade)
	{
		this.servicesFacade = servicesFacade;
	}

	@GetMapping("/{bpartnerIdentifier}/sales/prices/{countryCode}")
	public ResponseEntity<JsonResponsePriceList> getSalesPriceList(
			@Parameter(required = true, description = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr,
			//
			@Parameter(required = true, description = "Country code (2 letters)") //
			@PathVariable("countryCode") //
			@NonNull final String countryCode,
			//
			@Parameter(required = false, description = "Date on which the prices shall be valid. The format is 'yyyy-MM-dd'.") //
			@RequestParam(name = "date", required = false) //
			@Nullable final String dateStr)
	{
		final IdentifierString bpartnerIdentifier = IdentifierString.of(bpartnerIdentifierStr);
		return getProductPrices(bpartnerIdentifier, SOTrx.SALES, countryCode, dateStr);
	}

	@GetMapping("/{bpartnerIdentifier}/purchase/prices/{countryCode}")
	public ResponseEntity<JsonResponsePriceList> getPurchasePriceList(
			@Parameter(required = true, description = BPARTNER_IDENTIFIER_DOC) //
			@PathVariable("bpartnerIdentifier") //
			@NonNull final String bpartnerIdentifierStr,
			//
			@Parameter(required = true, description = "Country code (2 letters)") //
			@PathVariable("countryCode") //
			@NonNull final String countryCode,
			//
			@Parameter(required = false, description = "Date on which the prices shall be valid. The format is 'yyyy-MM-dd'.") //
			@RequestParam(name = "date", required = false) //
			@Nullable final String dateStr)
	{
		final IdentifierString bpartnerIdentifier = IdentifierString.of(bpartnerIdentifierStr);
		return getProductPrices(bpartnerIdentifier, SOTrx.PURCHASE, countryCode, dateStr);
	}

	private ResponseEntity<JsonResponsePriceList> getProductPrices(
			@NonNull final IdentifierString bpartnerIdentifier,
			@NonNull final SOTrx soTrx,
			@NonNull final String countryCode,
			@Nullable final String dateStr)
	{
		try
		{
			final LocalDate date = !Check.isEmpty(dateStr) ? TimeUtil.asLocalDate(dateStr) : SystemTime.asLocalDate();

			final JsonResponsePriceList result = GetPriceListCommand.builder()
					.servicesFacade(servicesFacade)
					//
					.bpartnerIdentifier(bpartnerIdentifier)
					.soTrx(soTrx)
					.countryCode(countryCode)
					.date(date)
					.execute();

			return ResponseEntity.ok(result);
		}
		catch (final Exception ex)
		{
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(JsonResponsePriceList.error(ex, Env.getADLanguageOrBaseLanguage()));
		}
	}
}
