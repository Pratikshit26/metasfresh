/*
 * #%L
 * de.metas.shipper.gateway.dhl
     
 * #L%
 */

package de.metas.shipper.gateway.dpd.logger;

import com.google.common.annotations.VisibleForTesting;
import de.metas.shipper.gateway.dpd.model.DpdClientConfig;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.oxm.Marshaller;
import org.springframework.xml.transform.StringResult;

import javax.annotation.Nullable;

@Value
@Builder
public class DpdClientLogEvent
{
	/**
	 * Regexp explanation: .*? means ungreedy (while .* means greedy).
	 */
	private static final String PARCELLABELS_PDF_REGEX = "(?m)<parcellabelsPDF>(.*?\\s*?)</parcellabelsPDF>";
	private static final String PARCELLABELS_PDF_REPLACEMENT_TEXT = "<parcellabelsPDF>PDF TEXT REMOVED!</parcellabelsPDF>";

	int deliveryOrderRepoId;
	DpdClientConfig config;

	Marshaller marshaller;
	Object requestElement;

	@Nullable
	Object responseElement;

	@Nullable
	Exception responseException;

	long durationMillis;

	String getConfigSummary()
	{
		return config != null ? config.toString() : "";
	}

	@Nullable
	String getRequestAsString()
	{
		return elementToString(requestElement);
	}

	@Nullable
	String getResponseAsString()
	{
		return elementToString(responseElement);
	}

	@Nullable
	private String elementToString(@Nullable final Object element)
	{
		if (element == null)
		{
			return null;
		}

		try
		{
			final StringResult result = new StringResult();
			marshaller.marshal(element, result);

			return cleanupPdfData(result.toString());
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed converting " + element + " to String", ex);
		}
	}

	/**
	 * remove the pdfdata since it's long and useless and we also attach it to the PO record
	 */
	@NonNull
	@VisibleForTesting
	static String cleanupPdfData(@NonNull final String s)
	{
		return s.replaceAll(PARCELLABELS_PDF_REGEX, PARCELLABELS_PDF_REPLACEMENT_TEXT);
	}
}
