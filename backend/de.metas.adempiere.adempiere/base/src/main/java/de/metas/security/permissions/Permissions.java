package de.metas.security.permissions;

 


import java.util.Collection;

import com.google.common.base.Optional;

/**
 * Collection of {@link Permission}s.
 * 
 * Implementations of this interface could be window permissions, process permissions etc.
 * 
 * @author tsa
 *
 * @param <PermissionType>
 */
public interface Permissions<PermissionType extends Permission>
{
	/** @return how many {@link Permission}s do we have */
	int size();

	Collection<PermissionType> getPermissionsList();

	/**
	 * Checks if requested access is granted for resource.
	 * 
	 * To find the resource's permission, {@link #getPermissionOrDefault(Resource)} will be used.
	 * 
	 * @param resource
	 * @param access requested access
	 * @return true if access is granted.
	 */
	boolean hasAccess(Resource resource, Access access);

	/**
	 * Checks if given permision is contained in our permissions list.
	 * 
	 * @param permission
	 * @return true if we already have that permission.
	 */
	boolean hasPermission(Permission permission);

	/**
	 * Gets the actual permision of given resource, if any.
	 * 
	 * @param resource
	 * @return actual resource's permision or absent.
	 */
	Optional<PermissionType> getPermissionIfExists(Resource resource);

	/**
	 * Gets the permission of given resource.
	 * 
	 * It will check and return (in this order):
	 * <ul>
	 * <li>actual resource permision: permision for given resource, if any
	 * <li>default permision: permision of {@link #noPermission()}'s resource, if any
	 * <li>no permision: {@link #noPermission()}, if any
	 * <li><code>null</code>, in case the {@link #noPermission()} is returning <code>null</code>
	 * </ul>
	 * 
	 * @param resource
	 * @return resource permission or default permission or <code>null</code>
	 */
	PermissionType getPermissionOrDefault(Resource resource);

}
