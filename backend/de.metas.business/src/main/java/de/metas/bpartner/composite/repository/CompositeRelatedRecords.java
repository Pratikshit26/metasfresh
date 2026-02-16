package de.metas.bpartner.composite.repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerId;
import de.metas.location.LocationId;
import de.metas.location.PostalId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.table.RecordChangeLogEntry;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner_CreditLimit;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Postal;

import java.util.Optional;

/*
 * #%L
 * de.metas.business
     
 * #L%
 */

@Value
@Builder
@Getter(AccessLevel.NONE)
class CompositeRelatedRecords
{
	@NonNull
	@Builder.Default
	ImmutableListMultimap<BPartnerId, I_AD_User> bpartnerId2Users = ImmutableListMultimap.of();

	@NonNull
	@Builder.Default
	ImmutableListMultimap<BPartnerId, I_C_BPartner_Location> bpartnerId2BPartnerLocations = ImmutableListMultimap.of();

	@NonNull
	@Builder.Default
	ImmutableMap<LocationId, I_C_Location> locationId2Location = ImmutableMap.of();

	@NonNull
	@Builder.Default
	ImmutableMap<PostalId, I_C_Postal> postalId2Postal = ImmutableMap.of();

	@NonNull
	@Builder.Default
	ImmutableListMultimap<BPartnerId, I_C_BP_BankAccount> bpartnerId2BankAccounts = ImmutableListMultimap.of();

	@NonNull
	@Getter(AccessLevel.PUBLIC)
	@Builder.Default
	ImmutableListMultimap<TableRecordReference, RecordChangeLogEntry> recordRef2LogEntries = ImmutableListMultimap.of();

	@NonNull
	@Builder.Default
	ImmutableListMultimap<BPartnerId, I_C_BPartner_CreditLimit> bpartnerId2CreditLimits = ImmutableListMultimap.of();

	public ImmutableList<I_C_BPartner_Location> getBPartnerLocationsByBPartnerId(@NonNull final BPartnerId bpartnerId)
	{
		return bpartnerId2BPartnerLocations.get(bpartnerId);
	}

	public Optional<I_C_Location> getLocationById(@NonNull final LocationId locationId) { return Optional.ofNullable(locationId2Location.get(locationId)); }

	public Optional<I_C_Postal> getPostalById(@NonNull final PostalId postalId) { return Optional.ofNullable(postalId2Postal.get(postalId));}

	public ImmutableList<I_AD_User> getContactsByBPartnerId(@NonNull final BPartnerId bpartnerId)
	{
		return bpartnerId2Users.get(bpartnerId);
	}

	public ImmutableList<I_C_BP_BankAccount> getBankAccountsByBPartnerId(@NonNull final BPartnerId bpartnerId)
	{
		return bpartnerId2BankAccounts.get(bpartnerId);
	}

	@NonNull
	public ImmutableList<I_C_BPartner_CreditLimit> getCreditLimitsByBPartnerId(@NonNull final BPartnerId bpartnerId)
	{
		return bpartnerId2CreditLimits.get(bpartnerId);
	}
}
