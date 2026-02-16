package de.metas.ui.web.view;

import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.model.DocumentQueryOrderByList;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.Comparator;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

@EqualsAndHashCode
@ToString
public final class ViewRowsOrderBy
{
	public static ViewRowsOrderBy of(final DocumentQueryOrderByList orderBys, @NonNull final JSONOptions jsonOpts)
	{
		return new ViewRowsOrderBy(orderBys, jsonOpts);
	}

	public static ViewRowsOrderBy parseString(final String orderBysListStr, @NonNull final JSONOptions jsonOpts)
	{
		final DocumentQueryOrderByList orderBys = DocumentQueryOrderByList.parse(orderBysListStr);
		return of(orderBys, jsonOpts);
	}

	public static ViewRowsOrderBy empty(@NonNull final JSONOptions jsonOpts)
	{
		return new ViewRowsOrderBy(DocumentQueryOrderByList.EMPTY, jsonOpts);
	}

	private final DocumentQueryOrderByList orderBys;
	private final JSONOptions jsonOpts;

	private ViewRowsOrderBy(final DocumentQueryOrderByList orderBys, @NonNull final JSONOptions jsonOpts)
	{
		this.orderBys = orderBys != null ? orderBys : DocumentQueryOrderByList.EMPTY;
		this.jsonOpts = jsonOpts;
	}

	public boolean isEmpty()
	{
		return orderBys.isEmpty();
	}

	public DocumentQueryOrderByList toDocumentQueryOrderByList()
	{
		return orderBys;
	}

	public ViewRowsOrderBy withOrderBys(final DocumentQueryOrderByList orderBys)
	{
		if (DocumentQueryOrderByList.equals(this.orderBys, orderBys))
		{
			return this;
		}
		else
		{
			return new ViewRowsOrderBy(orderBys, jsonOpts);
		}
	}

	public <T extends IViewRow> Comparator<T> toComparator()
	{
		return orderBys.toComparator(IViewRow::getFieldValueAsComparable, jsonOpts);
	}

	public <T extends IViewRow> Comparator<T> toComparatorOrNull()
	{
		return !orderBys.isEmpty() ? toComparator() : null;
	}

}
