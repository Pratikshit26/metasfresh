package de.metas.ui.web.document.filter.provider;

import de.metas.ui.web.window.descriptor.CreateFiltersProviderContext;
import lombok.NonNull;

import javax.annotation.Nullable;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

public interface DocumentFilterDescriptorsProviderFactory
{
	@Nullable
	DocumentFilterDescriptorsProvider createFiltersProvider(@NonNull CreateFiltersProviderContext context);

	default boolean isActive()
	{
		return true;
	}
}
