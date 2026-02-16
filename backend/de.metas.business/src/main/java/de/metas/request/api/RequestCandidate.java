package de.metas.request.api;

import de.metas.bpartner.BPartnerId;
import de.metas.inout.QualityNoteId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.request.RequestTypeId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/*
 * #%L
 * de.metas.swat.base
     
 * #L%
 */

@Value
@Builder
public class RequestCandidate
{
	@NonNull
	RequestTypeId requestTypeId;

	@NonNull
	OrgId orgId;
	ProductId productId;
	BPartnerId partnerId;
	UserId userId;

	TableRecordReference recordRef;

	@NonNull
	ZonedDateTime dateDelivered;
	@NonNull
	String confidentialType;
	@NonNull
	String summary;

	@Nullable
	QualityNoteId qualityNoteId;
	@Nullable
	String performanceType;
}
