package de.metas.bpartner;

import javax.annotation.Nullable;

import de.metas.organization.OrgId;
import lombok.Data;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Data(staticConstructor = "of")
public class BPGroup
{
	@NonNull
	private final OrgId orgId;

	@Nullable
	private final BPGroupId id;

	@NonNull
	private String name;
}
