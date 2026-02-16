package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_440.response;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.response.model.XmlResponse;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.xmlunit.validation.Languages;
import org.xmlunit.validation.ValidationResult;
import org.xmlunit.validation.Validator;

import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_440.response
     
 * #L%
 */

@ExtendWith(SnapshotExtension.class)
class Invoice440ToCrossVersionModelToolTest
{
	private Invoice440ResponseConversionService invoice440ResponseConversionService;
	private Expect expect;

	@BeforeEach void init()
	{
		invoice440ResponseConversionService = new Invoice440ResponseConversionService();
	}

	@Test
	void toCrossVersionResponse()
	{
		final XmlResponse result = toCrossVersionResponseWithXmlFile("/Cancelation_KV_12345.xml");
		assertThat(result.getPayload().getInvoice().getRequestId()).isEqualTo("KV_12345"); // sortof smoke-test
		expect.serializer("orderedJson").toMatchSnapshot(result);
	}

	@SuppressWarnings({ "SameParameterValue", "UnnecessaryLocalVariable" }) private XmlResponse toCrossVersionResponseWithXmlFile(@NonNull final String inputXmlFileName)
	{
		final InputStream inputStream = createInputStream(inputXmlFileName);
		assertXmlIsValid(inputStream); // guard

		final XmlResponse xResponse = invoice440ResponseConversionService.toCrossVersionResponse(createInputStream(inputXmlFileName));
		return xResponse;
	}

	private InputStream createInputStream(@NonNull final String resourceName)
	{
		final InputStream xmlInput = this.getClass().getResourceAsStream(resourceName);
		assertThat(xmlInput).as("Unable to load resource %s", resourceName).isNotNull();

		return xmlInput;
	}

	private void assertXmlIsValid(@NonNull final InputStream inputStream)
	{
		final StreamSource xsdInvoice = new StreamSource(getClass().getResourceAsStream("/de/metas/vertical/healthcare_ch/forum_datenaustausch_ch/invoice_440/response/generalInvoiceResponse_440.xsd"));
		final StreamSource xsdEnc = new StreamSource(getClass().getResourceAsStream("/de/metas/vertical/healthcare_ch/forum_datenaustausch_ch/invoice_440/response/xenc-schema.xsd"));
		final StreamSource xsdSig = new StreamSource(getClass().getResourceAsStream("/de/metas/vertical/healthcare_ch/forum_datenaustausch_ch/invoice_440/response/xmldsig-core-schema.xsd"));

		final Validator v = Validator.forLanguage(Languages.W3C_XML_SCHEMA_NS_URI);
		v.setSchemaSources(xsdSig, xsdEnc, xsdInvoice); // the ordering is important for the validator to load them successfully

		final ValidationResult r = v.validateInstance(new StreamSource(inputStream));

		Assertions.assertTrue(r.isValid());
	}
}
