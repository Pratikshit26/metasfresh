package de.metas.security.permissions;

 


import org.junit.Assert;
import org.junit.Ignore;

/**
 * Permissions related assertion utilities.
 * 
 * @author tsa
 */
@Ignore
public final class PermissionAsserts
{
	public static void assertAccess(final Permissions<?> permissions, final Resource resource, final Access access, final boolean expected)
	{
		Assert.assertEquals("" + access + " to resource " + resource,
				expected,
				permissions.hasAccess(resource, access));
	}

}
