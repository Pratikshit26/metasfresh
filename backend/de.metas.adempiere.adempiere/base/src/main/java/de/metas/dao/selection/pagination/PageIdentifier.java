package de.metas.dao.selection.pagination;

import static de.metas.util.Check.assume;

import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
@Builder
public class PageIdentifier
{
	public static final String SEPARATOR = "_";
	private static final Splitter SPLITTER = Splitter.on(SEPARATOR).trimResults();
	private static final Joiner JOINER = Joiner.on(SEPARATOR);

	public static PageIdentifier ofCombinedString(@NonNull String completePageId)
	{
		final List<String> split = SPLITTER.splitToList(completePageId);
		assume(split.size() == 2, "Param completePageId needs to consist of two components; completePageId={}", completePageId);

		return new PageIdentifier(split.get(0), split.get(1));
	}

	@NonNull
	String selectionUid;

	@NonNull
	String pageUid;

	public String getCombinedUid()
	{
		return JOINER.join(selectionUid, pageUid);
	}
}
