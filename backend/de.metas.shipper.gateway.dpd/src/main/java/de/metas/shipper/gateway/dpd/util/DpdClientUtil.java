/*
 * #%L
 * de.metas.shipper.gateway.dpd
     
 * #L%
 */

package de.metas.shipper.gateway.dpd.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;

@UtilityClass
public class DpdClientUtil
{

	@NonNull
	public static WebServiceTemplate createWebServiceTemplate()
	{
		final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setPackagesToScan(
				"com.dpd.common"
		);

		final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		//		webServiceTemplate.setDefaultUri(apiUrl); // there are different URLs for Login and Shipment creation, so nothing is set here.
		webServiceTemplate.setMarshaller(marshaller);
		webServiceTemplate.setUnmarshaller(marshaller);
		return webServiceTemplate;
	}
}
