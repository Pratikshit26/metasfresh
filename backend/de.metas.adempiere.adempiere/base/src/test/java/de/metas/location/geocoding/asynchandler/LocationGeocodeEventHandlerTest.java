/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

package de.metas.location.geocoding.asynchandler;

import de.metas.event.impl.PlainEventBusFactory;
import de.metas.location.LocationId;
import de.metas.location.geocoding.GeoCoordinatesRequest;
import de.metas.location.geocoding.GeocodingService;
import de.metas.location.geocoding.GeographicalCoordinates;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.X_C_Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class LocationGeocodeEventHandlerTest
{
	private GeocodingService geocodingService;
	private LocationGeocodeEventHandler eventHandler;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		geocodingService = Mockito.mock(GeocodingService.class);

		eventHandler = new LocationGeocodeEventHandler(
				PlainEventBusFactory.newInstance(),
				geocodingService);
	}

	@Test
	public void locationIsFound()
	{
		final BigDecimal latitude = BigDecimal.valueOf(50.6583491);
		final BigDecimal longitude = BigDecimal.valueOf(7.16960605354244);

		final String address1 = "Am Nossbacher Weg 2";
		final String postal = "53179";
		final String city = "Bonn";

		final I_C_Location location;
		{
			final I_C_Country countryDE = InterfaceWrapperHelper.newInstance(I_C_Country.class);
			countryDE.setCountryCode("DE");
			InterfaceWrapperHelper.saveRecord(countryDE);

			location = InterfaceWrapperHelper.newInstance(I_C_Location.class);
			location.setAddress1(address1);
			location.setPostal(postal);
			location.setCity(city);
			location.setC_Country_ID(countryDE.getC_Country_ID());
			InterfaceWrapperHelper.saveRecord(location);
		}

		Mockito.doReturn(Optional.of(GeographicalCoordinates.builder().latitude(latitude).longitude(longitude).build()))
				.when(geocodingService)
				.findBestCoordinates(GeoCoordinatesRequest.builder()
						.countryCode2("DE")
						.address(address1)
						.postal(postal)
						.city(city)
						.build());

		// create request
		final LocationGeocodeEventRequest locationGeocodeEventRequest = LocationGeocodeEventRequest.of(LocationId.ofRepoId(location.getC_Location_ID()));
		eventHandler.handleEvent(locationGeocodeEventRequest);

		InterfaceWrapperHelper.refresh(location);
		assertThat(location)
				.extracting(I_C_Location::getLatitude, I_C_Location::getLongitude, I_C_Location::getGeocodingStatus, I_C_Location::getGeocoding_Issue_ID)
				.containsExactly(latitude, longitude, X_C_Location.GEOCODINGSTATUS_Resolved, -1);
	}

	@Test
	public void locationIsNotFound()
	{
		final String address1 = "this";
		final String address2 = "location";
		final String postal = "doesn't ";
		final String city = "exist";

		final I_C_Location location;
		{
			final I_C_Country countryDE = InterfaceWrapperHelper.newInstance(I_C_Country.class);
			countryDE.setCountryCode("DE");
			InterfaceWrapperHelper.saveRecord(countryDE);

			location = InterfaceWrapperHelper.newInstance(I_C_Location.class);
			location.setAddress1(address1);
			location.setAddress2(address2);
			location.setPostal(postal);
			location.setCity(city);
			location.setC_Country_ID(countryDE.getC_Country_ID());
			InterfaceWrapperHelper.saveRecord(location);
		}

		Mockito.doReturn(Optional.empty())
				.when(geocodingService)
				.findBestCoordinates(GeoCoordinatesRequest.builder()
						.countryCode2("DE")
						.address(address1 + " " + address2)
						.postal(postal)
						.city(city)
						.build());

		// create request
		final LocationGeocodeEventRequest locationGeocodeEventRequest = LocationGeocodeEventRequest.of(LocationId.ofRepoId(location.getC_Location_ID()));
		eventHandler.handleEvent(locationGeocodeEventRequest);

		InterfaceWrapperHelper.refresh(location);
		assertThat(location)
				.extracting(I_C_Location::getLatitude, I_C_Location::getLongitude, I_C_Location::getGeocodingStatus, I_C_Location::getGeocoding_Issue_ID)
				.containsExactly(BigDecimal.ZERO, BigDecimal.ZERO, X_C_Location.GEOCODINGSTATUS_NotResolved, -1);
	}
}
