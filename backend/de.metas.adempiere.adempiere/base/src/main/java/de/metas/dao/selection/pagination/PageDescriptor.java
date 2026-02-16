package de.metas.dao.selection.pagination;

import static de.metas.util.Check.assumeNotEmpty;

import java.time.Instant;

import de.metas.util.lang.UIDStringUtil;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
public class PageDescriptor
{
	PageIdentifier pageIdentifier;

	int offset;

	int pageSize;

	int totalSize;

	Instant selectionTime;

	public static PageDescriptor createNew(
			@NonNull final String querySelectionUUID,
			final int pageSize,
			final int totalSize,
			@NonNull final Instant selectionTime)
	{
		return new PageDescriptor(
				querySelectionUUID,
				UIDStringUtil.createNext(),
				0,
				pageSize,
				totalSize,
				selectionTime);
	}

	/** Create another page descriptor for the next page. */
	public PageDescriptor createNext()
	{
		return PageDescriptor.builder()
				.selectionUid(pageIdentifier.getSelectionUid())
				.pageUid(UIDStringUtil.createNext())
				.offset(offset + pageSize)
				.pageSize(pageSize)
				.totalSize(totalSize)
				.selectionTime(selectionTime)
				.build();
	}

	@Builder
	private PageDescriptor(
			@NonNull final String selectionUid,
			@NonNull final String pageUid,
			final int offset,
			final int pageSize,
			final int totalSize,
			@NonNull final Instant selectionTime)
	{
		assumeNotEmpty(selectionUid, "Param selectionUid may not be empty");
		assumeNotEmpty(pageUid, "Param selectionUid may not be empty");

		this.pageIdentifier = PageIdentifier.builder()
				.selectionUid(selectionUid)
				.pageUid(pageUid)
				.build();

		this.offset = offset;
		this.pageSize = pageSize;

		this.totalSize = totalSize;
		this.selectionTime = selectionTime;
	}

	public PageDescriptor withSize(final int adjustedSize)
	{
		if (pageSize == adjustedSize)
		{
			return this;
		}
		return PageDescriptor.builder()
				.selectionUid(pageIdentifier.getSelectionUid())
				.pageUid(pageIdentifier.getPageUid())
				.offset(offset)
				.pageSize(adjustedSize)
				.totalSize(totalSize)
				.selectionTime(selectionTime)
				.build();
	}
}
