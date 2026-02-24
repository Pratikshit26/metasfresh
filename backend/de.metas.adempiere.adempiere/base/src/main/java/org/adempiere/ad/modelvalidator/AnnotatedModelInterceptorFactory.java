package org.adempiere.ad.modelvalidator;

 


import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;

/**
 * Factory class which is able to create and bind(register) annotated model validators
 * 
 * @author tsa
 * 
 */
public class AnnotatedModelInterceptorFactory
{
	private static final AnnotatedModelInterceptorFactory instance = new AnnotatedModelInterceptorFactory();

	public static AnnotatedModelInterceptorFactory get()
	{
		return instance;
	}

	/**
	 * Creates a {@link ModelValidator} object for given annotated class.
	 * 
	 * This method is not checking if the annotatedObject was already registered.
	 * 
	 * @param annotatedObject
	 * @return {@link ModelValidator} or null if the given object is not a valid annotated model validator or it has no pointcuts
	 * @throws AdempiereException
	 *             if annotations were not correctly used
	 */
	public IModelInterceptor createModelInterceptor(Object annotatedObject)
	{
		return createAnnotatedModelInterceptor(annotatedObject);
	}

	private final AnnotatedModelInterceptor createAnnotatedModelInterceptor(Object annotatedObject)
	{
		final AnnotatedModelInterceptor validator = new AnnotatedModelInterceptor(annotatedObject);
		if (validator.isEmpty())
		{
			return null;
		}

		return validator;
	}
}
