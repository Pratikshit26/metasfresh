/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.milestone;

import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.time.Instant;

@Data
@Builder
public class Milestone
{
	@NonNull
	private OrgId orgId;

	@Nullable
	private MilestoneId milestoneId;

	@Nullable
	private String externalURL;

	@Nullable
	private String description;

	@NonNull
	private String name;

	@NonNull
	private String value;

	@Nullable
	private Instant dueDate;

	private boolean processed;
}
