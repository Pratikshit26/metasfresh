package de.metas.security.permissions.record_access;

import com.google.common.collect.ImmutableList;
import de.metas.event.log.EventLogService;
import de.metas.event.log.EventLogsRepository;
import de.metas.security.Principal;
import de.metas.security.permissions.Access;
import de.metas.user.UserGroupRepository;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

@ExtendWith(AdempiereTestWatcher.class)
public class UserGroupRecordAccessServiceTest
{
	private static final PermissionIssuer ISSUER = PermissionIssuer.ofCode("bla");
	private static final Principal USER_ID = Principal.userId(UserId.ofRepoId(1));

	private RecordAccessService userGroupRecordAccessService;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(new EventLogService(mock(EventLogsRepository.class)));

		userGroupRecordAccessService = new RecordAccessService(
				new RecordAccessRepository(),
				new RecordAccessConfigService(Optional.empty()),
				new UserGroupRepository());
	}

	@SuppressWarnings("SameParameterValue")
	private RecordAccess grantAccessAndTest(@NonNull final TableRecordReference recordRef, final Access permission)
	{
		final ImmutableList<RecordAccess> recordAcceses = grantAccessesAndTest(recordRef, permission);
		assertThat(recordAcceses).hasSize(1);
		return recordAcceses.get(0);
	}

	private ImmutableList<RecordAccess> grantAccessesAndTest(@NonNull final TableRecordReference recordRef, final Access... permissions)
	{
		Check.assumeNotEmpty(permissions, "permissions is not empty");

		userGroupRecordAccessService.grantAccess(RecordAccessGrantRequest.builder()
				.recordRef(recordRef)
				.principal(USER_ID)
				.permissions(Arrays.asList(permissions))
				.issuer(ISSUER)
				.requestedBy(UserId.ofRepoId(111))
				.build());

		final ImmutableList<RecordAccess> accessRecords = userGroupRecordAccessService.getAccessesByRecordAndIssuer(recordRef, ISSUER);
		assertThat(accessRecords).hasSize(permissions.length);

		for (int i = 0; i < accessRecords.size(); i++)
		{
			final RecordAccess accessRecord = accessRecords.get(i);
			final Access expectedPermission = permissions[i];

			assertThat(accessRecord.getRecordRef()).isEqualTo(recordRef);
			assertThat(accessRecord.getPrincipal()).isEqualTo(USER_ID);
			assertThat(accessRecord.getPermission()).isEqualTo(expectedPermission);
			assertThat(accessRecord.getIssuer()).isEqualTo(ISSUER);
			assertThat(accessRecord.getCreatedBy()).isEqualTo(UserId.ofRepoId(111));

			assertThat(accessRecord.getId()).isNotNull();
			assertThat(accessRecord.getRootId()).isEqualTo(accessRecord.getId());
		}

		return accessRecords;
	}

	@Test
	public void grantAccess_READ_and_WRITE()
	{
		final TableRecordReference order = TableRecordReference.of("C_Order", 123);
		grantAccessesAndTest(order, Access.READ, Access.WRITE);
	}
}
