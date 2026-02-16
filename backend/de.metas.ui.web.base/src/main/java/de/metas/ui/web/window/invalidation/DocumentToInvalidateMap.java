package de.metas.ui.web.window.invalidation;

import lombok.NonNull;
import lombok.ToString;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

@ToString
final class DocumentToInvalidateMap
{
	private final HashMap<TableRecordReference, DocumentToInvalidate> documents = new HashMap<>();

	public DocumentToInvalidate getDocumentToInvalidate(final TableRecordReference rootDocumentRef)
	{
		return documents.computeIfAbsent(rootDocumentRef, DocumentToInvalidate::new);
	}

	public boolean isEmpty()
	{
		return documents.isEmpty();
	}

	public int size()
	{
		return documents.size();
	}

	public TableRecordReferenceSet getRootRecords()
	{
		return TableRecordReferenceSet.of(documents.keySet());
	}

	public Collection<DocumentToInvalidate> toCollection()
	{
		return documents.values();
	}

	DocumentToInvalidateMap combine(@NonNull final DocumentToInvalidateMap other)
	{
		if (isEmpty())
		{
			return other;
		}
		else if (other.isEmpty())
		{
			return other;
		}
		else
		{
			for (final Map.Entry<TableRecordReference, DocumentToInvalidate> e : other.documents.entrySet())
			{
				this.documents.merge(
						e.getKey(),
						e.getValue(),
						(item1, item2) -> item1.combine(item2));
			}

			return this;
		}
	}

	public static DocumentToInvalidateMap combine(@NonNull final List<DocumentToInvalidateMap> list)
	{
		if (list.isEmpty())
		{
			return new DocumentToInvalidateMap();
		}
		else if (list.size() == 1)
		{
			return list.get(0);
		}
		else
		{
			return list.stream().reduce(DocumentToInvalidateMap::combine).get();
		}
	}
}
