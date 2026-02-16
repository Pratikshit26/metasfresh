/*
 * #%L
 * de.metas.edi
     
 * #L%
 */

package de.metas.edi.api;

import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum ZebraPrinterSysConfigType
{
	SQL_SELECT("de.metas.handlingunit.sscc18Label.zebra.sql-select", Boolean.TRUE),
	HEADER_LINE_1("de.metas.handlingunit.sscc18Label.zebra.header.line-1", Boolean.TRUE),
	HEADER_LINE_2("de.metas.handlingunit.sscc18Label.zebra.header.line-2", Boolean.TRUE),
	FILE_ENCODING("de.metas.handlingunit.sscc18Label.zebra.encoding", Boolean.TRUE);

	private final String sysConfigName;
	private final boolean mandatory;

	public static List<ZebraPrinterSysConfigType> listMandatoryConfigs()
	{
		return Arrays.stream(ZebraPrinterSysConfigType.values())
				.filter(ZebraPrinterSysConfigType::isMandatory)
				.collect(ImmutableList.toImmutableList());
	}
}
