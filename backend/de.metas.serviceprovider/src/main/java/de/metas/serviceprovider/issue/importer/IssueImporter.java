/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.issue.importer;

import com.google.common.collect.ImmutableList;
import de.metas.serviceprovider.issue.importer.info.ImportIssuesRequest;

public interface IssueImporter
{
	void start(ImmutableList<ImportIssuesRequest> importIssuesRequestList);
}
