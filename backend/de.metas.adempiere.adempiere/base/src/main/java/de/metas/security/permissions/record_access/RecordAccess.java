package de.metas.security.permissions.record_access;

import javax.annotation.Nullable;

import org.adempiere.util.lang.impl.TableRecordReference;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.security.Principal;
import de.metas.security.permissions.Access;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class RecordAccess
{
	@JsonProperty("recordRef")
	TableRecordReference recordRef;

	@JsonProperty("principal")
	Principal principal;

	@JsonProperty("permission")
	Access permission;

	@JsonProperty("issuer")
	PermissionIssuer issuer;

	@JsonProperty("createdBy")
	UserId createdBy;

	@JsonProperty("description")
	String description;

	@JsonProperty("id")
	@NonFinal
	RecordAccessId id;

	@JsonProperty("parentId")
	RecordAccessId parentId;

	@JsonProperty("rootId")
	@NonFinal
	RecordAccessId rootId;

	@Builder(toBuilder = true)
	@JsonCreator
	private RecordAccess(
			@JsonProperty("recordRef") @NonNull final TableRecordReference recordRef,
			@JsonProperty("principal") @NonNull final Principal principal,
			@JsonProperty("permission") @NonNull final Access permission,
			@JsonProperty("issuer") @NonNull final PermissionIssuer issuer,
			@JsonProperty("createdBy") @NonNull final UserId createdBy,
			@JsonProperty("description") @Nullable final String description,
			//
			@JsonProperty("id") @Nullable final RecordAccessId id,
			@JsonProperty("parentId") @Nullable final RecordAccessId parentId,
			@JsonProperty("rootId") @Nullable final RecordAccessId rootId)
	{
		this.recordRef = recordRef;
		this.principal = principal;
		this.permission = permission;
		this.issuer = issuer;
		this.createdBy = createdBy;
		this.description = description;

		this.id = id;
		this.parentId = parentId;
		this.rootId = rootId;
	}

	void setId(@NonNull final RecordAccessId id)
	{
		this.id = id;
	}

	void setRootId(final RecordAccessId rootId)
	{
		this.rootId = rootId;
	}
}
