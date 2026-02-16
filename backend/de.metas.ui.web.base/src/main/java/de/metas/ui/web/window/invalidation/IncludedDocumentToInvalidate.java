package de.metas.ui.web.window.invalidation;

import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Check;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.HashSet;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

@ToString
public final class IncludedDocumentToInvalidate
{
	@Getter
	private final String tableName;
	private boolean invalidateAll;
	private final HashSet<Integer> recordIds = new HashSet<>();

	IncludedDocumentToInvalidate(@NonNull final String tableName)
	{
		this.tableName = tableName;
	}

	public void invalidateAll()
	{
		if (invalidateAll)
		{
			return;
		}
		else
		{
			invalidateAll = true;
			recordIds.clear();
		}
	}

	public void addRecordId(final int recordId)
	{
		if (!invalidateAll)
		{
			recordIds.add(recordId);
		}
	}

	public DocumentIdsSelection toDocumentIdsSelection()
	{
		return invalidateAll
				? DocumentIdsSelection.ALL
				: DocumentIdsSelection.ofIntSet(recordIds);
	}

	IncludedDocumentToInvalidate combine(@NonNull final IncludedDocumentToInvalidate other)
	{
		Check.assumeEquals(tableName, other.tableName, "tableName");
		this.invalidateAll = this.invalidateAll || other.invalidateAll;

		if(invalidateAll)
		{
			this.recordIds.clear();
		}
		else
		{
			this.recordIds.addAll(other.recordIds);
		}

		return this;
	}
}
