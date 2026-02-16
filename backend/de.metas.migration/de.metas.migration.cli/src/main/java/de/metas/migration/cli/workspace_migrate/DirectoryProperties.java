package de.metas.migration.cli.workspace_migrate;

import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Properties;

/*
 * #%L
 * de.metas.migration.cli
     
 * #L%
 */

@Value
@Builder
public class DirectoryProperties
{

	public static final String PROPERTIES_FILENAME = ".workspace-sql-scripts.properties";

	public static DirectoryProperties ofDirectory(final File directory)
	{
		if (!directory.isDirectory())
		{
			return NONE;
		}

		final File propertiesFile = new File(directory, PROPERTIES_FILENAME);
		if (!propertiesFile.exists())
		{
			return NONE;
		}

		final Properties properties = new Properties();
		try (FileInputStream in = new FileInputStream(propertiesFile))
		{
			properties.load(in);
		}
		catch (final Exception e)
		{
			throw new RuntimeException("Failed loading " + propertiesFile, e);
		}

		return DirectoryProperties.builder()
				.labels(Label.ofCommaSeparatedString(properties.getProperty("labels", "")))
				.build();
	}

	public static final DirectoryProperties NONE = DirectoryProperties.builder().build();

	@NonNull @Singular ImmutableSet<Label> labels;

	public DirectoryProperties mergeFromParent(@NonNull final DirectoryProperties parent)
	{
		if (this == NONE)
		{
			return parent;
		}
		if (parent == NONE)
		{
			return this;
		}

		return DirectoryProperties.builder()
				.labels(labels)
				.labels(parent.labels)
				.build();
	}

	public boolean hasAnyOfLabels(final Collection<Label> labelsToCheck)
	{
		if (labelsToCheck.isEmpty())
		{
			return false;
		}
		if (labels.isEmpty())
		{
			return false;
		}

		for (final Label label : labelsToCheck)
		{
			if (labels.contains(label))
			{
				return true;
			}
		}

		return false;
	}
}
