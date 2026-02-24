/**
 *
 */
package de.metas.letters.model;

 


import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_R_RequestType;
import org.compiere.model.Query;

/**
 * R_RequestType Service
 * 
 * @author teo.sarca@gmail.com
 */
public class MRequestTypeService
{
	private final Properties ctx;

	public MRequestTypeService(final Properties ctx)
	{
		this.ctx = ctx;
	}

	public int getDefault(final String defaultName)
	{
		final String whereClause = defaultName + "=?";
		final int id = new Query(ctx, I_R_RequestType.Table_Name, whereClause, null)
				.setParameters(new Object[] { true })
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.firstIdOnly();

		if (id <= 0)
		{
			throw new AdempiereException("@NotFound@ @R_RequestType_ID@ (@" + defaultName + "@)");
		}
		return id;
	}
}
