package de.metas.security.impl;

 


import org.adempiere.exceptions.AdempiereException;

import de.metas.util.Check;

public class RolePermissionsNotFoundException extends AdempiereException
{
	private static final String MSG = "RolePermissionsNotFoundException";

	/**
	 * 
	 */
	private static final long serialVersionUID = -5635853326303323078L;

	public RolePermissionsNotFoundException(final String additionalInfo)
	{
		this(buildMsg(additionalInfo), (Throwable)null);
	}

	public RolePermissionsNotFoundException(final String additionalInfo, Throwable cause)
	{
		super(buildMsg(additionalInfo), cause);
	}

	private static String buildMsg(String additionalInfo)
	{
		final StringBuilder sb = new StringBuilder()
				.append("@").append(MSG).append("@");

		if (!Check.isEmpty(additionalInfo, true))
		{
			sb.append(": ").append(additionalInfo);
		}

		return sb.toString();
	}
}
