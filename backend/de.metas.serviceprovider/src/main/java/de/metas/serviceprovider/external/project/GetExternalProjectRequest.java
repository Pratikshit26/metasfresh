/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.external.project;

import de.metas.organization.OrgId;
import de.metas.serviceprovider.external.ExternalSystem;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class GetExternalProjectRequest
{
	@NonNull
	ExternalSystem externalSystem;

	@NonNull
	String externalReference;

	@NonNull
	String externalProjectOwner;

	@NonNull
	OrgId orgId;
}
