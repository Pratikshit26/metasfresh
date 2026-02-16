package de.metas.dataentry.layout;

import com.google.common.collect.ImmutableList;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
@Builder
public class DataEntryLine
{
	int seqNo;
	
	@NonNull
	@Singular
	ImmutableList<DataEntryField> fields;
}
