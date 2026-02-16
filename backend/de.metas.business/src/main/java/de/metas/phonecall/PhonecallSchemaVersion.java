package de.metas.phonecall;

import java.time.LocalDate;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import de.metas.util.time.generator.Frequency;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
@Builder
public class PhonecallSchemaVersion
{
	@NonNull
	PhonecallSchemaId phonecallSchemaId;

	@Nullable
	PhonecallSchemaVersionId id;

	@NonNull
	String name;

	@NonNull
	LocalDate validFrom;

	@Nullable
	Frequency frequency;

	ImmutableList<PhonecallSchemaVersionLine> lines;
}
