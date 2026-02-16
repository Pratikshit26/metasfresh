package de.metas.costing;

import de.metas.organization.OrgId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
     
 * #L%
 */

public class CostingLevelTest
{
	@Test
	public void test_ClientLevel_effectiveValue()
	{
		assertThat(CostingLevel.Client.effectiveValue(OrgId.ANY)).isEqualTo(OrgId.ANY);
		assertThat(CostingLevel.Client.effectiveValue(OrgId.ofRepoId(123))).isEqualTo(OrgId.ANY);

		assertThat(CostingLevel.Client.effectiveValue(AttributeSetInstanceId.NONE)).isEqualTo(AttributeSetInstanceId.NONE);
		assertThat(CostingLevel.Client.effectiveValue(AttributeSetInstanceId.ofRepoId(123))).isEqualTo(AttributeSetInstanceId.NONE);
	}

	@Test
	public void test_OrgLevel_effectiveValue_AnyOrg()
	{
		assertThatThrownBy(() -> CostingLevel.Organization.effectiveValue(OrgId.ANY))
				.isInstanceOf(AdempiereException.class)
				.hasMessageStartingWith("Regular organization expected when costing level is Organization");
	}

	@Test
	public void test_OrgLevel_effectiveValue()
	{
		// Assert.assertEquals(OrgId.ANY, CostingLevel.Organization.effectiveValue(OrgId.ANY));
		assertThat(CostingLevel.Organization.effectiveValue(OrgId.ofRepoId(123))).isEqualTo(OrgId.ofRepoId(123));

		assertThat(CostingLevel.Organization.effectiveValue(AttributeSetInstanceId.NONE)).isEqualTo(AttributeSetInstanceId.NONE);
		assertThat(CostingLevel.Organization.effectiveValue(AttributeSetInstanceId.ofRepoId(123))).isEqualTo(AttributeSetInstanceId.NONE);
	}

	@Test
	public void test_BatchLotLevel_effectiveValue_NoASI()
	{
		assertThatThrownBy(() -> CostingLevel.BatchLot.effectiveValue(AttributeSetInstanceId.NONE))
				.isInstanceOf(AdempiereException.class)
				.hasMessageStartingWith("Regular ASI expected when costing level is Batch/Lot");
	}

	@Test
	public void test_BatchLotLevel_effectiveValue()
	{
		assertThat(CostingLevel.BatchLot.effectiveValue(OrgId.ANY)).isEqualTo(OrgId.ANY);
		assertThat(CostingLevel.BatchLot.effectiveValue(OrgId.ofRepoId(123))).isEqualTo(OrgId.ANY);

		// Assert.assertEquals(AttributeSetInstanceId.NONE, CostingLevel.BatchLot.effectiveValue(AttributeSetInstanceId.NONE));
		assertThat(CostingLevel.BatchLot.effectiveValue(AttributeSetInstanceId.ofRepoId(123))).isEqualTo(AttributeSetInstanceId.ofRepoId(123));
	}
}
