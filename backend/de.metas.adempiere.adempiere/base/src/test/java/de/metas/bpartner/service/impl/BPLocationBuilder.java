package de.metas.bpartner.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;

import de.metas.bpartner.BPartnerId;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

public class BPLocationBuilder
{
	private boolean shipTo;

	private boolean billTo;

	private boolean billToDefault;
	
	private boolean active = true;

	private final BPartnerId bpartnerId;

	public BPLocationBuilder(@NonNull BPartnerId bpartnerId)
	{
		this.bpartnerId = bpartnerId;
	}

	public I_C_BPartner_Location createRecord()
	{
		final I_C_BPartner_Location bpLocationRecord = newInstance(I_C_BPartner_Location.class);
		bpLocationRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		bpLocationRecord.setIsActive(active);
		bpLocationRecord.setIsShipTo(shipTo);
		bpLocationRecord.setIsBillTo(billTo);
		bpLocationRecord.setIsBillToDefault(billToDefault);

		final int locationID = createLocation();
		bpLocationRecord.setC_Location_ID(locationID);
		
		saveRecord(bpLocationRecord);

		return bpLocationRecord;
	}
	
	private int createLocation()
	{
		final I_C_Location locationRecord = newInstance(I_C_Location.class);
		locationRecord.setAddress1("test");
		locationRecord.setC_Country_ID(101);
		saveRecord(locationRecord);

		return locationRecord.getC_Location_ID();
	}
	
	

	public BPLocationBuilder billTo(final boolean billTo)
	{
		this.billTo = billTo;
		return this;
	}

	public BPLocationBuilder shipTo(final boolean shipTo)
	{
		this.shipTo = shipTo;
		return this;
	}

	public BPLocationBuilder billToDefault(final boolean billToDefault)
	{
		this.billToDefault = billToDefault;
		return this;
	}
	
	public BPLocationBuilder active(final boolean active)
	{
		this.active = active;
		return this;
	}
}
