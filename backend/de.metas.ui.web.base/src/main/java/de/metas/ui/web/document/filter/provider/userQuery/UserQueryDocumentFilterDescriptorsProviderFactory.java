package de.metas.ui.web.document.filter.provider.userQuery;

import com.google.common.collect.ImmutableList;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProviderFactory;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.window.descriptor.CreateFiltersProviderContext;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.compiere.apps.search.IUserQueryField;
import org.compiere.apps.search.UserQueryRepository;
import org.compiere.model.POInfo;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

@Component
final public class UserQueryDocumentFilterDescriptorsProviderFactory implements DocumentFilterDescriptorsProviderFactory
{
	private final IADTableDAO adTablesRepo = Services.get(IADTableDAO.class);

	public UserQueryDocumentFilterDescriptorsProviderFactory()
	{
	}

	@Override
	public DocumentFilterDescriptorsProvider createFiltersProvider(@NonNull final CreateFiltersProviderContext context)
	{
		final String tableName = StringUtils.trimBlankToNull(context.getTableName());
		final AdTabId adTabId = context.getAdTabId();
		if (tableName == null || adTabId == null)
		{
			return NullDocumentFilterDescriptorsProvider.instance;
		}

		final AdTableId adTableId = adTablesRepo.retrieveAdTableId(tableName);

		final List<IUserQueryField> searchFields = context.getFields()
				.stream()
				.map(UserQueryDocumentFilterDescriptorsProviderFactory::createUserQueryField)
				.collect(ImmutableList.toImmutableList());

		final UserQueryRepository repository = UserQueryRepository.builder()
				.setAD_Tab_ID(adTabId.getRepoId())
				.setAD_Table_ID(adTableId.getRepoId())
				.setAD_User_ID(UserId.METASFRESH.getRepoId()) // FIXME: hardcoded, see https://github.com/metasfresh/metasfresh-webui/issues/162
				.setSearchFields(searchFields)
				.setColumnDisplayTypeProvider(POInfo.getPOInfoNotNull(tableName))
				.build();

		return new UserQueryDocumentFilterDescriptorsProvider(repository);
	}

	private static UserQueryField createUserQueryField(final DocumentFieldDescriptor field)
	{
		return UserQueryField.builder()
				.columnName(field.getFieldName())
				.displayName(field.getCaption())
				.widgetType(field.getWidgetType())
				// TODO: use a lookup descriptor without validation rules with params
				.lookupDescriptor(field.getLookupDescriptorForFiltering())
				.build();
	}
}
