package de.metas.security.permissions.bpartner_hierarchy;

import com.google.common.collect.ImmutableSet;
import de.metas.security.permissions.record_access.RecordAccessFeature;
import de.metas.security.permissions.record_access.RecordAccessRepository;
import de.metas.security.permissions.record_access.handlers.RecordAccessHandler;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.Set;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Component
public class BPartnerHierarchyRecordAccessHandler implements RecordAccessHandler
{
	private final RecordAccessRepository recordAccessRepository;

	public BPartnerHierarchyRecordAccessHandler(
			@NonNull final RecordAccessRepository recordAccessRepository)
	{
		this.recordAccessRepository = recordAccessRepository;
	}

	@Override
	public Set<RecordAccessFeature> getHandledFeatures()
	{
		return ImmutableSet.of(RecordAccessFeature.BPARTNER_HIERARCHY);
	}

	@Override
	public Set<String> getHandledTableNames() { return recordAccessRepository.getHandledTableNames(); }
}
