package de.metas.ui.web.dataentry.window.descriptor.factory;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor.LookupSource;
import de.metas.dataentry.layout.DataEntryListValue;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFetcher;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

public class DataEntryListValueLookupDescriptor implements LookupDescriptor
{
	private final DataEntryListValueDataSourceFetcher dataEntryListValueDataSourceFetcher;

	public static DataEntryListValueLookupDescriptor of(@NonNull final List<DataEntryListValue> listValues)
	{
		return new DataEntryListValueLookupDescriptor(listValues);
	}

	private DataEntryListValueLookupDescriptor(@NonNull final List<DataEntryListValue> listValues)
	{
		dataEntryListValueDataSourceFetcher = new DataEntryListValueDataSourceFetcher(listValues);
	}

	@Override
	public LookupDataSourceFetcher getLookupDataSourceFetcher()
	{
		return dataEntryListValueDataSourceFetcher;
	}

	@Override
	public boolean isHighVolume()
	{
		return false;
	}

	@Override
	public LookupSource getLookupSourceType()
	{
		return LookupSource.list;
	}

	@Override
	public boolean hasParameters()
	{
		return false;
	}

	@Override
	public boolean isNumericKey()
	{
		return true;
	}

	@Override
	public Set<String> getDependsOnFieldNames()
	{
		return ImmutableSet.of();
	}

}
