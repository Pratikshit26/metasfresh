package de.metas.impexp.parser.csv;

import de.metas.impexp.parser.ErrorMessage;
import de.metas.impexp.parser.ImpDataLine;
import de.metas.impexp.parser.ImpDataParser;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Util;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@ToString
final class CsvImpDataParser implements ImpDataParser
{
	private final boolean multiline;
	private final @NonNull CsvImpDataLineParser lineParser;
	private final @NonNull Charset charset;
	private final int skipFirstNRows;

	@Builder
	private CsvImpDataParser(
			final boolean multiline,
			@NonNull final CsvImpDataLineParser lineParser,
			@NonNull final Charset charset,
			final int skipFirstNRows
	)
	{
		this.multiline = multiline;
		this.lineParser = lineParser;
		this.charset = charset;
		this.skipFirstNRows = Math.max(skipFirstNRows,0);
	}

	@Override
	public Stream<ImpDataLine> streamDataLines(final Resource resource)
	{
		final AtomicInteger nextLineNo = new AtomicInteger(1);

		return streamSourceLines(resource)
				.skip(skipFirstNRows)
				.map(lineStr -> createImpDataLine(lineStr, nextLineNo));
	}

	private Stream<String> streamSourceLines(final Resource resource)
	{
		final byte[] data = toByteArray(resource);
		try
		{
			if (multiline)
			{
				return FileImportReader.readMultiLines(data, charset).stream();
			}
			else
			{
				return FileImportReader.readRegularLines(data, charset).stream();
			}
		}
		catch (final IOException ex)
		{
			throw new AdempiereException("Failed reading resource: " + resource, ex);
		}
	}

	private static byte[] toByteArray(final Resource resource)
	{
		try
		{
			return Util.readBytes(resource.getInputStream());
		}
		catch (final IOException ex)
		{
			throw new AdempiereException("Failed reading resource: " + resource, ex);
		}
	}

	private ImpDataLine createImpDataLine(final String lineStr, final AtomicInteger nextLineNo)
	{
		try
		{
			return ImpDataLine.builder()
					.fileLineNo(nextLineNo.getAndIncrement())
					.lineStr(lineStr)
					.cells(lineParser.parseDataCells(lineStr))
					.build();
		}
		catch (final Exception ex)
		{
			return ImpDataLine.builder()
					.fileLineNo(nextLineNo.getAndIncrement())
					.lineStr(lineStr)
					.parseError(ErrorMessage.of(ex))
					.build();
		}
	}
}
