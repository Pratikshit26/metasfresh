package de.metas.migration.cli.workspace_migrate;

import de.metas.migration.IScript;
import de.metas.migration.scanner.IFileRef;
import de.metas.migration.scanner.impl.AbstractScriptFactory;

/*
 * #%L
 * de.metas.migration.cli
     
 * #L%
 */

class WorkspaceScriptFactory extends AbstractScriptFactory
{

	@Override
	public IScript createScript(final IFileRef fileRef)
	{
		return createScript(getProjectName(fileRef), fileRef);
	}

	private String getProjectName(final IFileRef fileRef)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("not impl");
	}
}
