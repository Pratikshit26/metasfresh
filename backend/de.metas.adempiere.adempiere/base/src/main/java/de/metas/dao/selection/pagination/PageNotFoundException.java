package de.metas.dao.selection.pagination;

import org.adempiere.exceptions.AdempiereException;

import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

public class PageNotFoundException extends AdempiereException
{
	private static final long serialVersionUID = 2037196076279971989L;

	public PageNotFoundException(@NonNull final String completePageId)
	{
		super("Page with ID " + completePageId + " not found");
	}


}
