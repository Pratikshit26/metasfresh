package de.metas.ui.web.window.datatypes.json;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.model.DocumentFieldChange;
import de.metas.ui.web.window.model.DocumentFieldLogicExpressionResultRevaluator;
import de.metas.ui.web.window.model.IDocumentFieldView;
import de.metas.util.Check;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Predicate;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

public class JSONDocumentOptions
{
	public static JSONDocumentOptions of(@NonNull final UserSession userSession)
	{
		return builder().userSession(userSession).build();
	}

	@Getter
	private final JSONOptions jsonOpts;

	private final String showOnlyFieldsListStr;
	private Predicate<IDocumentFieldView> _documentFieldFilter; // lazy
	private Predicate<DocumentFieldChange> _documentFieldChangeFilter; // lazy

	private final boolean showAdvancedFields;

	@NonNull private final JSONDocumentPermissions documentPermissions;

	private static final Splitter FIELDS_LIST_SPLITTER = Splitter.on(",")
			.trimResults()
			.omitEmptyStrings();

	@Builder
	private JSONDocumentOptions(
			@NonNull final UserSession userSession,
			@Nullable final String showOnlyFieldsListStr,
			final boolean showAdvancedFields)
	{
		this.jsonOpts = JSONOptions.of(userSession);
		this.showOnlyFieldsListStr = showOnlyFieldsListStr;
		this.showAdvancedFields = showAdvancedFields;
		this.documentPermissions = new JSONDocumentPermissions(userSession.getUserRolePermissions());
	}

	public String getAdLanguage()
	{
		return getJsonOpts().getAdLanguage();
	}

	public Predicate<IDocumentFieldView> documentFieldFilter()
	{
		if (_documentFieldFilter == null)
		{
			_documentFieldFilter = createDocumentFieldFilter();
		}
		return _documentFieldFilter;
	}

	private Predicate<IDocumentFieldView> createDocumentFieldFilter()
	{
		final Predicate<IDocumentFieldView> filter = showAdvancedFields ? FILTER_DocumentFieldView_ALL_PUBLIC_FIELDS : FILTER_DocumentFieldView_BASIC_PUBLIC_FIELDS;

		final Set<String> dataFieldNamesSet = Check.isEmpty(showOnlyFieldsListStr, true) ? ImmutableSet.of() : ImmutableSet.copyOf(FIELDS_LIST_SPLITTER.splitToList(showOnlyFieldsListStr));
		if (dataFieldNamesSet.isEmpty() || dataFieldNamesSet.contains("*"))
		{
			return filter;
		}

		return new FILTER_DocumentFieldView_ByFieldNamesSet(dataFieldNamesSet, filter);
	}

	public Predicate<DocumentFieldChange> documentFieldChangeFilter()
	{
		if (_documentFieldChangeFilter == null)
		{
			_documentFieldChangeFilter = createDocumentFieldChangeFilter();
		}

		return _documentFieldChangeFilter;
	}

	private Predicate<DocumentFieldChange> createDocumentFieldChangeFilter()
	{
		final Predicate<DocumentFieldChange> filter = showAdvancedFields ? FILTER_DocumentFieldChange_ALL_PUBLIC_FIELDS : FILTER_DocumentFieldChange_BASIC_PUBLIC_FIELDS;

		final Set<String> dataFieldNamesSet = Check.isEmpty(showOnlyFieldsListStr, true) ? ImmutableSet.of() : ImmutableSet.copyOf(FIELDS_LIST_SPLITTER.splitToList(showOnlyFieldsListStr));
		if (dataFieldNamesSet.isEmpty() || dataFieldNamesSet.contains("*"))
		{
			return filter;
		}

		return new FILTER_DocumentFieldChange_ByFieldNamesSet(dataFieldNamesSet, filter);
	}

	@NonNull
	public JSONDocumentPermissions getDocumentPermissions()
	{
		return documentPermissions;
	}

	public DocumentFieldLogicExpressionResultRevaluator getDocumentFieldReadonlyChecker()
	{
		return documentPermissions.getLogicExpressionResultRevaluator();
	}

	//
	//
	//
	//

	private static final Predicate<IDocumentFieldView> FILTER_DocumentFieldView_BASIC_PUBLIC_FIELDS = new Predicate<IDocumentFieldView>()
	{
		@Override
		public String toString()
		{
			return "basic public fields";
		}

		@Override
		public boolean test(final IDocumentFieldView field)
		{
			return field.isPublicField() && !field.isAdvancedField();
		}
	};

	private static final Predicate<DocumentFieldChange> FILTER_DocumentFieldChange_BASIC_PUBLIC_FIELDS = new Predicate<DocumentFieldChange>()
	{
		@Override
		public String toString()
		{
			return "basic public fields";
		}

		@Override
		public boolean test(final DocumentFieldChange field)
		{
			return field.isPublicField() && !field.isAdvancedField();
		}
	};

	private static final Predicate<IDocumentFieldView> FILTER_DocumentFieldView_ALL_PUBLIC_FIELDS = new Predicate<IDocumentFieldView>()
	{
		@Override
		public String toString()
		{
			return "all public fields";
		}

		@Override
		public boolean test(final IDocumentFieldView field)
		{
			return field.isPublicField();
		}
	};

	private static final Predicate<DocumentFieldChange> FILTER_DocumentFieldChange_ALL_PUBLIC_FIELDS = new Predicate<DocumentFieldChange>()
	{
		@Override
		public String toString()
		{
			return "all public fields";
		}

		@Override
		public boolean test(final DocumentFieldChange field)
		{
			return field.isPublicField();
		}
	};

	private static final class FILTER_DocumentFieldView_ByFieldNamesSet implements Predicate<IDocumentFieldView>
	{
		private final Set<String> fieldNamesSet;
		private final Predicate<IDocumentFieldView> parentFilter;

		private FILTER_DocumentFieldView_ByFieldNamesSet(final Set<String> fieldNamesSet, final Predicate<IDocumentFieldView> parentFilter)
		{
			super();
			this.fieldNamesSet = fieldNamesSet;
			this.parentFilter = parentFilter;
		}

		@Override
		public String toString()
		{
			return "field name in " + fieldNamesSet + " and " + parentFilter;
		}

		@Override
		public boolean test(final IDocumentFieldView field)
		{
			if (!fieldNamesSet.contains(field.getFieldName()))
			{
				return false;
			}

			return parentFilter.test(field);
		}
	}

	private static final class FILTER_DocumentFieldChange_ByFieldNamesSet implements Predicate<DocumentFieldChange>
	{
		private final Set<String> fieldNamesSet;
		private final Predicate<DocumentFieldChange> parentFilter;

		private FILTER_DocumentFieldChange_ByFieldNamesSet(final Set<String> fieldNamesSet, final Predicate<DocumentFieldChange> parentFilter)
		{
			super();
			this.fieldNamesSet = fieldNamesSet;
			this.parentFilter = parentFilter;
		}

		@Override
		public String toString()
		{
			return "field name in " + fieldNamesSet + " and " + parentFilter;
		}

		@Override
		public boolean test(final DocumentFieldChange field)
		{
			if (!fieldNamesSet.contains(field.getFieldName()))
			{
				return false;
			}

			return parentFilter.test(field);
		}
	}
}
