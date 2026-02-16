/*
 * #%L
 * de.metas.issue.tracking.everhour
     
 * #L%
 */

package de.metas.issue.tracking.everhour.configuration;

import de.metas.util.Services;
import org.adempiere.service.ISysConfigBL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration
{
	@Bean
	public ISysConfigBL sysConfigBL()
	{
		return Services.get(ISysConfigBL.class);
	}
}
