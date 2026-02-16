package de.metas.security;

import static org.assertj.core.api.Assertions.assertThat;

import de.metas.common.util.time.SystemTime;
import org.adempiere.service.ClientId;
import org.junit.Test;

import de.metas.user.UserId;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

public class UserRolePermissionsKeyTest
{
	@Test
	public void test()
	{
		testToFromString(UserRolePermissionsKey.of(
				RoleId.ofRepoId(1),
				UserId.ofRepoId(2),
				ClientId.ofRepoId(3),
				SystemTime.asLocalDate()));
	}

	public void testToFromString(final UserRolePermissionsKey key)
	{
		final String str = key.toPermissionsKeyString();
		final UserRolePermissionsKey keyDeserialized = UserRolePermissionsKey.fromString(str);
		assertThat(keyDeserialized).isEqualTo(key);
	}
}
