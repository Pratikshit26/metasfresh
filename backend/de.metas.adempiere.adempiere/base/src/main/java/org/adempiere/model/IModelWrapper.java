package org.adempiere.model;

 


/**
 * To be implemented by classes which are wrapping a database model.
 * 
 * Methods like {@link InterfaceWrapperHelper#save(Object)} will automatically handle those records.
 * 
 * @author tsa
 *
 */
public interface IModelWrapper
{
	/** @return underlying model that was wrapped */
	Object getModel();
}
