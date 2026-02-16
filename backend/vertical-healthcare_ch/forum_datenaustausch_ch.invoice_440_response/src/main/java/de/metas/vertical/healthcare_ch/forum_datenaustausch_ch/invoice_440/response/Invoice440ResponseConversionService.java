package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.response;

import java.io.InputStream;

import javax.xml.bind.JAXBElement;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.ForumDatenaustauschChConstants;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.XmlVersion;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.CrossVersionResponseConverter;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.JaxbUtil;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.XmlResponse;
import lombok.NonNull;

/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_440.response
     
 * #L%
 */

@Service
@Profile(ForumDatenaustauschChConstants.PROFILE)
public class Invoice440ResponseConversionService implements CrossVersionResponseConverter
{
	public static final String INVOICE_440_RESPONSE_XSD = "http://www.forum-datenaustausch.ch/invoice generalInvoiceResponse_440.xsd";

	@Override
	public XmlResponse toCrossVersionResponse(@NonNull final InputStream xmlInput)
	{
		final JAXBElement<ResponseType> jaxbRequest = JaxbUtil.unmarshalToJaxbElement(xmlInput, ResponseType.class);

		return Invoice440ToCrossVersionModelTool.INSTANCE.toCrossVersionModel(jaxbRequest.getValue());
	}

	@Override
	public String getXsdName()
	{
		return INVOICE_440_RESPONSE_XSD;
	}

	@Override
	public XmlVersion getVersion()
	{
		return XmlVersion.v440;
	}

}
