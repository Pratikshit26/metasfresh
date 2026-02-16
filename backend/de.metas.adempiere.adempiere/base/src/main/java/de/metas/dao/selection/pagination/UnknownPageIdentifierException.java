package de.metas.dao.selection.pagination;

import org.adempiere.exceptions.AdempiereException;

import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

public class UnknownPageIdentifierException extends AdempiereException
{
	private static final long serialVersionUID = -1833126347909632687L;

	public UnknownPageIdentifierException(@NonNull final String pageIdentifier)
	{
		super(pageIdentifier);
	}

}
