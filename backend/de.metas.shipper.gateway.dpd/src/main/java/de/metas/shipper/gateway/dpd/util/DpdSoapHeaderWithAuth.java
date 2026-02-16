/*
 * #%L
 * de.metas.shipper.gateway.dpd
     
 * #L%
 */

package de.metas.shipper.gateway.dpd.util;

import com.dpd.common.ws.authentication.v2_0.types.Authentication;
import com.dpd.common.ws.authentication.v2_0.types.ObjectFactory;
import com.dpd.common.ws.loginservice.v2_0.types.Login;
import de.metas.shipper.gateway.dpd.DpdConstants;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class DpdSoapHeaderWithAuth implements WebServiceMessageCallback
{
	private final Login login;

	public DpdSoapHeaderWithAuth(@NonNull final Login login)
	{
		this.login = login;
	}

	// thx to https://www.devglan.com/spring-mvc/custom-header-in-spring-soap-request
	// 		for the SoapHeader reference
	@Override
	public void doWithMessage(final WebServiceMessage message)
	{
		try
		{
			final Authentication authentication = new ObjectFactory().createAuthentication();
			authentication.setDelisId(login.getDelisId());
			authentication.setAuthToken(login.getAuthToken());
			authentication.setMessageLanguage(DpdConstants.DEFAULT_MESSAGE_LANGUAGE);

			// add the authentication to header
			final SoapMessage soapMessage = (SoapMessage)message;
			final SoapHeader header = soapMessage.getSoapHeader();

			final JAXBContext context = JAXBContext.newInstance(authentication.getClass());
			final javax.xml.bind.Marshaller marshaller = context.createMarshaller();
			marshaller.marshal(authentication, header.getResult());
		}
		catch (final JAXBException e)
		{
			throw new AdempiereException("Error while setting soap header", e);
		}
	}
}
