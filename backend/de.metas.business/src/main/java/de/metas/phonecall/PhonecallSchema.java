package de.metas.phonecall;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableList;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
public class PhonecallSchema
{
	@NonNull
	PhonecallSchemaId id;
	@NonNull
	String name;

	@Getter(AccessLevel.PRIVATE)
	ImmutableList<PhonecallSchemaVersion> versions;

	@Builder
	private PhonecallSchema(
			@NonNull final PhonecallSchemaId id,
			@NonNull final String name,
			@NonNull final List<PhonecallSchemaVersion> versions)
	{
		this.id = id;
		this.name = name;
		this.versions = versions.stream()
				.sorted(Comparator.comparing(PhonecallSchemaVersion::getValidFrom))
				.collect(ImmutableList.toImmutableList());
	}

	public List<PhonecallSchemaVersion> getChronologicallyOrderedPhonecallSchemaVersions(@NonNull final LocalDate endDate)
	{
		ImmutableList<PhonecallSchemaVersion> phonecallVersionsForDateRange = versions.stream()
				.filter(version -> version.getValidFrom().compareTo(endDate) <= 0)
				.collect(ImmutableList.toImmutableList());

		if (phonecallVersionsForDateRange.isEmpty())
		{
			throw new AdempiereException("No version found before " + endDate);
		}

		return phonecallVersionsForDateRange;
	}

	public Optional<PhonecallSchemaVersion> getVersionByValidFrom(@NonNull final LocalDate validFrom)
	{
		return getVersions()
				.stream()
				.filter(version -> version.getValidFrom().equals(validFrom))
				.findFirst();
	}
}
