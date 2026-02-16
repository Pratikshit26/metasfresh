package de.metas.email.mailboxes;

import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.email.EMailCustomType;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import de.metas.process.ProcessExecutor;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
@Builder
public class MailboxQuery
{
	@NonNull ClientId clientId;
	@NonNull @Builder.Default OrgId orgId = ProcessExecutor.getCurrentOrgId();
	@Nullable @Builder.Default AdProcessId adProcessId = ProcessExecutor.getCurrentProcessIdOrNull();
	@Nullable DocBaseAndSubType docBaseAndSubType;
	@Nullable EMailCustomType customType;

	@Nullable UserId fromUserId;

	public static MailboxQuery ofClientId(@NonNull final ClientId clientId) {return MailboxQuery.builder().clientId(clientId).build();}

	public DocBaseType getDocBaseType() { return docBaseAndSubType != null ? docBaseAndSubType.getDocBaseType() : null; }
	public DocSubType getDocSubType() { return docBaseAndSubType != null ? docBaseAndSubType.getDocSubType() : DocSubType.ANY; }
}
