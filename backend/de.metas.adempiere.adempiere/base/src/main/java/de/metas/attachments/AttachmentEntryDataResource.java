package de.metas.attachments;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

import javax.annotation.Nullable;

import org.springframework.core.io.AbstractResource;

import com.google.common.base.MoreObjects;

import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

public final class AttachmentEntryDataResource extends AbstractResource
{
	private final byte[] source;
	private final String filename;
	private final String description;

	@Builder
	private AttachmentEntryDataResource(
			@Nullable final byte[] source,
			@NonNull final String filename,
			@Nullable final String description)
	{
		this.source = source != null ? source : new byte[] {};
		this.filename = filename;
		this.description = description;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("filename", filename)
				.add("description", description)
				.add("contentLength", source.length)
				.toString();
	}

	@Override
	public int hashCode()
	{
		return 1;
	}

	@Override
	public boolean equals(final Object other)
	{
		if (other instanceof AttachmentEntryDataResource)
		{
			return Arrays.equals(source, ((AttachmentEntryDataResource)other).source);
		}
		else
		{
			return false;
		}
	}

	@Override
	@NonNull
	public String getFilename()
	{
		return filename;
	}

	@Override
	public String getDescription()
	{
		return description;
	}

	@Override
	public long contentLength()
	{
		return source.length;
	}

	@Override
	public InputStream getInputStream()
	{
		return new ByteArrayInputStream(source);
	}

}
