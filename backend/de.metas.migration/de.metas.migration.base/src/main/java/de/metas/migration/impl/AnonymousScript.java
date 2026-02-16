package de.metas.migration.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import de.metas.migration.IScript;
import de.metas.migration.ScriptType;
import de.metas.migration.util.FileUtils;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/*
 * #%L
 * de.metas.migration.base
     
 * #L%
 */

@ToString(of = { "scriptContent" })
public class AnonymousScript implements IScript
{
	@Getter
	private final String projectName;
	@Getter
	private final String moduleName;
	@Getter
	private final String fileName;
	@Getter
	private final ScriptType type;

	private final String scriptContent;
	private File localFile;

	@Getter
	@Setter
	private long lastDurationMillis = -1;

	@Builder
	private AnonymousScript(
			@NonNull final String fileName,
			@NonNull final String scriptContent,
			final String projectName,
			final String moduleName)
	{
		this.projectName = projectName != null ? projectName : "000";
		this.moduleName = moduleName != null ? moduleName : "000";
		this.fileName = fileName;
		type = FileUtils.getScriptTypeByFilename(fileName);
		this.scriptContent = scriptContent;
	}

	@Override
	public File getLocalFile()
	{
		File localFile = this.localFile;
		if (localFile == null)
		{
			localFile = this.localFile = createLocalFile();
		}
		return localFile;
	}

	private File createLocalFile()
	{
		final InputStream in = new ByteArrayInputStream(scriptContent.getBytes(StandardCharsets.UTF_8));
		return FileUtils.createLocalFile(getFileName(), in);
	}
}
