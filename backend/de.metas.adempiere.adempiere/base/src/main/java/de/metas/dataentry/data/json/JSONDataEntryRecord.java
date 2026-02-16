package de.metas.dataentry.data.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.CreatedUpdatedInfo;
import de.metas.dataentry.DataEntryListValueId;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
public class JSONDataEntryRecord
{
	Map<Integer, LocalDate> dates;

	Map<Integer, DataEntryListValueId> listValues;

	Map<Integer, BigDecimal> numbers;

	Map<Integer, String> strings;

	Map<Integer, Boolean> yesNos;

	Map<Integer, CreatedUpdatedInfo> createdUpdatedInfos;

	@Builder
	@JsonCreator
	private JSONDataEntryRecord(
			@Singular @JsonProperty("dates") final Map<Integer, LocalDate> dates,
			@Singular @JsonProperty("listValues") final Map<Integer, DataEntryListValueId> listValues,
			@Singular @JsonProperty("numbers") final Map<Integer, BigDecimal> numbers,
			@Singular @JsonProperty("strings") final Map<Integer, String> strings,
			@Singular @JsonProperty("yesNos") final Map<Integer, Boolean> yesNos,
			@Singular @JsonProperty("createdUpdatedInfos") final Map<Integer, CreatedUpdatedInfo> createdUpdatedInfos)
	{
		this.dates = dates;
		this.listValues = listValues;
		this.numbers = numbers;
		this.strings = strings;
		this.yesNos = yesNos;
		this.createdUpdatedInfos = createdUpdatedInfos;
	}

}
