package de.metas.report.util;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*
 * #%L
 * report-service
     
 * #L%
 */

@UtilityClass
public final class DevelopmentWorkspaceJasperDirectoriesFinder
{
	public static List<File> getReportsDirectoriesForWorkspace(final File workspaceDir)
	{
		if (!workspaceDir.isDirectory())
		{
			return ImmutableList.of();
		}

		final File[] projectDirs = workspaceDir.listFiles(File::isDirectory);

		final ArrayList<File> result = new ArrayList<>();

		// First consider all other projects, but not metasfresh.
		for (final File projectDir : projectDirs)
		{
			if (!isMetasfreshRepository(projectDir))
			{
				result.addAll(getReportsDirectories(projectDir));
			}
		}

		// Consider metasfresh projects
		for (final File projectDir : projectDirs)
		{
			if (isMetasfreshRepository(projectDir))
			{
				final File metasfreshBackend = new File(projectDir, "backend");
				if (isProjectDir(metasfreshBackend))
				{
					result.addAll(getReportsDirectories(metasfreshBackend));
				}
			}
		}

		return result;
	}

	private static List<File> getReportsDirectories(@NonNull final File dir)
	{
		final ArrayList<File> result = new ArrayList<>();

		if (isIgnore(dir))
		{
			return result;
		}

		if (isProjectDir(dir))
		{
			final File reportsDir = getReportsDirOfProjectDirIfExists(dir);
			if (reportsDir != null)
			{
				result.add(reportsDir);
			}
		}

		for (final File subProjectDir : dir.listFiles(DevelopmentWorkspaceJasperDirectoriesFinder::isProjectDir))
		{
			result.addAll(getReportsDirectories(subProjectDir));
		}

		return result;
	}

	@Nullable
	private static File getReportsDirOfProjectDirIfExists(final File projectDir)
	{
		final File reportsDir = new File(projectDir, "src//main//jasperreports");
		if (reportsDir.exists() && reportsDir.isDirectory())
		{
			return reportsDir;
		}
		else
		{
			return null;
		}
	}

	private static boolean isProjectDir(final File dir)
	{
		return dir.isDirectory()
				&& new File(dir, "pom.xml").exists()
				&& !isIgnore(dir);
	}

	private static boolean isIgnore(final File dir)
	{
		return new File(dir, ".ignore-reports").exists();
	}

	private static boolean isMetasfreshRepository(@NonNull final File projectDir)
	{
		return "metasfresh".equals(projectDir.getName())
				&& projectDir.isDirectory();
	}
}
