package de.metas.migration.cli.workspace_migrate;

import de.metas.migration.IDatabase;
import de.metas.migration.applier.IScriptsApplierListener;
import de.metas.migration.applier.impl.ConsoleScriptsApplierListener;
import de.metas.migration.applier.impl.NullScriptsApplierListener;
import de.metas.migration.applier.impl.ScriptsApplier;
import de.metas.migration.applier.impl.SwingUIScriptsApplierListener;
import de.metas.migration.executor.IScriptExecutorFactory;
import de.metas.migration.executor.impl.DefaultScriptExecutorFactory;
import de.metas.migration.impl.AbstractScriptsApplierTemplate;
import de.metas.migration.impl.SQLDatabase;
import de.metas.migration.scanner.IScriptFactory;
import de.metas.migration.scanner.IScriptScanner;
import de.metas.migration.scanner.IScriptScannerFactory;
import de.metas.migration.scanner.impl.GloballyOrderedScannerDecorator;
import lombok.Builder;
import lombok.NonNull;

import java.awt.*;

/*
 * #%L
 * de.metas.migration.cli
     
 * #L%
 */

class WorkspaceScriptsApplier extends AbstractScriptsApplierTemplate
{
	private final WorkspaceMigrateConfig config;
	private IScriptExecutorFactory scriptExecutorFactory = new DefaultScriptExecutorFactory();

	@Builder
	public WorkspaceScriptsApplier(@NonNull final WorkspaceMigrateConfig config)
	{
		this.config = config;
	}

	@Override
	protected IScriptFactory createScriptFactory()
	{
		return new WorkspaceScriptFactory();
	}

	@Override
	protected IScriptScanner createScriptScanner(final IScriptScannerFactory IGNORED)
	{
		final WorkspaceScriptScanner scanner = WorkspaceScriptScanner.builder()
				.workspaceDir(config.getWorkspaceDir())
				.supportedScriptTypes(scriptExecutorFactory.getSupportedScriptTypes())
				.acceptLabels(config.getLabels())
				.build();

		return new GloballyOrderedScannerDecorator(scanner);
	}

	@Override
	protected IScriptExecutorFactory createScriptExecutorFactory()
	{
		return scriptExecutorFactory;
	}

	@Override
	protected void configureScriptExecutorFactory(final IScriptExecutorFactory scriptExecutorFactory)
	{
		scriptExecutorFactory.setDryRunMode(config.isDryRunMode());
	}

	@Override
	protected IDatabase createDatabase()
	{
		return new SQLDatabase(config.getDbUrl(), config.getDbUsername(), config.getDbPassword());
	}

	@Override
	protected ScriptsApplier createScriptApplier(final IDatabase database)
	{
		final ScriptsApplier scriptApplier = super.createScriptApplier(database);
		scriptApplier.setSkipExecutingAfterScripts(config.isSkipExecutingAfterScripts());
		return scriptApplier;
	}

	@Override
	protected IScriptsApplierListener createScriptsApplierListener()
	{
		switch (config.getOnScriptFailure())
		{
			case FAIL:
				return NullScriptsApplierListener.instance;
			case ASK:
				if (GraphicsEnvironment.isHeadless())
				{
					return ConsoleScriptsApplierListener.instance;
				}
				return new SwingUIScriptsApplierListener();
			default:
				throw new RuntimeException("Unexpected WorkspaceMigrateConfig.onScriptFailure=" + config.getOnScriptFailure());
		}
	}
}
