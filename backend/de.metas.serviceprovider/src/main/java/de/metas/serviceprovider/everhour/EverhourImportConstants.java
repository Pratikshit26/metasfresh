/*
 * #%L
 * de.metas.serviceprovider.base
     
 * #L%
 */

package de.metas.serviceprovider.everhour;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface EverhourImportConstants
{
	int PROCESSING_DATE_INTERVAL_SIZE = 7;

	@AllArgsConstructor
	@Getter enum EverhourImporterSysConfig
	{
		ACCESS_TOKEN("de.metas.issue.tracking.everhour.accessToken"),
		BPARTNER_USER_IMPORT("de.metas.serviceprovider.everhour.bpartnerUserImport");

		private final String name;
	}
}
