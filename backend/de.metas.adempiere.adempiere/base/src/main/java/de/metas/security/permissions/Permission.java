package de.metas.security.permissions;

 


/**
 * Defines a particular access to a resource.
 * 
 * e.g. read write access to window 12345.
 * 
 * @author tsa
 *
 */
public interface Permission
{
	/**
	 * @return resource for whom the permission is defined; never returns <code>null</code>.
	 */
	Resource getResource();

	/**
	 * Creates a new {@link Permission} by merging given access into this access.
	 * 
	 * @param accessFrom
	 * @return merged access
	 */
	Permission mergeWith(Permission accessFrom);

	/**
	 * Checks if this permission has the given access.
	 * 
	 * e.g. check if the table permission for a particular table resouce has the access of type "Write".
	 * 
	 * @param access
	 * @return true if required access is granted.
	 */
	boolean hasAccess(Access access);
}
