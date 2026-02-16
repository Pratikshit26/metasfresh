package de.metas.ui.web.view.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import de.metas.ui.web.view.ViewHeaderProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/*
 * #%L
 * metasfresh-webui-api
     
 * #L%
 */

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
@Builder
@Jacksonized
class JSONViewHeaderProperty
{
	String fieldName;
	String caption;
	String value;

	public static JSONViewHeaderProperty of(@NonNull final ViewHeaderProperty property, @NonNull final String adLanguage)
	{
		final String caption = property.getCaption().translate(adLanguage);

		return JSONViewHeaderProperty.builder()
				.fieldName(property.getFieldName() != null ? property.getFieldName() : caption)
				.caption(caption)
				.value(property.getValue().translate(adLanguage))
				.build();
	}
}
