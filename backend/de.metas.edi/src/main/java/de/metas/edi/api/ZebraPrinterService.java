/*
 * #%L
 * de.metas.edi
     
 * #L%
 */

package de.metas.edi.api;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.ZebraConfigId;
import de.metas.edi.api.impl.pack.EDIDesadvPackId;
import de.metas.process.PInstanceId;
import de.metas.report.ReportResultData;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Zebra_Config;
import org.compiere.util.DB;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZebraPrinterService
{
	private static final String SSCC18_FILE_NAME_TEMPLATE = "sscc_lables_:timestamp.csv";
	private static final String TIMESTAMP_PLACEHOLDER = ":timestamp";
	private static final String CSV_FORMAT = "text/csv";

	private final ZebraConfigRepository zebraConfigRepository = SpringContextHolder.instance.getBean(ZebraConfigRepository.class);

	/**
	 * Creates a CSV file for SSCC18 labels based on given {@link de.metas.esb.edi.model.I_EDI_Desadv_Pack_Item} IDs.
	 *
	 * @return {@link ReportResultData} containing information required for SSCC18 labels in CSV format.
	 */
	public ReportResultData createCSV_FileForSSCC18_Labels( final Collection<EDIDesadvPackId> desadvPackIds,
															final ZebraConfigId zebraConfigId,
															final PInstanceId pInstanceId )
	{
		final ZebraConfigId zebraConfigToUse = zebraConfigId != null ? zebraConfigId : zebraConfigRepository.getDefaultZebraConfigId();
		final I_AD_Zebra_Config zebraConfig = zebraConfigRepository.getById(zebraConfigToUse);

		DB.createT_Selection(pInstanceId, desadvPackIds, ITrx.TRXNAME_ThreadInherited);

		final ImmutableList<List<String>> resultRows = DB.getSQL_ResultRowsAsListsOfStrings(zebraConfig.getSQL_Select(),
																							Collections.singletonList(pInstanceId), ITrx.TRXNAME_ThreadInherited);

		Check.assumeNotEmpty(resultRows, "SSCC information records must be available!");

		final StringBuilder ssccLabelsInformationAsCSV = new StringBuilder(zebraConfig.getHeader_Line1());
		ssccLabelsInformationAsCSV.append("\n").append(zebraConfig.getHeader_Line2());

		final Joiner joiner = Joiner.on(",");

		resultRows.stream()
				.map(row -> row.stream().map(this::escapeCSV).collect(Collectors.toList()))
				.map(joiner::join)
				.forEach(row -> ssccLabelsInformationAsCSV.append("\n").append(row));

		return buildResult(ssccLabelsInformationAsCSV.toString(), zebraConfig.getEncoding());
	}

	private String escapeCSV(final String valueToEscape)
	{
		final String escapedQuote = "\"";
		return escapedQuote
				+ StringUtils.nullToEmpty(valueToEscape).replace(escapedQuote, escapedQuote + escapedQuote)
				+ escapedQuote;
	}

	private ReportResultData buildResult(final String fileData, final String fileEncoding)
	{
		final byte[] fileDataBytes = fileData.getBytes(Charset.forName(fileEncoding));

		return ReportResultData.builder()
				.reportData(new ByteArrayResource(fileDataBytes))
				.reportFilename(SSCC18_FILE_NAME_TEMPLATE.replace(TIMESTAMP_PLACEHOLDER, String.valueOf(System.currentTimeMillis())))
				.reportContentType(CSV_FORMAT)
				.build();
	}
}
