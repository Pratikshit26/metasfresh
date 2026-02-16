package de.metas.impexp.parser;

import de.metas.impexp.format.ImpFormat;
import de.metas.impexp.parser.csv.CsvImpDataParserFactory;
import de.metas.impexp.parser.xls.Excel97ImpDataParser;
import de.metas.util.FileUtil;
import lombok.NonNull;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

public class ImpDataParserFactory
{
	public ImpDataParser createParser(@NonNull final ImpFormat impFormat, @Nullable String filename)
	{
		final String fileExtension = FileUtil.getFileExtension(filename);

		if ("xls".equalsIgnoreCase(fileExtension))
		{
			return Excel97ImpDataParser.builder()
					.skipFirstNRows(impFormat.getSkipFirstNRows())
					.build();
		}
		else
		{
			return CsvImpDataParserFactory.createParser(impFormat);
		}
	}

}
