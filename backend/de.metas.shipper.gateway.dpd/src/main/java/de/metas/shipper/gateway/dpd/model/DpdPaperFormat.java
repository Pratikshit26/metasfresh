/*
 * #%L
 * de.metas.shipper.gateway.dpd
     
 * #L%
 */

package de.metas.shipper.gateway.dpd.model;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.shipper.gateway.spi.model.PackageLabelType;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Arrays;

public enum DpdPaperFormat implements PackageLabelType, ReferenceListAwareEnum
{
	PAPER_FORMAT_A6(X_DPD_Shipper_Config.PAPERFORMAT_A6),
	PAPER_FORMAT_A5(X_DPD_Shipper_Config.PAPERFORMAT_A5),
	PAPER_FORMAT_A4(X_DPD_Shipper_Config.PAPERFORMAT_A4);

	@Getter
	private final String code;

	DpdPaperFormat(final String code)
	{
		this.code = code;
	}

	public static DpdPaperFormat ofCode(@NonNull final String code)
	{
		final DpdPaperFormat type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + DpdPaperFormat.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, DpdPaperFormat> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), DpdPaperFormat::getCode);

}
