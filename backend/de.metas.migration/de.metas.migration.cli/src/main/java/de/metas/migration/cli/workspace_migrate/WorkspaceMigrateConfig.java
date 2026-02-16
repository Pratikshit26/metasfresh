package de.metas.migration.cli.workspace_migrate;

import java.io.File;

import com.google.common.collect.ImmutableSet;

import de.metas.migration.applier.IScriptsApplierListener;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.migration.cli
     
 * #L%
 */

@Value
@Builder
public class WorkspaceMigrateConfig
{
	public static final String PROP_DB_URL_DEFAULT = "jdbc:postgresql://localhost/metasfresh";
	public static final String PROP_DB_USERNAME_DEFAULT = "metasfresh";
	public static final String PROP_DB_PASSWORD_DEFAULT = "metasfresh";
	public static final String PROP_LABELS_DEFAULT = "mf15,common";

	@NonNull
	File workspaceDir;

	@NonNull
	@Builder.Default
	String dbUrl = PROP_DB_URL_DEFAULT;

	@NonNull
	@Builder.Default
	String dbUsername = PROP_DB_USERNAME_DEFAULT;

	@NonNull
	@Builder.Default
	String dbPassword = PROP_DB_PASSWORD_DEFAULT;

	boolean dryRunMode;
	boolean skipExecutingAfterScripts;

	@NonNull
	@Builder.Default
	ImmutableSet<Label> labels = Label.ofCommaSeparatedString(PROP_LABELS_DEFAULT);

	public enum OnScriptFailure
	{
		ASK, FAIL;
	}

	@NonNull
	@Builder.Default
	OnScriptFailure onScriptFailure = OnScriptFailure.ASK;
}
