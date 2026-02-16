package de.metas.organization;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.service.ClientId;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@Value
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class ClientAndOrgId
{

	public static ClientAndOrgId ofClientId(final int adClientId)
	{
		return ofClientAndOrg(
				ClientId.ofRepoId(adClientId),
				OrgId.ANY);
	}

	public static ClientAndOrgId ofClientAndOrg(final int adClientId, final int adOrgId)
	{
		return ofClientAndOrg(
				ClientId.ofRepoId(adClientId),
				OrgId.ofRepoId(adOrgId));
	}

	@JsonCreator
	public static ClientAndOrgId ofClientAndOrg(
			@JsonProperty("clientId") @NonNull final ClientId adClientId,
			@JsonProperty("orgId") @NonNull final OrgId adOrgId)
	{
		final ClientAndOrgId instance = new ClientAndOrgId(adClientId, adOrgId);

		// intern:
		if (SYSTEM.equals(instance))
		{
			return SYSTEM;
		}
		else if (MAIN.equals(instance))
		{
			return MAIN;
		}
		else
		{
			return instance;
		}
	}

	public static ClientAndOrgId SYSTEM = new ClientAndOrgId(ClientId.SYSTEM, OrgId.ANY);
	public static ClientAndOrgId MAIN = new ClientAndOrgId(ClientId.METASFRESH, OrgId.MAIN);

	@JsonProperty("clientId") @NonNull ClientId clientId;
	@JsonProperty("orgId") @NonNull OrgId orgId;

	private ClientAndOrgId(
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		this.clientId = clientId;
		this.orgId = orgId;
	}

	public ClientAndOrgId withSystemClientId()
	{
		return ClientId.equals(this.clientId, ClientId.SYSTEM)
				? this
				: ofClientAndOrg(ClientId.SYSTEM, orgId);
	}

	public ClientAndOrgId withAnyOrgId()
	{
		return OrgId.equals(this.orgId, OrgId.ANY)
				? this
				: ofClientAndOrg(clientId, OrgId.ANY);
	}

}
