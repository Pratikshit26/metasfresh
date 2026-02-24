package org.adempiere.exceptions;

import de.metas.error.AdIssueId;

 


/**
 * Interface implemented by exceptions which are aware of AD_Issue reporting.
 * 
 * To work with those kind of exceptions, please use {@link IssueReportableExceptions}.
 * 
 * @author tsa
 *
 */
public interface IIssueReportableAware
{
	/**
	 * Mark this exception as reported.
	 * 
	 * @param adIssueId
	 */
	void markIssueReported(final AdIssueId adIssueId);

	/**
	 * @return <code>true</code> if this exception was already reported
	 */
	boolean isIssueReported();
	
	AdIssueId getAdIssueId();
}
