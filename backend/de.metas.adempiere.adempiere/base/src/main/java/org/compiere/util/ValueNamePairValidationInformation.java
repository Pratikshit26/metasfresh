/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

package org.compiere.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.metas.i18n.AdMessageKey;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;
import org.compiere.util.ValueNamePairValidationInformation.ValueNamePairValidationInformationBuilder;

@Value
@Builder
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@JsonDeserialize(builder = ValueNamePairValidationInformationBuilder.class)
public class ValueNamePairValidationInformation
{
	@NonNull
	@Default
	private AdMessageKey title = AdMessageKey.of("de.metas.popupinfo.popupTitle");

	@NonNull
	private AdMessageKey question;

	@NonNull
	@Default
	private AdMessageKey answerYes = AdMessageKey.of("de.metas.popupinfo.yes");

	@NonNull
	@Default
	private AdMessageKey answerNo = AdMessageKey.of("de.metas.popupinfo.no");

	@JsonPOJOBuilder(withPrefix = "")
	public static class ValueNamePairValidationInformationBuilder
	{
	}
}
