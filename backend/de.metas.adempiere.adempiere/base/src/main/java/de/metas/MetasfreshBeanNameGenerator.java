package de.metas;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

public class MetasfreshBeanNameGenerator extends AnnotationBeanNameGenerator
{
	@Override
	protected String buildDefaultBeanName(@NonNull final BeanDefinition definition)
	{
		if (isMetasfreshPackage(definition))
		{
			return extractFullyQualifiedBeanClassName(definition);
		}

		// Fallback to standard way of naming beans
		// i.e. simple class name, first letter lower case
		return super.buildDefaultBeanName(definition);
	}

	private boolean isMetasfreshPackage(@NonNull final BeanDefinition definition)
	{
		final String beanClassName = definition.getBeanClassName();

		return beanClassName.startsWith("de.metas")
				|| beanClassName.startsWith("org.adempiere")
				|| beanClassName.startsWith("org.eevolution");
	}

	private static String extractFullyQualifiedBeanClassName(final BeanDefinition definition)
	{
		return definition.getBeanClassName();
	}
}
