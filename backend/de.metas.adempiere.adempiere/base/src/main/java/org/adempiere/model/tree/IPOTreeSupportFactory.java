package org.adempiere.model.tree;

 


import org.adempiere.model.tree.spi.IPOTreeSupport;

import de.metas.util.ISingletonService;

public interface IPOTreeSupportFactory extends ISingletonService
{

	IPOTreeSupport get(String tableName);

	void register(String tableName, Class<? extends IPOTreeSupport> clazz);

}
