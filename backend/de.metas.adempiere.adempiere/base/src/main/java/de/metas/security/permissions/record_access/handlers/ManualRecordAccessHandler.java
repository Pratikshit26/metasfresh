package de.metas.security.permissions.record_access.handlers;

import com.google.common.collect.ImmutableSet;
import de.metas.security.permissions.record_access.RecordAccessFeature;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collection;
import java.util.Set;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@EqualsAndHashCode
@ToString
public class ManualRecordAccessHandler implements RecordAccessHandler
{
	public static ManualRecordAccessHandler ofTableNames(final Collection<String> tableNames)
	{
		return new ManualRecordAccessHandler(tableNames);
	}

	private static final ImmutableSet<RecordAccessFeature> HANDLED_FEATURES = ImmutableSet.of(RecordAccessFeature.MANUAL_TABLE);

	private final ImmutableSet<String> handledTableNames;

	private ManualRecordAccessHandler(final Collection<String> tableNames)
	{
		Check.assumeNotEmpty(tableNames, "tableNames is not empty");
		this.handledTableNames = ImmutableSet.copyOf(tableNames);
	}

	@Override
	public Set<RecordAccessFeature> getHandledFeatures()
	{
		return HANDLED_FEATURES;
	}

	@Override
	public Set<String> getHandledTableNames()
	{
		return handledTableNames;
	}
}
