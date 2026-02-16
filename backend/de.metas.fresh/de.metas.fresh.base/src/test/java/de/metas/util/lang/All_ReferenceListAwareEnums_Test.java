package de.metas.util.lang;

import com.google.common.base.Stopwatch;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.fresh.base
     
 * #L%
 */

public class All_ReferenceListAwareEnums_Test
{
	@ParameterizedTest
	@ArgumentsSource(ReferenceListAwareEnumArgumentsProvider.class)
	public void testClass(final Class<? extends ReferenceListAwareEnum> clazz)
	{
		if (clazz.isInterface())
		{
			return; // nothing to do
		}

		final Set<? extends ReferenceListAwareEnum> values = ReferenceListAwareEnums.values(clazz);
		assertThat(values).isNotEmpty();

		for (final ReferenceListAwareEnum valueExpected : values)
		{
			assertThat(ReferenceListAwareEnums.ofCode(valueExpected.getCode(), clazz)).isSameAs(valueExpected);
		}
	}

	//
	//
	//
	//
	//

	public static class ReferenceListAwareEnumArgumentsProvider implements ArgumentsProvider
	{
		@Override
		public Stream<? extends Arguments> provideArguments(final ExtensionContext context)
		{
			return provideClasses().map(Arguments::of);
		}

		private Stream<Class<? extends ReferenceListAwareEnum>> provideClasses()
		{
			final Stopwatch stopwatch = Stopwatch.createStarted();

			final Reflections reflections = new Reflections(new ConfigurationBuilder()
					.addUrls(ClasspathHelper.forClassLoader())
					//thx to https://github.com/ronmamo/reflections/issues/373#issue-1080637248
					.forPackages("de")
					.setScanners(new SubTypesScanner()));

			final Set<Class<? extends ReferenceListAwareEnum>> classes = reflections.getSubTypesOf(ReferenceListAwareEnum.class);

			stopwatch.stop();
			System.out.println("Found " + classes.size() + " classes implementing " + ReferenceListAwareEnum.class + ". Took " + stopwatch + ". ");

			if (classes.isEmpty())
			{
				throw new RuntimeException("No classes found. Might be because for some reason Reflections does not work correctly with maven surefire plugin."
						+ "\n See https://github.com/metasfresh/metasfresh/issues/4773.");
			}

			return classes.stream()
					.sorted(Comparator.comparing(Class::getName));
		}
	}
}
