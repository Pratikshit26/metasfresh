/*
 * #%L
 * de.metas.shipper.gateway.commons
     
 * #L%
 */

package de.metas.shipper.gateway.commons;

import de.metas.shipper.gateway.spi.model.Address;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;

public class ShipperTestHelper
{
	@NonNull
	public static I_C_Location createLocation(@NonNull final Address pickupAddress)
	{
		final I_C_Location pickupFromLocation = InterfaceWrapperHelper.newInstance(I_C_Location.class);
		pickupFromLocation.setAddress1(pickupAddress.getStreet1() + " " + pickupAddress.getHouseNo());
		pickupFromLocation.setAddress2(pickupAddress.getStreet2());
		pickupFromLocation.setPostal(pickupAddress.getZipCode());
		pickupFromLocation.setCity(pickupAddress.getCity());
		final I_C_Country i_c_country = InterfaceWrapperHelper.newInstance(I_C_Country.class);
		i_c_country.setCountryCode(pickupAddress.getCountry().getAlpha2());
		InterfaceWrapperHelper.save(i_c_country);
		pickupFromLocation.setC_Country(i_c_country);

		return pickupFromLocation;
	}

	@NonNull
	public static I_C_BPartner createBPartner(@NonNull final Address pickupAddress)
	{
		final I_C_BPartner pickupFromBPartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		pickupFromBPartner.setName(pickupAddress.getCompanyName1());
		pickupFromBPartner.setName2(pickupAddress.getCompanyName2());
		return pickupFromBPartner;
	}

	public static void dumpPdfToDisk(final byte[] pdf)
	{
		try
		{
			Files.write(Paths.get("C:", "a", Instant.now().toString().replace(":", ".") + ".pdf"), pdf);
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}
}
