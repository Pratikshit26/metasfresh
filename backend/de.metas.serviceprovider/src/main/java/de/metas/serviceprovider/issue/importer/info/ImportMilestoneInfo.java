/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.issue.importer.info;

import de.metas.organization.OrgId;
import de.metas.externalreference.ExternalId;
import de.metas.serviceprovider.milestone.MilestoneId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.time.Instant;

@Data
@Builder
public class ImportMilestoneInfo
{
	@NonNull
	private OrgId orgId;

	@Nullable
	private MilestoneId milestoneId;

	@NonNull
	private ExternalId externalId;

	@Nullable
	private String externalURL;

	@Nullable
	private String description;

	@NonNull
	private String name;

	@Nullable
	private Instant dueDate;

	private boolean processed;
}
