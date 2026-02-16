package de.metas.organization.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import de.metas.organization.OrgId;
import de.metas.organization.OrgIdNotFoundException;
import de.metas.organization.OrgQuery;
import de.metas.organization.impl.OrgDAO;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

class OrgDAOTest
{

	private OrgDAO orgDAO;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		orgDAO = new OrgDAO();
	}

	@Test
	void retrieveOrgIdBy_non_existing_fail()
	{
		final OrgQuery query = OrgQuery
				.builder()
				.failIfNotExists(true)
				.orgValue("orgValue")
				.build();

		assertThrows(OrgIdNotFoundException.class, () -> orgDAO.retrieveOrgIdBy(query));
	}

	@Test
	void retrieveOrgIdBy_non_existing_dont_fail()
	{
		final OrgQuery query = OrgQuery
				.builder()
				.failIfNotExists(false)
				.orgValue("orgValue")
				.build();

		final Optional<OrgId> result = orgDAO.retrieveOrgIdBy(query);
		assertThat(result).isEmpty();
	}

}
